package dietbot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import dietbot.domain.actions.CreateNewDateHandler
import dietbot.domain.actions.ShowStatHandler
import dietbot.domain.actions.SendSimpleMessage
import dietbot.googlesheets.TableService
import dietbot.services.CommandWorker

fun initCommandWorker(
    tableService: TableService,
    botService: Bot,
    message: Message
): CommandWorker {
    return CommandWorker.Builder().build(
        listOf(
            CreateNewDateHandler(tableService),
            ShowStatHandler(tableService),
            SendSimpleMessage(botService, message)
        )
    )
}