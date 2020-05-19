package com.example.panaderosvm.utils

import android.content.Context
import android.os.Build
import com.scottyab.rootbeer.RootBeer
import org.abstractj.kalium.NaCl
import org.abstractj.kalium.NaCl.Sodium.CRYPTO_BOX_CURVE25519XSALSA20POLY1305_SECRETKEYBYTES
import org.abstractj.kalium.crypto.Hash
import org.abstractj.kalium.crypto.Password
import org.abstractj.kalium.crypto.SealedBox
import org.abstractj.kalium.crypto.SecretBox
import org.abstractj.kalium.encoders.Encoder.HEX
import org.abstractj.kalium.keys.KeyPair
import org.abstractj.kalium.keys.SigningKey
import org.abstractj.kalium.keys.VerifyKey
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.security.SecureRandom
import kotlin.random.Random

fun rootCheck(context: Context?): Boolean {
    var result: Boolean = false
    if (isDeviceRooted()) {
        result = true
    }
    val rootBeer = RootBeer(context)
    if (rootBeer.isRooted) {
        result = true
    }
    if (rootBeer.isRootedWithoutBusyBoxCheck) {
        result = true
    }
    return result
}

fun isDeviceRooted(): Boolean {
    return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
}

private fun checkRootMethod1(): Boolean {
    val buildTags = Build.TAGS
    return buildTags != null && buildTags.contains("test-keys")
}

private fun checkRootMethod2(): Boolean {
    val paths = arrayOf(
        "/system/app/Superuser.apk",
        "/sbin/su",
        "/system/bin/su",
        "/system/xbin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su",
        "/system/sd/xbin/su",
        "/system/bin/failsafe/su",
        "/data/local/su",
        "/su/bin/su"
    )
    for (path in paths) {
        if (File(path).exists()) return true
    }
    return false
}

private fun checkRootMethod3(): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime()
            .exec(arrayOf("/system/xbin/which", "su"))
        val `in` = BufferedReader(InputStreamReader(process.inputStream))
        if (`in`.readLine() != null) true else false
    } catch (t: Throwable) {
        false
    } finally {
        process?.destroy()
    }
}

fun hasEmulatorBuildProp(): Boolean {

    return (Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || Build.PRODUCT.contains("google_sdk")
            || Build.PRODUCT.contains("sdk")
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.BOARD.contains("unknown")
            || Build.ID.contains("FRF91")
            || Build.MANUFACTURER.contains("unknown")
            || Build.USER.contains("android-build"))
}

fun EncryptSecure()
{
    val hash = Hash()
    val password = Password()

    val DATA: String = "yo mamma"

    val PWHASH_MESSAGE: String = "yo mamma"
    val PWHASH_SALT: String  = "[<~A 32-bytes salt for scrypt~>]"

    val tb = hash.sha512(DATA.toByteArray())
    println("SHA512 HASH:")
    println (HEX.encode(tb))

    val gb = hash.blake2(DATA.toByteArray())
    println("Blake2 Hash:")
    println (HEX.encode(gb))

    val opslimit: Int = NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE
    val memlimit: Int = NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE

    val pw = password.hash(PWHASH_MESSAGE.toByteArray(),
        HEX,
        PWHASH_SALT.toByteArray(),
        opslimit,
        memlimit.toLong());

    println ("Scrypt hashed password:")
    println (pw)


//    random bytes
    val rb = Random.nextBytes(64)
    println("Random Bytes:")
    println (HEX.encode(rb))

//    random int

    var r = SecureRandom()
    println("Some Random Numbers:")
    println(r.nextInt(10000))
    println(r.nextInt(10000))
    println(r.nextInt(10000))


    val secretKey: String = "1b27556473e985d462cd51197a9a46c76009549eac6474f206c4ee0844f68389"
    val boxNonce: String = "69696ee955b62b73cd62bda875fc73d68219e0036b7a0b37"
    val yomamma: String = "yo mamma"

    var box = SecretBox(secretKey, HEX)
    val nonce: ByteArray = HEX.decode(boxNonce)

    val message = yomamma.toByteArray()
    val ciphertext = box.encrypt(nonce, message)
    println("(Symmetric Key) Encrypted")
    println(HEX.encode(ciphertext))

    val cleartext = box.decrypt(nonce, ciphertext)
    println("(Symmetric Key) Decrypted")
    val omsg: String = String(cleartext)
    println(omsg)


    val aMessage: String = "yo mamma"
    val m: ByteArray = aMessage.toByteArray()
    var nkeyPair: KeyPair = KeyPair(ByteArray(CRYPTO_BOX_CURVE25519XSALSA20POLY1305_SECRETKEYBYTES));

    val nsk: ByteArray = nkeyPair.getPrivateKey().toBytes()
    val npk: ByteArray = nkeyPair.getPublicKey().toBytes()

    println("pk:")
    println(HEX.encode(npk))

    //encrypt
    var sb = SealedBox(npk)
    val c: ByteArray = sb.encrypt(m);

    println("Encrypted (Asymmetric Keys)")
    println(HEX.encode(c))

    //decrypt
    var sb2 = SealedBox(npk, nsk)
    val m2: ByteArray = sb2.decrypt(c)

    println("Decrypted (Asymmetric Keys)")
    println(String(m2))





    var tkeyPair = KeyPair()

    val tsk: ByteArray = tkeyPair.privateKey.toBytes()
    val tpk: ByteArray = tkeyPair.publicKey.toBytes()

    println("generated Secret Key (sk):")
    println(HEX.encode(tsk))
    println("Generated Public Key (pk):")
    println(HEX.encode(tpk))



    val controllerPk = "28ece8b8a0d1717fc009459f742015d169bf620992924bb11f6873bee2251b30"
    val myPk = "96f1eefb3c837972e9e4a2a03b516104529b0d0e96a760f58114ac82bd898b39"
    val myVk = "f09044aada58127fda6c8522f16be55ddb9e50b8132ad973306ff46ab94ef173"
    val pk: ByteArray = HEX.decode(myPk)
    val opk: ByteArray = HEX.decode(controllerPk)
    val mySk = "433d9cbadf8f72219e3d08d406918a02366d585f1419a4777e8ae84fe6f53210"
    val sk: ByteArray = HEX.decode(mySk)

    val signkey = SigningKey(mySk, HEX)
    val verifykey =  signkey.verifyKey
    println ("Verify Signing Key:" + HEX.encode(verifykey.toBytes()))

    var msg: String = "yo mamma"
    var xmsg: String = HEX.encode(msg.toByteArray())
    val signature = signkey.sign(xmsg, HEX)
    val vxkey = VerifyKey(myVk,HEX)
    println("Detached Signature:")
    println(signature)
    val check = vxkey.verify(xmsg, signature, HEX)
    if (check)
    {
        println("Signature Verified")
    } else {
        println("Signature failed")
    }
}