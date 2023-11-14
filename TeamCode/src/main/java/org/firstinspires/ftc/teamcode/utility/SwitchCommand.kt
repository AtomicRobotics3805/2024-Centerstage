package org.firstinspires.ftc.teamcode.utility

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler

class SwitchCommand(
        private val value: () -> Any,
        private vararg val outcomes: Pair<Any, Command>
    ) : Command() {
        override val _isDone: Boolean
            get() = true

        override fun start() {
            val invokedValue: Any = value.invoke()
            outcomes.forEach {
                if(invokedValue == it.first) {
                    CommandScheduler.scheduleCommand(it.second)
                }
            }
        }
    }