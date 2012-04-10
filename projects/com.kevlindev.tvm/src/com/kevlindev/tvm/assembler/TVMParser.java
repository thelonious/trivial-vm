package com.kevlindev.tvm.assembler;

import java.util.ArrayList;
import com.kevlindev.tvm.assembler.ast.Number;
import java.util.Map;
import com.kevlindev.tvm.assembler.ast.Operand;
import com.kevlindev.tvm.assembler.ast.Register;
import java.util.HashMap;
import com.kevlindev.tvm.assembler.ast.Identifier;
import com.kevlindev.tvm.assembler.ast.OperandType;
import beaver.*;
import com.kevlindev.tvm.assembler.ast.Opcode;
import com.kevlindev.tvm.assembler.ast.Address;
import com.kevlindev.tvm.assembler.ast.BaseNode;
import com.kevlindev.tvm.assembler.ast.Instruction;

/**
 * This class is a LALR parser generated by
 * <a href="http://beaver.sourceforge.net">Beaver</a> v0.9.6.1
 * from the grammar specification "tvm-assembler.grammar".
 */
public class TVMParser extends Parser {

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9oTajbI0Z4GHMy640GTaLKsHKKK57omBBNSg$mmVzPztr1nQMyoZL8yy61Ifs#dqvraQXh" +
		"0aGX6i815B2ECI9zVGmnpcCSu9h225ZpQa9mWyPjJW#yoQiomcgIE8KwYA63KnJAe88L951" +
		"cjeu4cqiWYXpoci8GIofZ6B6PG1LWLOrMSDoJxVCFA4D$ZyTuaJqevc#MxUZc6qsOOPbZrg" +
		"JxVOZ6yBwYsCy9feq6suSgZpJZRMmFjOneHwL9WVOwKQfaOaZZMFOtxfA9#axJ8AjdGs2PP" +
		"9rjaMsDjiaFsTBrFTiaXEUW$N$Uhgck3UmDvzXiY#WqTZPLK2qFgyiFE$IySTTN6a9ns8F9" +
		"DFjNlofLSIeVUYri$aqVdFP47SaTkNEIMNCk5dCizlNVo9cToAg$o8LzCAB9Z37l78uRzun" +
		"73JWeJJp6kNsnt14IKQ5$yBvPslMNuvmCoIku79jLfh0==");

	private Map<String, BaseNode> symbols = new HashMap<String, BaseNode>();

	private void addSymbol(String symbol, BaseNode node) {
		// TODO: check for existing symbol
		symbols.put(symbol, node);
	}

	private Address createAddress(String text) {
		return new Address(Integer.parseInt(text.substring(1)));
	}

	private Register createRegister(String text) {
		return new Register(Integer.parseInt(text.substring(1)));
	}

	public Map<String, BaseNode> getSymbols() {
		return symbols;
	}

	public TVMParser() {
		super(PARSING_TABLES);
	}

	protected Symbol invokeReduceAction(int rule_num, int offset) {
		switch(rule_num) {
			case 1: // Statements = Statements StatementWithLabel
			{
					((ArrayList) _symbols[offset + 1].value).add(_symbols[offset + 2]); return _symbols[offset + 1];
			}
			case 2: // Statements = StatementWithLabel
			{
					ArrayList lst = new ArrayList(); lst.add(_symbols[offset + 1]); return new Symbol(lst);
			}
			case 3: // StatementWithLabel = LABEL.l Statement.s
			{
					final Symbol _symbol_l = _symbols[offset + 1];
					final String l = (String) _symbol_l.value;
					final Symbol _symbol_s = _symbols[offset + 2];
					final Instruction s = (Instruction) _symbol_s.value;
					
			addSymbol(l, s);
			
			// TODO: remove once symbol table is working
			s.setLabel(l);
			
			return s;
			}
			case 5: // Statement = SET Container.c COMMA Source.s
			{
					final Symbol _symbol_c = _symbols[offset + 2];
					final Operand c = (Operand) _symbol_c.value;
					final Symbol _symbol_s = _symbols[offset + 4];
					final Operand s = (Operand) _symbol_s.value;
					
			return new Instruction(Opcode.SET, c, s);
			}
			case 6: // Statement = INC Container.c
			{
					final Symbol _symbol_c = _symbols[offset + 2];
					final Operand c = (Operand) _symbol_c.value;
					
			return new Instruction(Opcode.INC, c);
			}
			case 7: // Statement = JNE Destination.d
			{
					final Symbol _symbol_d = _symbols[offset + 2];
					final Operand d = (Operand) _symbol_d.value;
					
			return new Instruction(Opcode.JNE, d);
			}
			case 8: // Statement = BRK
			{
					
			return new Instruction(Opcode.BRK);
			}
			case 9: // Container = Location.l
			{
					final Symbol _symbol_l = _symbols[offset + 1];
					final BaseNode l = (BaseNode) _symbol_l.value;
					
			return new Operand(OperandType.ADDRESS, l);
			}
			case 10: // Container = Location.l PLUS REGISTER.r
			{
					final Symbol _symbol_l = _symbols[offset + 1];
					final BaseNode l = (BaseNode) _symbol_l.value;
					final Symbol _symbol_r = _symbols[offset + 3];
					final String r = (String) _symbol_r.value;
					
			return new Operand(OperandType.ADDRESS_INDEXED, l, createRegister(r));
			}
			case 11: // Container = LBRACKET Location.l RBRACKET
			{
					final Symbol _symbol_l = _symbols[offset + 2];
					final BaseNode l = (BaseNode) _symbol_l.value;
					
			return new Operand(OperandType.ADDRESS_INDIRECT, l);
			}
			case 12: // Container = LBRACKET Location.l PLUS REGISTER.r RBRACKET
			{
					final Symbol _symbol_l = _symbols[offset + 2];
					final BaseNode l = (BaseNode) _symbol_l.value;
					final Symbol _symbol_r = _symbols[offset + 4];
					final String r = (String) _symbol_r.value;
					
			return new Operand(OperandType.ADDRESS_INDIRECT_PRE_INDEXED, l, createRegister(r));
			}
			case 13: // Container = LBRACKET Location.l RBRACKET PLUS REGISTER.r
			{
					final Symbol _symbol_l = _symbols[offset + 2];
					final BaseNode l = (BaseNode) _symbol_l.value;
					final Symbol _symbol_r = _symbols[offset + 5];
					final String r = (String) _symbol_r.value;
					
			return new Operand(OperandType.ADDRESS_INDIRECT_POST_INDEXED, l, createRegister(r));
			}
			case 14: // Container = LBRACKET Location.l PLUS REGISTER.r1 RBRACKET PLUS REGISTER.r2
			{
					final Symbol _symbol_l = _symbols[offset + 2];
					final BaseNode l = (BaseNode) _symbol_l.value;
					final Symbol _symbol_r1 = _symbols[offset + 4];
					final String r1 = (String) _symbol_r1.value;
					final Symbol _symbol_r2 = _symbols[offset + 7];
					final String r2 = (String) _symbol_r2.value;
					
			return new Operand(OperandType.ADDRESS_INDIRECT_PRE_AND_POST_INDEXED, l, createRegister(r1), createRegister(r2));
			}
			case 15: // Container = REGISTER.r
			{
					final Symbol _symbol_r = _symbols[offset + 1];
					final String r = (String) _symbol_r.value;
					
			return new Operand(OperandType.REGISTER, createRegister(r));
			}
			case 16: // Container = LBRACKET REGISTER.r RBRACKET
			{
					final Symbol _symbol_r = _symbols[offset + 2];
					final String r = (String) _symbol_r.value;
					
			return new Operand(OperandType.REGISTER_INDIRECT, createRegister(r));
			}
			case 17: // Container = LBRACKET REGISTER.r1 PLUS REGISTER.r2 RBRACKET
			{
					final Symbol _symbol_r1 = _symbols[offset + 2];
					final String r1 = (String) _symbol_r1.value;
					final Symbol _symbol_r2 = _symbols[offset + 4];
					final String r2 = (String) _symbol_r2.value;
					
			return new Operand(OperandType.REGISTER_INDIRECT_PRE_INDEXED, createRegister(r1), createRegister(r2));
			}
			case 18: // Container = LBRACKET REGISTER.r1 RBRACKET PLUS REGISTER.r2
			{
					final Symbol _symbol_r1 = _symbols[offset + 2];
					final String r1 = (String) _symbol_r1.value;
					final Symbol _symbol_r2 = _symbols[offset + 5];
					final String r2 = (String) _symbol_r2.value;
					
			return new Operand(OperandType.REGISTER_INDIRECT_POST_INDEXED, createRegister(r1), createRegister(r2));
			}
			case 19: // Container = LBRACKET REGISTER.r1 PLUS REGISTER.r2 RBRACKET PLUS REGISTER.r3
			{
					final Symbol _symbol_r1 = _symbols[offset + 2];
					final String r1 = (String) _symbol_r1.value;
					final Symbol _symbol_r2 = _symbols[offset + 4];
					final String r2 = (String) _symbol_r2.value;
					final Symbol _symbol_r3 = _symbols[offset + 7];
					final String r3 = (String) _symbol_r3.value;
					
			return new Operand(OperandType.REGISTER_INDIRECT_PRE_AND_POST_INDEXED, createRegister(r1), createRegister(r2), createRegister(r3));
			}
			case 21: // Source = NUMBER.n
			{
					final Symbol _symbol_n = _symbols[offset + 1];
					final String n = (String) _symbol_n.value;
					
			return new Operand(OperandType.NUMBER, new Number(Integer.parseInt(n.substring(1))));
			}
			case 22: // Location = ADDRESS.a
			{
					final Symbol _symbol_a = _symbols[offset + 1];
					final String a = (String) _symbol_a.value;
					
			return createAddress(a);
			}
			case 23: // Location = IDENTIFIER.i
			{
					final Symbol _symbol_i = _symbols[offset + 1];
					final String i = (String) _symbol_i.value;
					
			return new Identifier(i);
			}
			case 24: // Destination = ADDRESS.a
			{
					final Symbol _symbol_a = _symbols[offset + 1];
					final String a = (String) _symbol_a.value;
					
			return new Operand(OperandType.ADDRESS, createAddress(a));
			}
			case 25: // Destination = IDENTIFIER.i
			{
					final Symbol _symbol_i = _symbols[offset + 1];
					final String i = (String) _symbol_i.value;
					
			return new Operand(OperandType.LABEL, new Identifier(i));
			}
			case 0: // Grammar = Statements
			case 4: // StatementWithLabel = Statement
			case 20: // Source = Container
			{
				return _symbols[offset + 1];
			}
			default:
				throw new IllegalArgumentException("unknown production #" + rule_num);
		}
	}
}
