package thedantas.vestconnect.presentation.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.presentation.features.home.adapter.ProductsAdapter

/**
 * Created by Denis Costa on 28/06/20.
 */
class HomeActivity : BaseViewModelActivity(), ProductsAdapter.OnItemClick {

    private val mViewModel : HomeViewModel by inject()

    lateinit var productsAdapter: ProductsAdapter

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, HomeActivity::class.java)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        initViews()

        mViewModel.bind(::render)
        mViewModel.listen(::handle)

        mViewModel.getProductList()
    }

    private fun initViews(){

        productsAdapter = ProductsAdapter(
            R.layout.product_item_layout,
            mutableListOf(), this)

        rvProducts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProducts.adapter = productsAdapter

    }

    private fun render(homeState: HomeState) {
        homeState::loading partTo ::renderLoading
        homeState::hasError partTo ::showError
        homeState::emptyList partTo ::emptyList
    }

    private fun renderLoading(isLoading: Boolean) {
        homeLoading.apply {
            isVisible = isLoading
            isClickable = true
        }
    }

    private fun showError(hasError : Boolean){
        messageHelper.apply {
            isVisible = hasError
        }
    }

    private fun emptyList(empty : Boolean){
        messageHelper.apply {
            isVisible = empty
            text = if(empty) "Nenhum produto cadastro ainda" else ""
        }
    }

    private fun handle(homeCommand: HomeCommand) {
        when (homeCommand) {
            is HomeCommand.GetProductListSuccessful -> {
                productsAdapter.setNewData(homeCommand.products)
            }
            is HomeCommand.GetProductListFailed -> {
                messageHelper.text = homeCommand.message
            }
        }
    }

    override fun onProductClickListener(item: Product?) {

    }

}