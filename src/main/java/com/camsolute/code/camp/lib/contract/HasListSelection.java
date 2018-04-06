package com.camsolute.code.camp.lib.contract;

public interface HasListSelection<T extends HasId> {
	
	public T selected();
	public int selectionIndex();
	public void setSelectionIndex(int index);
	/**
	 * This contract specifies that a list of items adheres to requests to specify a list item to be considered as selected.
	 * @param id the technical identifier aspect of the list item.
	 * @return the list index value of currently selected list item, or -1 if list is empty. default value is 0.
	 */
	public int select(int itemId);
	public int select(T item);
}
