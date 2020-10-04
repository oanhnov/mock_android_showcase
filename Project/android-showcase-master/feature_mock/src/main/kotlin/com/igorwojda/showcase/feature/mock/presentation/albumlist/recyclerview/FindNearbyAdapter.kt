package com.igorwojda.showcase.feature.mock.presentation.albumlist.recyclerview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorwojda.showcase.feature.mock.R
import com.igorwojda.showcase.feature.mock.domain.model.AlbumDomainModel
import com.igorwojda.showcase.library.base.delegate.observer
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_inspire_list_item.view.*
import kotlin.random.Random


internal class FindNearbyAdapter : RecyclerView.Adapter<FindNearbyAdapter.MyViewHolder>() {

    var albums: List<AlbumDomainModel> by observer(listOf()) {
        notifyDataSetChanged()
    }
    val avatars = listOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6, R.drawable.avatar7)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_inspire_list_item, parent, false)
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
                Picasso.get().load(it).into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        itemView.imgItemCoverAlbum.setImageBitmap(bitmap)
                        var random = Random.nextInt(7)
                        itemView.imgItemAvatar.setImageResource(avatars[random])
                        itemView.txtItemNameInspire.text = name

                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        itemView.imgItemCoverAlbum.setImageResource(R.drawable.ic_image)
                    }
                })
            }
        }
        private var name = ""

        fun bind(albumDomainModel: AlbumDomainModel) {
            url = albumDomainModel.getDefaultImageUrl()
            name = albumDomainModel.name
        }

        private fun setDefaultImage() {
            itemView.coverErrorImageView.show()

        }
    }
}
