package thedantas.vestconnect.presentation.features.onboarding


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_onboarding.*
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.presentation.features.login.LoginActivity
import thedantas.vestconnect.presentation.features.pre_login.PreLoginActivity
import thedantas.vestconnect.presentation.paperonboarding.PaperOnboardingFragment
import thedantas.vestconnect.presentation.paperonboarding.PaperOnboardingPage

class OnboardingActivity : AppCompatActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, OnboardingActivity::class.java)
    }

    private val onboardingViewModel : OnboardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val scr1 = PaperOnboardingPage(
            "Olá!",
            "Somos a VestConnect e estamos felizes em ter você por aqui.",
            Color.WHITE,
            R.drawable.onboarding1,
            R.drawable.circle_icon
        )
        val scr2 = PaperOnboardingPage(
            "Uma nova experiência",
            "A VestConnect é uma plataforma que te permite ter uma interação digital com seus produtos físicos, de maneira inovadora e simples.",
            Color.WHITE,
            R.drawable.onboarding2,
            R.drawable.circle_icon
        )
        val scr3 = PaperOnboardingPage(
            "Na palma da sua mão",
            "Basta utilizar nosso aplicativo instalado em seu smartphone para escanear os produtos cadastrados por nossos parceiros e pronto!",
            Color.WHITE,
            R.drawable.onboarding3,
            R.drawable.circle_icon
        )

        val scr4 = PaperOnboardingPage(
            "Exclusividade",
            "Tenha acesso exclusivo a vídeos, fotos, promoções, produtos, notícias, eventos, experiências e muito mais!",
            Color.WHITE,
            R.drawable.onboarding4,
            R.drawable.circle_icon
        )

        val scr5 = PaperOnboardingPage(
            "Vamos lá?",
            "É hora de fazer parte do novo modelo de experiência de consumo. Aproveite!",
            Color.WHITE,
            R.drawable.onboarding5,
            R.drawable.circle_icon
        )

        val elements: ArrayList<PaperOnboardingPage> = ArrayList()
        elements.add(scr1)
        elements.add(scr2)
        elements.add(scr3)
        elements.add(scr4)
        elements.add(scr5)

        val onBoardingFragment = PaperOnboardingFragment.newInstance(elements)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, onBoardingFragment)
        fragmentTransaction.commit()

        btOnboarding.apply {
            setOnClickListener {
                onboardingViewModel.setOnboardingIntroShowed()
                startActivity(PreLoginActivity.newIntent(this@OnboardingActivity))
                finish()
            }
        }

        onBoardingFragment.setOnChangeListener { oldElementIndex, newElementIndex ->
            btOnboarding.visibility = if(newElementIndex == elements.lastIndex) VISIBLE else GONE
        }

        /*onBoardingFragment.setOnRightOutListener {
            val ft: FragmentTransaction =
                supportFragmentManager.beginTransaction()
            val bf: Fragment = BlankFragment()
            ft.replace(R.id.fragmentContainer, bf)
            ft.commit()
        }*/

    }
}