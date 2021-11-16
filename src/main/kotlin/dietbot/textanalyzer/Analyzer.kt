package dietbot.textanalyzer

import dietbot.domain.commands.Commands

interface Analyzer {
    fun produceCommand(): Commands
}