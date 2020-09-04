package thedantas.vestconnect.presentation.features.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_activity.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.presentation.features.home.adapter.ProductsAdapter
import thedantas.vestconnect.presentation.features.nfc_reader.NfcReaderActivity
import thedantas.vestconnect.presentation.features.product_content.ProductContentActivity
import thedantas.vestconnect.presentation.features.product_details.ProductDetailsActivity
import thedantas.vestconnect.presentation.features.product_details.ProductDetailsCommand
import thedantas.vestconnect.presentation.helper.YouTubeHelper
import java.util.*


/**
 * Created by Denis Costa on 28/06/20.
 */
class HomeActivity : BaseViewModelActivity(), ProductsAdapter.OnItemClick {

    private val mViewModel : HomeViewModel by inject()

    private lateinit var productsAdapter: ProductsAdapter

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

    @ExperimentalCoroutinesApi
    private fun initViews(){

        rvProducts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProducts.setHasFixedSize(true)

        productsAdapter = ProductsAdapter(
            R.layout.product_item_layout,
            mutableListOf(), this)

        val view = getHeaderView(View.OnClickListener {
            startActivityForResult(NfcReaderActivity.newIntent(this), 100)
        })

        swipeToRefresh.setOnRefreshListener {
            mViewModel.refreshProductList()
        }

        productsAdapter.addHeaderView(view)

        rvProducts.adapter = productsAdapter

    }

    private fun getHeaderView(listener: View.OnClickListener): View? {
        val view: View = layoutInflater.inflate(R.layout.layout_product_list_header, rvProducts, false)
        view.setOnClickListener(listener)
        return view
    }

    private fun render(homeState: HomeState) {
        homeState::loading partTo ::renderLoading
        homeState::hasError partTo ::showError
        homeState::emptyList partTo ::emptyList
        homeState::userName partTo ::renderWelcomeLabel
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

    private fun renderWelcomeLabel(userName : String){
        val timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        homeWelcomeLabel.text = if(timeOfDay in 5..12) "Bom dia, " else if(timeOfDay in 13..18) "Boa tarde, " else "Boa Noite, "
        homeUserName.text = userName
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
            is HomeCommand.RefreshProductListSuccessful -> {
                productsAdapter.setNewData(homeCommand.products)
                swipeToRefresh.isRefreshing = false
            }
        }
    }


    @ExperimentalCoroutinesApi
    override fun onProductClickListener(item: Product?) {
        startActivity(ProductDetailsActivity.newIntent(this, item!!))
    }

    @ExperimentalCoroutinesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            mViewModel.refreshProductList()
        }
    }

}