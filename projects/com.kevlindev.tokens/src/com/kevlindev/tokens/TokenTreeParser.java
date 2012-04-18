package com.kevlindev.tokens;

import java.util.ArrayList;
import com.kevlindev.text.StringIterator;
import java.util.Map;
import beaver.*;
import java.util.List;
import com.kevlindev.text.ListIterator;
import com.kevlindev.text.ResettableIterator;
import java.util.HashMap;
import java.io.IOException;

/**
 * This class is a LALR parser generated by
 * <a href="http://beaver.sourceforge.net">Beaver</a> v0.9.6.1
 * from the grammar specification "token.tree".
 */
public class TokenTreeParser extends Parser {

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9oDaEjImZ0GXKzA4DfQHGGL4U$Z3vwILyOhNjTlO#e0CywOpfUp9vjiqfKqruuwwcjN0vt" +
		"ywjXCXphIM3rDqSAMEiDrTOmRalzAqQcYIbMgrMXF#phGI1FeiDgbSe#AhGQ9zOZMINr$ZM" +
		"iu7xdNypzHmHVOqyvLGfejcCniXTJk8U0$qQdd6ETm2HVPNy4DN6T$2oCetM$3k8C9D5lhS" +
		"Iqkr$r$y7hj#$$83zitPp$JnaZ$wFgUzSrUawxioHxitXxj9Vbd#q2yju4vScy1tlEO2Hlg" +
		"tVRULvd1DrOeF9W=");

	private Map<String, ResettableIterator<String>> properties = new HashMap<String, ResettableIterator<String>>();
	
	public Map<String, ResettableIterator<String>> getProperties() {
		return properties;
	}

	public Object parse(TokenNode root) {
		Object result = null;
		
		if (root != null) {
			TreeScanner scanner = new TreeScanner();

			scanner.setNode(root);

			try {
				result = parse(scanner);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public TokenTreeParser() {
		super(PARSING_TABLES);
	}

	protected Symbol invokeReduceAction(int rule_num, int offset) {
		switch(rule_num) {
			case 2: // Statements = Statements Statement
			{
					((ArrayList) _symbols[offset + 1].value).add(_symbols[offset + 2]); return _symbols[offset + 1];
			}
			case 3: // Statements = Statement
			{
					ArrayList lst = new ArrayList(); lst.add(_symbols[offset + 1]); return new Symbol(lst);
			}
			case 4: // Statement = PACKAGE DOWN IDENTIFIER.i UP
			{
					final Symbol _symbol_i = _symbols[offset + 3];
					final Symbol i = (Symbol) _symbol_i.value;
					
			properties.put("package", new StringIterator(i.value.toString()));
			
			return i;
			}
			case 5: // Statement = LANGUAGE DOWN IDENTIFIER.i UP
			{
					final Symbol _symbol_i = _symbols[offset + 3];
					final Symbol i = (Symbol) _symbol_i.value;
					
			properties.put("language", new StringIterator(i.value.toString()));
			
			return i;
			}
			case 7: // Statement = KEYWORDS DOWN Words.ws UP
			{
					final Symbol _symbol_ws = _symbols[offset + 3];
					final ArrayList _list_ws = (ArrayList) _symbol_ws.value;
					final Symbol[] ws = _list_ws == null ? new Symbol[0] : (Symbol[]) _list_ws.toArray(new Symbol[_list_ws.size()]);
					
			List<String> keywords = new ArrayList<String>();
			
			for (Object word : _list_ws) {
				Symbol wordSymbol = (Symbol) word;
				String content = (String) wordSymbol.value;
				
				if (wordSymbol.getId() == TokenType.STRING.getIndex()) {
					keywords.add(content.substring(1, content.length() - 1));
				} else {
					keywords.add(content);
				}
			}
			
			properties.put("keywords", new ListIterator(keywords));
			
			return _symbol_ws;
			}
			case 9: // Statement = OPERATORS DOWN Words.ws UP
			{
					final Symbol _symbol_ws = _symbols[offset + 3];
					final ArrayList _list_ws = (ArrayList) _symbol_ws.value;
					final Symbol[] ws = _list_ws == null ? new Symbol[0] : (Symbol[]) _list_ws.toArray(new Symbol[_list_ws.size()]);
					
			List<String> operators = new ArrayList<String>();
			
			for (Object word : _list_ws) {
				Symbol wordSymbol = (Symbol) word;
				String content = (String) wordSymbol.value;
				
				if (wordSymbol.getId() == TokenType.STRING.getIndex()) {
					operators.add(content.substring(1, content.length() - 1));
				} else {
					operators.add(content);
				}
			}
			
			properties.put("operators", new ListIterator(operators));
			
			return _symbol_ws;
			}
			case 10: // Words = Words Word
			{
					((ArrayList) _symbols[offset + 1].value).add(_symbols[offset + 2].value); return _symbols[offset + 1];
			}
			case 11: // Words = Word
			{
					ArrayList lst = new ArrayList(); lst.add(_symbols[offset + 1].value); return new Symbol(lst);
			}
			case 0: // Grammar = Tree
			case 6: // Statement = KEYWORDS
			case 8: // Statement = OPERATORS
			case 12: // Word = IDENTIFIER
			case 13: // Word = STRING
			{
				return _symbols[offset + 1];
			}
			case 1: // Tree = ROOT DOWN Statements UP
			{
				return _symbols[offset + 4];
			}
			default:
				throw new IllegalArgumentException("unknown production #" + rule_num);
		}
	}
}
