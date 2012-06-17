package com.android.main;

class SongEntity {
	String echonest_id, artist, title;
	float energy, tempo, danceability, duration;
	int id;
	

	public SongEntity() {
		echonest_id = "";
		artist = "";
		title = "";
		energy = 0.0F;
		tempo = 0.0F;
		danceability = 0.0F;
		duration = 0.0F;
	}

	public SongEntity(String echoNestId, String artst, String ttl, float nrgy,
			float tmpo, float dncblty, float drtn) {
		echonest_id = echoNestId;
		artist = artst;
		title = ttl;
		energy = nrgy;
		tempo = tmpo;
		danceability = dncblty;
		duration = drtn;
	}
	
	public int get_id()
	{ return id; }
	
	public void set_id(int ID)
	{ id = ID; }

	public String get_echonest_id() {
		return echonest_id;
	}

	public void set_echonest_id(String name) {
		echonest_id = name;
	}

	public String get_artist() {
		return artist;
	}

	public void set_artist(String name) {
		artist = name;
	}

	public String get_title() {
		return title;
	}

	public void set_title(String name) {
		title = name;
	}

	public float get_energy() {
		return energy;
	}

	public void set_energy(float value) {

	}

	public float get_tempo() {
		return tempo;
	}

	public void set_tempo(float value) {
		tempo = value;
	}

	public float get_danceability() {
		return danceability;
	}

	public void set_danceability(float value) {
		danceability = value;
	}

	public float get_duration() {
		return duration;
	}

	public void set_duration(float value) {
		duration = value;
	}

}