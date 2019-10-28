package main.web

import base.WebGenerator
import com.google.gson.Gson
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
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getOrFail
import io.ktor.util.pipeline.PipelineContext
import base.model.Car
import base.model.CarUpdate
import main.service.CarService
import main.utils.guardSafe

class RegularWebGenerator(val service: CarService) : WebGenerator() {

    @KtorExperimentalAPI
    override fun generateWeb(portX: Int): ApplicationEngine =
        WebBuilder().run {
            port = portX
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
            service.createCar(car)

            println("Create Car: $car")
            req.call.respond(HttpStatusCode.OK)
        }
    }

    private suspend fun getAllCars(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val cars = service.getAllCars()
            req.call.respondText { Gson().toJson(cars) }

            println("Get all Cars $cars")
        }
    }

    @KtorExperimentalAPI
    private suspend fun getCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val id = req.call.request.queryParameters.getOrFail("id").toInt()
            val car = service.getCar(id)
            req.call.respondText { Gson().toJson(car) }

            println("Get Car with id $id: $car")
        }
    }

    @KtorExperimentalAPI
    private suspend fun deleteCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val id = req.call.request.queryParameters.getOrFail("id").toInt()
            service.deleteCar(id)

            println("Delete Car with id $id")
            req.call.respond(HttpStatusCode.OK)
        }
    }

    @KtorExperimentalAPI
    private suspend fun updateCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val carUpdate = Gson().fromJson(req.call.receiveText(), CarUpdate::class.java)
            println(carUpdate)
            service.updateCar(carUpdate)

            println("Update car: $carUpdate")
            req.call.respond(HttpStatusCode.OK)
        }
    }
}