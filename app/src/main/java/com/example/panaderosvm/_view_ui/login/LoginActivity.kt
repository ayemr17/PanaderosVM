package com.example.panaderosvm._view_ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.panaderosvm._view_ui.MainActivity
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.Base.BaseActivity
import com.example.panaderosvm._view_ui.Base.BasicMethods
import com.example.panaderosvm._view_ui.DatabaseViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), BasicMethods {

    var email: String = ""
    var pass: String = ""
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun init() {}

    override fun initListeners() {
        usuario_textImputLayout_activityLogin.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    usuario_textImputLayout_activityLogin.isErrorEnabled = false
                }
            }
        })

        contraseña_textImputLayout_activityLogin.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    contraseña_textImputLayout_activityLogin.isErrorEnabled = false
                }
            }
        })

        ingresar_button_activityLogin.setOnClickListener {
            if (validarEmail() && validarContraseña()) {
                /*Toast.makeText(
                    this,
                    "No hay ningun campo vacío! Felicitaciones!",
                    Toast.LENGTH_SHORT
                ).show()*/

                loginUser()
            }
        }
    }

    private fun loginUser() {
        loginViewModel.crearDatabase()
        goHome()
    }

    private fun goHome() {
        //findNavController().navigate(R.id.action_nav_login_to_nav_main)

        val nIntent = Intent(this, MainActivity::class.java)
        startActivity(nIntent)
        finish()
    }

    private fun validarEmail(): Boolean {
        if (!usuario_textImputLayout_activityLogin.editText?.text
                .toString()
                .toLowerCase()
                .trim()
                .isEmpty()
        ) {

            usuario_textImputLayout_activityLogin.error = null
            email = usuario_textImputLayout_activityLogin.editText?.text
                .toString()
                .toLowerCase()
                .trim()
            return true
        } else {
            usuario_textImputLayout_activityLogin.error = "Este campo no debe estar vacío."
            return false
        }
    }

    private fun validarContraseña(): Boolean {
        if (!contraseña_textImputLayout_activityLogin.editText?.text
                .toString()
                .toLowerCase()
                .trim()
                .isEmpty()
        ) {
            contraseña_textImputLayout_activityLogin.error = null
            pass = contraseña_textImputLayout_activityLogin.editText?.text
                .toString()
                .toLowerCase()
                .trim()
            return true
        } else {
            contraseña_textImputLayout_activityLogin.error = "Este campo no debe estar vacío."
            return false
        }
    }
}
