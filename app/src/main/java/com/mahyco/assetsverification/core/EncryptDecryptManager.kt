package com.mahyco.rcbucounterboys2020.utils

import android.content.Context
import android.util.Base64
import android.util.Log
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.cmr_app.core.Constant
import com.mahyco.cmr_app.core.DLog
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptDecryptManager {

    private val uIdPass = "Mahyco@123"

    fun encryptData(value: String?, password: String): String {
        var result = ""
        try {
            val dataEncrypted = encrypt(
                password.toByteArray(charset("UTF-16LE")),
                value?.toByteArray(charset("UTF-16LE"))
            )
            result = dataEncrypted
        } catch (e: Exception) {
            Log.d("Exception", "MSG:" + e.message)
        }
        return result
    }

    fun decryptData(value: String?, password: String): String {
        var result = ""
        try {
            val dataDecrypted = decrypt(
                password,
                Base64.decode(
                    value?.toByteArray(charset("UTF-16LE")),
                    Base64.DEFAULT
                )
            )
            result = dataDecrypted
        } catch (e: Exception) {
            Log.d("Exception", "MSG:" + e.message)
        }
        return result
    }

    fun makeMD5WithRandom(data: String): String {
        val min = 10000000
        val max = 99999999
        val random = Random().nextInt(max - min + 1) + min
        return md5(data + random)
    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (ex: Exception) {
        }
        return ""
    }

    @Throws(Exception::class)
    fun encrypt(key: ByteArray?, clear: ByteArray?): String {
        val md = MessageDigest.getInstance("md5")
        val digestOfPassword = md.digest(key)
        val skeySpec =
            SecretKeySpec(digestOfPassword, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val encrypted = cipher.doFinal(clear)
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decrypt(key: String, encrypted: ByteArray?): String {
        val md = MessageDigest.getInstance("md5")
        val digestOfPassword = md.digest(key.toByteArray(charset("UTF-16LE")))
        val skeySpec = SecretKeySpec(digestOfPassword, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        val decrypted = cipher.doFinal(encrypted)
        val charset: Charset = Charsets.UTF_16LE
        return String(decrypted, charset)
    }

    fun encryptStringData(id: String?): String? {
        try {
            return encryptData(id, uIdPass)
        } catch (e: java.lang.Exception) {
            Log.d("Msg", e.message.toString())
        }
        return ""
    }

    fun decryptStringData(id: String?): String? {
        try {
            return decryptData(id, uIdPass)
        } catch (e: java.lang.Exception) {
            Log.d("Msg", e.message.toString())
        }
        return ""
    }

    fun saveUserCodeWithEncryption(userCode:String, context:Context){
        DLog.d("SAVE USER CODE : $userCode")
        val sharedPreference: SharedPreference = SharedPreference(context)
        val userCodeEncrypted = ""+EncryptDecryptManager.encryptStringData(userCode)
        DLog.d("SAVE USER CODE ENCRYPTED: $userCodeEncrypted")
        sharedPreference.save(Constant.USER_CODE,userCodeEncrypted )
        DLog.d("Decrypted USER CODE : "+""+ decryptStringData(userCodeEncrypted))

    }

    fun getDecryptedUserCode(mContext:Context):String{
        val sharedPreference: SharedPreference = SharedPreference(mContext)
        val encryptedUserCode = sharedPreference.getValueString(Constant.USER_CODE)
        DLog.d("Decrypted USER CODE : "+""+ decryptStringData(encryptedUserCode))
        return ""+ decryptStringData(encryptedUserCode)
    }
}