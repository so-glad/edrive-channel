package so.glad.channel.edrive.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Des {

	public static byte[] DES_IV={'a','b','c','d','1','2','3','4'};
	

	public static String DES_ALGORITHM_DES="DES";

	public static final String ALGORITHM_3DES_PKCS7 = "DESede/CBC/PKCS7Padding";
	
	public static final String chart_set = "utf-8";

	public static String encrypt(String plain, String charset,String hexKey, String hexIv, String algorithm)throws Exception {
		if (plain == null || charset == null ||
			hexKey == null || hexIv == null || algorithm == null) {
			log.info("encrypt() - one of input parameter is null, return empty string!");
			return "";
		}
		
		return ByteUtil.toHex(encrypt(plain.getBytes(charset),ByteUtil.hexToBytes(hexKey),ByteUtil.hexToBytes(hexIv),algorithm));
	}

	public static String decrypt(String hexCipher, String charset,String hexKey,String hexIv,String algorithm) {
		if (hexCipher == null || charset == null ||
			hexKey == null || hexIv == null || algorithm == null) {
			log.info("decrypt() - one of input parameter is null, return empty string!");
			return "";
		}

		try {
			return new String(decrypt(ByteUtil.hexToBytes(hexCipher),ByteUtil.hexToBytes(hexKey),ByteUtil.hexToBytes(hexIv),algorithm),charset);
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("decrypt() - ", ex);
			return "";
		}
	}

	public static byte[] encrypt(byte[] plainData, byte[] key, byte[] iv, String algorithm) {
		if (plainData == null || key == null || iv == null || algorithm == null) {
			log.info("encrypt() - one of input parameter is null, return byte[0]!");
			return new byte[0];
		}
		
		try {
			SecretKey desKey = new SecretKeySpec(key, algorithm);
			IvParameterSpec ivParam = new IvParameterSpec(iv);
			Cipher tcipher = Cipher.getInstance(algorithm);
			tcipher.init(Cipher.ENCRYPT_MODE, desKey, ivParam);
			
			return tcipher.doFinal(plainData);
		}
		catch (Exception ex) {
			log.warn("encrypt() - ", ex);
			ex.printStackTrace();
			return new byte[0];
		}
	}

	public static byte[] decrypt(byte[] cipherData, byte[] key, byte[] iv, String algorithm) {
		if (cipherData == null || key == null || iv == null || algorithm == null) {
			log.info("decrypt() - one of input parameter is null, return byte[0]!");
			return new byte[0];
		}
		
		try {
			SecretKey desKey = new SecretKeySpec(key, algorithm);
			IvParameterSpec ivParam = new IvParameterSpec(iv);
			Cipher tcipher = Cipher.getInstance(algorithm);
			tcipher.init(Cipher.DECRYPT_MODE, desKey, ivParam);
			
			return tcipher.doFinal(cipherData);
		}
		catch (Exception ex) {
			log.warn("decrypt() - ", ex);
			return new byte[0];
		}
	}

	private static Logger log = LoggerFactory.getLogger(Des.class);
}