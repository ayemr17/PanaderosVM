package com.example.panaderosvm._view_ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.panaderosvm.R
import com.example.panaderosvm._view_ui.login.LoginActivity
import com.example.panaderosvm.utils.encrypt
import com.example.panaderosvm.utils.hasEmulatorBuildProp
import com.example.panaderosvm.utils.rootCheck
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mDatabaseViewModel: DatabaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        encrypt("http://infraccionesciudad.gcba.gob.ar")
        if (rootCheck(this)) {
            //
        }
        if (hasEmulatorBuildProp()) {
            //El applicativo se encuentra ejecutando en un emulador
            /*Toast.makeText(this, "El APP se encuentra ejecuntado en un emulador", Toast.LENGTH_LONG)
                .show()*/
        }
        launchAnimation()
    }

    private fun launchAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(this)
                .load(R.drawable.pikachu_gif)
                .diskCacheStrategy(
                    DiskCacheStrategy.RESOURCE
                )
                .into(contenedorSplash_imageview_activitySplash)
        } else {
            Glide.with(this)
                .load(R.drawable.pikachu_imagen)
                .into(contenedorSplash_imageview_activitySplash)
        }


        Timer().schedule(object : TimerTask() {
            override fun run() {
                launchLogin()
            }
        }, 4000)
    }


    private fun launchLogin() {
        val nIntent = Intent(this, LoginActivity::class.java)
        intent.action?.let { action ->
            nIntent.action = action
            intent.extras?.let { extras ->
                nIntent.putExtras(extras)
            }
        }
        startActivity(nIntent)
        finish()
    }
}
