package dietbot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.extensions.filters.Filter
import dietbot.domain.actions.ShowStatAction
import dietbot.domain.states.StateList
import dietbot.googlesheets.TableService
import dietbot.services.AppState
import dietbot.telegram.keyboards.StatKeyboard
import dietbot.textanalyzer.AnalyzerFactory

val myChatId = 336322411L

fun main() {
    val TABLE_SERVICE = TableService("1LQHrg_vnp3ueejshvKO8i9iVIvaAJsIeHKGStW3rQS8", "new_format")
    val appState = AppState(listOf(StateList.ADD_NMD, StateList.INITIAL), StateList.INITIAL)

    val bot = bot {
        token = "THAT'S SECRET :)"
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
                    text = ShowStatAction(TABLE_SERVICE).execute().message!!
                )

            }
            message(!Filter.User(myChatId)) {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "bot won't be converse with you",
                )
            }
            message(Filter.Text and Filter.User(myChatId)) {
                val text = message.text
                val analyzer = AnalyzerFactory.matchAndCreate(text)

                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = "",
                    replyMarkup = StatKeyboard.draw()
                )

            }
            message(Filter.Custom { appState.isState(StateList.INITIAL) } and Filter.Text and Filter.User(myChatId)) {

            }
        }
    }

    bot.startPolling()
}