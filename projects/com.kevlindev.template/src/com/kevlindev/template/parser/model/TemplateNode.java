/**
 * 
 */
package com.kevlindev.template.parser.model;

import beaver.Symbol;

import com.kevlindev.parsing.model.ParseNode;
import com.kevlindev.template.parser.TemplateTokenType;

/**
 * @author Kevin Lindsey
 */
public class TemplateNode extends ParseNode<TemplateTokenType> {
	public TemplateNode(Symbol symbol) {
		super(symbol);
	}

	public TemplateNode(TemplateTokenType type) {
		super(type);
	}

	@Override
	protected TemplateTokenType getType(short id) {
		return TemplateTokenType.getTokenType(id);
	}

	@Override
	protected short getTypeIndex(TemplateTokenType type) {
		return type.getIndex();
	}
}
