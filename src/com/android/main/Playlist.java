package com.android.main;

class Playlist {
	private int playlistId;
	private String name;
	private int numSongs;
	private String songListTableName;
	
	public Playlist(String nm, int nmsngs, String snglsttblnm)
	{
		name = nm;
		numSongs = nmsngs;
		songListTableName = snglsttblnm;
	}
	
	public String getName()
	{ return name; }
	
	public int getNumSongs()
	{ return numSongs; }
	
	public String getSongListTableName()
	{ return songListTableName; }
	
	public void setName(String nm)
	{ name = nm; }
	
	public void setNumSongs(int nmsngs)
	{ numSongs = nmsngs; }
	
	public void setSongListTableName(String snglsttblnm)
	{ songListTableName = snglsttblnm; }
}