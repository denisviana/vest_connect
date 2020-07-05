package thedantas.vestconnect.presentation.features.home

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import thedantas.vestconnect.R
import thedantas.vestconnect.device.util.NfcUtils

/**
 * Created by Denis Costa on 28/06/20.
 */
class HomeActivity : AppCompatActivity() {

    private val mViewModel : HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val intent = intent
        handleIntents(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntents(intent)
    }

    private fun handleIntents(intent: Intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED  == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED  == intent.action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                Log.i("NFC UID", NfcUtils.getUID(intent))
                Toast.makeText(this, NfcUtils.getData(rawMsgs), Toast.LENGTH_SHORT).show()
                Log.i("NFC Data",  NfcUtils.getData(rawMsgs))
            }
        }
    }

}