package adam.web

import adam.service.AdamCarService
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
import eva.service.CarService
import kotlinx.coroutines.delay
import main.utils.guardSafe
import kotlin.math.min

class AdamWebGenerator(val service: AdamCarService) : WebGenerator() {

    companion object {

        const val ADAM_URL = "http://localhost:8081"
    }

    @KtorExperimentalAPI
    override fun generateWeb(): ApplicationEngine =
        WebBuilder().run {
            port = 8081
            engine = Engine.NETTY
            module = {
                routing {
                    get("/search") { getCar(this) }
                    get("/gen") { generateCars(this) }
                }
            }
            build()
        }

    @KtorExperimentalAPI
    private suspend fun getCar(req: PipelineContext<Unit, ApplicationCall>) {
        guardSafe {
            val minPrice = req.call.request.queryParameters["minLimit"]?.toInt() ?: 0
            val maxPrice = req.call.request.queryParameters["maxLimit"]?.toInt() ?: Int.MAX_VALUE
            val cars = service.getCar(minPrice, maxPrice)
            delay(20100)
            req.call.respondText { Gson().toJson(cars) }

            println("ADAM : Get Cars withing price limit [$minPrice, $maxPrice]")
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