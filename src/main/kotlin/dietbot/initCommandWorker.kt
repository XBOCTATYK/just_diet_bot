package dietbot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import dietbot.domain.actions.*
import dietbot.googlesheets.TableService
import dietbot.services.AppState
import dietbot.services.CommandWorker

fun initCommandWorker(
    tableService: TableService,
): CommandWorker {
    return CommandWorker.Builder().build(
        listOf(
            CreateNewDateHandler(tableService),
            ShowStatHandler(tableService),
        )
    )
}

fun initTelegramCommandWorker(
    botService: Bot,
    inputDetails: Message
): CommandWorker {
    return CommandWorker.Builder().build(
        listOf(
            SendSimpleMessage(botService, inputDetails),
            CallInfoHandler(botService, inputDetails)
        )
    )
}

fun initStateCommandWorker(
    stateService: AppState
): CommandWorker {
    return CommandWorker.Builder().build(
        listOf(
            ChangeStatusHandler(stateService)
        )
    )
}