package thedantas.vestconnect.presentation.features.product_content


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_product_content.*
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.presentation.features.product_content.adapter.ProductContentAdapter
import thedantas.vestconnect.presentation.features.youtube_player.YouTubePlayerActivity
import thedantas.vestconnect.presentation.helper.YouTubeHelper


class ProductContentActivity : BaseViewModelActivity(), ProductContentAdapter.OnItemClick{

    companion object{

        private const val TAG = "product"

        fun newIntent(context : Context, product : Product) : Intent = Intent(context, ProductContentActivity::class.java)
            .apply {
                putExtra(TAG, product)
            }
    }

    lateinit var productContentAdapter: ProductContentAdapter
    lateinit var videoUrl : String
    lateinit var product : Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_content)

        product = intent.getParcelableExtra(TAG)!!
        videoUrl = YouTubeHelper.extractVideoIdFromUrl(product.linkVideo1) ?: ""

        val color = Color.parseColor(product.primaryColor)
        toolbar.setBackgroundColor(color)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Conteúdo do produto"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color

        initView(videoUrl)
    }

    private fun initView(videoUrl : String){

        rvProductContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvProductContent.setHasFixedSize(true)

        productContentAdapter = ProductContentAdapter(
            R.layout.layout_product_content_item,
            mutableListOf(videoUrl),
            this)

        rvProductContent.adapter = productContentAdapter

    }

    override fun onVideoClicked(url: String) {
        startActivity(YouTubePlayerActivity.newIntent(this, url))
    }
}