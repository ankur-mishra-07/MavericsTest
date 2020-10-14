package com.test.mavericstest.ui.movielist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.mavericstest.R
import com.test.mavericstest.data.models.Search

/**
 * [RecyclerView.Adapter] that can display a [MovieItem].
 */
class MyMovieDataRecyclerViewAdapter(listener: MyMovieDataRecyclerViewAdapter.onMovieClicked) :
    RecyclerView.Adapter<MyMovieDataRecyclerViewAdapter.ViewHolder>() {

    private var mMovieList: List<Search> = arrayListOf()
    private lateinit var mContext: Context
    private var listener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mMovieList[position]
        holder.idView.text = item.title
        Glide.with(mContext)
            .load(item.poster)
            .into(holder.itemImage)
        holder.itemView.setOnClickListener{
            listener.onAvatarClicked(item)
        }
    }

    override fun getItemCount(): Int = mMovieList.size
    fun setRefreshData(
        search: List<Search>,
        requireContext: Context
    ) {
        this.mMovieList = search
        this.mContext = requireContext
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_poster_title)
        val itemImage: AppCompatImageView = view.findViewById(R.id.item_poster_post)

        override fun toString(): String {
            return super.toString() + " '" + idView.text + "'"
        }
    }

    interface onMovieClicked {
        fun onAvatarClicked(mMovieItem: Search)
    }
}