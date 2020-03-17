package com.technofreak.projectv14.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.technofreak.projectv14.R
import com.technofreak.projectv14.model.GalleryPicture
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import kotlinx.android.synthetic.main.listitem.view.*


class ListGalleryAdapter(private val list: List<GalleryPicture>) : RecyclerView.Adapter<GVH2>() {

    private lateinit var onClick: (GalleryPicture) -> Unit


    fun setOnClickListener(onClick: (GalleryPicture) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GVH2 {


        val vh = GVH2(LayoutInflater.from(p0.context).inflate(R.layout.listitem2, p0, false))


        vh.containerView.setOnClickListener {
            val position = vh.adapterPosition
            val picture = getItem(position)
            onClick(picture)
        }
        return vh
    }


    private fun getItem(position: Int) = list[position]
    override fun onBindViewHolder(p0: GVH2, p1: Int) {
        when(p0) {

            is GVH2 -> {
                p0.bind(list.get(p1))
            }

        }
      }

    override fun getItemCount() = list.size
}


class GVH2(    override val containerView: View)    : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    val image: ImageView =containerView.image


    fun bind(album: GalleryPicture){


        Log.i("DEBUGme",""+album.path)
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(album.path)
            .into(image)

    }

}