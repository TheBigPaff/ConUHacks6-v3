package com.example.tempconuhacks6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.tempconuhacks6.connectors.ArtistService;
import com.example.tempconuhacks6.connectors.PlaylistService;
import com.example.tempconuhacks6.connectors.SongService;
import com.example.tempconuhacks6.connectors.UserService;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.util.ArrayList;


public class SplashActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;

    private RequestQueue queue;

    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "conuhacks6-login://callback";
    private static final String CLIENT_ID = "c4ce0b8af1984fdabddcdae01b394c7c";
    private static final String SCOPES = "user-read-recently-played," +
            "user-read-email,user-read-private,playlist-read-private";

    /* STUFF FOR APIS */
    private ArtistService artistService;
    private ArrayList<String> genres;

    private PlaylistService playlistService;
    private ArrayList<Playlist> playlists;

    private SongService songService;
    private ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        authenticateSpotify();

        msharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(this);


        songService = new SongService(getApplicationContext());
        playlistService = new PlaylistService(getApplicationContext());
        artistService = new ArtistService(getApplicationContext());

        getPlaylists();
    }

    private void authenticateSpotify() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{SCOPES});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response

                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("STARTING", "GOT AUTH TOKEN");
                    editor.apply();
                    waitForUserInfo();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    private void waitForUserInfo() {
        UserService userService = new UserService(queue, msharedPreferences);
        userService.get(() -> {
            User user = userService.getUser();
            editor = getSharedPreferences("SPOTIFY", 0).edit();
            editor.putString("userid", user.id);
            Log.d("STARTING", "GOT USER INFORMATION");
            // We use commit instead of apply because we need the information stored immediately
            editor.commit();
        });
    }

    private void startMainActivity() {
        Intent newIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(newIntent);
    }

    /* API STUFF */

    private void getPlaylists(){
        playlistService.getPlaylists(() -> {
            playlists = playlistService.getPlaylists();
            UserData.playlistList = playlists;
            for(int i = 0; i < UserData.playlistList.size(); i++){
                getSongs(i);
            }
        });
    }

    private void getSongs(int playlistIndex) {
        String endpoint = UserData.playlistList.get(playlistIndex).getTracks().getHref();
        playlistService.getPlaylistSongs(endpoint, () -> {
            songs = playlistService.getPlaylistSongs();
            UserData.playlistList.get(playlistIndex).setSongs(songs.toArray(new Song[0]));

            // NOW SET THE PLAYLIST GENRES AND ARTISTS GIVEN ONLY THE FIRST SONG OF THE PLAYLIST
            UserData.playlistList.get(playlistIndex).setArtists(UserData.playlistList.get(playlistIndex).getSongs()[0].getArtists());
            getGenres(playlistIndex, 0);
        });
    }

    private void getGenres(int playlistIndex, int songIndex){
        String endpoint = UserData.playlistList.get(playlistIndex).getSongs()[songIndex].getArtists()[0].getHref();
        UserData.playlistList.get(playlistIndex).setGenres(new String[]{"pop"});


        // check if the last playlist is done
        if(playlistIndex == UserData.playlistList.size() - 1){
            startMainActivity();
        }

        // TODO FIX THIS LMAOOO
        /*artistService.getArtistGenres(endpoint, () -> {
            Artist artist = artistService.getArtistGenres();
            //genres = artistService.getArtistGenres();
            *//*if(artist.getGenres().length == 0){
                getGenres(playlistIndex, songIndex+1);
                UserData.playlistList.get(playlistIndex).setGenres(artist.getGenres());
            }*//*
            UserData.playlistList.get(playlistIndex).setGenres(artist.getGenres());
        });*/
    }
}