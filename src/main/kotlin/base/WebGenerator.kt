package base

import io.ktor.application.Application
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

abstract class WebGenerator {

    abstract fun generateWeb(portX: Int): ApplicationEngine

    protected inner class WebBuilder {
        var port: Int = 8080
        var engine: Engine = Engine.NETTY
        var module: Application.() -> Unit = {}

        fun build(): ApplicationEngine = embeddedServer(getRealEngine(), port = port, module = module)

        private fun getRealEngine() = when (engine) {
            Engine.NETTY -> Netty
        }
    }

    enum class Engine {
        NETTY
    }
}