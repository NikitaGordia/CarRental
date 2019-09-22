package web

import io.ktor.util.KtorExperimentalAPI
import db.Database
import service.CarService

object WebStarter {
    @KtorExperimentalAPI
    fun run() {
        RegularWebGenerator(CarService(Database.getInstance()))
            .generateWeb()
            .start(wait = true)
    }
}