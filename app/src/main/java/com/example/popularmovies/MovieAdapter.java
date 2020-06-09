package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    final private onMovieClickListener mOnMovieClickListener;
    private Context mContext;
    private ArrayList<MovieDataSrc> mMovieList;

    public MovieAdapter(Context mContext, ArrayList<MovieDataSrc> mMovieList, onMovieClickListener onMovieClickListener) {
        this.mContext = mContext;
        this.mMovieList = mMovieList;
        this.mOnMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_item_list, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieDataSrc movieDataSrc = mMovieList.get(position);
        String img_url = movieDataSrc.getmImgUrl();
        Picasso.get()
                .load(img_url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.noimg).fit()
                .centerInside()
                .into(holder.mMovieImage);
    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        }
        return 0;
    }

    public interface onMovieClickListener {
        void onMovieItemClickListener(int pos);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mMovieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieImage = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnMovieClickListener.onMovieItemClickListener(position);
        }
    }
}
