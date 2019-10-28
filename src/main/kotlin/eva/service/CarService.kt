package eva.service

import eva.db.Database
import base.model.Car
import eva.model.PricePair

class CarService(val db: Database) {

    fun getAllCars(): List<PricePair> = db.getAllCars()

    fun getCar(id: Int): Car = db.getCar(id)
}