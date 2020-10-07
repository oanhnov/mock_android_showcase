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
import kotlinx.android.synthetic.main.fragment_inspire_list_item.view.*
import kotlin.random.Random


internal class InspireYourselfAdapter : RecyclerView.Adapter<InspireYourselfAdapter.MyViewHolder>() {

    var albums: List<AlbumDomainModel> by observer(listOf()) {
        notifyDataSetChanged()
    }
    val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5,
        R.drawable.avatar6,
        R.drawable.avatar7
    )

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
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(it)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                            val resizedBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width/2, bitmap.height/2, false)
                            itemView.imgItemCoverAlbum.setImageBitmap(resizedBitmap)
                            var random = Random.nextInt(7)
                            itemView.imgItemAvatar.setImageResource(avatars[random])
                            itemView.txtItemNameInspire.text = name
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
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
            itemView.imgItemAvatar.visibility = View.GONE
            itemView.txtItemNameInspire.visibility = View.GONE
        }
    }
}
