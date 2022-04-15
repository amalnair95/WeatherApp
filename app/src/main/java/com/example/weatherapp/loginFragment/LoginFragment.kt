package com.example.weatherapp.loginFragment

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.example.weatherapp.databaseFiles.DatabaseHelper
import com.example.weatherapp.databinding.FragmentLoginBinding
import com.example.weatherapp.isNullOrEmpty
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
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 100
    var value = ""
    var isRegister: Boolean = false

    private var dbHelper: DatabaseHelper? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "On create view started..")
        //CommonMethod.loadLocaleLang(requireContext())
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // CommonMethod.backButtonCode(view)
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        CommonMethod.loadLocaleLang(requireContext())
        inflater.inflate(R.menu.main,menu)
        val userProfileItem = menu.findItem(R.id.userProfile)
        userProfileItem.isVisible = false
        val scannerItem = menu.findItem(R.id.scanner)
        scannerItem.isVisible = false
        val logoutProfileItem = menu.findItem(R.id.logoutProfile)
        logoutProfileItem.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }*/

   /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appTranslate->{
                CommonMethod.showLanguageChange(requireContext(),requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    private fun init() {
        fragmentLoginBinding.touchLayout.setOnTouchListener { view, motionEvent ->
            Log.d(TAG, "Frame layout touch event found")
            CommonMethod.hideKeyboard(fragmentLoginBinding.root, requireActivity())
            false
        }

        dbHelper = DatabaseHelper(requireContext())
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
                Log.d(TAG, "expiry date ${userSession?.accessToken?.expiration}")
                val refreshToken = userSession?.refreshToken?.token
                val checkBiQuery = dbHelper!!.ExecuteBiQuery("Select * from TokenDetails")
                lateinit var password: String
                if (checkBiQuery.isNullOrEmpty()) {
                    Log.d(TAG, "No data")
                    if (fragmentLoginBinding.passwordEditText.text.toString() != "") {
                        val checkInsert = dbHelper!!.insertTokenData(
                            refreshToken,
                            value,
                            fragmentLoginBinding.passwordEditText.text.toString()
                        )
                        if (checkInsert == true) {
                            Log.d(TAG, "data inserted")
                        } else {
                            Log.d(TAG, "data not inserted")
                        }
                        val checkBiQuery2 = dbHelper!!.ExecuteBiQuery("Select * from TokenDetails")
                        password = checkBiQuery2.getString(3)
                    }
                } else {
                    Log.d(TAG, "data ${checkBiQuery.columnCount}")
                    if (checkBiQuery.getString(1) == null) {
                        val insertRefreshToken =
                            dbHelper!!.ExecuteBiQuery("Update TokenDetails SET RefreshToken='$refreshToken' where UserName='$value'")
                    }
                    password = checkBiQuery.getString(3)
                }
                if (password != null) {
                    if (password.equals(fragmentLoginBinding.passwordEditText.text.toString())) {
                        Log.d(TAG, "Login Successful")
                        val args = Bundle()
                        args.putString("@USERNAME", value)
                        args.putString("@PROGRESSBAR", "50")
                        Navigation.findNavController(fragmentLoginBinding.root)
                            .navigate(R.id.action_login_to_farmer_details, args)
                    } else {
                        CommonMethod.loadPopUp("Incorrect Password", requireContext())
                    }
                } else {
                    CommonMethod.loadPopUp("Incorrect Password", requireContext())
                }
            }

            override fun getAuthenticationDetails(
                authenticationContinuation: AuthenticationContinuation, userId: String?
            ) {
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
                CommonMethod.loadPopUp(
                    exception?.message.toString().substringBefore("("),
                    requireContext()
                )
            }

        }
        fragmentLoginBinding.loginButton.setOnClickListener {
            val cognitoSettings = CognitoSettings(requireContext())
            value = CommonMethod.getUserNameForAuthentication(
                fragmentLoginBinding.userNameEditText,
                requireActivity()
            )
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
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
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
                Log.d(TAG, "Person Name: $personName")
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}