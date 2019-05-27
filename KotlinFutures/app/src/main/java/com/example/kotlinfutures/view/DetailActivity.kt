package com.example.kotlinfutures.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kotlinfutures.R
import com.example.kotlinfutures.databinding.ActivityDetailBinding
import com.example.kotlinfutures.model.common.Encryption
import com.example.kotlinfutures.model.local.User
import com.example.kotlinfutures.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var model: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        binding = setContentView(this, R.layout.activity_detail)
        model.getData().observe(this, Observer { binding.user = it })
        model.loadData()
        if (intent.getSerializableExtra(User.EXTRA_ENCRYPT) != null) {
            val extras = intent.getSerializableExtra(User.EXTRA_ENCRYPT) as HashMap<String, ByteArray>
            val loginExt = intent.getStringExtra(User.EXTRA_LOGIN)
            val decrypt = Encryption.decrypt(extras, loginExt)
        }
    }
}
