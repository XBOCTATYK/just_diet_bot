package dietbot.googlesheets

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.GetSpreadsheetByDataFilterRequest
import com.google.api.services.sheets.v4.model.UpdateValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import java.lang.Error

class TableService(
    val spreadSheetId: String,
    val tableName: String
) {
    private var spredsheets: Sheets = Initializer.initialize()

    fun findCellsInRange(range: String): MutableList<MutableList<Any>> {
        return runCatching {
            val response = spredsheets.spreadsheets().values()
                .get(spreadSheetId, "'$tableName'!$range")
                .execute()

            response.getValues()
        }.getOrNull() ?: throw Error("could not find cells")
    }

    fun writeRange(range: String, data: MutableList<MutableList<Any>> ): Int {
        return runCatching {
            val response = spredsheets.spreadsheets().values()
                .update(spreadSheetId, "'$tableName'!$range", ValueRange().setValues(data))
                .setValueInputOption("USER_ENTERED")
                .execute()

            return response.updatedCells
        }.getOrNull() ?: throw Error("could not write cells")
    }

    fun appendRange(range: String, data: MutableList<MutableList<Any>> ): Int {
        runCatching {
            val response = spredsheets.spreadsheets().values()
                .append(spreadSheetId, "'$tableName'!$range", ValueRange().setValues(data))
                .setValueInputOption("USER_ENTERED")
                .execute()

            return response.updates.updatedCells
        }.getOrThrow() ?: throw Error("could not write cells")
    }
}