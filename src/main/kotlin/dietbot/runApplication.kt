package dietbot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.extensions.filters.Filter
import dietbot.domain.actions.CreateNewDateHandler
import dietbot.domain.actions.ShowStatHandler
import dietbot.domain.states.StateList
import dietbot.googlesheets.TableService
import dietbot.services.AppState
import dietbot.services.CommandWorker
import dietbot.services.CredentialsService
import dietbot.telegram.keyboards.StatKeyboard
import dietbot.textanalyzer.AnalyzerFactory

val myChatId = 336322411L

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

    val TABLE_SERVICE = TableService(credentialsService.config.spreadsheetId, "new_format")
    val commandWorker = CommandWorker(
        listOf(
            CreateNewDateHandler(tableService = TABLE_SERVICE),
            ShowStatHandler(tableService = TABLE_SERVICE),
        )
    )

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
            command("test") {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = ShowStatHandler(TABLE_SERVICE).on().message!!
                )

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
                val analyzer = AnalyzerFactory.matchAndCreate(text)


            }
        }
    }

    bot.startPolling()
}