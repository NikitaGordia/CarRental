package adam.service

import adam.db.AdamDatabase
import base.model.Car
import base.model.CarUpdate
import main.utils.CarUpdateMatcher
import main.utils.guard

class AdamCarService(val db: AdamDatabase) {
    fun getCar(minPrice: Int, maxPrice: Int): List<Car> = db.getCar(minPrice, maxPrice)
}