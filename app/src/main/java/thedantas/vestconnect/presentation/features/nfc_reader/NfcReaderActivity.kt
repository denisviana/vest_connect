package thedantas.vestconnect.presentation.features.nfc_reader

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.*
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.core.util.isNotEmpty
import androidx.core.view.isVisible
import com.romellfudi.fudinfc.util.sync.NfcReadUtilityImpl
import kotlinx.android.synthetic.main.activity_nfc_reader.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import matteocrippa.it.karamba.toCamelCase
import org.koin.android.viewmodel.ext.android.viewModel
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import thedantas.vestconnect.presentation.features.product_details.ProductDetailsActivity
import timber.log.Timber
import java.util.*

@ExperimentalCoroutinesApi
class NfcReaderActivity : BaseViewModelActivity(){

    private var adapter: NfcAdapter? = null

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, NfcReaderActivity::class.java)
    }

    private val nfcReaderViewModel : NfcReaderViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initNfcAdapter()

        nfcReaderViewModel.bind(::render)
        nfcReaderViewModel.listen(::handler)

    }


    private fun render(state : NfcReaderState){
        state::loading partTo ::renderLoading
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.apply {
            isVisible = isLoading
        }
        animation_view.apply {
            isVisible = !isLoading
        }
    }

    private fun handler(command: NfcReaderCommand){
        when(command){
            is NfcReaderCommand.GetProductByNfcTagIdSuccessful -> {
                startActivity(ProductDetailsActivity.newIntent(this, command.product))
                finish()
            }
            is NfcReaderCommand.GetProductByNfcTagIdFailed -> {
                showToast(command.message)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    private fun enableNfcForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            adapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
        } catch (ex: IllegalStateException) {
            Timber.e(ex, "Error enabling NFC foreground dispatch")
        }
    }

    private fun disableNfcForegroundDispatch() {
        try {
            adapter?.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Timber.e(ex, "Error disabling NFC foreground dispatch")
        }
    }

    private fun initNfcAdapter() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        adapter = nfcManager.defaultAdapter
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val items: SparseArray<String> = NfcReadUtilityImpl().readFromTagWithSparseArray(intent)
            if(items.isNotEmpty()) {
                val tagId = items.valueAt(0).replace("en", "").toLowerCase(Locale.ROOT)
                Timber.i("Tag $tagId")
                nfcReaderViewModel.getProductByNfcTagId(tagId)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
