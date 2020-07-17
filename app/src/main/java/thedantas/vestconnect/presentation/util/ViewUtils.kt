package thedantas.vestconnect.presentation.util

import android.app.Activity
import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat

fun setCustomElevation(
    view: View,
    elevationDips: Float,
    alpha: Float = 0.20f,
    cornerRadiusDips: Float
) {
    val viewOutlineProvider = object : ViewOutlineProvider() {
        private val rect = Rect()

        override fun getOutline(view: View, outline: Outline) {
            view.background?.copyBounds(rect)
            rect.scale(1f, 1f)
            rect.offset(0, -view.context.dp(6f))
            outline.setRoundRect(rect, view.context.dp(cornerRadiusDips).toFloat())
            outline.alpha = alpha
        }
    }
    view.outlineProvider = viewOutlineProvider
    view.elevation = view.context.dp(elevationDips).toFloat()
}

private fun Rect.scale(
    @FloatRange(from = -2.0, to = 2.0) scaleX: Float,
    @FloatRange(from = -2.0, to = 2.0) scaleY: Float
) {
    val newWidth = width() * scaleX
    val newHeight = height() * scaleY
    val deltaX = (width() - newWidth) / 2
    val deltaY = (height() - newHeight) / 2

    set(
        (left + deltaX).toInt(),
        (top + deltaY).toInt(),
        (right - deltaX).toInt(),
        (bottom - deltaY).toInt()
    )
}

fun Context.dp(dps: Float): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, dps,
    this.resources.displayMetrics
).toInt()

fun Activity.setStatusBarColor(@ColorRes color: Int) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}

fun setSystemBarTheme(window: Window, isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.run {
            val lFlags = decorView.systemUiVisibility
            decorView.systemUiVisibility =
                if (isDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
