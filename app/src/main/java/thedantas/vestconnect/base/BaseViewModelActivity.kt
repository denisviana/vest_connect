package thedantas.vestconnect.base

import ViewDiffContainer
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty0

abstract class BaseViewModelActivity : BaseActivity(),
    ViewDiffContainer {

    override val updated: HashMap<String, Any?> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updated.clear()
    }

    protected inline infix fun <reified R> KProperty0<R>.partTo(noinline renderer: (R) -> Unit) {
        val func = renderer as KFunction<*>
        val named = "${this.name}:${func.name}"
        part(named, this.get(), renderer)
    }

    protected inline fun <T, VM : BaseViewModel<T, *>> VM.bind(crossinline state: T.() -> Unit) {
        this.state.observe(this@BaseViewModelActivity, Observer {
            if (it != null) {
                state(it)
            }
        })
    }

    protected inline fun <T, VM : BaseViewModel<*, T>> VM.listen(crossinline command: T.() -> Unit) {
        this.command.observe(this@BaseViewModelActivity, Observer {
            if (it != null) {
                command(it)
            }
        })
    }
}