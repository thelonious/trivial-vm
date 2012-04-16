package com.kevlindev.tokens;

import java.io.Reader;
import java.io.StringReader;

import beaver.Symbol;
import beaver.Scanner;

import beaver.Scanner;
import beaver.Symbol;

import com.kevlindev.tokens.TokenType;

%%

%public
%class TokenLexer
%extends Scanner
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode
%line
%column

%{
	public TokenLexer() {
		this((Reader) null);
	}

	private Symbol newToken(TokenType type, Object value) {
		return newToken(type.getIndex(), value);
	}

	private Symbol newToken(short id, Object value) {
		int start = yychar;
		int end = start + yylength() - 1;

		return new Symbol(id, start, end, value);
	}

	public Symbol nextToken() throws java.io.IOException, Scanner.Exception
	{
		Symbol result;

		try {
			// get next token
			result = yylex();
		} catch (Scanner.Exception e) {
			// create default token type
			String text = yytext();
			int end = yychar + text.length() - 1;

			result = new Symbol(TokenType.EOF.getIndex(), yychar, end, text);
		}

		return result;
	}

	public void setSource(String source) {
		yyreset(new StringReader(source));
	}
%}

Whitespace = [ \t\r\n]
EndOfLine = \r|\n|\r\n
Comment = ;[^\r\n]*{EndOfLine}

Identifier = [_a-zA-Z][-_a-zA-Z0-9]*
DottedName = {Identifier}(\.{Identifier})*
String = "\"" ([^\"\r\n] | "\\"[^\r\n]) "\""

%%

/* keywords */
<YYINITIAL> {
	{Whitespace}	{ /* ignore */ }
	{Comment}		{ /* ignore */ }
	
	"="				{ return newToken(TokenType.EQUAL,		yytext()); }
	"["				{ return newToken(TokenType.LBRACKET,	yytext()); }
	"]"				{ return newToken(TokenType.RBRACKET,	yytext()); }
	{String}		{ return newToken(TokenType.STRING,		yytext()); }
	
	"package"		{ return newToken(TokenType.PACKAGE,	yytext()); }
	"language"		{ return newToken(TokenType.LANGUAGE,	yytext()); }
	"keywords"		{ return newToken(TokenType.KEYWORDS,	yytext()); }
	"operators"		{ return newToken(TokenType.OPERATORS,	yytext()); }
	
	{DottedName}	{ return newToken(TokenType.IDENTIFIER,	yytext()); }
}

/* error fallback */
.|\n               { throw new Error("Illegal character <"+  yytext() + ">"); }