package weixin.basic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	private static String token = "weixiaokai";

	public static boolean checkSignature(String signature, String timestamp,
			String nouce) {
		String[] paramArr = new String[] { token, timestamp, nouce };
		Arrays.sort(paramArr);
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase())
				: false;
	}

	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);

		}
		return strDigest;
	}

	private static String byteToHexStr(byte b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(b >>> 4) & 0x0f];
		tempArr[1] = Digit[b & 0x0f];

		String s = new String(tempArr);
		return s;
	}
}
