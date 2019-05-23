package com.skaravatskiy.randomusers.ui.userslist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.databinding.ItemUsersListBinding

class UsersListAdapter(onItemClickListener: OnItemClickListener) : Adapter<UserListHolder>() {

    private val usersList: MutableList<User> = mutableListOf()
    private var listener: OnItemClickListener = onItemClickListener

    fun setUsersList(usersList: List<User>) {
        this.usersList.clear()
        this.usersList.addAll(usersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(group: ViewGroup, type: Int): UserListHolder {
        val inflater = LayoutInflater.from(group.context)
        val binding = ItemUsersListBinding.inflate(inflater, group, false)
        return UserListHolder(binding)
    }

    override fun getItemCount(): Int = usersList.size

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        holder.binding.user = usersList[position]
        holder.binding.layoutCard.setOnClickListener { listener.onItemClicked(usersList[position]) }
        holder.binding.executePendingBindings()
    }
}

interface OnItemClickListener {
    fun onItemClicked(user: User)
}

class UserListHolder(item: ItemUsersListBinding) : ViewHolder(item.root) {
    var binding: ItemUsersListBinding = DataBindingUtil.bind(item.root)!!
}
