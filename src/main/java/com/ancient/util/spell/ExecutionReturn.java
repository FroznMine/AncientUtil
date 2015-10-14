package com.ancient.util.spell;

import java.util.Iterator;
import java.util.LinkedList;

import com.ancient.util.spell.data.Data;

/** ExecutionReturn objects contain everything a <code>SpellItem</code> may return.
 * 
 * @author FroznMine
 *
 */
public class ExecutionReturn {
	/** Containing the line that will be executed next.
	 */
	private final int NEXTLINE;
	/** A list holding every <code>Data</code> passed to the object in the order it was add.
	 */
	private final LinkedList<Data> DATA;
	
	/** Create a new ExecutionReturn object by passing line, that should be executed next.
	 * 
	 * @param nextLine The next line
	 */
	public ExecutionReturn(int nextLine) {
		this.NEXTLINE = nextLine;
		this.DATA = new LinkedList<Data>();
	}
	
	/** Add an array of data to the object.<br>
	 * The items are added in the order they are passed at the end of the list.
	 * 
	 * @param data The data that should be added
	 */
	public void addData(Data... data) {
		for (Data d : data) {
			this.DATA.add(d);
		}
	}
	/** Returns the line which is saved in the object.
	 * 
	 * @return The line which has to be executed next
	 */
	public int getNextLine() {
		return this.NEXTLINE;
	}
	
	/** Returns an iterator over all data that is returned. So it can't be removed from the original list.
	 * 
	 * @return An iterator over all stored data
	 */
	public Iterator<Data> getData() {
		return this.DATA.iterator();
	}
}
