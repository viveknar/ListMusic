package com.android.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class EchoNest {
	
	
	private static String API_KEY = null;

	//public static String SIMILAR_SEARCH_URL = "http://developer.echonest.com/api/v4/song/search?api_key=N6E4NIOVYMTHNDM8J&format=json&results=1&";
	
	public static void setAPIKey(String api_key) {
		API_KEY = api_key;
	}
	
	private static JSONObject return_response(String link) throws IOException, JSONException {
		String response = "";
		JSONObject jobj = null;
		try {
			URL url = new URL(link);
			InputStream in = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			for (String line; (line = reader.readLine()) != null;) {
			    response += line;
			}
			in.close();
			jobj = new JSONObject(response);
		} catch (NullPointerException e) {
			System.out.println("Error returning a response because of malformed URL");
		}
		return jobj;
		
	}
	
	public static JSONObject searchSong(String artist, String title) throws IOException, JSONException {
		String link = null;
		try {
			String SONG_SEARCH_URL = "http://developer.echonest.com/api/v4/song/search?api_key="+API_KEY+"&format=json&results=3&bucket=id:7digital-US&bucket=audio_summary&bucket=tracks&";		
			link = SONG_SEARCH_URL+"artist="+URLEncoder.encode(artist)+"&title="+URLEncoder.encode(title);
		} catch(NullPointerException e) {
			System.out.println("API Key is not set");
		}
		return return_response(link);
	}
	
	public static JSONObject similarSongs(String description) throws IOException, JSONException {
		String link = null;
		try {
			String SONG_SEARCH_URL = "http://developer.echonest.com/api/v4/song/search?api_key="+API_KEY+"&format=json&results=3&bucket=id:7digital-US&bucket=audio_summary&bucket=tracks&";		
			link = SONG_SEARCH_URL+description;
		} catch(NullPointerException e) {
			System.out.println("API Key is not set");
		}
		return return_response(link);
	}
	
	public static JSONObject analyzeTrack(String echonestID) throws IOException, JSONException {
		String link = null;
		try {
			String ANALYZE_TRACK_URL = "http://developer.echonest.com/api/v4/track/profile?api_key="+API_KEY+"&format=json&bucket=audio_summary&";
			link = ANALYZE_TRACK_URL+"id="+URLEncoder.encode(echonestID);
		} catch(NullPointerException e) {
			System.out.println("API Key is not set");
		}
		return return_response(link);
	}

	
	public static SongEntity get_song_details(String artist, String title) throws IOException, JSONException {
		@SuppressWarnings("deprecation")
		JSONObject response = searchSong(artist, title);		
		response = response.getJSONObject("response");
		SongEntity song = new SongEntity();
		JSONArray songs = response.getJSONArray("songs");
		for (int i = 0;i < songs.length();i++) {
			JSONObject c = songs.getJSONObject(i);
			song.set_artist(c.getString("artist_name"));
			song.set_title(c.getString("title"));
			try {
				JSONArray tracks = c.getJSONArray("tracks");
				song.set_echonest_id(tracks.getJSONObject(0).getString("id"));
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}	
		
		song = get_metadata(song);
		return song;
	}
	
	private static SongEntity get_metadata(SongEntity song) throws IOException, JSONException {		
		if(song.get_echonest_id()!="")
		{
		@SuppressWarnings("deprecation")
		JSONObject response = analyzeTrack(song.get_echonest_id());
		response = response.getJSONObject("response");
		song.set_danceability(new Float(response.getJSONObject("track").getJSONObject("audio_summary").getLong("danceability")));
		song.set_duration(new Float(response.getJSONObject("track").getJSONObject("audio_summary").getLong("duration")));
		song.set_energy(new Float(response.getJSONObject("track").getJSONObject("audio_summary").getLong("energy")));
		song.set_tempo(new Float(response.getJSONObject("track").getJSONObject("audio_summary").getLong("tempo")));
		}
		return song;
	}
	
	@SuppressWarnings("deprecation")
	public static SongEntity get_similar_song(String description) throws IOException, JSONException {
		String[] words = description.split(" ");
		String params = "";
		for (String word : words) {
			word = word.replaceAll("[!@#$%^&*()-_,.]", "");
			params += "description="+URLEncoder.encode(word)+"&";
		}
		JSONObject response = similarSongs(params);
		response = response.getJSONObject("response");
		SongEntity song = new SongEntity();
		JSONArray songs = response.getJSONArray("songs");
		for (int i = 0;i < songs.length();i++) {
			JSONObject c = songs.getJSONObject(i);
			song.set_artist(c.getString("artist_name"));
			song.set_title(c.getString("title"));
			try {
				JSONArray tracks = c.getJSONArray("tracks");
				song.set_echonest_id(tracks.getJSONObject(0).getString("id"));
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}
		song = get_metadata(song);
		return song;
		
	}
}