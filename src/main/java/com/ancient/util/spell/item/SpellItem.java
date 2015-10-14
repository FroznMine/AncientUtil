package com.ancient.util.spell.item;

import com.ancient.util.spell.data.Data;
import com.ancient.util.spell.ExecutionReturn;

/** The SpellItem interface is a general inferface for all things that should be usable in <code>Spells</code><br>
 * excecute(Data... data) is the only method that has to be implemented in every SpellItem, so it can be called. Things like values of wrong format have to be caught by the specific items
 * 
 * @author FroznMine
 *
 */
public interface SpellItem {
	/** This method is called, when the SpellItem is executed.
	 * 
	 * @param data An array of values that get passed on execution
	 * @return An <code>ExcecutionReturn</code> containing the line that has to be executed next and maybe also other returned values
	 */
	public ExecutionReturn excecute(Data... data);
}
