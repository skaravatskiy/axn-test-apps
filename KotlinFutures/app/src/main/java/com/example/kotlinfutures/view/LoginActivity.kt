package com.example.kotlinfutures.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import com.example.kotlinfutures.R
import com.example.kotlinfutures.model.common.Encryption
import com.example.kotlinfutures.model.local.User.Companion.EXTRA_ENCRYPT
import com.example.kotlinfutures.model.local.User.Companion.EXTRA_LOGIN
import com.example.kotlinfutures.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var model: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val manager = FingerprintManagerCompat.from(this)
        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        model.getStatus().observe(this, Observer {
            if (it!!) {
                GlobalScope.launch {
                    showDetail()
                }
            }
        })
        if (manager.isHardwareDetected && manager.hasEnrolledFingerprints()) {
            showDialog()
        }

        button_sign_in.setOnClickListener {
            model.isLogin(true)
        }
    }

    private fun showDetail() {
        val intent = Intent(this@LoginActivity, DetailActivity::class.java)
        putExtras(intent)
        finish()
        startActivity(intent)
    }

    private fun putExtras(intent: Intent){
        if (edit_password.text.isNotEmpty() && edit_login.text.isNotEmpty()) {
            val encryptPass =
                Encryption.encrypt(edit_password.text.toString(), edit_login.text.toString())
            intent.putExtra(EXTRA_ENCRYPT, encryptPass)
            intent.putExtra(EXTRA_LOGIN, edit_login.text.toString())
        }
    }
    private fun showDialog() {
        val dialog = FingerprintDialog.newInstance()
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, FingerprintDialog::class.java.simpleName)
    }
}
