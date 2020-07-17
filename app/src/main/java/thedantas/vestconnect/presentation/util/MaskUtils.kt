package thedantas.vestconnect.presentation.util
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object MaskUtils {

    const val DATE_MASK = "##/##/####"

    private const val PHONE_MASK_1 = "(##) ####-####"
    private const val PHONE_MASK_2 = "(##) #####-####"


    fun applyMask(mask: String, editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                val userInput = "" + s.toString().replace("[^\\d]".toRegex(), "")
                var result = ""

                var j = 0
                var i = 0
                while (i < mask.length && j < userInput.length) {
                    result += if (mask[i] == '#') {
                        userInput[j++]
                    } else {
                        mask[i]
                    }
                    i++
                }

                editText.removeTextChangedListener(this)
                s.replace(0, s.length, result)
                editText.addTextChangedListener(this)
            }
        }
    }

    private fun applyMasks(masks: Array<String>, editText: EditText): TextWatcher {

        val rawMasks = arrayOfNulls<String>(masks.size)
        for (i in masks.indices) {
            rawMasks[i] = masks[i].replace("[^#]".toRegex(), "")
        }

        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                val userInput = "" + s.toString().replace("[^\\d]".toRegex(), "")
                var result = ""

                var mask = masks[0]
                for (i in masks.indices) {
                    if (userInput.length >= rawMasks[i]!!.length) {
                        mask = masks[i]
                    }
                }

                var j = 0
                var i = 0
                while (i < mask.length && j < userInput.length) {
                    result += if (mask[i] == '#') {
                        userInput[j++]
                    } else {
                        mask[i]
                    }
                    i++
                }

                editText.removeTextChangedListener(this)
                s.replace(0, s.length, result)
                editText.addTextChangedListener(this)
            }
        }
    }

    fun removePhoneMask(text: String): String {
        return text.replace("[^0-9]".toRegex(), "").trim()
    }

    fun applyPhoneNumberMask(editText: EditText): TextWatcher {
        return applyMasks(
            arrayOf(
                PHONE_MASK_1,
                PHONE_MASK_2
            ), editText
        )
    }

    fun applyDateMask(editText: EditText): TextWatcher {
        return object : TextWatcher {
            private var deletingBar: Boolean = false
            private var deletingBarPosition = 0
            private var previousText: String? = null
            private var afterText: String? = null

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                afterText = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                previousText = s.toString()
                if (s.isNotEmpty() && after == 0) { // está deletando
                    if (s[start] == DATE_MASK[start]) {
                        deletingBar = true
                        deletingBarPosition = start
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

                var userInput = "" + s.toString().replace("[^\\d]".toRegex(), "")

                if (deletingBar) {
                    if (previousText!![deletingBarPosition] == '/') {
                        userInput = afterText!!.substring(
                            0,
                            deletingBarPosition - 1
                        ) + afterText!!.substring(
                            Math.min(deletingBarPosition + 1, afterText!!.length),
                            afterText!!.length
                        )
                        userInput = userInput.replace("[^\\d]".toRegex(), "")
                    }
                }

                var result = ""

                var j = 0
                var i = 0
                while (i < DATE_MASK.length && j < userInput.length) {
                    if (DATE_MASK[i] == '#') {
                        result += userInput[j++]
                    }
                    if (i + 1 < DATE_MASK.length && DATE_MASK[i + 1] != '#') {
                        result += DATE_MASK[++i]
                    }
                    i++
                }

                deletingBar = false

                editText.removeTextChangedListener(this)
                s.replace(0, s.length, result)
                editText.addTextChangedListener(this)
            }
        }
    }
}
