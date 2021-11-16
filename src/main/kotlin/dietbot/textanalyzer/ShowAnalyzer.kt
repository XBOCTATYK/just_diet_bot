package dietbot.textanalyzer

import dietbot.domain.commands.Commands

class ShowAnalyzer(val subject: String, val date: String?): Analyzer {
    override fun produceCommand(): Commands {
        return when (subject) {
            "stat" -> checkIsDateDefined(date)
            "list" -> Commands.ShowDishList()
            else -> Commands.ShowListForCurrentDate()
        }
    }

    private fun checkIsDateDefined(date: String?): Commands {
        return if (date == null)
            Commands.ShowListForCurrentDate()
        else Commands.ShowListForDate(date)
    }
}