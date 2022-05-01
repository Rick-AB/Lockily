package com.example.lockily.data.multidatasetservice.model

import android.app.assist.AssistStructure
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.Expose

@RequiresApi(Build.VERSION_CODES.O)
class FilledAutofillField(viewNode: AssistStructure.ViewNode) {
    @Expose
    var textValue: String? = null

    @Expose
    var dateValue: Long? = null

    @Expose
    var toggleValue: Boolean? = null

    val autofillHints = viewNode.autofillHints?.filter(AutofillHelper::isValidHint)?.toTypedArray()

    init {
        viewNode.autofillValue?.let {
            if (it.isList) {
                val index = it.listValue
                viewNode.autofillOptions?.let { autofillOptions ->
                    if (autofillOptions.size > index) {
                        textValue = autofillOptions[index].toString()
                    }
                }
            } else if (it.isDate) {
                dateValue = it.dateValue
            } else if (it.isText) {
                // Using toString of AutofillValue.getTextValue in order to save it to
                // SharedPreferences.
                textValue = it.textValue.toString()
            } else {
            }
        }
    }

    fun isNull(): Boolean {
        return textValue == null && dateValue == null && toggleValue == null
    }
}