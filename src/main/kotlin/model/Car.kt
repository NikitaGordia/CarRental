package model

import java.sql.ResultSet

data class Car(
    val id: Int,
    val producer: String,
    val model: String,
    val mileage: Int,
    val numberplate: String,
    val seats: Int,
    val type: String,
    val color: String
)

fun ResultSet.toCarList(): List<Car> {
    val resList = ArrayList<Car>()
    while (next()) {
        resList.add(
            Car(
                getInt("id"),
                getString("producer"),
                getString("model"),
                getInt("mileage"),
                getString("numberplate"),
                getInt("seats"),
                getString("type"),
                getString("color")
            )
        )
    }
    return resList
}