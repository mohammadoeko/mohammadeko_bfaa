package com.dicoding.picodiploma.mohammadeko.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.mohammadeko.DetailActivity
import com.dicoding.picodiploma.mohammadeko.R
import com.dicoding.picodiploma.mohammadeko.model.DataUsers
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_row_user.view.*

var followingFilterList = ArrayList<DataUsers>()

class ListDataFollowingAdapter(listData: ArrayList<DataUsers>) :
    RecyclerView.Adapter<ListDataFollowingAdapter.ListDataHolder>() {

    init {
        followingFilterList = listData
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.avatar
        var name: TextView = itemView.user_name
        var username: TextView = itemView.username
        var company: TextView = itemView.work_in
        var location: TextView = itemView.locationn
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(DataUsers: DataUsers)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListDataHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_user, viewGroup, false)
        val sch = ListDataHolder(view)
        mcontext = viewGroup.context
        return sch
    }

    override fun getItemCount(): Int {
        return followingFilterList.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val data = followingFilterList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imageAvatar)
        holder.name.text = data.name
        holder.username.text = data.username
        holder.company.text = data.company
        holder.location.text = data.location

            holder.itemView.setOnClickListener {
                val dataUser = DataUsers(
                    data.username,
                    data.name,
                    data.avatar,
                    data.company,
                    data.location,
                    data.repository,
                    data.followers,
                    data.following,
                    data.isFavorite
                )
                val intentDetail = Intent(mcontext, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_DATA, dataUser)
                intentDetail.putExtra(DetailActivity.EXTRA_FAVORITE, dataUser)
                mcontext.startActivity(intentDetail)
            }

        }
    }
