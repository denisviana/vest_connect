package thedantas.vestconnect.presentation.features.splash_screen


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import thedantas.vestconnect.R
import thedantas.vestconnect.presentation.features.pre_login.PreLoginActivity

class SplashActivity : AppCompatActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, this::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splach_activity)

        val handler = Handler()
        handler.postDelayed({
            startActivity(PreLoginActivity.newIntent(this))
            finish()
        }, 4000)


    }
}