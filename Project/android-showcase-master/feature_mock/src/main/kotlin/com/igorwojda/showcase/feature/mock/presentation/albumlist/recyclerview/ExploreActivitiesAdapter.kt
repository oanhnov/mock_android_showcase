package com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.igorwojda.showcase.feature.mock.R
import com.igorwojda.showcase.feature.mock.domain.model.AlbumDomainModel
import com.igorwojda.showcase.library.base.delegate.observer
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_explore_list_item.view.*
import kotlinx.android.synthetic.main.fragment_explore_list_item.view.coverErrorImageView
import kotlinx.android.synthetic.main.fragment_explore_list_item.view.imgItemCoverAlbum
import kotlinx.android.synthetic.main.fragment_inspire_list_item.view.*


internal class ExploreActivitiesAdapter : RecyclerView.Adapter<ExploreActivitiesAdapter.MyViewHolder>() {

    var albums: List<AlbumDomainModel> by observer(listOf()) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_explore_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size


    internal inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private var url by observer<String?>(null) {
            itemView.coverErrorImageView.hide()
            if (it == null) {
                setDefaultImage()
            } else {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(it)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                            val resizedBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, false)
                            itemView.imgItemCoverAlbum.setImageBitmap(resizedBitmap)
                            itemView.txtItemArtistAlbum.text = artist
                            itemView.txtItemNameAlbum.text = name
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        }
        private var artist = ""
        private var name = ""

        fun bind(albumDomainModel: AlbumDomainModel) {
            url = albumDomainModel.getDefaultImageUrl()
            artist = albumDomainModel.artist
            name = albumDomainModel.name
        }

        private fun setDefaultImage() {
            itemView.coverErrorImageView.show()
            itemView.txtItemArtistAlbum.visibility = View.GONE
            itemView.txtItemNameInspire.visibility = View.GONE
        }
    }
}
