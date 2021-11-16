package dietbot.telegram.keyboards

import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton

object StatKeyboard: Keyboard {
    override fun draw(): KeyboardReplyMarkup {
        return KeyboardReplyMarkup(
            KeyboardButton("show stat"),
            KeyboardButton("show list"),
            resizeKeyboard = true
        )
    }
}