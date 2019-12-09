package eva.web

import base.WebGenerator
import com.google.gson.Gson
import eva.service.CarService
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.util.pipeline.PipelineContext
import main.utils.guardSafe

class EvaWebGenerator(val service: CarService) : WebGenerator() {

    companion object {

        const val EVA_URL = "http://localhost:8082"
    }

    override fun generateWeb(): ApplicationEngine =
        WebBuilder().run {
            port = 8082
            engine = Engine.NETTY
            module = {
                routing {
                    get("/price-list") { priceList(this) }
                    get("/details") { carDetails(this) }
                    get("/gen") { generateCars(this) }
                }
            }
            build()
        }

    private suspend fun priceList(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val priceList = service.getPriceList()

            req.call.respondText { Gson().toJson(priceList) }
            println("EVA: price-list success ($priceList)")
        }
    }

    private suspend fun carDetails(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val id = req.call.parameters["id"]?.toInt() ?: return@guardSafe
            val car = service.getCar(id)

            req.call.respondText { Gson().toJson(car) }
            println("EVA: Details success ($id: $car)")
        }
    }

    private suspend fun generateCars(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val count = req.call.request.queryParameters["count"]?.toInt() ?: 0
            service.generateCars(count)
            req.call.respondText { "OK" }
        }
    }
}
