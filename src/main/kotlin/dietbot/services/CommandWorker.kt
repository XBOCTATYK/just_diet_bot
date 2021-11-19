package dietbot.services

import dietbot.domain.actions.Handler
import dietbot.domain.actions.HandlerResult
import dietbot.domain.commands.Commands
import java.util.stream.Collectors

data class Subscription(val name: String, val handler: (results: List<HandlerResult>) -> Any)

class CommandWorker(
    private val handlerList: List<Handler>
) {
    private val subscribers: MutableSet<Subscription> = mutableSetOf()

    fun dispatch(command: Commands): HandlerResult {
        val performResults = handlerList
            .parallelStream()
            .map { it.on(command) }
            .collect(Collectors.toList())

        subscribers.parallelStream()
            .forEach { it.handler(performResults) }

        dispatchFromResult(performResults)

        return HandlerResult.Success("")
    }

    fun dispatchFromResult(performResults: List<HandlerResult>): HandlerResult {
        performResults.map {
            if (it is HandlerResult.Command)
                dispatch(it.command)
            else
                it
        }

        return HandlerResult.Success("")
    }

    fun subscribe(name: String, supplier: (results: List<HandlerResult>) -> Any) {
        subscribers.add(Subscription(name, supplier))
    }

    fun unsubscribe(name: String) {
        subscribers.removeIf { it.name == name }
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