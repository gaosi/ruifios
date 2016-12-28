package com.ruifios.spring;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class README {

	/**
	 * http://blog.csdn.net/qq519805712/article/details/51365017
	 * http://blog.csdn.net/u013412066/article/details/50667960
	 * encode_base64(byte[], int, StringBuilder)
char64(char)
decode_base64(String, int)
encipher(int[], int)
streamtoword(byte[], int[])
init_key()
key(byte[])
ekskey(byte[], byte[])
roundsForLogRounds(int)
crypt_raw(byte[], byte[], int)
hashpw(String, String)
gensalt(int, SecureRandom)
gensalt(int)
gensalt()
checkpw(String, String)
equalsNoEarlyReturn(String, String)
	 */
	public static void main(String[] args) {
		String password = "admin";
		String salt = BCrypt.gensalt(); 
		String pswencode = BCrypt.hashpw(password, salt);
		System.out.println(salt+" "+password+" "+pswencode);
		System.out.println(BCrypt.checkpw(password, pswencode));
	}
}
