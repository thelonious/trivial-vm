package com.kevlindev.tvm.assembler;

import java.io.Reader;
import java.io.StringReader;

import beaver.Symbol;
import beaver.Scanner;

import com.kevlindev.tvm.assembler.TVMTokenType;

%%

%public
%class TVMLexer
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
	public TVMLexer() {
		this((Reader) null);
	}

	private Symbol newToken(TVMTokenType type, Object value) {
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

			result = new Symbol(TVMTokenType.EOF.getIndex(), yychar, end, text);
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
Label = ":" {Identifier}

%%

/* keywords */
<YYINITIAL> {
	{Whitespace}	{ /* ignore */ }
	{Comment}		{ /* ignore */ }
	
	","				{ return newToken(TVMTokenType.COMMA,		yytext()); }
	"+"				{ return newToken(TVMTokenType.PLUS,		yytext()); }
	"["				{ return newToken(TVMTokenType.LBRACKET,	yytext()); }
	"]"				{ return newToken(TVMTokenType.RBRACKET,	yytext()); }
	[rR][0-9]		{ return newToken(TVMTokenType.REGISTER,	yytext()); }
	"#"[0-9]+		{ return newToken(TVMTokenType.NUMBER,		yytext()); }
	"$"[0-9]+		{ return newToken(TVMTokenType.ADDRESS,		yytext()); }
	
	"BRK"			{ return newToken(TVMTokenType.BRK,			yytext()); }
	"BNE"			{ return newToken(TVMTokenType.BNE,			yytext()); }
	"INC"			{ return newToken(TVMTokenType.INC,			yytext()); }
	"SET"			{ return newToken(TVMTokenType.SET,			yytext()); }
	
	{Label}			{ return newToken(TVMTokenType.LABEL,		yytext()); }
	{Identifier}	{ return newToken(TVMTokenType.IDENTIFIER,	yytext()); }
}

/* error fallback */
.|\n               { throw new Error("Illegal character <"+  yytext() + ">"); }