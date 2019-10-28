package main.service

import main.db.Database
import base.model.Car
import base.model.CarUpdate
import main.utils.CarUpdateMatcher
import main.utils.guard

class CarService(val db: Database) {

    fun createCar(car: Car) {
        db.insertCar(car)
    }

    fun getAllCars(): List<Car> = db.getAllCars()

    fun getCar(id: Int): Car = db.getCar(id)

    fun deleteCar(id: Int) = db.deleteCar(id)

    fun updateCar(carUpdate: CarUpdate) {
        guard { getCar(carUpdate.id) }?.takeIf {
            !CarUpdateMatcher.checkMatch(it, carUpdate)
        } ?: run {
            println("Trying to update the same object")
            return
        }
        db.updateCar(carUpdate)
    }
}