package main.requests

import adam.Adam
import adam.model.AdamCar
import adam.service.AdamCarService
import adam.web.AdamWebGenerator.Companion.ADAM_URL
import base.model.Car
import com.google.gson.Gson
import java.net.URL

class AdamSource : CarSource<AdamCar>() {
    override fun getData(minLimit: Int, maxLimit: Int) =
        Gson().fromJson(URL("$ADAM_URL/search?minLimit=$minLimit&maxLimit=$maxLimit").readText(), Array<AdamCar>::class.java).toList()

    override fun toCars(list: List<AdamCar>) = list.map {
        Car(it.id, it.producer, it.price)
    }
}