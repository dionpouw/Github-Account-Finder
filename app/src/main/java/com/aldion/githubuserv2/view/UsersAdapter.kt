package com.aldion.githubuserv2.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldion.githubuserv2.R
import com.aldion.githubuserv2.data.database.UserFav
import com.aldion.githubuserv2.data.model.ItemsItem
import com.aldion.githubuserv2.databinding.ItemUserBinding
import com.aldion.githubuserv2.view.ui.detailactivity.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UsersAdapter :
    RecyclerView.Adapter<UsersAdapter.ListViewHolder>() {

    private val results: MutableList<ItemsItem> = ArrayList()
    fun setList(result: List<ItemsItem>) {
        results.clear()
        results.addAll(result)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val bind = ItemUserBinding.bind(itemView)
        fun binding(user: ItemsItem) {
            bind.tvUsername.text = user.login
            bind.tvId.text = user.id.toString()
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(100))
                .into(bind.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding(results[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY, results[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    fun getNoteAt(position: Int):UserFav {
        return UserFav(results[position].id,results[position].login,results[position].avatarUrl)
    }
 override fun getItemCount(): Int {
        return results.size
    }
}