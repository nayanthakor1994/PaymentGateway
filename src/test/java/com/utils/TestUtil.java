package com.utils;

import java.util.Random;

public class TestUtil {
	
	public String randomGenerator(int size) {
		char[] chars = "QWERTYUIOPLKJHGFDSAZXCVBNM1234567890".toCharArray();
		StringBuilder sb = new StringBuilder(5);
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String dcValue = sb.toString();
		return dcValue;
	}

}
