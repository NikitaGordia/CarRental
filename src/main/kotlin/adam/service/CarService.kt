package adam.service

import adam.db.AdamDatabase
import base.model.Car
import base.model.CarUpdate
import main.utils.CarUpdateMatcher
import main.utils.guard
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

class AdamCarService(val db: AdamDatabase) {

    companion object {
        private const val MAX_GEN = 50_000
    }

    fun getCar(minPrice: Int, maxPrice: Int): List<Car> = db.getCar(minPrice, maxPrice)

    fun generateCars(count: Int) {
        val rand = Random(Date().time + count * (-12))
        for (i in 1..min(count, MAX_GEN)) {
            val id = rand.nextInt()
            db.insertCar(Car(id, "Producer $id", abs(id)), CarUpdate(id, "Producer $id", "Model $id", id, "$id", 4, "$id", "Color#$id"))
        }
    }
}