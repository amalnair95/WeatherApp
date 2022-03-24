package com.example.weatherapp.loginFragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.example.weatherapp.models.CognitoSettings
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class VerificationViewModel : ViewModel() {
    private val TAG = VerificationViewModel::class.java.simpleName
    var mutableResultData = MutableLiveData<String?>()
    var mutableResendCodeData = MutableLiveData<String?>()

    fun clearResultSet() {
        mutableResultData = MutableLiveData()
        mutableResendCodeData = MutableLiveData()
    }

    fun resendVerificationCode(cognitoUser: CognitoUser) {
        val result = mutableListOf<String>()
        Completable.fromAction {
            val resendConfCodeHandler=object :VerificationHandler{
                override fun onSuccess(verificationCodeDeliveryMedium: CognitoUserCodeDeliveryDetails) {
                    result.add(0,"Confirmation Code was Successfully sent to :${verificationCodeDeliveryMedium.destination}")
                }

                override fun onFailure(exception: Exception) {
                    result.add(0,"Failure:${exception.message.toString().substringBefore("(")}")
                }
            }
            cognitoUser.resendConfirmationCode(resendConfCodeHandler)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Log.d(TAG, "Confirmation result: $result")
                    mutableResendCodeData.postValue(result[0])
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "Error Occurred:${e.localizedMessage}")
                }

            })
    }

    fun getConfirmationCallback(context: Context, verificationCode: String, userName: String) {
        val result = mutableListOf<String>()
        Completable.fromAction {
            val confirmationCallback = object : GenericHandler {
                override fun onSuccess() {
                    result.add(0,"Success")
                }
                override fun onFailure(exception: Exception) {
                    result.add(0,"Failure:${exception.message.toString().substringBefore("(")}")
                }
            }
            val cognitoSettings = CognitoSettings(context)
            val thisUser = cognitoSettings.userPool.getUser(userName)
            thisUser.confirmSignUp(verificationCode, false, confirmationCallback)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Log.d(TAG, "Confirmation result: $result")
                    mutableResultData.postValue(result[0])

                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "Error Occurred:${e.localizedMessage}")
                }

            })

    }
}