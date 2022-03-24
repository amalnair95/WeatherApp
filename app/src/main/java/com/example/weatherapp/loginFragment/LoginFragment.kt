package com.example.weatherapp.loginFragment

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.example.weatherapp.R
import com.example.weatherapp.commonMethod.CommonMethod
import com.example.weatherapp.databinding.FragmentLoginBinding
import com.example.weatherapp.models.CognitoSettings
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class LoginFragment : Fragment(R.layout.fragment_login) {
    private val fragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    private val TAG = LoginFragment::class.java.simpleName
    private lateinit var mGoogleSignInClient:GoogleSignInClient
    private val RC_SIGN_IN=100
    var value=""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       // CommonMethod.backButtonCode(view)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()


        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        //updateUI(account)

        // Set the dimensions of the sign-in button.
        fragmentLoginBinding.signInButton.setSize(SignInButton.SIZE_STANDARD)
        fragmentLoginBinding.signInButton.setOnClickListener {
            signIn()
        }


        fragmentLoginBinding.showPasswordCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked) {
                fragmentLoginBinding.passwordEditText.transformationMethod =
                    PasswordTransformationMethod()
            } else {
                fragmentLoginBinding.passwordEditText.transformationMethod = null
            }
        }

        fragmentLoginBinding.googleLoginButton.setOnClickListener {
            Navigation.findNavController(fragmentLoginBinding.root)
                .navigate(R.id.action_login_to_web_view)
        }

        val authenticationHandler = object : AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                Log.d(TAG, "Login Successful")
                val args = Bundle()
                args.putString("@USERNAME", value)
                args.putString("@PROGRESSBAR","50")
                Navigation.findNavController(fragmentLoginBinding.root).navigate(R.id.action_login_to_farmer_details, args)
            }

            override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation, userId: String?) {
                Log.d(TAG, "inside getAuthenticationDetails")
                val authenticationDetails = AuthenticationDetails(
                    userId,
                    fragmentLoginBinding.passwordEditText.text.toString(),
                    null
                )
                authenticationContinuation.setAuthenticationDetails(authenticationDetails)
                authenticationContinuation.continueTask()
            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
                Log.d(TAG, "inside getMFACode")
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                Log.d(TAG, "inside authenticationChallenge")
            }

            override fun onFailure(exception: Exception?) {
                Log.d(TAG, "Login Failed:${exception?.localizedMessage}")
                CommonMethod.loadPopUp(exception?.message.toString().substringBefore("("), requireContext())
            }

        }
        fragmentLoginBinding.loginButton.setOnClickListener {
            val cognitoSettings = CognitoSettings(requireContext())
            value = CommonMethod.getUserNameForAuthentication(fragmentLoginBinding.userNameEditText,requireActivity())
            val thisUser = cognitoSettings.userPool.getUser(value)
            Log.d(TAG, "Login Button Clicked")
            thisUser.getSessionInBackground(authenticationHandler)
        }

        fragmentLoginBinding.signUpButton.setOnClickListener {
            Navigation.findNavController(fragmentLoginBinding.root)
                .navigate(R.id.action_login_to_register)
        }

        fragmentLoginBinding.forgotPasswordTextView.setOnClickListener {
            Navigation.findNavController(fragmentLoginBinding.root)
                .navigate(R.id.action_login_to_forgot_password)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val acct = GoogleSignIn.getLastSignedInAccount(requireContext())
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                //val personPhoto: Uri? = acct.photoUrl
                Log.d(TAG,"Person Name: $personName")
            }
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.statusCode)
           //updateUI(null)
        }
    }


    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}