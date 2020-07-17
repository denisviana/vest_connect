
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import thedantas.vestconnect.presentation.util.setSystemBarTheme

open class BaseDialogFragment : DialogFragment() {

    protected open fun isFullScreen() = true

    protected open fun isDefaultSystemBarDark() = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        if (isFullScreen()) {
            dialog.window?.run {
                decorView.systemUiVisibility = decorView.systemUiVisibility and
                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                setSystemBarTheme(this, isDark = isDefaultSystemBarDark())
            }
        }

        return dialog
    }
}
