package dietbot.domain.actions

import dietbot.domain.commands.Commands

sealed class HandlerResult {
    open val message: String? = null
    open val command: Commands? = null
    data class Success(override val message: String?): HandlerResult()
    data class Command(override val command: Commands): HandlerResult()
    data class Error(override val message: String): HandlerResult()
    data class Empty(override val message: String? = "name"): HandlerResult()
}

interface Handler {
    fun on(command: Commands): HandlerResult
}