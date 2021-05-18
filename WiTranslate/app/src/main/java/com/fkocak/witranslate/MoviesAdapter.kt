package com.fkocak.witranslate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fkocak.witranslate.config.Config
import com.fkocak.witranslate.deleteAfter.model.Movie
import com.fkocak.witranslate.deleteAfter.utils.Genre

class MoviesAdapter(private val context: Context, private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
            val tvGenre = itemView.findViewById<TextView>(R.id.tvGenre)
            val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)

            tvTitle.text = movie.title
            Glide.with(context).load(Config.IMAGE_URL + movie.poster_path)
                .apply(RequestOptions().override(400, 400).centerInside()).into(ivPoster)
            tvGenre.text = Genre.getGenre(movie.genre_ids)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(context, view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateData(newList: List<Movie>) {
        list.clear()
        val sortedList = newList.sortedBy { movie -> movie.popularity }
        list.addAll(sortedList)
        notifyDataSetChanged()
    }
}