package com.ancient.util.spell.operations;

import java.util.Queue;

import com.ancient.util.spell.ExecutionReturn;
import com.ancient.util.spell.data.Data;
import com.ancient.util.spell.item.SpellItem;

public class Operation implements SpellItem {
	private Queue<String> queue;

	@Override
	public ExecutionReturn excecute(Data... data) {
		return null;
//		new ExecutionReturn().addData(new DoubleData(calculateRPN(queue));
	}

	public Operation(String expression) {
		queue = Mathematic.convertToRPN(expression);
	}
}
