package com.example.tempconuhacks6.connectors;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tempconuhacks6.Playlist;
import com.example.tempconuhacks6.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaylistService {
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private ArrayList<Song> songs = new ArrayList<>();
    private Playlist createdPlaylist;

    private ArrayList<Song> recommendedSongs = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private RequestQueue queue;

    public PlaylistService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public ArrayList<Playlist> getPlaylists(final VolleyCallBack callBack) {
        String endpoint = "https://api.spotify.com/v1/me/playlists";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            //object = object.optJSONObject("track");
                            Playlist playlist = gson.fromJson(object.toString(), Playlist.class);
                            playlists.add(playlist);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error
                    Log.e("API", "ERROR playlist  " + error.toString());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        return playlists;
    }


    public ArrayList<Song> getPlaylistSongs() {
        return songs;
    }
    public ArrayList<Song> getPlaylistSongs(String endpoint, final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            object = object.optJSONObject("track");
                            Song song = gson.fromJson(object.toString(), Song.class);
                            songs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error
                    Log.e("API", "ERROR " + error.toString());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        return songs;
    }

    public ArrayList<Song> getRecommendedSongs() {
        return recommendedSongs;
    }
    public ArrayList<Song> getRecommendedSongs(String endpoint, final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("tracks");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            Song song = gson.fromJson(object.toString(), Song.class);
                            recommendedSongs.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error
                    Log.e("API", "ERROR " + error.toString());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        return recommendedSongs;
    }

    public Playlist createPlaylist(){ return createdPlaylist; }
    public Playlist createPlaylist(String endpoint, final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, endpoint, null, response -> {
                    Gson gson = new Gson();
                    createdPlaylist = gson.fromJson(response.toString(), Playlist.class);
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error
                    Log.e("API", "ERROR " + error.toString());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                String requestBody = "{" +
                        "  \"name\": \"Playlist by ConUHacks 6\"," +
                        "  \"description\": \"Playlist made by our Spotify recommender. Made @ ConUHacks 6.\"," +
                        "  \"public\": false" +
                        "}";

                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        return createdPlaylist;
    }

    public void addItemsToPlaylist(String endpoint, final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, endpoint, null, response -> {}, error -> {
                    // TODO: Handle error
                    Log.e("API", "ERROR " + error.toString());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                String requestBody = "{" +
                        "  \"name\": \"Playlist by ConUHacks 6\"," +
                        "  \"description\": \"Playlist made by our Spotify recommender. Made @ ConUHacks 6.\"," +
                        "  \"public\": false" +
                        "}";

                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
