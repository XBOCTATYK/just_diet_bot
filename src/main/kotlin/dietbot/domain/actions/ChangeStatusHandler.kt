package dietbot.domain.actions

import dietbot.domain.commands.Commands
import dietbot.domain.states.StateList
import dietbot.services.AppState

class ChangeStatusHandler(private val stateService: AppState): Handler {
    override fun on(command: Commands): HandlerResult {
        return when (command) {
            is Commands.CallToInfoAboutCaloriesInDish -> askForCaloriesInDish()
            else -> HandlerResult.Empty()
        }
    }

    private fun askForCaloriesInDish(): HandlerResult {
        stateService.set(StateList.ADD_NMD)

        return HandlerResult.Success("Asking for calories!")
    }
}