package thedantas.vestconnect.presentation.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_activity.*
import thedantas.vestconnect.R
import thedantas.vestconnect.presentation.features.register.RegisterActivity

/**
 * Created by Denis Costa on 28/06/20.
 */
class LoginActivity : AppCompatActivity() {


    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        initViews()
        setSupportActionBar(toolbarLogin)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    private fun initViews(){

        createAccountLabelButton.setOnClickListener { startActivity(RegisterActivity.newIntent(this)) }

    }
}