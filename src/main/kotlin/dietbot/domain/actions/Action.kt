package dietbot.domain.actions

sealed class ActionResult {
    open val message: String? = null
    data class Success(override val message: String?): ActionResult()
    data class Error(override val message: String): ActionResult()
}

interface Action {
    fun execute(): ActionResult
}