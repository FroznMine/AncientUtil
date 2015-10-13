package com.ancient.util.spell.item;

import com.ancient.util.spell.data.Data;

/** The interface for all SpellItems which return something.
 * 
 * @author FroznMine
 *
 * @param <T> The type which a method will return when executed. Class T must implement Data
 */
public interface Returning<T extends Data> extends SpellItem {
	public T getReturn();
}
