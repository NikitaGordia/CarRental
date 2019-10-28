package main.web

import adam.db.AdamDatabase
import adam.service.AdamCarService
import adam.web.AdamWebGenerator
import io.ktor.util.KtorExperimentalAPI
import main.service.CarService
import main.db.Database

object WebStarter {
    @KtorExperimentalAPI
    fun run() {
        RegularWebGenerator(CarService(Database.getInstance()))
            .generateWeb(8080)
            .start()
    }
}