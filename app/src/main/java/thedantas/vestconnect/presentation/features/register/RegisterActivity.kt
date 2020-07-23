package thedantas.vestconnect.presentation.features.register


import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.domain.entity.User
import thedantas.vestconnect.presentation.features.home.HomeActivity
import thedantas.vestconnect.presentation.util.isValidDate
import thedantas.vestconnect.presentation.util.parseLocalDate

class RegisterActivity : BaseViewModelActivity(){

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, RegisterActivity::class.java)
    }

    private val mViewModel : RegisterViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        setSupportActionBar(toolbarRegister)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loading.isVisible = false

        btCreateAccount.setOnClickListener {

            if(isValid()){
                mViewModel.createUser(
                    User(
                        holder = nameInput.text.toString(),
                        email = emailInput.text.toString(),
                        birthday = parseLocalDate(birthdayInput.text.toString()),
                        password = passwordInput.text.toString(),
                        uid = ""
                    )
                )
            }

        }

        mViewModel.bind(::render)
        mViewModel.listen(::handle)

    }

    private fun render(state: RegisterState) {
        state::loading partTo ::renderLoading
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.apply {
            isVisible = isLoading
            isClickable = true
        }
        btCreateAccount.isEnabled = !isLoading
    }

    private fun isValid(): Boolean {

        if (nameInput.text.isNullOrBlank()) {
            nameInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        if (birthdayInput.text.isNullOrBlank()) {
            birthdayInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        if (emailInput.text.isNullOrBlank()) {
            emailInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        if (birthdayInput.text.isNullOrBlank()) {
            birthdayInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        if (!isValidDate(birthdayInput.text.toString())) {
            birthdayInputLayout.error = getString(R.string.register_invalid_date)
            return false
        }

        if (passwordInput.text.isNullOrBlank()) {
            passwordInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        if (confirmPasswordInput.text.isNullOrBlank()) {
            confirmPasswordInputLayout.error = getString(R.string.register_mandatory_field)
            return false
        }

        return true
    }

    private fun handle(command: RegisterCommand) {
        when (command) {
            is RegisterCommand.RegisterSuccessful -> startActivity(HomeActivity.newIntent(this))
            is RegisterCommand.RegisterFailed -> {
                btCreateAccount.isEnabled = true
                Toast.makeText(this@RegisterActivity, getString(command.message),Toast.LENGTH_LONG).show()
            }
        }
    }
}