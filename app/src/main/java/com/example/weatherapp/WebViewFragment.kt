package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentRegisterBinding
import com.example.weatherapp.databinding.FragmentWebviewBinding
import com.example.weatherapp.loginFragment.RegisterFragment
import com.example.weatherapp.models.CognitoSettings
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class WebViewFragment:Fragment(R.layout.fragment_webview) {
    private val fragmentWebviewBinding by viewBinding(FragmentWebviewBinding::bind)

    private val TAG = WebViewFragment::class.java.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

   fun init(){
       val USER_AGENT_FAKE = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Mobile Safari/537.36";
       val cognitoSettings= CognitoSettings(requireContext())
       fragmentWebviewBinding.webViewLayout.settings.userAgentString = USER_AGENT_FAKE;
       fragmentWebviewBinding.webViewLayout.settings.loadsImagesAutomatically = true
       fragmentWebviewBinding.webViewLayout.settings.javaScriptEnabled = true
       fragmentWebviewBinding.webViewLayout.settings.domStorageEnabled = true
       fragmentWebviewBinding.webViewLayout.settings.setSupportZoom(true)
       fragmentWebviewBinding.webViewLayout.settings.builtInZoomControls = true
       fragmentWebviewBinding.webViewLayout.settings.displayZoomControls = false
       fragmentWebviewBinding.webViewLayout.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
       fragmentWebviewBinding.webViewLayout.webViewClient = WebViewClient()
       /*fragmentWebviewBinding.webViewLayout.loadUrl("https://${cognitoSettings.poolUrl}" +
               ".amazoncognito.com/oauth2/authorize?identity_provider=Google&redirect_uri=" +
               "myapp://&response_type=CODE&client_id=${cognitoSettings.clientId}" +
               "&scope=email%20openid%20profile%20aws.cognito.signin.user.admin")*/
       val url="https://${cognitoSettings.poolUrl}.amazoncognito.com/oauth2/authorize?identity_provider=Google&response_type=CODE&client_id=${cognitoSettings.clientId}&scope=email%20openid%20profile%20aws.cognito.signin.user.admin"
       //fragmentWebviewBinding.webViewLayout.loadUrl("https://famersapp.auth.ap-south-1.amazoncognito.com/login?client_id=31d0kmedhci14hc1992mqoldar&response_type=code&scope=aws.cognito.signin.user.admin+email+openid+phone+profile&redirect_uri=myapp://")
       fragmentWebviewBinding.webViewLayout.loadUrl(url)

   }
}