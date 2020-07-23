package thedantas.vestconnect.presentation.features.splash_screen


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.robin.locationgetter.EasyLocation
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.presentation.features.home.HomeActivity
import thedantas.vestconnect.presentation.features.pre_login.PreLoginActivity
import timber.log.Timber

class SplashActivity : BaseViewModelActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, this::class.java)
    }

    private val splashViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splach_activity)

        splashViewModel.listen(::handle)

        splashViewModel.checkIfUserIsLoggedIn()

    }

    private fun handle(command: SplashCommand) {
        when (command) {
            is SplashCommand.CheckIfIsLoggedInResult -> {
                if(command.isLogged) {
                    startActivity(HomeActivity.newIntent(this))
                    finish()
                }else {
                    startActivity(PreLoginActivity.newIntent(this))
                    finish()
                }
            }
        }
    }
}