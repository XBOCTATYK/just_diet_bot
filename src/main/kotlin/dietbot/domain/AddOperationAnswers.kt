package dietbot.domain

sealed class AddOperationAnswers {
    open val message: String = ""

    data class SuccessfullyAdded(override val message: String): AddOperationAnswers()
    data class NotFullData(override val message: String): AddOperationAnswers()
    data class Fail(override val message: String): AddOperationAnswers()
    data class NeedToAction(override val message: String): AddOperationAnswers()
}