package thedantas.vestconnect.presentation.features.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.login_activity.loading
import kotlinx.android.synthetic.main.login_activity.passwordInputLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.data.model.domain.UserAuth
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.presentation.features.home.HomeActivity
import thedantas.vestconnect.presentation.features.register.RegisterActivity

/**
 * Created by Denis Costa on 28/06/20.
 */
class LoginActivity : BaseViewModelActivity() {


    companion object{

        private const val TAG = "product"

        fun newIntent(context : Context, product: Product? = null) : Intent = Intent(context, LoginActivity::class.java).apply {
            if(product != null)
                putExtra(TAG, product)
        }
    }

    private val loginViewModel : LoginViewModel by viewModel()
    private var productToLink : Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        initViews()
        setSupportActionBar(toolbarLogin)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(intent.hasExtra(TAG) && intent.getParcelableExtra<Product>(TAG) != null)
            productToLink = intent.getParcelableExtra(TAG)

        productToLink?.let {
            loginViewModel.setProductToLink(it)
        }

        loginViewModel.bind(::render)
        loginViewModel.listen(::handle)

    }


    @ExperimentalCoroutinesApi
    private fun initViews(){

        btEnter.setOnClickListener {
            if(isValid())
                loginViewModel.login(
                    UserAuth(
                        email = loginEmailInput.text.toString(),
                        password = loginPwdInput.text.toString()
                    )
                )
        }

        createAccountLabelButton.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this, productToLink))
            finish()
        }
    }

    private fun render(state: LoginState) {
        state::loading partTo ::renderLoading
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.apply {
            isVisible = isLoading
            isClickable = true
        }
    }

    private fun handle(command: LoginCommand) {
        when (command) {
            is LoginCommand.LoginSuccess -> {
                startActivity(HomeActivity.newIntent(this))
                finish()
            }
            is LoginCommand.LoginFailed -> {
                Toast.makeText(this@LoginActivity, getString(command.message), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isValid(): Boolean {

        if (loginEmailInput.text.isNullOrBlank()) {
            loginEmailInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        if (loginPwdInput.text.isNullOrBlank()) {
            passwordInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        return true
    }
}