package com.d1m.wechat.util;

import java.util.Random;

public class Rand {
	private static char CHARS[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'v',
			'w', 'y', 'z' };

	private static char CHARS_NUMBER[] = { '2', '3', '4', 'R', 'S', 'T', 'W',
			'5', '6', '7', '8', '9', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's',
			'H', 'J', 'K', 't', 'v', 'w', 'B', 'C', 'D', 'F', 'G', 'L', 'M',
			'N', 'P', 'Q', 'b', 'c', 'd', 'V', 'f', 'g', 'h' };

	private static Random random = new Random();
	static {
		random.setSeed(System.currentTimeMillis());
	}

	public static char randomChar() {
		return CHARS_NUMBER[randomInt(0, CHARS.length - 1)];
	}

	public static double randomDouble() {
		return random.nextDouble();
	}

	public static int randomInt() {
		return random.nextInt();
	}

	public static int randomInt(int min, int max) {
		return (int) ((Math.random() * (max + 1 - min)) + min);
	}

	public static long randomLong() {
		return random.nextLong();
	}

	public static long randomLong(long min, long max) {
		return (long) ((Math.random() * (max + 1L - min)) + min);
	}

	public static String randomString() {
		return "" + randomLong();
	}

	public static String randomString(int aLength) {
		StringBuffer sb = new StringBuffer(aLength);
		for (int i = 0; i < aLength; i++) {
			sb.append(randomChar());
		}
		return sb.toString();
	}

	public static String getRandom(int len) {
		StringBuffer buf = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < len; i++) {
			buf.append(Integer.toHexString(rand.nextInt(16)));
		}
		return buf.toString();
	}
}
