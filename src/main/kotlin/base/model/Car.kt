package base.model

import eva.model.PricePair
import java.sql.ResultSet

data class Car(
    val id: Int,
    val producer: String,
    val model: String,
    val mileage: Int,
    val numberplate: String,
    val seats: Int,
    val type: String,
    val color: String,
    val price: Int
)

fun ResultSet.toCarModel(): List<Car> {
    val resList = ArrayList<Car>()
    while (next()) {
        resList.add(
            Car(
                getInt("id"),
                getString("producer"),
                getString("main/modelmodel"),
                getInt("mileage"),
                getString("numberplate"),
                getInt("seats"),
                getString("type"),
                getString("color"),
                getInt("price")
            )
        )
    }
    return resList
}

fun ResultSet.toPricePair(): List<PricePair> {
    val resList = ArrayList<PricePair>()
    while (next()) {
        resList.add(
            PricePair(
                getInt("id"),
                getInt("price")
            )
        )
    }
    return resList
}

data class CarUpdate(
    val id: Int,
    val producer: String? = null,
    val model: String? = null,
    val mileage: Int? = null,
    val numberplate: String? = null,
    val seats: Int? = null,
    val type: String? = null,
    val color: String? = null
)