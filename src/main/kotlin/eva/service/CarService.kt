package eva.service

import main.db.Database
import base.model.Car
import base.model.CarUpdate
import eva.model.PricePair
import main.utils.CarUpdateMatcher
import main.utils.guard

class CarService(val db: Database) {

    fun getAllCars(): List<PricePair> = db.getAllCars()

    fun getCar(id: Int): Car = db.getCar(id)
}