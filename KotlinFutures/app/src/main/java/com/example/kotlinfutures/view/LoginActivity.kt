package com.example.kotlinfutures.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kotlinfutures.R
import com.example.kotlinfutures.model.Encryption
import com.example.kotlinfutures.model.User.Companion.EXTRA_ENCRYPT
import com.example.kotlinfutures.model.User.Companion.EXTRA_LOGIN
import kotlinx.android.synthetic.main.login_activity.btnSignIn
import kotlinx.android.synthetic.main.login_activity.etLogin
import kotlinx.android.synthetic.main.login_activity.etPassword

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        btnSignIn.setOnClickListener {
            val intent = Intent(this@LoginActivity, DetailActivity::class.java)
            val encryptPass =
                Encryption.encrypt(etPassword.text.toString(), etLogin.text.toString())
            intent.putExtra(EXTRA_ENCRYPT, encryptPass)
            intent.putExtra(EXTRA_LOGIN, etLogin.text.toString())
            startActivity(intent)
        }
    }
}