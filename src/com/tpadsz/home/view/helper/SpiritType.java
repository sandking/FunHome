package com.tpadsz.home.view.helper;

public interface SpiritType
{

	public enum Type
	{
		SPIRIT(0), LISTVIEW(1), TOUCHPAD(2), SCROLLBAR(3);

		final int value;

		Type(int value)
		{
			this.value = value;
		}

		public int value()
		{
			return this.value;
		}
	}

	Type getType();
}
