package thedantas.vestconnect.presentation.features.home.adapter

import android.view.View
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.threeten.bp.format.DateTimeFormatter
import thedantas.vestconnect.R
import thedantas.vestconnect.domain.entity.Product

class ProductsAdapter(
    layoutResId : Int,
    data : MutableList<Product?>,
    private val listener : OnItemClick) : BaseQuickAdapter<Product,BaseViewHolder>(layoutResId, data){

    override fun convert(helper: BaseViewHolder?, item: Product?) {

        helper?.setText(R.id.productName, item?.name)
        helper?.setText(R.id.productDetail, item?.type)
        helper?.setText(R.id.productRegisterDate, item?.registerDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))

        Glide.with(mContext)
            .asDrawable()
            .load(item?.image1)
            .circleCrop()
            .into(helper?.getView(R.id.productImage)!!)

        helper?.getView<CardView>(R.id.productItem)
            ?.setOnClickListener { listener.onProductClickListener(item) }

    }

    interface OnItemClick{
        fun onProductClickListener(item : Product?)
    }

}