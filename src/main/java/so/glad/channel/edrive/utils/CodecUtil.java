package so.glad.channel.edrive.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import so.glad.channel.edrive.Const;

public class CodecUtil {

	private static Logger log = LoggerFactory.getLogger(CodecUtil.class);

	private final static String ALGORITHM_MD5 = "MD5";

	private final static String ALGORITHM_SHA1 = "SHA1";

	private final static String ALGORITHM_HMACSHA1 = "HmacSHA1";

	public static String md5Hash(String source, String charset) {
		if (StringUtils.isEmpty(source) || StringUtils.isBlank(charset)) {
			return "";
		}
		try {
			return ByteUtil.toHex(md5Hash(source.getBytes(charset)));
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("md5Hash([" + source + "], [" + charset + "])", ex);
			return "";
		}
	}

	public static byte[] md5Hash(byte[] source) {
		return digest(source, ALGORITHM_MD5);
	}

	public static String md5Hash(File sourceFile) {
		return ByteUtil.toHex(fileDigest(sourceFile, ALGORITHM_MD5));
	}

	public static String sha1Hash(String source, String charset) {
		if (StringUtils.isEmpty(source) || StringUtils.isBlank(charset)) {
			return "";
		}
		try {
			return ByteUtil.toHex(sha1Hash(source.getBytes(charset)));
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("sha1Hash([" + source + "], [" + charset + "])", ex);
			return "";
		}
	}

	public static byte[] sha1Hash(byte[] source) {
		return digest(source, ALGORITHM_SHA1);
	}

	public static String hmacsha1(String source, String key, String charset) {
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(key) || StringUtils.isBlank(charset)) {
			return "";
		}
		try {
			return ByteUtil.toHex(
					hmacsha1(source.getBytes(charset), key.getBytes(charset)));
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("hmacsha1Hash([" + source + "], [" + charset + "])", ex);
			return "";
		}
	}
	
    public static String hmacsha1(String data, String key) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance(ALGORITHM_HMACSHA1);
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), ALGORITHM_HMACSHA1);
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignore) {
            // should never happen
        }
        return ByteUtil.toHex(byteHMAC);
    }

	public static byte[] hmacsha1(byte[] source, byte[] key) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, ALGORITHM_HMACSHA1);
			Mac mac = Mac.getInstance(ALGORITHM_HMACSHA1);
			mac.init(signingKey);
			return mac.doFinal(source);
		}
		catch (NoSuchAlgorithmException ex) {
			log.warn("digest() - No such algorithm[" + ALGORITHM_HMACSHA1 + "]!", ex);
			return new byte[0];
		}
		catch (InvalidKeyException ex) {
			log.warn("digest() - InvalidKey!", ex);
			return new byte[0];
		}

	}

	public static String sha1Hash(File sourceFile) {
		return ByteUtil.toHex(fileDigest(sourceFile, ALGORITHM_SHA1));
	}
	
	public static String base64Encode(String source, String charset) {
		if (StringUtils.isEmpty(source) || StringUtils.isBlank(charset)) {
			return "";
		}
		try {
			return base64Encode(source.getBytes(charset));
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("base64Encode([" + source + "], [" + charset + "])", ex);
			return "";
		}
	}

	public static String base64Encode(byte[] data) {
		if (data == null) {
			return "";
		}
		
		return CodecUtil.base64Encode(data) ;
	}

	public static String encodeURL(String source) {
		if (StringUtils.isEmpty(source)) {
			return "";
		}
		
		try {
			return URLEncoder.encode(source, Const.DEFAULT_ENCODING);
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("Encode url segment error .[" + source + "]", ex);
		}
		return "";
	}

	public static String urlDecode(String s, String charset) {
		if (StringUtils.isEmpty(s) || StringUtils.isBlank(charset)) {
			return "";
		}
		
		try {
			return URLDecoder.decode(s, charset);
		}
		catch (UnsupportedEncodingException ex) {
			log.warn("urlDecode([" + s + "], [" + charset + "])", ex);
		}
		return "";
	}

	private static byte[] digest(byte[] source, String algorithm) {
		if (source == null) {
			return new byte[0];
		}
		
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(source);
			return md.digest();
		}
		catch (NoSuchAlgorithmException ex) {
			log.warn("digest() - No Such Algorithm[" + algorithm + "]", ex);
			return new byte[0];
		}
	}

	private static byte[] fileDigest(File file, String algorithm) {
		log.debug("fileDigest() - file[" + file + "], algorithm[" + algorithm + "]");

		if (file == null || file.length() == 0) {
			log.warn("fileDigest() - file[" + file + "] is null or zero length, return byte[0]!");
			return new byte[0];
		}

		long maxBufferSize = 1024 * 1024 * 256;
		try {
			FileInputStream in = new FileInputStream(file);
			FileChannel ch = in.getChannel();
			long fileLen = file.length();
			int digestUpdateCount = fileLen % maxBufferSize == 0 ? 
					(int)(fileLen / maxBufferSize) :
					(int)(fileLen / maxBufferSize) + 1;

			MessageDigest md = MessageDigest.getInstance(algorithm);
			
			log.debug("fileDigest() - file.length=" + fileLen +
					  ", digestUpdateCount=" + digestUpdateCount);

			long position = 0;
			for (int i = 0; i < digestUpdateCount; i++) {
				long size = file.length() - position;
				if (size > maxBufferSize) {
					size = maxBufferSize;
				}
				log.debug("fileDigest() - map buffer[" + i + "] position=" +
						  position + ", size=" + size);
				md.update(ch.map(FileChannel.MapMode.READ_ONLY, position, size));
				position += size;
			}

			byte[] digest = md.digest();

			if (log.isDebugEnabled()) {
				log.debug("fileDigest() - file[" + file + "], algorithm[" + algorithm +
						  "], digest[" + ByteUtil.toHex(digest) + "]");
			}
			
			return digest;
		}
		catch (NoSuchAlgorithmException ex) {
			log.warn("digest() - No such algorithm[" + algorithm + "]!", ex);
			return new byte[0];
		}
		catch (FileNotFoundException ex) {
			log.warn("digest() - File[" + file.getAbsolutePath() + "] not found!", ex);
			return new byte[0];
		}
		catch (IOException ex) {
			log.warn("digest() - File[" + file.getAbsolutePath() + "] read error!", ex);
			return new byte[0];
		}
	}

}
