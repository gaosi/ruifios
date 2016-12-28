package com.ruifios.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	/**
	 * AES 对称加密算法 
	 */
	private static final String EncodeAlgorithm = "AES";
	/**
	 * 加密密钥和解密密钥
	 */
	public static final String KEY = "afeeaeteafaaeddejjncv";
	/**
	 * 请求 SecureRandom 对象的方法的算法名称
	 */
	private static final String SecureAlgorithm = "SHA1PRNG";

	/**
	 * 加密
	 * @param key
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String key, String plaintext) {
		try {
			byte[] raw = getRawKey(key.getBytes());
			SecretKeySpec skeySpec = new SecretKeySpec(raw, EncodeAlgorithm);
			Cipher cipher = Cipher.getInstance(EncodeAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(plaintext.getBytes());
			return bytesToString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 解密
	 * @param key
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String key, String ciphertext) {
		try {
			byte[] raw = getRawKey(key.getBytes());
			SecretKeySpec skeySpec = new SecretKeySpec(raw, EncodeAlgorithm);
			Cipher cipher = Cipher.getInstance(EncodeAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] arr = stringToBytes(ciphertext);
			byte[] decrypted = cipher.doFinal(arr);
			return new String(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(EncodeAlgorithm);
		SecureRandom sr = SecureRandom.getInstance(SecureAlgorithm);
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}
	
	private static String bytesToString(byte[] bs) {
		if (bs == null || bs.length == 0)
			return "";
		int len = bs.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(String.format("%02X", bs[i]));
		}
		return sb.toString();
	}
	
	private static byte[] stringToBytes(String str) {
		if (str == null || str.length() < 2 || str.length() % 2 != 0)
			return new byte[0];
		int len = str.length();
		byte[] bs = new byte[len / 2];
		for (int i = 0; i * 2 < len; i++) {
			bs[i] = (byte) (Integer.parseInt(str.substring(i * 2, i * 2 + 2),
					16) & 0xFF);
		}
		return bs;
	}
	
	public static void main(String[] args) throws Exception {
		String content = "admin";
		String enpwd = encrypt(KEY, content);
		String depwd = decrypt(KEY, enpwd);
		
		System.err.println(content);
		System.err.println(enpwd);
		System.err.println(depwd);
	}
}