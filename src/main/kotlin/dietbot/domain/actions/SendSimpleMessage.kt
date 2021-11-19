package dietbot.domain.actions

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import dietbot.domain.commands.Commands

class SendSimpleMessage(private val bot: Bot, private val message: Message): Handler {
    override fun on(command: Commands): HandlerResult {
        return when(command) {
            is Commands.ShowSimpleMessage -> showMessage(command, message)
            else -> HandlerResult.Empty()
        }
    }

    private fun showMessage(command: Commands.ShowSimpleMessage, message: Message): HandlerResult {
        bot.sendMessage(
            chatId = ChatId.fromId(message.chat.id),
            text = command.message
        )

        return HandlerResult.Success("Message sent")
    }
}