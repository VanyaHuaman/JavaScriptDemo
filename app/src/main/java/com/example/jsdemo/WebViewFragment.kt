package com.example.jsdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jsdemo.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private var binding: FragmentWebViewBinding? = null
    val webViewFragmentViewModel: WebViewFragmentViewModel by viewModels()
    val webViewClient = object : WebViewClient() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(layoutInflater)
        val view = binding?.root
        binding?.fragmentWebView?.webViewClient = webViewClient
        binding?.fragmentWebView?.settings?.javaScriptEnabled = true
        binding?.fragmentWebView?.addJavascriptInterface(
            WebAppInterface(requireContext()),
            ANDROID
        )

        binding?.loadButton?.setOnClickListener {
            binding?.fragmentWebView?.loadUrl(binding?.urlText?.text.toString() ?: "")
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        binding?.fragmentWebView?.loadUrl("http://10.0.2.2:3000/custom")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ANDROID = "Android"
    }
}