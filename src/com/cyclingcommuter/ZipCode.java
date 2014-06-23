package com.cyclingcommuter;

public class ZipCode {

	private static final int ZIPCODE_LENGTH = 5;

	public static boolean isValid(String s) {
		return s.length() == ZIPCODE_LENGTH;
	}

}
