package com.kevlindev.tvm.assembler;

import java.util.ArrayList;

import com.kevlindev.tvm.assembler.ast.*;

import beaver.*;

/**
 * This class is a LALR parser generated by
 * <a href="http://beaver.sourceforge.net">Beaver</a> v0.9.6.1
 * from the grammar specification "tvm-assembler.grammar".
 */
public class TVMParser extends Parser {

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9nzZyaEmo0CH2S98GjawNBkj#QhkwMjEu25E5Irz9YH6OmCu282Yn4UCrgOx7lw1IjgkUR" +
		"UWWOT70QacaY5cbt3XCs#YoyStqp5lUFy5ht4YZCEMvfZmw5#LJqIKsHEgkTDioMiXhZWEN" +
		"p$bVb7#2U9VCXB$PCyo5rkiikRhI5kvBcJfpggeuvnlrgfC7y15AKhzG==");

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
					
			s.setLabel(l);
			
			return s;
			}
			case 5: // Statement = LOAD REGISTER.r COMMA NUMBER.n
			{
					final Symbol _symbol_r = _symbols[offset + 2];
					final String r = (String) _symbol_r.value;
					final Symbol _symbol_n = _symbols[offset + 4];
					final String n = (String) _symbol_n.value;
					
			return new Instruction(Opcode.LOAD, new Operand(OperandType.REGISTER, r), new Operand(OperandType.NUMBER, n));
			}
			case 6: // Statement = INC REGISTER.r
			{
					final Symbol _symbol_r = _symbols[offset + 2];
					final String r = (String) _symbol_r.value;
					
			return new Instruction(Opcode.INC, new Operand(OperandType.REGISTER, r));
			}
			case 7: // Statement = BNE IDENTIFIER.i
			{
					final Symbol _symbol_i = _symbols[offset + 2];
					final String i = (String) _symbol_i.value;
					
			return new Instruction(Opcode.BNE, new Operand(OperandType.LABEL, i));
			}
			case 8: // Statement = BRK
			{
					
			return new Instruction(Opcode.BRK);
			}
			case 0: // Grammar = Statements
			case 4: // StatementWithLabel = Statement
			{
				return _symbols[offset + 1];
			}
			default:
				throw new IllegalArgumentException("unknown production #" + rule_num);
		}
	}
}