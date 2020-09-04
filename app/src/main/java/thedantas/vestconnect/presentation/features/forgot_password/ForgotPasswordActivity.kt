package thedantas.vestconnect.presentation.features.forgot_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.actvity_forgot_password.*
import kotlinx.android.synthetic.main.actvity_forgot_password.loading
import kotlinx.android.synthetic.main.login_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.presentation.features.home.HomeActivity

class ForgotPasswordActivity : BaseViewModelActivity() {

    companion object{
        fun newIntent(context: Context) : Intent = Intent(context, ForgotPasswordActivity::class.java)
    }

    private val viewModel : ForgotPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_forgot_password)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()

        viewModel.bind(::render)
        viewModel.listen(::handle)

    }

    private fun initViews(){
        btForgotPassword.setOnClickListener {
            if(isValid())
                viewModel.forgotPassword(emailInput.text.toString())
        }
    }

    private fun render(state: ForgotPasswordState) {
        state::loading partTo ::renderLoading
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.apply {
            isVisible = isLoading
            isClickable = true
        }
    }

    private fun handle(command: ForgotPasswordCommand) {
        when (command) {
            is ForgotPasswordCommand.SendEmailPasswordSuccessful -> {
                Toast.makeText(
                    this@ForgotPasswordActivity, "E-mail enviado com sucesso.",
                    Toast.LENGTH_LONG)
                    .show()
            }
            is ForgotPasswordCommand.SendEmailPasswordFailed -> {
                Toast.makeText(
                    this@ForgotPasswordActivity, getString(command.message),
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun isValid(): Boolean {

        if (emailInput.text.isNullOrBlank()) {
            emailInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        return true
    }

}