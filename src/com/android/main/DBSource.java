package com.android.main;

import android.database.Cursor;

public interface DBSource <T, U> {
	public void open();
	public void close();
	public void insertEntry(T object);
	public Cursor readEntry(U object);
	public boolean deleteEntry(U object);
	public boolean updateEntry(T  object);
}
