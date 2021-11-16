package dietbot.domain.actions

import dietbot.googlesheets.TableService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ShowStatAction(
    val tableService: TableService
): Action {
    override fun execute(): ActionResult {
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val dates = tableService.findCellsInRange("E10:Z10")
        val stats = tableService.findCellsInRange("E1:Z3")

        val index = dates[0].indexOf(currentDate)
        val dayAmount = stats[0][index]
        val dayBalance = stats[2][index] as String

        return ActionResult.Success("Today you took $dayAmount and ${0 - dayBalance.toInt()} calories left")
    }
}