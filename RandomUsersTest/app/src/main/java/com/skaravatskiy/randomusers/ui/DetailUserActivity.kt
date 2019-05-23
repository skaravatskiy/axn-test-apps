package com.skaravatskiy.randomusers.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skaravatskiy.randomusers.R
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.databinding.ActivityDetailUserBinding
import com.skaravatskiy.randomusers.viewmodel.DetailViewModel

class DetailUserActivity : AppCompatActivity() {
    private lateinit var model: DetailViewModel
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.getParcelableExtra(User.EXTRA_USER) as User

        model = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_user)
        model.getData().observe(this, Observer { binding.user = it })
        model.setUser(extras)
    }

}
