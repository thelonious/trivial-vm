package com.kevlindev.mcpu.assembler;

import java.io.Reader;
import java.io.StringReader;

import beaver.Symbol;
import beaver.Scanner;

import beaver.Scanner;
import beaver.Symbol;

import com.kevlindev.mcpu.assembler.MCPUTokenType;

%%

%public
%class MCPULexer
%extends Scanner
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode
%ignorecase
%line
%column

%{
	public MCPULexer() {
		this((Reader) null);
	}

	private Symbol newToken(MCPUTokenType type, Object value) {
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

			result = new Symbol(MCPUTokenType.EOF.getIndex(), yychar, end, text);
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
Label = {Identifier} ":"

Number = [0-9]+
Hex = "0x"[a-fA-F0-9]{1,2}

%%

/* keywords */
<YYINITIAL> {
	// assume we always have whitespace and that it is skipped
	{Whitespace}	{ /* ignore */ }
	
	// assume we always have comments and they are skipped
	{Comment}		{ /* ignore */ }
	
	{Number}		{ return newToken(MCPUTokenType.NUMBER,	yytext()); }
	{Hex}			{ return newToken(MCPUTokenType.HEX,	yytext()); }
	
	// emit keywords
	".DB"			{ return newToken(MCPUTokenType.DB,		yytext()); }
	"NOR"			{ return newToken(MCPUTokenType.NOR,	yytext()); }
	"ADD"			{ return newToken(MCPUTokenType.ADD,	yytext()); }
	"STA"			{ return newToken(MCPUTokenType.STA,	yytext()); }
	"JCC"			{ return newToken(MCPUTokenType.JCC,	yytext()); }
	
	// identifiers and such
	{Label}			{ return newToken(MCPUTokenType.LABEL,		yytext()); }
	{Identifier}	{ return newToken(MCPUTokenType.IDENTIFIER,	yytext()); }
}

/* error fallback */
.|\n               { throw new Error("Illegal character <"+  yytext() + ">"); }
