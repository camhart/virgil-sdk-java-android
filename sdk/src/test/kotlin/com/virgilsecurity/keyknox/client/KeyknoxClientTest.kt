/*
 * Copyright (c) 2015-2020, Virgil Security, Inc.
 *
 * Lead Maintainer: Virgil Security Inc. <support@virgilsecurity.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     (1) Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *     (2) Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *     (3) Neither the name of virgil nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.virgilsecurity.keyknox.client

import com.virgilsecurity.crypto.foundation.Aes256Gcm
import com.virgilsecurity.crypto.foundation.RecipientCipher
import com.virgilsecurity.crypto.foundation.Sha512
import com.virgilsecurity.crypto.foundation.Signer
import com.virgilsecurity.keyknox.TestConfig
import com.virgilsecurity.keyknox.utils.base64Encode
import com.virgilsecurity.sdk.common.TimeSpan
import com.virgilsecurity.sdk.crypto.*
import com.virgilsecurity.sdk.jwt.JwtGenerator
import com.virgilsecurity.sdk.jwt.accessProviders.CachingJwtProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

class KeyknoxClientTest {

    private val identity = UUID.randomUUID().toString()
    private lateinit var root: String
    private lateinit var path: String
    private lateinit var key: String
    private lateinit var virgilCrypto: VirgilCrypto
    private lateinit var keyknoxClient: KeyknoxClientProtocol
    private lateinit var publicKey: VirgilPublicKey
    private lateinit var privateKey: VirgilPrivateKey

    @BeforeEach
    fun setup() {
        this.root = UUID.randomUUID().toString()
        this.path = UUID.randomUUID().toString()
        this.key = UUID.randomUUID().toString()
        this.virgilCrypto = VirgilCrypto(false)
        val keyPair = this.virgilCrypto.generateKeyPair(KeyPairType.ED25519)
        this.privateKey = keyPair.privateKey
        this.publicKey = keyPair.publicKey


        val jwtGenerator = JwtGenerator(TestConfig.appId, TestConfig.appPrivateKey, TestConfig.appPublicKeyId, TimeSpan.fromTime(600, TimeUnit.SECONDS),
                VirgilAccessTokenSigner(this.virgilCrypto))
        val tokenProvider = CachingJwtProvider(CachingJwtProvider.RenewJwtCallback { jwtGenerator.generateToken(identity) })
        this.keyknoxClient = KeyknoxClient(tokenProvider, URL(TestConfig.keyknoxServiceAddress))
    }

    @Test
    fun pushValue_v1() {
        // KTC-1
        val data = base64Encode(UUID.randomUUID().toString())

        val signer = Signer()
        signer.setHash(Sha512())

        signer.reset()
        signer.appendData(data)

        val signature = signer.sign(this.privateKey.privateKey)

        val cipher = RecipientCipher()
        cipher.setRandom(virgilCrypto.rng)
        cipher.setEncryptionCipher(Aes256Gcm())

        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature)

        cipher.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher.startEncryption()

        val meta = cipher.packMessageInfo()
        var encryptedData = cipher.processEncryption(data)
        encryptedData += cipher.finishEncryption()

        val pushedValue = this.keyknoxClient.pushValue(null, meta, encryptedData, null)
        assertArrayEquals(encryptedData, pushedValue.value)
        assertArrayEquals(meta, pushedValue.meta)
        assertEquals("1.0", pushedValue.version)
        assertNotNull(pushedValue.keyknoxHash)
        assertFalse(pushedValue.keyknoxHash.isEmpty())

        val pulledValue = this.keyknoxClient.pullValue(null)
        assertArrayEquals(encryptedData, pulledValue.value)
        assertArrayEquals(meta, pulledValue.meta)
        assertEquals("1.0", pulledValue.version)
        assertNotNull(pulledValue.keyknoxHash)
        assertFalse(pulledValue.keyknoxHash.isEmpty())
    }

    @Test
    fun pushValue_v2() {
        // KTC-1
        val data = base64Encode(UUID.randomUUID().toString())

        val signer = Signer()
        signer.setHash(Sha512())

        signer.reset()
        signer.appendData(data)

        val signature = signer.sign(this.privateKey.privateKey)

        val cipher = RecipientCipher()
        cipher.setRandom(virgilCrypto.rng)
        cipher.setEncryptionCipher(Aes256Gcm())

        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature)

        cipher.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher.startEncryption()

        val meta = cipher.packMessageInfo()
        var encryptedData = cipher.processEncryption(data)
        encryptedData += cipher.finishEncryption()

        val pushParams = KeyknoxPushParams(listOf(this.identity), this.root, this.path, this.key)

        val pushedValue = this.keyknoxClient.pushValue(pushParams, meta, encryptedData, null)
        assertArrayEquals(encryptedData, pushedValue.value)
        assertArrayEquals(meta, pushedValue.meta)
        assertEquals(this.root, pushedValue.root)
        assertEquals(this.path, pushedValue.path)
        assertEquals(this.key, pushedValue.key)
        assertNotNull(pushedValue.keyknoxHash)
        assertFalse(pushedValue.keyknoxHash.isEmpty())

        val pullParams = KeyknoxPullParams(this.identity, this.root, this.path, this.key)
        val pulledValue = this.keyknoxClient.pullValue(pullParams)
        assertArrayEquals(encryptedData, pulledValue.value)
        assertArrayEquals(meta, pulledValue.meta)
        assertEquals(this.root, pulledValue.root)
        assertEquals(this.path, pulledValue.path)
        assertEquals(this.key, pulledValue.key)
        assertNull(pulledValue.version)
        assertNotNull(pulledValue.keyknoxHash)
        assertFalse(pulledValue.keyknoxHash.isEmpty())
    }

    @Test
    fun pushValue_updateData_v1() {
        // KTC-2
        val data = base64Encode(UUID.randomUUID().toString())
        val data2 = base64Encode(UUID.randomUUID().toString())

        val signer = Signer()
        signer.setHash(Sha512())

        signer.reset()
        signer.appendData(data)

        val signature = signer.sign(this.privateKey.privateKey)

        val cipher = RecipientCipher()
        cipher.setRandom(virgilCrypto.rng)
        cipher.setEncryptionCipher(Aes256Gcm())

        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature)

        cipher.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher.startEncryption()

        val meta = cipher.packMessageInfo()
        var encryptedData = cipher.processEncryption(data)
        encryptedData += cipher.finishEncryption()

        val pushedValue = this.keyknoxClient.pushValue(null, meta, encryptedData, null)
        assertArrayEquals(encryptedData, pushedValue.value)

        signer.reset()
        signer.appendData(data2)

        val signature2 = signer.sign(this.privateKey.privateKey)

        val cipher2 = RecipientCipher()
        cipher2.setRandom(virgilCrypto.rng)
        cipher2.setEncryptionCipher(Aes256Gcm())

        cipher2.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher2.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature2)

        cipher2.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher2.startEncryption()

        val meta2 = cipher2.packMessageInfo()
        var encryptedData2 = cipher2.processEncryption(data2)
        encryptedData2 += cipher2.finishEncryption()

        val pushedValue2 = this.keyknoxClient.pushValue(null, meta2, encryptedData2, pushedValue.keyknoxHash)
        assertArrayEquals(encryptedData2, pushedValue2.value)
        assertArrayEquals(meta2, pushedValue2.meta)
        assertEquals("2.0", pushedValue2.version)
        assertNotNull(pushedValue2.keyknoxHash)
        assertFalse(pushedValue2.keyknoxHash.isEmpty())
    }

    @Test
    fun pushValue_updateData_v2() {
        // KTC-2
        val data = base64Encode(UUID.randomUUID().toString())
        val data2 = base64Encode(UUID.randomUUID().toString())

        val signer = Signer()
        signer.setHash(Sha512())

        signer.reset()
        signer.appendData(data)

        val signature = signer.sign(this.privateKey.privateKey)

        val cipher = RecipientCipher()
        cipher.setRandom(virgilCrypto.rng)
        cipher.setEncryptionCipher(Aes256Gcm())

        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature)

        cipher.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher.startEncryption()

        val meta = cipher.packMessageInfo()
        var encryptedData = cipher.processEncryption(data)
        encryptedData += cipher.finishEncryption()

        val pushParams = KeyknoxPushParams(listOf(this.identity), this.root, this.path, this.key)
        val pushedValue = this.keyknoxClient.pushValue(pushParams, meta, encryptedData, null)
        assertArrayEquals(encryptedData, pushedValue.value)

        signer.reset()
        signer.appendData(data2)

        val signature2 = signer.sign(this.privateKey.privateKey)

        val cipher2 = RecipientCipher()
        cipher2.setRandom(virgilCrypto.rng)
        cipher2.setEncryptionCipher(Aes256Gcm())

        cipher2.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher2.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature2)

        cipher2.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher2.startEncryption()

        val meta2 = cipher2.packMessageInfo()
        var encryptedData2 = cipher2.processEncryption(data2)
        encryptedData2 += cipher2.finishEncryption()

        val pushedValue2 = this.keyknoxClient.pushValue(pushParams, meta2, encryptedData2, pushedValue.keyknoxHash)
        assertArrayEquals(encryptedData2, pushedValue2.value)
        assertArrayEquals(meta2, pushedValue2.meta)
        assertEquals(this.root, pushedValue.root)
        assertEquals(this.path, pushedValue.path)
        assertEquals(this.key, pushedValue.key)
        assertNull(pushedValue2.version)
        assertNotNull(pushedValue2.keyknoxHash)
        assertFalse(pushedValue2.keyknoxHash.isEmpty())
    }



    @Test
    fun pullValue_empty_v1() {
        // KTC-3
        val pulledValue = this.keyknoxClient.pullValue(null)
        assertNotNull(pulledValue.value)
        assertTrue(pulledValue.value.isEmpty())
        assertNotNull(pulledValue.meta)
        assertTrue(pulledValue.meta.isEmpty())
        assertEquals("1.0", pulledValue.version)
    }

    @Test
    fun pullValue_empty_v2() {
        // KTC-3
        val pullParams = KeyknoxPullParams(this.identity, this.root, this.path, this.key)
        val pulledValue = this.keyknoxClient.pullValue(pullParams)
        assertNotNull(pulledValue.value)
        assertTrue(pulledValue.value.isEmpty())
        assertNotNull(pulledValue.meta)
        assertTrue(pulledValue.meta.isEmpty())
        assertNull(pulledValue.version)
    }

    @Test
    fun resetValue_v1() {
        // KTC-4
        val data = base64Encode(UUID.randomUUID().toString())
        val meta = base64Encode(UUID.randomUUID().toString())
        val pushedValue = this.keyknoxClient.pushValue(null, meta, data, null)
        assertArrayEquals(data, pushedValue.value)

        val resetValue = this.keyknoxClient.resetValue(null)
        assertNotNull(resetValue.value)
        assertTrue(resetValue.value.isEmpty())
        assertNotNull(resetValue.meta)
        assertTrue(resetValue.meta.isEmpty())
        assertEquals("2.0", resetValue.version)
    }

    @Test
    fun resetValue_v2() {
        // KTC-4
        val data = base64Encode(UUID.randomUUID().toString())
        val meta = base64Encode(UUID.randomUUID().toString())
        val pushParams = KeyknoxPushParams(listOf(this.identity), this.root, this.path, this.key)
        val pushedValue = this.keyknoxClient.pushValue(pushParams, meta, data, null)
        assertArrayEquals(data, pushedValue.value)

        val resetParams = KeyknoxResetParams(this.root, this.path, this.key)
        val resetValue = this.keyknoxClient.resetValue(resetParams)
        assertNotNull(resetValue.value)
        assertTrue(resetValue.value.isEmpty())
        assertNotNull(resetValue.meta)
        assertTrue(resetValue.meta.isEmpty())
        assertNull(resetValue.version)
    }

    @Test
    fun resetValue_empty_v1() {
        // KTC-5
        val resetValue = this.keyknoxClient.resetValue(null)
        assertNotNull(resetValue.value)
        assertTrue(resetValue.value.isEmpty())
        assertNotNull(resetValue.meta)
        assertTrue(resetValue.meta.isEmpty())
        assertEquals("1.0", resetValue.version)
    }

    @Test
    fun resetValue_empty_v2() {
        // KTC-5
        val resetParams = KeyknoxResetParams(this.root, this.path, this.key)
        val resetValue = this.keyknoxClient.resetValue(resetParams)
        assertNotNull(resetValue.value)
        assertTrue(resetValue.value.isEmpty())
        assertNotNull(resetValue.meta)
        assertTrue(resetValue.meta.isEmpty())
        assertNull(resetValue.version)
    }

    @Test
    fun getKeys() {
        val data = base64Encode(UUID.randomUUID().toString())

        val signer = Signer()
        signer.setHash(Sha512())

        signer.reset()
        signer.appendData(data)

        val signature = signer.sign(this.privateKey.privateKey)

        val cipher = RecipientCipher()
        cipher.setRandom(virgilCrypto.rng)
        cipher.setEncryptionCipher(Aes256Gcm())

        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature)

        cipher.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher.startEncryption()

        val meta = cipher.packMessageInfo()
        var encryptedData = cipher.processEncryption(data)
        encryptedData += cipher.finishEncryption()

        val pushParams = KeyknoxPushParams(listOf(this.identity), this.root, this.path, this.key)

        val pushedValue = this.keyknoxClient.pushValue(pushParams, meta, encryptedData, null)
        assertNotNull(pushedValue.keyknoxHash)

        val getKeysParams = KeyknoxGetKeysParams(this.identity, this.root, this.path)
        val keys = this.keyknoxClient.getKeys(getKeysParams)
        assertNotNull(keys)
        assertEquals(1, keys.size)
        assertEquals(this.key, keys.firstOrNull())
    }

    @Test
    fun deleteRecipient() {
        val data = base64Encode(UUID.randomUUID().toString())

        val signer = Signer()
        signer.setHash(Sha512())

        signer.reset()
        signer.appendData(data)

        val signature = signer.sign(this.privateKey.privateKey)

        val cipher = RecipientCipher()
        cipher.setRandom(virgilCrypto.rng)
        cipher.setEncryptionCipher(Aes256Gcm())

        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNER_ID, privateKey.identifier)
        cipher.customParams().addData(VirgilCrypto.CUSTOM_PARAM_SIGNATURE, signature)

        cipher.addKeyRecipient(publicKey.identifier, this.publicKey.publicKey)

        cipher.startEncryption()

        val meta = cipher.packMessageInfo()
        var encryptedData = cipher.processEncryption(data)
        encryptedData += cipher.finishEncryption()

        val pushParams = KeyknoxPushParams(listOf(this.identity), this.root, this.path, this.key)

        val pushedValue = this.keyknoxClient.pushValue(pushParams, meta, encryptedData, null)
        assertNotNull(pushedValue)

        val pullParams = KeyknoxPullParams(this.identity, this.root, this.path, this.key)
        val pulledValue = this.keyknoxClient.pullValue(pullParams)
        assertNotNull(pulledValue)

        val deleteParams = KeyknoxDeleteRecipientParams(this.identity, this.root, this.path, this.key)
        val deletedValue = this.keyknoxClient.deleteRecipient(deleteParams)
        assertNotNull(deletedValue)
        assertTrue(deletedValue.identities.isEmpty())
        assertEquals(this.root, deletedValue.root)
        assertEquals(this.path, deletedValue.path)
        assertEquals(this.key, deletedValue.key)

        val pulledValue2 = this.keyknoxClient.pullValue(pullParams)
        assertNotNull(pulledValue2)
        assertTrue(pulledValue2.identities.isEmpty())
    }
}
