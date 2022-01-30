package com.example.tempconuhacks6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.tempconuhacks6.connectors.ArtistService;
import com.example.tempconuhacks6.connectors.PlaylistService;
import com.example.tempconuhacks6.connectors.SongService;
import com.example.tempconuhacks6.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;

    private ViewPager2 viewPager2 = null;


    private PlaylistService playlistService;
    private ArrayList<Song> songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViewPager(binding);

        binding.controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PlaylistsActivity.class));
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        UserData.user_id = sharedPreferences.getString("userid", "No User");

        playlistService = new PlaylistService(getApplicationContext());
    }

    private void setupViewPager(ActivityMainBinding binding){
        SelectionAdapter adapter = new SelectionAdapter(UserData.playlistList);
        viewPager2 = binding.viewPager;
        viewPager2.setAdapter(adapter);
        // Register callback
        binding.dotsIndicator.setViewPager2(viewPager2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister callback
    }


    // API STUFF

    /*
    EXAMPLE:
    https://api.spotify.com/v1/recommendations?
    limit=10&
    seed_artists=5AWtYkPhj1X2cECuTUfq7Z&
    seed_genres=belgian%20pop&
    seed_tracks=3I756vFQ1PWvG2Q2jJsIkA%2C7HEKgyGKb9oil5ZrW7cHH4%2C2i05vSN6Qp14HGI4fc1M14*/
    private void getRecommendations(int playlistIndex){
        // split href, get only id (last)
        String artist_href = UserData.playlistList.get(playlistIndex).getArtists()[0].getHref();
        String artist = artist_href.substring(artist_href.lastIndexOf("/") + 1);
        String genre = UserData.playlistList.get(playlistIndex).getGenres()[0];
        // get first 3 songs
        String tracks = "";
        for(int i = 0; i < 3; i++){
            tracks += UserData.playlistList.get(playlistIndex).getSongs()[i].getId();
            if(i != 2) tracks += ",";
        }

        String endpoint = "https://api.spotify.com/v1/recommendations?" +
                "limit=10&seed_artists="+artist+"&seed_genres"+genre+"&seed_tracks="+tracks;
        playlistService.getRecommendedSongs(endpoint, () -> {
            songs = playlistService.getRecommendedSongs();
        });
    }

    private void createPlaylist(List<Song> songList){
        String endpoint = "https://api.spotify.com/v1/users/"+UserData.user_id+"/playlists";
        playlistService.createPlaylist(endpoint, () -> {
            Playlist createdPlaylist = playlistService.createPlaylist();
            addItemsToPlaylist(createdPlaylist.getId(), songList);
        });
    }
    private void addItemsToPlaylist(String playlistId, List<Song> songList){
        String tracks = "";
        for(Song song : songList){
            tracks += song.getId() + ",";
        }
        tracks = tracks.substring(0,tracks.length()-1); // remove last comma

        String endpoint = "https://api.spotify.com/v1/playlists/"+playlistId+"/tracks?"+tracks;
        playlistService.addItemsToPlaylist(endpoint, () -> {
            // feedback to the user after creating playlist
        });
    }
}