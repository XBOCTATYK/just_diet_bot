package dietbot.services

import dietbot.domain.actions.Handler
import dietbot.domain.actions.HandlerResult
import dietbot.domain.commands.Commands
import java.util.stream.Collectors

class CommandWorker(
    private val handlerList: List<Handler>
) {
    private val subscribers: MutableList<(results: List<HandlerResult>) -> Any> = mutableListOf()

    fun dispatch(command: Commands): HandlerResult {
        val performResults = handlerList
            .parallelStream()
            .map { it.on(command) }
            .collect(Collectors.toList())

        subscribers.parallelStream()
            .forEach { it(performResults) }

        performResults.map {
            if (it is HandlerResult.Command)
                dispatch(it.command)
            else
                it
        }

        return HandlerResult.Success("")
    }

    fun subscribe(supplier: (results: List<HandlerResult>) -> Any) {
        subscribers.add(supplier)
    }

    class Builder {
        private val list = mutableListOf<Handler>()

        public fun addHandler(handler: Handler): CommandWorker.Builder {
            list.add(handler)

            return this
        }

        public fun build(handlerList: List<Handler>): CommandWorker {
            list.addAll(handlerList)
            return CommandWorker(handlerList)
        }
    }
}