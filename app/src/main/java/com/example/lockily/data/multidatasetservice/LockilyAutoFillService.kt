package com.example.lockily.data.multidatasetservice

import android.app.assist.AssistStructure
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.*
import androidx.annotation.RequiresApi
import com.example.lockily.domain.models.ParsedStructure

@RequiresApi(Build.VERSION_CODES.O)
class LockilyAutoFillService: AutofillService() {
    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        val context: List<FillContext> = request.fillContexts
        val structure: AssistStructure = context[context.size - 1].structure
        val parsedStructure: ParsedStructure = parseStructure(structure)
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        TODO("Not yet implemented")
    }
}