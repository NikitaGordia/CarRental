package main.web

import io.ktor.util.KtorExperimentalAPI
import main.db.Database
import main.service.CarService

object WebStarter {
    @KtorExperimentalAPI
    fun run() {
        RegularWebGenerator(CarService(Database.getInstance()))
            .generateWeb(8080)
            .start()
    }
}