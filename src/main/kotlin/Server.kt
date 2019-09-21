import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext

class RegularServerGenerator(val db: Db) : ServerGenerator() {

    override fun generateServer(): ApplicationEngine =
        ServerBuilder().run {
            port = 8080
            engine = Engine.NETTY
            module = {
                routing {
                    get("/car") { getCar(this) }
                    delete("/car") { deleteCar(this) }
                    post("/car") { createCar(this) }
                    post("/updCar") { updateCar(this) }
                }
            }
            build()
        }

    private suspend fun createCar(req: PipelineContext<Unit, ApplicationCall>) {
        TODO()
    }

    private suspend fun getCar(req: PipelineContext<Unit, ApplicationCall>) {
        TODO()
    }

    private suspend fun deleteCar(req: PipelineContext<Unit, ApplicationCall>) {
        TODO()
    }

    private suspend fun updateCar(req: PipelineContext<Unit, ApplicationCall>) {
        TODO()
    }
}

abstract class ServerGenerator {

    abstract fun generateServer(): ApplicationEngine

    protected inner class ServerBuilder {
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