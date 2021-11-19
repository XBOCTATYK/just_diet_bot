package dietbot.domain.commands

sealed class Commands {
    data class ShowListForCurrentDate(val name: String? = "name"): Commands()
    data class ShowListForDate(val date: String): Commands()
    data class ShowDishList(val name: String? = "name"): Commands()
    data class ShowTodayStat(val name: String? = "name"): Commands()
    data class AddCalories(val amount: Int): Commands()
    data class AddCaloriesWithDish(val dish: String, val amount: Int): Commands()
    data class RemoveCalories(val amount: Int): Commands()
    data class RemoveCaloriesWithActivity(val activity: String, val amount: Int): Commands()
    data class CallToInfoAboutCaloriesInDish(val dish: String): Commands()
    data class CallToInfoAboutCaloriesInActivity(val activity: String): Commands()
    data class CreateNextDate(val name: String? = "name"): Commands()
    data class ShowSimpleMessage(val message: String): Commands()
    data class Empty(val name: String? = "name"): Commands()
}