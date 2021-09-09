package com.ids.mercury.custom


import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import com.ids.mercury.utils.AppHelper


class CustomTextViewBold : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        if (!isInEditMode)
            typeface = AppHelper.getTypeFaceBold(context)

/*        if (Locale.getDefault().language == "fa")
            setTypeface(typeface, Typeface.BOLD)*/
    }
}
