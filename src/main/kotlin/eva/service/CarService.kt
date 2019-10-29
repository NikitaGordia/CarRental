package eva.service

import eva.db.Database
import base.model.Car
import eva.model.PricePair

class CarService(val db: Database) {

    fun getPriceList(): List<PricePair> = db.getPriceList()

    fun getCar(id: Int): Car = db.getDefails(id)
}