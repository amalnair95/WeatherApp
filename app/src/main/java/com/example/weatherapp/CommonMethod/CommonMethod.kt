package com.example.weatherapp.commonMethod

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.example.weatherapp.R
import com.example.weatherapp.loginFragment.RegisterFragment
import com.example.weatherapp.models.Coordinates

class CommonMethod {

    companion object{
        private val TAG = CommonMethod::class.java.simpleName
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        fun isNetworkConnected(context: Context): Boolean {
            return if (isNetworkAvailable(context)) {
                true
            } else {
                showAlert(context)
                false
            }
        }

        private fun showAlert(context: Context?) {
            val alertDialog = AlertDialog.Builder(context)

            //Setting Dialog Title
            alertDialog.setTitle(R.string.NetworkPermission)

            //Setting Dialog Message
            alertDialog.setMessage(R.string.NetworkConnection)

            //On pressing cancel button
            alertDialog.setNegativeButton(R.string.ok) { dialog, _ -> dialog.cancel() }
            alertDialog.show()
        }

        fun getLocation(context: Context) : Coordinates {
            var gpsTracker: GPSTracker? = null
            val coordinates=Coordinates()
            gpsTracker = GPSTracker(context)
            if (gpsTracker.isGPSTrackingEnabled) {
                coordinates.latitude = gpsTracker.getCurrentLatitude()
                coordinates.longitude = gpsTracker.getCurrentLongitude()
                coordinates.address = gpsTracker.getAddressLine(context).toString()
                val postalCode = gpsTracker.getPostalCode(context)
                val country = gpsTracker.getCountryName(context)
                println("Latitude:${coordinates.latitude} & Longitude:${coordinates.longitude}")
                //weatherViewModel.clearResultSet()
                //weatherViewModel.getWeatherDetail(latitude,longitude)

            }
            return coordinates
        }

        fun loadPopUp(stringMessage:String,context: Context) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.load_popup)
            val txt: TextView = dialog.findViewById(R.id.messageTextView)
            val dismissButton: ImageButton =dialog.findViewById(R.id.dismissButton)
            txt.movementMethod = ScrollingMovementMethod()
            txt.text = stringMessage
            dialog.show()
            dismissButton.setOnClickListener {
                dialog.dismiss()
            }
        }

        fun backButtonCode(view: View){
            view.isFocusableInTouchMode=true
            view.requestFocus()
            view.setOnKeyListener(object : View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (event.action === KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            return true
                        }
                    }
                    return false
                }
            })
        }

        fun checkIfStringIsNumber(inputString: String?): Boolean {
            return if (inputString == null || inputString.isEmpty()) {
                false
            } else {
                inputString.matches(Config.REGEX_IS_NUMBER.toRegex())
            }
        }

        fun countryToPhone(isoString:String):String{
            val countryToPhoneMap= mutableMapOf<String,String>()
            countryToPhoneMap["AF"] = "+93"
            countryToPhoneMap["AL"] = "+355"
            countryToPhoneMap["DZ"] = "+213"
            countryToPhoneMap["AD"] = "+376"
            countryToPhoneMap["AO"] = "+244"
            countryToPhoneMap["AG"] = "+1-268"
            countryToPhoneMap["AR"] = "+54"
            countryToPhoneMap["AM"] = "+374"
            countryToPhoneMap["AU"] = "+61"
            countryToPhoneMap["AT"] = "+43"
            countryToPhoneMap["AZ"] = "+994"
            countryToPhoneMap["BS"] = "+1-242"
            countryToPhoneMap["BH"] = "+973"
            countryToPhoneMap["BD"] = "+880"
            countryToPhoneMap["BB"] = "+1-246"
            countryToPhoneMap["BY"] = "+375"
            countryToPhoneMap["BE"] = "+32"
            countryToPhoneMap["BZ"] = "+501"
            countryToPhoneMap["BJ"] = "+229"
            countryToPhoneMap["BT"] = "+975"
            countryToPhoneMap["BO"] = "+591"
            countryToPhoneMap["BA"] = "+387"
            countryToPhoneMap["BW"] = "+267"
            countryToPhoneMap["BR"] = "+55"
            countryToPhoneMap["BN"] = "+673"
            countryToPhoneMap["BG"] = "+359"
            countryToPhoneMap["BF"] = "+226"
            countryToPhoneMap["BI"] = "+257"
            countryToPhoneMap["KH"] = "+855"
            countryToPhoneMap["CM"] = "+237"
            countryToPhoneMap["CA"] = "+1"
            countryToPhoneMap["CV"] = "+238"
            countryToPhoneMap["CF"] = "+236"
            countryToPhoneMap["TD"] = "+235"
            countryToPhoneMap["CL"] = "+56"
            countryToPhoneMap["CN"] = "+86"
            countryToPhoneMap["CO"] = "+57"
            countryToPhoneMap["KM"] = "+269"
            countryToPhoneMap["CD"] = "+243"
            countryToPhoneMap["CG"] = "+242"
            countryToPhoneMap["CR"] = "+506"
            countryToPhoneMap["CI"] = "+225"
            countryToPhoneMap["HR"] = "+385"
            countryToPhoneMap["CU"] = "+53"
            countryToPhoneMap["CY"] = "+357"
            countryToPhoneMap["CZ"] = "+420"
            countryToPhoneMap["DK"] = "+45"
            countryToPhoneMap["DJ"] = "+253"
            countryToPhoneMap["DM"] = "+1-767"
            countryToPhoneMap["DO"] = "+1-809"
            countryToPhoneMap["EC"] = "+593"
            countryToPhoneMap["EG"] = "+20"
            countryToPhoneMap["SV"] = "+503"
            countryToPhoneMap["GQ"] = "+240"
            countryToPhoneMap["ER"] = "+291"
            countryToPhoneMap["EE"] = "+372"
            countryToPhoneMap["ET"] = "+251"
            countryToPhoneMap["FJ"] = "+679"
            countryToPhoneMap["FI"] = "+358"
            countryToPhoneMap["FR"] = "+33"
            countryToPhoneMap["GA"] = "+241"
            countryToPhoneMap["GM"] = "+220"
            countryToPhoneMap["GE"]= "+995"
            countryToPhoneMap["DE"]= "+49"
            countryToPhoneMap["GH"]= "+233"
            countryToPhoneMap["GR"]= "+30"
            countryToPhoneMap["GD"]= "+1-473"
            countryToPhoneMap["GT"]= "+502"
            countryToPhoneMap["GN"]= "+224"
            countryToPhoneMap["GW"]= "+245"
            countryToPhoneMap["GY"]= "+592"
            countryToPhoneMap["HT"]= "+509"
            countryToPhoneMap["HN"]= "+504"
            countryToPhoneMap["HU"]= "+36"
            countryToPhoneMap["IS"]= "+354"
            countryToPhoneMap["IN"]= "+91"
            countryToPhoneMap["ID"]= "+62"
            countryToPhoneMap["IR"]= "+98"
            countryToPhoneMap["IQ"]= "+964"
            countryToPhoneMap["IE"]= "+353"
            countryToPhoneMap["IL"]= "+972"
            countryToPhoneMap["IT"]= "+39"
            countryToPhoneMap["JM"]= "+1-876"
            countryToPhoneMap["JP"]= "+81"
            countryToPhoneMap["JO"]= "+962"
            countryToPhoneMap["KZ"]= "+7"
            countryToPhoneMap["KE"]= "+254"
            countryToPhoneMap["KI"]= "+686"
            countryToPhoneMap["KP"]= "+850"
            countryToPhoneMap["KR"]= "+82"
            countryToPhoneMap["KW"]= "+965"
            countryToPhoneMap["KG"]= "+996"
            countryToPhoneMap["LA"]= "+856"
            countryToPhoneMap["LV"]= "+371"
            countryToPhoneMap["LB"]= "+961"
            countryToPhoneMap["LS"]= "+266"
            countryToPhoneMap["LR"]= "+231"
            countryToPhoneMap["LY"]= "+218"
            countryToPhoneMap["LI"]= "+423"
            countryToPhoneMap["LT"]= "+370"
            countryToPhoneMap["LU"]= "+352"
            countryToPhoneMap["MK"]= "+389"
            countryToPhoneMap["MG"]= "+261"
            countryToPhoneMap["MW"]= "+265"
            countryToPhoneMap["MY"]= "+60"
            countryToPhoneMap["MV"]= "+960"
            countryToPhoneMap["ML"]= "+223"
            countryToPhoneMap["MT"]= "+356"
            countryToPhoneMap["MH"]= "+692"
            countryToPhoneMap["MR"]= "+222"
            countryToPhoneMap["MU"]= "+230"
            countryToPhoneMap["MX"]= "+52"
            countryToPhoneMap["FM"]= "+691"
            countryToPhoneMap["MD"]= "+373"
            countryToPhoneMap["MC"]= "+377"
            countryToPhoneMap["MN"]= "+976"
            countryToPhoneMap["ME"]= "+382"
            countryToPhoneMap["MA"]= "+212"
            countryToPhoneMap["MZ"]= "+258"
            countryToPhoneMap["MM"]= "+95"
            countryToPhoneMap["NA"]= "+264"
            countryToPhoneMap["NR"]= "+674"
            countryToPhoneMap["NP"]= "+977"
            countryToPhoneMap["NL"]= "+31"
            countryToPhoneMap["NZ"]= "+64"
            countryToPhoneMap["NI"]= "+505"
            countryToPhoneMap["NE"]= "+227"
            countryToPhoneMap["NG"]= "+234"
            countryToPhoneMap["NO"]= "+47"
            countryToPhoneMap["OM"]= "+968"
            countryToPhoneMap["PK"]= "+92"
            countryToPhoneMap["PW"]= "+680"
            countryToPhoneMap["PA"]= "+507"
            countryToPhoneMap["PG"]= "+675"
            countryToPhoneMap["PY"]= "+595"
            countryToPhoneMap["PE"]= "+51"
            countryToPhoneMap["PH"]= "+63"
            countryToPhoneMap["PL"]= "+48"
            countryToPhoneMap["PT"]= "+351"
            countryToPhoneMap["QA"]= "+974"
            countryToPhoneMap["RO"]= "+40"
            countryToPhoneMap["RU"]= "+7"
            countryToPhoneMap["RW"]= "+250"
            countryToPhoneMap["KN"]= "+1-869"
            countryToPhoneMap["LC"]= "+1-758"
            countryToPhoneMap["VC"]= "+1-784"
            countryToPhoneMap["WS"]= "+685"
            countryToPhoneMap["SM"]= "+378"
            countryToPhoneMap["ST"]= "+239"
            countryToPhoneMap["SA"]= "+966"
            countryToPhoneMap["SN"]= "+221"
            countryToPhoneMap["RS"]= "+381"
            countryToPhoneMap["SC"]= "+248"
            countryToPhoneMap["SL"]= "+232"
            countryToPhoneMap["SG"]= "+65"
            countryToPhoneMap["SK"]= "+421"
            countryToPhoneMap["SI"]= "+386"
            countryToPhoneMap["SB"]= "+677"
            countryToPhoneMap["SO"]= "+252"
            countryToPhoneMap["ZA"]= "+27"
            countryToPhoneMap["ES"]= "+34"
            countryToPhoneMap["LK"]= "+94"
            countryToPhoneMap["SD"]= "+249"
            countryToPhoneMap["SR"]= "+597"
            countryToPhoneMap["SZ"]= "+268"
            countryToPhoneMap["SE"]= "+46"
            countryToPhoneMap["CH"]= "+41"
            countryToPhoneMap["SY"]= "+963"
            countryToPhoneMap["TJ"]= "+992"
            countryToPhoneMap["TZ"]= "+255"
            countryToPhoneMap["TH"]= "+66"
            countryToPhoneMap["TL"]= "+670"
            countryToPhoneMap["TG"]= "+228"
            countryToPhoneMap["TO"]= "+676"
            countryToPhoneMap["TT"]= "+1-868"
            countryToPhoneMap["TN"]= "+216"
            countryToPhoneMap["TR"]= "+90"
            countryToPhoneMap["TM"]= "+993"
            countryToPhoneMap["TV"]= "+688"
            countryToPhoneMap["UG"]= "+256"
            countryToPhoneMap["UA"]= "+380"
            countryToPhoneMap["AE"]= "+971"
            countryToPhoneMap["GB"]= "+44"
            countryToPhoneMap["US"]= "+1"
            countryToPhoneMap["UY"]= "+598"
            countryToPhoneMap["UZ"]= "+998"
            countryToPhoneMap["VU"]= "+678"
            countryToPhoneMap["VA"]= "+379"
            countryToPhoneMap["VE"]= "+58"
            countryToPhoneMap["VN"]= "+84"
            countryToPhoneMap["YE"]= "+967"
            countryToPhoneMap["ZM"]= "+260"
            countryToPhoneMap["ZW"]= "+263"
            countryToPhoneMap["GE"]= "+995"
            countryToPhoneMap["TW"]= "+886"
            countryToPhoneMap["AZ"]= "+374-97"
            countryToPhoneMap["CY"]= "+90-392"
            countryToPhoneMap["MD"]= "+373-533"
            countryToPhoneMap["SO"]= "+252"
            countryToPhoneMap["GE"]= "+995"
            countryToPhoneMap["CX"]= "+61"
            countryToPhoneMap["CC"]= "+61"
            countryToPhoneMap["NF"]= "+672"
            countryToPhoneMap["NC"]= "+687"
            countryToPhoneMap["PF"]= "+689"
            countryToPhoneMap["YT"]= "+262"
            countryToPhoneMap["GP"]= "+590"
            countryToPhoneMap["GP"]= "+590"
            countryToPhoneMap["PM"]= "+508"
            countryToPhoneMap["WF"]= "+681"
            countryToPhoneMap["CK"]= "+682"
            countryToPhoneMap["NU"]= "+683"
            countryToPhoneMap["TK"]= "+690"
            countryToPhoneMap["GG"]= "+44"
            countryToPhoneMap["IM"]= "+44"
            countryToPhoneMap["JE"]= "+44"
            countryToPhoneMap["AI"]= "+1-264"
            countryToPhoneMap["BM"]= "+1-441"
            countryToPhoneMap["IO"]= "+246"
            countryToPhoneMap["VG"]= "+1-284"
            countryToPhoneMap["KY"]= "+1-345"
            countryToPhoneMap["FK"]= "+500"
            countryToPhoneMap["GI"]= "+350"
            countryToPhoneMap["MS"]= "+1-664"
            countryToPhoneMap["SH"]= "+290"
            countryToPhoneMap["TC"]= "+1-649"
            countryToPhoneMap["MP"]= "+1-670"
            countryToPhoneMap["PR"]= "+1-787)"
            countryToPhoneMap["AS"]= "+1-684"
            countryToPhoneMap["GU"]= "+1-671"
            countryToPhoneMap["VI"]= "+1-340"
            countryToPhoneMap["HK"]= "+852"
            countryToPhoneMap["MO"]= "+853"
            countryToPhoneMap["FO"]= "+298"
            countryToPhoneMap["GL"]= "+299"
            countryToPhoneMap["GF"]= "+594"
            countryToPhoneMap["GP"]= "+590"
            countryToPhoneMap["MQ"]= "+596"
            countryToPhoneMap["RE"]= "+262"
            countryToPhoneMap["AX"]= "+358-18"
            countryToPhoneMap["AW"]= "+297"
            countryToPhoneMap["AN"]= "+599"
            countryToPhoneMap["SJ"]= "+47"
            countryToPhoneMap["AC"]= "+247"
            countryToPhoneMap["TA"]= "+290"
            countryToPhoneMap["CS"]= "+381"
            countryToPhoneMap["PS"]= "+970"
            countryToPhoneMap["EH"]= "+212"

            return countryToPhoneMap[isoString.uppercase()].toString()

        }

        fun getUserNameForAuthentication(userNameEditText: EditText,activity: Activity):String{
            val value = if (!checkIfStringIsNumber(userNameEditText.text.toString())){
                userNameEditText.text.toString()
            }else{
                if (!userNameEditText.text.toString().startsWith("+")) {
                    val tm = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
                    val countryCode=countryToPhone(tm?.networkCountryIso.toString())
                    "$countryCode${userNameEditText.text}"
                } else {
                    userNameEditText.text.toString()
                }

            }
            Log.d(TAG,"UserName: $value")
            return value
        }
    }
}