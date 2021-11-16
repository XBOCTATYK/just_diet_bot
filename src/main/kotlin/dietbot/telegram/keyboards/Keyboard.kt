package dietbot.telegram.keyboards
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup

interface Keyboard {
    fun draw(): KeyboardReplyMarkup
}