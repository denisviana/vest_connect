package thedantas.vestconnect.presentation.features.splash_screen


import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.presentation.features.home.HomeActivity
import thedantas.vestconnect.presentation.features.onboarding.OnboardingActivity
import thedantas.vestconnect.presentation.features.pre_login.PreLoginActivity

class SplashActivity : BaseViewModelActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, this::class.java)
    }

    private val splashViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splach_activity)

        splashViewModel.listen(::handle)

        splashViewModel.checkIfUserIsOnboardinIntroWasShowed()

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
            is SplashCommand.CheckIfOnboardingIntroWasShowed -> {
                if(command.wasShowed)
                    splashViewModel.checkIfUserIsLoggedIn()
                else {
                    startActivity(OnboardingActivity.newIntent(this))
                    finish()
                }
            }
        }
    }
}