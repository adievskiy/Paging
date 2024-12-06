package com.example.pagging.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagging.databinding.ItemListBinding
import com.example.pagging.model.movie.Movie

class MoviePagingAdapter : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(DIFF_UTIL) {


    var onCLick: ((String) -> Unit)? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun onMovieClick(listener: (String) -> Unit) {
        onCLick = listener
    }

    inner class MovieViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        //holder.binding.setVariable(com.example.moviedb.BR.movie, getItem(position))
        holder.binding.movieTitle.text = getItem(position)?.Title
        holder.binding.movieDescription.text = getItem(position)?.Year
        Glide.with(holder.binding.root.context)
            .load(getItem(position)?.Poster)
            .into(holder.binding.movieImage)

        holder.binding.root.setOnClickListener {
            onCLick?.let {
                it(getItem(position)!!.imdbID)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}