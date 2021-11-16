package dietbot.textanalyzer

import dietbot.domain.commands.Commands

class AddAnalyzer(private val dish: String, private val amount: String = ""): Analyzer {
    override fun produceCommand(): Commands {
        return when (!amount.isNullOrBlank()) {
            true -> Commands.AddCaloriesWithDish(dish, amount.toInt())
            false -> checkIfDishIsCalories(dish)
        }
    }

    private fun checkIfDishIsCalories(dish: String): Commands {
        return when (dish.toIntOrNull() === null) {
            true -> Commands.CallToInfoAboutCaloriesInDish(dish)
            false -> Commands.AddCalories(dish.toInt())
        }
    }
}