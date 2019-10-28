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
import main.utils.guardSafe

class AdamWebGenerator(val service: AdamCarService) : WebGenerator() {

    @KtorExperimentalAPI
    override fun generateWeb(portX: Int): ApplicationEngine =
        WebBuilder().run {
            port = portX
            engine = Engine.NETTY
            module = {
                routing {
                    get("/search") { getCar(this) }
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
            req.call.respondText { Gson().toJson(cars) }

            println("Get Cars withing price limit [$minPrice, $maxPrice]")
        }
    }
}