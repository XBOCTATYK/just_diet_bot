package dietbot.domain.actions

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import dietbot.domain.commands.Commands
import dietbot.telegram.keyboards.StatKeyboard

class SendSimpleMessage(private val bot: Bot, private val input: Message): Handler {
    override fun on(command: Commands): HandlerResult {
        return when(command) {
            is Commands.ShowSimpleMessage -> showMessage(command.message)
            else -> HandlerResult.Empty()
        }
    }

    private fun showMessage(message: String): HandlerResult {
        bot.sendMessage(
            chatId = ChatId.fromId(input.chat.id),
            text = message
        )

        return HandlerResult.Success("Message sent")
    }

    private fun showWithMainControls(message: String): HandlerResult {
        bot.sendMessage(
            chatId = ChatId.fromId(input.chat.id),
            text = message,
            replyMarkup = StatKeyboard.draw()
        )

        return HandlerResult.Success("Message sent")
    }
}