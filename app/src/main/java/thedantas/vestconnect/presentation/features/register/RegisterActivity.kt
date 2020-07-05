package thedantas.vestconnect.presentation.features.register


import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_activity.*
import thedantas.vestconnect.R

class RegisterActivity : AppCompatActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, RegisterActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        setSupportActionBar(toolbarRegister)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}