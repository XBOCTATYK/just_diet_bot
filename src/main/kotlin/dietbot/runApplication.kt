package dietbot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.extensions.filters.Filter
import dietbot.domain.states.StateList
import dietbot.googlesheets.TableService
import dietbot.services.AppState
import dietbot.services.CredentialsService
import dietbot.telegram.keyboards.StatKeyboard
import dietbot.textanalyzer.AnalyzerFactory

const val myChatId = 336322411L

fun main() {
    val credentialsService = CredentialsService()
    val appState = AppState(
        listOf(
            StateList.ADD_NMD,
            StateList.INITIAL,
            StateList.MINUS_NMD,
            StateList.NEW_DISH_CALORIES,
            StateList.NEW_DISH_NAME
        ),
        StateList.INITIAL
    )

    val tableService = TableService(credentialsService.config.spreadsheetId, "new_format")
    val commandWorker = initCommandWorker(tableService)
    val stateChangeWorker = initStateCommandWorker(appState)

    val bot = bot {
        token = credentialsService.config.token
        dispatch {
            command("start") {
                val result = bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "Hi there! ${message.chat.id}",
                    replyMarkup = StatKeyboard.draw()
                )
                /*result.fold({
                    // do something here with the response
                },{
                    // do something with the error
                })*/
            }
            message(!Filter.User(myChatId)) {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "bot won't be converse with you",
                )
            }
            message(Filter.Text and Filter.User(myChatId)) {

            }
            message(Filter.Custom { appState.isState(StateList.INITIAL) } and Filter.Text and Filter.User(myChatId)) {
                val text = message.text
                val startCommand = AnalyzerFactory.matchAndCreate(text).produceCommand()
                val telegramWorker = initTelegramCommandWorker(bot, message)

                commandWorker.subscribe("out") { it -> it.map { println(it.toString()) } }
                commandWorker.subscribe("telegram_worker") {
                    telegramWorker.dispatchFromResult(it)
                    stateChangeWorker.dispatchFromResult(it)
                }

                telegramWorker.dispatch(startCommand)
                stateChangeWorker.dispatch(startCommand)

                val results = commandWorker.dispatch(startCommand)

                commandWorker.unsubscribe("telegram_worker")

                println("uu")
            }
        }
    }

    bot.startPolling()
}