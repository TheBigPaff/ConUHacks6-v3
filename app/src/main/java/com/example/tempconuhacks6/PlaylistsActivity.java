package com.example.tempconuhacks6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tempconuhacks6.databinding.ActivityPlaylistsBinding;
import com.example.tempconuhacks6.databinding.PlaylistItemsBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaylistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlaylistsBinding binding = ActivityPlaylistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager2 playlistsViewPager = binding.playlistsVP;

        // get playlist index


        // JUST USE SECOND PLAYLIST INDEX
        PlaylistAdapter adapter =
                new PlaylistAdapter(
                        new ArrayList<>(Arrays.asList(UserData.playlistList.get(1).getSongs()))
                );
        playlistsViewPager.setAdapter(adapter);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlaylistsActivity.this, MainActivity.class));
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo add to playlist
                System.out.println("clicked add to playlist");
            }
        });
    }
}