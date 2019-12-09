package main.requests

import base.model.Car
import com.google.gson.Gson
import eva.Eva
import eva.model.EvaCar
import eva.model.PricePair
import eva.web.EvaWebGenerator
import java.net.URL

class EvaSource : CarSource() {

    override suspend fun getData(minLimit: Int, maxLimit: Int): List<Car> =
        Gson().fromJson(URL("${EvaWebGenerator.EVA_URL}/price-list").readText(), Array<PricePair>::class.java).filter {
            it.price in minLimit..maxLimit
        }.map {
            Gson().fromJson(URL("${EvaWebGenerator.EVA_URL}/details/${it.id}").readText(), EvaCar::class.java)
        }.map {
            Car(it.id, it.producer, it.price)
        }
}