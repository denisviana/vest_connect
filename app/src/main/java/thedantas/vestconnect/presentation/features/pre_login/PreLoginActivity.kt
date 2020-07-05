package thedantas.vestconnect.presentation.features.pre_login


import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.pre_login_activity.*
import thedantas.vestconnect.R
import thedantas.vestconnect.presentation.features.login.LoginActivity

class PreLoginActivity : AppCompatActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, PreLoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_login_activity)
        initViews()

    }

    private fun initViews(){
        btAccessAccount.setOnClickListener { startActivity(LoginActivity.newIntent(this)) }
    }
}