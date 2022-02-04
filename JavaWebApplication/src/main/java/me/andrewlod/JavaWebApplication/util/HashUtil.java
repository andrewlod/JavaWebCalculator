package me.andrewlod.JavaWebApplication.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashUtil {
	
	public static String hash(String str) {

		byte[] salt = EnvironmentUtil.getInstance().getKey("PSW_SALT").getBytes();
		KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hashed = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hashed = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return null;
		}
		Base64.Encoder enc = Base64.getEncoder();
		
		return enc.encodeToString(hashed);
	}

}
