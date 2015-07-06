package com.nedap_bi.examples.server.authorizationcode.helpers;

public class StringHelpers {
	
	/**
	 * Helper for if the string is blank.
	 * @param string The string to check
	 * @return true if the object is null or empty.
	 */
	public static boolean isBlank(String string){		
		return string == null || string.isEmpty();		
	}

}
