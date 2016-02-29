/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.virgilsecurity.crypto;

public class VirgilKeyPair implements java.lang.AutoCloseable {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected VirgilKeyPair(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(VirgilKeyPair obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        virgil_crypto_javaJNI.delete_VirgilKeyPair(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
    delete();
  }

  public static VirgilKeyPair generate(VirgilKeyPair.Type type, byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_generate__SWIG_0(type.swigValue(), pwd), true);
  }

  public static VirgilKeyPair generate(VirgilKeyPair.Type type) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_generate__SWIG_1(type.swigValue()), true);
  }

  public static VirgilKeyPair generate() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_generate__SWIG_2(), true);
  }

  public static VirgilKeyPair ecNist192(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist192__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecNist192() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist192__SWIG_1(), true);
  }

  public static VirgilKeyPair ecNist224(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist224__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecNist224() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist224__SWIG_1(), true);
  }

  public static VirgilKeyPair ecNist256(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist256__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecNist256() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist256__SWIG_1(), true);
  }

  public static VirgilKeyPair ecNist384(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist384__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecNist384() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist384__SWIG_1(), true);
  }

  public static VirgilKeyPair ecNist521(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist521__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecNist521() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecNist521__SWIG_1(), true);
  }

  public static VirgilKeyPair ecBrainpool256(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecBrainpool256__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecBrainpool256() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecBrainpool256__SWIG_1(), true);
  }

  public static VirgilKeyPair ecBrainpool384(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecBrainpool384__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecBrainpool384() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecBrainpool384__SWIG_1(), true);
  }

  public static VirgilKeyPair ecBrainpool512(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecBrainpool512__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecBrainpool512() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecBrainpool512__SWIG_1(), true);
  }

  public static VirgilKeyPair ecKoblitz192(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecKoblitz192__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecKoblitz192() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecKoblitz192__SWIG_1(), true);
  }

  public static VirgilKeyPair ecKoblitz224(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecKoblitz224__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecKoblitz224() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecKoblitz224__SWIG_1(), true);
  }

  public static VirgilKeyPair ecKoblitz256(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecKoblitz256__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair ecKoblitz256() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_ecKoblitz256__SWIG_1(), true);
  }

  public static VirgilKeyPair rsa256(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa256__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair rsa256() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa256__SWIG_1(), true);
  }

  public static VirgilKeyPair rsa512(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa512__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair rsa512() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa512__SWIG_1(), true);
  }

  public static VirgilKeyPair rsa1024(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa1024__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair rsa1024() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa1024__SWIG_1(), true);
  }

  public static VirgilKeyPair rsa2048(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa2048__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair rsa2048() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa2048__SWIG_1(), true);
  }

  public static VirgilKeyPair rsa4096(byte[] pwd) {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa4096__SWIG_0(pwd), true);
  }

  public static VirgilKeyPair rsa4096() {
    return new VirgilKeyPair(virgil_crypto_javaJNI.VirgilKeyPair_rsa4096__SWIG_1(), true);
  }

  public static boolean isKeyPairMatch(byte[] publicKey, byte[] privateKey, byte[] privateKeyPassword) {
    return virgil_crypto_javaJNI.VirgilKeyPair_isKeyPairMatch__SWIG_0(publicKey, privateKey, privateKeyPassword);
  }

  public static boolean isKeyPairMatch(byte[] publicKey, byte[] privateKey) {
    return virgil_crypto_javaJNI.VirgilKeyPair_isKeyPairMatch__SWIG_1(publicKey, privateKey);
  }

  public static boolean checkPrivateKeyPassword(byte[] key, byte[] pwd) {
    return virgil_crypto_javaJNI.VirgilKeyPair_checkPrivateKeyPassword(key, pwd);
  }

  public static boolean isPrivateKeyEncrypted(byte[] privateKey) {
    return virgil_crypto_javaJNI.VirgilKeyPair_isPrivateKeyEncrypted(privateKey);
  }

  public VirgilKeyPair(byte[] pwd) {
    this(virgil_crypto_javaJNI.new_VirgilKeyPair__SWIG_0(pwd), true);
  }

  public VirgilKeyPair() {
    this(virgil_crypto_javaJNI.new_VirgilKeyPair__SWIG_1(), true);
  }

  public VirgilKeyPair(byte[] publicKey, byte[] privateKey) {
    this(virgil_crypto_javaJNI.new_VirgilKeyPair__SWIG_2(publicKey, privateKey), true);
  }

  public byte[] publicKey() {
    return virgil_crypto_javaJNI.VirgilKeyPair_publicKey(swigCPtr, this);
  }

  public byte[] privateKey() {
    return virgil_crypto_javaJNI.VirgilKeyPair_privateKey(swigCPtr, this);
  }

  public VirgilKeyPair(VirgilKeyPair other) {
    this(virgil_crypto_javaJNI.new_VirgilKeyPair__SWIG_3(VirgilKeyPair.getCPtr(other), other), true);
  }

  public final static class Type {
    public final static VirgilKeyPair.Type Default = new VirgilKeyPair.Type("Default", virgil_crypto_javaJNI.VirgilKeyPair_Default_get());
    public final static VirgilKeyPair.Type RSA_256 = new VirgilKeyPair.Type("RSA_256");
    public final static VirgilKeyPair.Type RSA_512 = new VirgilKeyPair.Type("RSA_512");
    public final static VirgilKeyPair.Type RSA_1024 = new VirgilKeyPair.Type("RSA_1024");
    public final static VirgilKeyPair.Type RSA_2048 = new VirgilKeyPair.Type("RSA_2048");
    public final static VirgilKeyPair.Type RSA_3072 = new VirgilKeyPair.Type("RSA_3072");
    public final static VirgilKeyPair.Type RSA_4096 = new VirgilKeyPair.Type("RSA_4096");
    public final static VirgilKeyPair.Type RSA_8192 = new VirgilKeyPair.Type("RSA_8192");
    public final static VirgilKeyPair.Type EC_SECP192R1 = new VirgilKeyPair.Type("EC_SECP192R1");
    public final static VirgilKeyPair.Type EC_SECP224R1 = new VirgilKeyPair.Type("EC_SECP224R1");
    public final static VirgilKeyPair.Type EC_SECP256R1 = new VirgilKeyPair.Type("EC_SECP256R1");
    public final static VirgilKeyPair.Type EC_SECP384R1 = new VirgilKeyPair.Type("EC_SECP384R1");
    public final static VirgilKeyPair.Type EC_SECP521R1 = new VirgilKeyPair.Type("EC_SECP521R1");
    public final static VirgilKeyPair.Type EC_BP256R1 = new VirgilKeyPair.Type("EC_BP256R1");
    public final static VirgilKeyPair.Type EC_BP384R1 = new VirgilKeyPair.Type("EC_BP384R1");
    public final static VirgilKeyPair.Type EC_BP512R1 = new VirgilKeyPair.Type("EC_BP512R1");
    public final static VirgilKeyPair.Type EC_M221 = new VirgilKeyPair.Type("EC_M221");
    public final static VirgilKeyPair.Type EC_M255 = new VirgilKeyPair.Type("EC_M255");
    public final static VirgilKeyPair.Type EC_M383 = new VirgilKeyPair.Type("EC_M383");
    public final static VirgilKeyPair.Type EC_M511 = new VirgilKeyPair.Type("EC_M511");
    public final static VirgilKeyPair.Type EC_SECP192K1 = new VirgilKeyPair.Type("EC_SECP192K1");
    public final static VirgilKeyPair.Type EC_SECP224K1 = new VirgilKeyPair.Type("EC_SECP224K1");
    public final static VirgilKeyPair.Type EC_SECP256K1 = new VirgilKeyPair.Type("EC_SECP256K1");

    public final int swigValue() {
      return swigValue;
    }

    public String toString() {
      return swigName;
    }

    public static Type swigToEnum(int swigValue) {
      if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
        return swigValues[swigValue];
      for (int i = 0; i < swigValues.length; i++)
        if (swigValues[i].swigValue == swigValue)
          return swigValues[i];
      throw new IllegalArgumentException("No enum " + Type.class + " with value " + swigValue);
    }

    private Type(String swigName) {
      this.swigName = swigName;
      this.swigValue = swigNext++;
    }

    private Type(String swigName, int swigValue) {
      this.swigName = swigName;
      this.swigValue = swigValue;
      swigNext = swigValue+1;
    }

    private Type(String swigName, Type swigEnum) {
      this.swigName = swigName;
      this.swigValue = swigEnum.swigValue;
      swigNext = this.swigValue+1;
    }

    private static Type[] swigValues = { Default, RSA_256, RSA_512, RSA_1024, RSA_2048, RSA_3072, RSA_4096, RSA_8192, EC_SECP192R1, EC_SECP224R1, EC_SECP256R1, EC_SECP384R1, EC_SECP521R1, EC_BP256R1, EC_BP384R1, EC_BP512R1, EC_M221, EC_M255, EC_M383, EC_M511, EC_SECP192K1, EC_SECP224K1, EC_SECP256K1 };
    private static int swigNext = 0;
    private final int swigValue;
    private final String swigName;
  }

}
