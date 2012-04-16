package com.kevlindev.tokens;

import java.io.FileInputStream;
import java.io.IOException;

import beaver.Scanner.Exception;

import com.kevlindev.utils.IOUtils;

public class Test {
	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, Exception {
		String source = IOUtils.getString(new FileInputStream(args[0]));
		TokenNode result = new TokenParser().parse(source);

		System.out.println("result:");
		System.out.println(result);

		if (result != null) {
			TokenTreeParser parser = new TokenTreeParser();
			parser.parse(result);

			System.out.println();
			System.out.println("package = " + parser.getPackage());
			System.out.println("language = " + parser.getLanguage());
			// keywords
			System.out.println("keywords =");
			for (String keyword : parser.getKeywords()) {
				System.out.println("  " + keyword);
			}
			// operators
			System.out.println("operators =");
			for (String operator : parser.getOperators()) {
				System.out.println("  " + operator);
			}
		}
	}
}
