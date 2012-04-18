package com.kevlindev.tokens;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beaver.Scanner.Exception;

import com.kevlindev.text.ResettableIterator;
import com.kevlindev.utils.CollectionUtils;
import com.kevlindev.utils.IOUtils;

public class Test {
	private static final Pattern VARIABLE = Pattern.compile("\\$\\{([a-z]+)\\}");

	public Test() {
	}

	public void run(String file) throws IOException, Exception {
		String source = IOUtils.getString(new FileInputStream(file));
		TokenNode result = new TokenParser().parse(source);

		System.out.println("result:");
		System.out.println(result);

		TokenTreeParser parser = new TokenTreeParser();
		parser.parse(result);

		// apply properties
		Map<String, ResettableIterator<String>> properties = parser.getProperties();
		BufferedReader input = new BufferedReader(new FileReader("template/flex.ttmpl"));

		try {
			String line = null;

			while ((line = input.readLine()) != null) {
				Collection<String> variables = getVariables(line);

				if (!CollectionUtils.isEmpty(variables)) {

					// reset all iterators that will be used on this line
					for (String variable : variables) {
						if (properties.containsKey(variable)) {
							properties.get(variable).reset();
						}
					}

					StringBuffer buffer = new StringBuffer();

					// keep iterating and emitting the current line
					do {
						Matcher m = VARIABLE.matcher(line);

						while (m.find()) {
							String key = m.group(1);
							String text = (properties.containsKey(key)) ? properties.get(key).peek() : m.group();

							m.appendReplacement(buffer, text);
						}

						m.appendTail(buffer);
						System.out.println(buffer.toString());
						buffer.setLength(0);
					} while (iterate(properties, variables));
				} else {
					System.out.println(line);
				}
			}
		} finally {
			input.close();
		}
	}

	protected boolean iterate(Map<String, ResettableIterator<String>> properties, Collection<String> variables) {
		boolean result = false;

		for (String variable : variables) {
			if (properties.containsKey(variable)) {
				ResettableIterator<String> property = properties.get(variable);

				// advance
				property.next();

				if (property.hasNext()) {
					result = true;
					break;
				} else {
					property.reset();
				}
			}
		}

		return result;
	}

	protected Collection<String> getVariables(String line) {
		Set<String> result = new LinkedHashSet<String>();

		Matcher m = VARIABLE.matcher(line);

		while (m.find()) {
			String key = m.group(1);

			result.add(key);
		}

		return result;
	}

	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, Exception {
		if (args.length > 0) {
			Test test = new Test();

			test.run(args[0]);
		} else {
			System.out.println("usage: <token-file>");
		}
	}
}
