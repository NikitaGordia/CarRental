package adam

import adam.db.AdamDatabase
import adam.service.AdamCarService
import adam.web.AdamWebGenerator
import io.ktor.util.KtorExperimentalAPI

object Adam {
    @KtorExperimentalAPI
    fun create() = AdamWebGenerator(AdamCarService(AdamDatabase.getInstance()))
        .generateWeb(8081)
        .start()
}