/**
 * 
 */
package com.kevlindev.template.parser.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beaver.Symbol;

import com.kevlindev.parsing.SourceBuilder;
import com.kevlindev.template.parser.TemplateTokenType;
import com.kevlindev.utils.StringUtils;

/**
 * @author Kevin Lindsey
 */
public class TemplateNode extends Symbol {
	private Symbol symbol;
	private TemplateTokenType type;
	private TemplateNode parent;
	private List<TemplateNode> children;

	public TemplateNode(Symbol symbol) {
		this.symbol = symbol;
		this.type = TemplateTokenType.getTokenType(symbol.getId());
	}

	public TemplateNode(TemplateTokenType type) {
		this.symbol = new Symbol(type.getIndex());
		this.type = type;
	}

	public void addChild(TemplateNode child) {
		if (children == null) {
			children = new ArrayList<TemplateNode>();
		}

		children.add(child);
		child.setParent(this);
	}

	public void addChildren(List<TemplateNode> children) {
		// HACK: adding no children will create the backing array which is used to indicate that this is not a leaf node
		// even though it has no children
		if (this.children == null) {
			this.children = new ArrayList<TemplateNode>();
		}

		if (children != null) {
			for (TemplateNode child : children) {
				addChild(child);
			}
		}
	}

	public List<TemplateNode> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		} else {
			return children;
		}
	}

	public TemplateNode getFirstChild() {
		return (children != null && !children.isEmpty()) ? children.get(0) : null;
	}

	public TemplateNode getParent() {
		return parent;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public String getText() {
		Object value = getValue();

		return (value != null) ? value.toString() : StringUtils.EMPTY;
	}

	public TemplateTokenType getType() {
		return type;
	}

	public Object getValue() {
		return (symbol != null) ? symbol.value : null;
	}

	public boolean hasChildren() {
		return (children != null && !children.isEmpty());
	}

	protected void setParent(TemplateNode parent) {
		this.parent = parent;
	}

	public String toString() {
		SourceBuilder builder = new SourceBuilder();

		toString(builder);

		return builder.toString();
	}

	private void toString(SourceBuilder builder) {
		builder.printWithIndent("(").print(getType().toString());

		if (children != null) {
			int size = children.size();

			if (size > 0) {
				builder.println().indent();

				for (int i = 0; i < size; i++) {
					TemplateNode child = children.get(i);

					child.toString(builder);

					if (i < size - 1) {
						builder.println();
					}
				}

				builder.dedent();
			}
		} else {
			if (symbol != null && symbol.value != null) {
				builder.print(" ").print(symbol.value.toString());
			}
		}

		builder.print(")");
	}
}
