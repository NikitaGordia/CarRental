package main

import main.db.Database
import main.service.CarService
import main.web.MainWebGenerator

object Main {

    fun create() = MainWebGenerator(CarService(Database.getInstance())).generateWeb()
}