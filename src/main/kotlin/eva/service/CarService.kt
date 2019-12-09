package eva.service

import adam.service.AdamCarService
import eva.db.Database
import base.model.Car
import base.model.CarUpdate
import eva.model.PricePair
import java.util.*
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

class CarService(val db: Database) {

    companion object {

        private const val MAX_GEN = 50_000
    }

    fun getPriceList(): List<PricePair> = db.getPriceList()

    fun getCar(id: Int): Car = db.getDefails(id)

    fun generateCars(count: Int) {
        val rand = Random(Date().time + count * (-12))
        for (i in 1..min(count, MAX_GEN)) {
            val id = rand.nextInt()
            db.insertCar(Car(id, "Producer $id", abs(id)), CarUpdate(id, "Producer $id", "Model $id", id, "$id", 4, "$id", "Color#$id"))
        }
    }
}