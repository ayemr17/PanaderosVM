package com.example.panaderosvm._view_ui.splashActivity

import android.util.Log
import android.widget.Toast
import com.example.panaderosvm.BuildConfig
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.Base.BaseViewModel
import retrofit2.Response
import retrofit2.Retrofit

open class BaseServiceImpl {

    constructor(baseViewModel: BaseViewModel)

    private val TAG = BaseServiceImpl::class.java.simpleName
    //public static final String BASE_URL = "https://209.126.120.191:7243/";
    //public static final String BASE_URL = "http://209.126.120.191:7280/";
    // public static final String BASE_URL = "http://192.168.0.13:8080/";
    // probar esta url!! public static final String BASE_URL = "https://209.126.120.191:7243/";

    //public static final int TIMEOUT = 40;

    //public static final String BASE_URL = "https://209.126.120.191:7243/";
//public static final String BASE_URL = "http://209.126.120.191:7280/";
// public static final String BASE_URL = "http://192.168.0.13:8080/";
// probar esta url!! public static final String BASE_URL = "https://209.126.120.191:7243/";
//public static final int TIMEOUT = 40;

    val REQUEST_SMS_CODE = 0
    val VERIFY_SMS_CODE = 1
    val LOGIN = 2
    val SEND_GCM_ID = 3
    val REFRESH_TOKEN = 4
    val GET_PROFILE = 5
    val UPDATE_MATRICULADO = 6
    val UPDATE_NO_MATRICULADO = 7
    val UNREGISTER_GCM_ID = 8
    val GET_CIRCUNSCRIPCIONES = 9
    val GET_COLEGIOS = 10
    val GET_INITIAL_EXPEDIENTES = 11
    val GET_EXPEDIENTE_INFORMATION = 12
    val GET_ACTUACIONES_EXPEDIENTE = 13
    val GET_ACTUACION_PDF = 14
    val SEARCH_EXPEDIENTE_BY_CUIJ = 15
    val UPDATE_PROFILE_BUSINESS = 16
    val UPDATE_PROFILE_NAME = 17
    val GET_LOCALIDADES = 18
    val GET_ALL_LOOKUPS = 19
    val GET_ORGANISMOS_BY_LOC = 20
    val SEARCH_EXPEDIENTE_BY_CUIJ_NM = 21
    val UPDATE_PROFILE_INFO = 22
    val SYNC_RFB = 23
    val GET_PDF_LIST = 24
    val CHANGE_PASS = 25
    val GET_AVATAR_NAME = 26
    val FORGET_PASS = 27
    val SET_NEW_PASS = 28
    val GIVE_ME_THE_CODE = 29
    val UPDATE_ACCOUNT = 30
    val GET_ALL_NOTIFICATION = 31
    val REQUEST_SMS_CODE_TWILO = 32
    val VERIFICAR_EXPEDIENTE_VISIBLE = 33
    val REQUEST_CALL_TWILIO_CODE = 34
    val SEARCH_EXPEDIENTE_BY_CARATULA = 35
    val LAST_EXPEDIENTES_UPDATE = 36
    val GET_VERSION_SUPPORT = 37

    protected var mOnResponse: OnResponse? = null
    protected var mRetrofit: Retrofit? = null
    protected var mRetrofitUtils : RetrofitUtils = RetrofitUtils()
    protected var mAuthorizationResponse : AuthorizationResponse = AuthorizationResponse()
    protected var mViewModel: BaseViewModel? = null

    open fun BaseServiceImpl() {

        mRetrofit = mRetrofitUtils.getRetrofit(
            BuildConfig.BASE_URL,
            BuildConfig.ENABLE_LOG,
            BuildConfig.CONNECT_TIME_OUT
        )
        mAuthorizationResponse.initRetries()
    }

