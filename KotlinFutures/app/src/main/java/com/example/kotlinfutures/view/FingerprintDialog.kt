package com.example.kotlinfutures.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinfutures.R
import com.example.kotlinfutures.databinding.DialogFingerprintBinding
import com.example.kotlinfutures.model.local.FingerDialog
import com.example.kotlinfutures.model.common.FingerprintHelper
import com.example.kotlinfutures.viewmodel.LoginViewModel

class FingerprintDialog : DialogFragment(), Callback {
    private lateinit var helper: FingerprintHelper
    private lateinit var fingerDialog: FingerDialog
    private lateinit var binding: DialogFingerprintBinding
    private lateinit var model: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fingerprint, container, false)
        binding.fingerprint = defaultDialog
        fingerDialog = defaultDialog
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCancel.setOnClickListener { dismiss() }
        model = activity?.run { ViewModelProviders.of(this).get(LoginViewModel::class.java) }!!
        helper = FingerprintHelper(FingerprintManagerCompat.from(this.context!!), this)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        helper.cryptoInstance()?.let { helper.startListening(it) }
    }

    override fun onPause() {
        super.onPause()
        helper.stopListening()
    }

    override fun onAuthenticated() {
        binding.fingerprint = successDialog
        model.isLogin(true)
        dismiss()
    }

    override fun onError(message: CharSequence?) {
        binding.fingerprint = errorDialog(message.toString())
    }

    companion object {

        private val successDialog = FingerDialog("Succeeded", R.drawable.ic_fingerprint, R.color.green)
        private val defaultDialog = FingerDialog("Touch your sensor", R.drawable.ic_fingerprint, R.color.black)

        fun errorDialog(message: String): FingerDialog {
            return FingerDialog(message, R.drawable.ic_fingerprint_error, R.color.red)
        }

        fun newInstance(): FingerprintDialog {
            val args = Bundle()
            return FingerprintDialog().apply { this.arguments = args }
        }
    }
}

