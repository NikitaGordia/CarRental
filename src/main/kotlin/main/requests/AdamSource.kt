package main.requests

import adam.model.AdamCar
import adam.web.AdamWebGenerator.Companion.ADAM_URL
import base.model.Car
import com.google.gson.Gson
import java.net.URL

class AdamSource : CarSource() {

    override suspend fun getData(minLimit: Int, maxLimit: Int) =
        Gson().fromJson(
            URL("$ADAM_URL/search?minLimit=$minLimit&maxLimit=$maxLimit").readText(),
            Array<AdamCar>::class.java
        ).toList()
            .map {
                Car(it.id, it.producer, it.price)
            }

}