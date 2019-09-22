package main

import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getOrFail
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Car
import model.CarUpdate
import utils.guardSafe

class RegularServerGenerator(val db: Db) : ServerGenerator(), CoroutineScope {

    override val coroutineContext = Dispatchers.IO

    @KtorExperimentalAPI
    override fun generateServer(): ApplicationEngine =
        ServerBuilder().run {
            port = 8080
            engine = Engine.NETTY
            module = {
                routing {
                    get("/allCars") { getAllCars(this) }
                    get("/car") { getCar(this) }
                    delete("/car") { deleteCar(this) }
                    post("/car") { createCar(this) }
                    post("/updCar") { updateCar(this) }
                }
            }
            build()
        }

    private suspend fun createCar(req: PipelineContext<Unit, ApplicationCall>) {
        val bodyText = req.call.receiveText()
        guardSafe {
            val car = Gson().fromJson(bodyText, Car::class.java)
            db.insertCar(car)

            println("Create Car: $car")
            req.call.respond(HttpStatusCode.OK)
        }
    }

    private suspend fun getAllCars(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val cars = db.getAllCars()
            req.call.respondText { Gson().toJson(cars) }

            println("Get all Cars $cars")
        }
    }

    @KtorExperimentalAPI
    private suspend fun getCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val id = req.call.request.queryParameters.getOrFail("id").toInt()
            val car = db.getCar(id)
            req.call.respondText { Gson().toJson(car) }

            println("Get Car with id $id: $car")
        }
    }

    @KtorExperimentalAPI
    private suspend fun deleteCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val id = req.call.request.queryParameters.getOrFail("id").toInt()
            db.deleteCar(id)

            println("Delete Car with id $id")
            req.call.respond(HttpStatusCode.OK)
        }
    }

    @KtorExperimentalAPI
    private suspend fun updateCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val carUpdate = Gson().fromJson(req.call.receiveText(), CarUpdate::class.java)
            println(carUpdate)
            db.updateCar(carUpdate)

            println("Update car: $carUpdate")
            req.call.respond(HttpStatusCode.OK)
        }
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