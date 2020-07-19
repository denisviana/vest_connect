package thedantas.vestconnect.presentation.features.pre_login


import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.robin.locationgetter.EasyLocation
import kotlinx.android.synthetic.main.pre_login_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.presentation.features.login.LoginActivity
import timber.log.Timber

class PreLoginActivity : AppCompatActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, PreLoginActivity::class.java)
    }

    private val preLoginViewModel : PreLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_login_activity)
        initViews()
        getUserLocation()
    }

    private fun initViews(){
        btAccessAccount.setOnClickListener { startActivity(LoginActivity.newIntent(this)) }
    }

    private fun getUserLocation(){
        EasyLocation(this@PreLoginActivity, object: EasyLocation.EasyLocationCallBack{
            @SuppressLint("TimberArgCount")
            override fun permissionDenied() {
                Timber.i("Permission denied")
            }

            override fun getLocation(location: Location) {
                preLoginViewModel.saveUserLocation(location)
            }

            override fun locationSettingFailed() {
                Timber.i("Location Setting Failed")
            }
        })
    }
}