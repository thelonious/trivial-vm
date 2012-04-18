package com.kevlindev.template.parser;

import java.io.Reader;
import java.io.StringReader;

import beaver.Symbol;
import beaver.Scanner;

import beaver.Scanner;
import beaver.Symbol;

import com.kevlindev.tokens.TemplateTokenType;

%%

%public
%class TemplateLexer
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
	public TemplateLexer() {
		this((Reader) null);
	}

	private Symbol newToken(TemplateTokenType type, Object value) {
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

			result = new Symbol(TemplateTokenType.EOF.getIndex(), yychar, end, text);
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
	
	"="				{ return newToken(TemplateTokenType.EQUAL,		yytext()); }
	"["				{ return newToken(TemplateTokenType.LBRACKET,	yytext()); }
	"]"				{ return newToken(TemplateTokenType.RBRACKET,	yytext()); }
	{String}		{ return newToken(TemplateTokenType.STRING,		yytext()); }
	
	"package"		{ return newToken(TemplateTokenType.PACKAGE,	yytext()); }
	"language"		{ return newToken(TemplateTokenType.LANGUAGE,	yytext()); }
	"keywords"		{ return newToken(TemplateTokenType.KEYWORDS,	yytext()); }
	"operators"		{ return newToken(TemplateTokenType.OPERATORS,	yytext()); }
	
	{DottedName}	{ return newToken(TemplateTokenType.IDENTIFIER,	yytext()); }
}

/* error fallback */
.|\n               { throw new Error("Illegal character <"+  yytext() + ">"); }