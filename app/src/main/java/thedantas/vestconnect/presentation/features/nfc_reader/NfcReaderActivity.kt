package thedantas.vestconnect.presentation.features.nfc_reader

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.*
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import com.romellfudi.fudinfc.util.sync.NfcReadUtilityImpl
import kotlinx.android.synthetic.main.activity_nfc_reader.*
import thedantas.vestconnect.R
import thedantas.vestconnect.base.BaseViewModelActivity
import timber.log.Timber


class NfcReaderActivity : BaseViewModelActivity(){

    private var adapter: NfcAdapter? = null

    companion object{
        fun newIntent(context : Context) : Intent = Intent(context, NfcReaderActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initNfcAdapter()

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
            for (i in 0 until items.size()) {
                showToast(items.valueAt(i).replace("en", ""))
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
