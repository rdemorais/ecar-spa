package br.gov.saude.impl.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.gov.saude.auth.SentinelaPasswordHash;

public class SentinelaPasswordHashImpl implements SentinelaPasswordHash{
	
	private static final String ALGORITIMO = "md5";	
	private static final String HEX_DIGITS = "0123456789abcdef";
	
	public String criptografar(String senha) {
		byte[] b = null;
		String str = senha;
		
		try {
			b = digest(str.getBytes(), ALGORITIMO);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return byteArrayToHexString(b);
	}
	
	private String byteArrayToHexString(byte[] b) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			int j = b[i] & 0xFF;
			buf.append(HEX_DIGITS.charAt(j / 16));
			buf.append(HEX_DIGITS.charAt(j % 16));
		}
		return buf.toString();
	}
	
	private byte[] digest(byte[] input, String palgoritmo1)
		throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(palgoritmo1);
		md.reset();
		return md.digest(input);
	}
}