    open fun BaseServiceImpl(baseViewModel: BaseViewModel?) {
        mRetrofit = mRetrofitUtils.getRetrofit(
            BuildConfig.BASE_URL,
            BuildConfig.ENABLE_LOG,
            BuildConfig.CONNECT_TIME_OUT
        )
        mViewModel = baseViewModel
        mAuthorizationResponse.initRetries()
    }

    open fun handleSuccessfulResponse(
        model: BaseViewModel,
        response: Response<*>,
        mOnResponse: OnResponse,
        requestType: Int
    ) {
        if (response.isSuccessful) {
            mOnResponse.onSuccess(SuccessGenericResponse(requestType, response.body()))
        } else {
            val bodyResponse: ErrorBodyResponse =
                ErrorBodyResponse.getErrorBodyResponse(response.errorBody())
            if (bodyResponse != null) {
                if (Integer.valueOf(bodyResponse.getCode()!!) === StatusCodes.AUTH) { // Retrieve a new token and persist it
                    Log.e(BaseServiceImpl.TAG, "model.retrieveNewAuthToken()")
                    model.retrieveNewAuthToken()
                    if (AuthorizationResponse.isAvailableToTyAgain()) {
                        mOnResponse.onAuthorizationError(AuthorizationResponse(requestType))
                    } else {
                        Toast.makeText(
                            model.getApplication().getApplicationContext(),
                            "Error de autentificacion",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (Integer.valueOf(bodyResponse.getCode()!!) === StatusCodes.AUTH_MATRICULA) {
                    if (PreferencesManager.getInstance().isMatriculado()) {
                        mOnResponse.onError(
                            ErrorGenericResponse(
                                requestType,
                                bodyResponse,
                                mViewModel!!.getApplication().getString(R.string.message_error_matricula_config),
                                true
                            )
                        )
                    } else {
                        mOnResponse.onError(
                            ErrorGenericResponse(
                                requestType,
                                bodyResponse,
                                mViewModel!!.getApplication().getString(R.string.message_error_pass_change),
                                true
                            )
                        )
                    }
                } else if (Integer.valueOf(bodyResponse.getCode()!!) === StatusCodes.MULTIPLE_ATTACHMENTS) {
                    mOnResponse.onError(ErrorGenericResponse(requestType, bodyResponse, true))
                } else if (Integer.valueOf(bodyResponse.getCode()!!) === StatusCodes.DATA_ERROR) {
                    mOnResponse.onError(
                        ErrorGenericResponse(
                            requestType,
                            bodyResponse,
                            mViewModel!!.getApplication().getString(R.string.msg_data_invalid)
                        )
                    )
                } else if (Integer.valueOf(bodyResponse.getCode()!!) === StatusCodes.INVALID_MAIL) {
                    mOnResponse.onError(
                        ErrorGenericResponse(
                            requestType,
                            bodyResponse,
                            mViewModel!!.getApplication().getString(R.string.message_invalid_mail)
                        )
                    )
                } else {
                    Log.e(
                        "errormailnovalido",
                        Integer.valueOf(bodyResponse.getCode()!!).toString()
                    )
                    mOnResponse.onError(
                        ErrorGenericResponse(
                            requestType,
                            bodyResponse,
                            mViewModel!!.getApplication().getString(R.string.generic_message)
                        )
                    )
                }
            } else if (response.raw() != null && response.raw().code() == StatusCodes.AUTH) { // Retrieve a new token and persist it
                model.retrieveNewAuthToken()
                if (AuthorizationResponse.isAvailableToTyAgain()) {
                    mOnResponse.onAuthorizationError(AuthorizationResponse(requestType))
                } else {
                    Toast.makeText(
                        model.getApplication().getApplicationContext(),
                        model.getApplication().getApplicationContext().getString(R.string.error_auth),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                mOnResponse.onError(ErrorGenericResponse(requestType))
            }
        }
    }

    open fun setOnResponse(onResponse: OnResponse?) {
        mOnResponse = onResponse
    }

}