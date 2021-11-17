package dietbot.domain.actions

import dietbot.domain.commands.Commands
import dietbot.googlesheets.TableService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreateNewDateHandler(
    val tableService: TableService
): Handler {
    override fun on(command: Commands): HandlerResult {
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val dates = tableService.findCellsInRange("E10:Z10")

        if (!dates[0].contains(currentDate)) {
            val newList = mutableListOf<Any>()
            newList.addAll(dates[0])
            newList.add(currentDate)
            tableService.writeRange("E10:Z10", mutableListOf(newList))
        }

        return HandlerResult.Success("We got a result")
    }
}