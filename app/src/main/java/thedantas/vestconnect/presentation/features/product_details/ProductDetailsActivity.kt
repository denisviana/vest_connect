package thedantas.vestconnect.presentation.features.product_details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.login_activity.loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.format.DateTimeFormatter
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.presentation.features.login.LoginActivity

@ExperimentalCoroutinesApi
class ProductDetailsActivity : BaseViewModelActivity() {

    private val productDetailsViewModel : ProductDetailsViewModel by viewModel()

    companion object{

        private const val TAG : String = "product"

        fun newIntent(context : Context, product: Product) : Intent {
            return Intent(context, ProductDetailsActivity::class.java).apply {
                putExtra(TAG, product)
            }
        }

    }

    private lateinit var product : Product
    private var color = 0
    private var linked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getParcelableExtra(TAG)!!

        color = Color.parseColor(product.primaryColor)
        toolbar.setBackgroundColor(color)
        setSupportActionBar(toolbar)
        supportActionBar?.title = product.fabricator
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color

        initViews(product)

        productDetailsViewModel.bind(::render)
        productDetailsViewModel.listen(::handle)
        productDetailsViewModel.setProduct(product)

    }

    private fun initViews(product: Product){

        Glide.with(this)
            .asDrawable()
            .load(product.image1)
            .centerCrop()
            .into(productImage)

        productDetail.text = product.detail
        productDescription.text = product.description
        productStatus.apply {
            title = if(product.status) "Ativo" else "Inativo"
            setTextColor( if(product.status) color else Color.RED)
        }
        productCategory.text = product.category
        productType.text = product.type
        productIdentifier.text = product.identify
        productRegisterDate.text = product.registerDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))


        if(product.expirationDate != null){
            productExpirationDate.text = product.expirationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }

        btLinkProductToAccount.setOnClickListener { productDetailsViewModel.linkProductToUser(product) }

    }

    private fun render(state: ProductDetailsState) {
        state::loading partTo ::renderLoading
        state::showMoreDetailsContainer partTo  ::renderProductMoreDetailsContainer
        state::showLinkButton partTo ::showLinkButton
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.apply {
            isVisible = isLoading
            isClickable = true
        }
    }

    private fun renderProductMoreDetailsContainer(renderContainer : Boolean){
        productFullDetailsContainer.apply {
            visibility = if(renderContainer) VISIBLE else GONE
        }
    }

    private fun showLinkButton(showLinkButton : Boolean){
        btLinkProductToAccount.apply {
            isVisible = showLinkButton
        }
    }

    private fun handle(command: ProductDetailsCommand) {
        when (command) {
            is ProductDetailsCommand.LinkProductToUserSuccessful -> {
                linked = true
                initViews(command.product)
            }
            is ProductDetailsCommand.LinkProductToUserFailed -> {
                Toast.makeText(this@ProductDetailsActivity, command.message, Toast.LENGTH_LONG).show()
            }
            is ProductDetailsCommand.UserIsNotLoggedIn -> {
                startActivity(LoginActivity.newIntent(this, command.product))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(linked)
            setResult(Activity.RESULT_OK)
    }

}