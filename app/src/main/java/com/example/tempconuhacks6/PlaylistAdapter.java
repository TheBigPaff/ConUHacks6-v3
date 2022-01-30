package com.example.tempconuhacks6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tempconuhacks6.databinding.PlaylistItemsBinding;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private List<Song> songs;
    public PlaylistAdapter(ArrayList<Song> songs) {
        this.songs = songs;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaylistViewHolder(PlaylistItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bindItem(songs.get(position));
    }

    @Override
    public int getItemCount() {
        if(songs != null)
            return songs.size();
        else
            return 0;
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder{
        PlaylistItemsBinding binding;

        public PlaylistViewHolder(PlaylistItemsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindItem(Song song){
            binding.artistText.setText((CharSequence) song.getArtists()[0]);

            new DownLoadImageTask(binding).execute(song.getAlbum().getImages()[0].getUrl());
        }
    }

    private static class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        PlaylistItemsBinding binding;

        public DownLoadImageTask(PlaylistItemsBinding binding){
            this.binding = binding;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            binding.coverImageView.setImageBitmap(result);
        }
    }
}
