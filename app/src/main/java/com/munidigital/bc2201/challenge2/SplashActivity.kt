package com.munidigital.bc2201.challenge2

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DURATION: Long = 2000 // Setea el tiempo del splash en ms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        displayAppVersion()
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        Handler(Looper.getMainLooper()).postDelayed({

            if (loginViewModel.userLogged())
            {
                startActivity(Intent(this, MainActivity::class.java))
            }else
            {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish()
        }, SPLASH_DURATION)



    }

    private fun displayAppVersion() {
        try {
            val version = this.packageManager.getPackageInfo(this.packageName, 0).versionName
            findViewById<TextView>(R.id.tv_versioname).text = version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
}
