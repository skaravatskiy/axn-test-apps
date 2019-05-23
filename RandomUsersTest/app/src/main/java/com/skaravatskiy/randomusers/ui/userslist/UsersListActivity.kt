package com.skaravatskiy.randomusers.ui.userslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.skaravatskiy.randomusers.R
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.ui.DetailUserActivity
import com.skaravatskiy.randomusers.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.activity_users_list.*

class UsersListActivity : AppCompatActivity() {
    private lateinit var model: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        model = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        val adapter = UsersListAdapter(object : OnItemClickListener {
            override fun onItemClicked(user: User) {
                val intent =
                    Intent(this@UsersListActivity, DetailUserActivity::class.java).apply {
                        putExtra(User.EXTRA_USER, user)
                    }
                startActivity(intent)
            }
        })
        recycler_users_list.adapter = adapter
        recycler_users_list.layoutManager = LinearLayoutManager(this)
        model.getData().observe(this, Observer {
            if (it != null) adapter.setUsersList(it)
        })
        model.requestUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.item_name_sort -> model.sortBy(Sort.NAME)
            R.id.item_age_sort -> model.sortBy(Sort.AGE)
            R.id.item_location_sort -> model.sortBy(Sort.LOCATION)
        }
        return super.onOptionsItemSelected(item)
    }
}

enum class Sort {
    NAME, AGE, LOCATION
}
