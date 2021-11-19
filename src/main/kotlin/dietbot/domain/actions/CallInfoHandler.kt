package dietbot.domain.actions

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import dietbot.domain.commands.Commands

class CallInfoHandler(private val bot: Bot, private val input: Message): Handler {
    override fun on(command: Commands): HandlerResult {
        return when (command) {
            is Commands.CallToInfoAboutCaloriesInDish -> HandlerResult.Command(Commands.ShowSimpleMessage("Сколько грамм?"))
            else -> HandlerResult.Empty()
        }
    }
}