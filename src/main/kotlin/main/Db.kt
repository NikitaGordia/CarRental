package main

import model.Car
import model.CarUpdate
import model.toCarList
import utils.CarUpdateMatcher
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class Db(private val connection: Connection) {

    companion object {
        private const val DB_NAME = "CarRental"
        private const val USER_NAME = "root"
        private const val USER_PASSWORD = "carrental"

        private var db: Db? = null

        fun getInstance(): Db {
            if (db == null) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver")
                    db = Db(
                        DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/$DB_NAME?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                            USER_NAME,
                            USER_PASSWORD
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return db ?: throw Exception("Database isn't initialized")
        }
    }

    init {
        runSqlUpdate(
            "CREATE TABLE IF NOT EXISTS Car( " +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "producer TEXT," +
                    "model TEXT," +
                    "mileage INT," +
                    "numberplate TEXT," +
                    "seats INT," +
                    "type TEXT," +
                    "color TEXT)"
        )
    }


    fun insertCar(car: Car) = with(car) {
        runSqlUpdate(
            "INSERT INTO Car(producer, model, mileage, numberplate, seats, type, color) " +
                    "VALUES('$producer', '$model', '$mileage', '$numberplate', '$seats', '$type', '$color')"
        )
    }

    fun getAllCars(): List<Car>? = runSqlQuery("SELECT * FROM Car")?.toCarList()

    fun getCar(id: Int): Car? = runSqlQuery("SELECT * FROM Car WHERE id = $id")?.toCarList()?.get(0)

    fun deleteCar(id: Int) = runSqlUpdate("DELETE FROM Car WHERE id = $id")

    fun updateCar(carUpdate: CarUpdate) = with(carUpdate) {
        val car = try {
            getCar(id)
        } catch (e:Exception) {
            null
        }

        car?.let {
            if (CarUpdateMatcher.checkMatch(car, carUpdate)) {
                println("TRUE")
                return
            }
        }

        val sqlUpdatePref = "UPDATE Car SET"
        val sqlUpdareSuff = "WHERE id = '$id'"

        val initList = mutableListOf<String>()
        producer?.let { initList += "producer = '$producer'" }
        model?.let { initList += "model = '$model'" }
        mileage?.let { initList += "mileage = '$mileage'" }
        numberplate?.let { initList += "numberplate = '$numberplate'" }
        seats?.let { initList += "seats = '$seats'" }
        type?.let { initList += "type = '$type'" }
        color?.let { initList += "color = '$color'"}

        val update = sqlUpdatePref + " " + initList.joinToString(",") + " " + sqlUpdareSuff
        println(update)
        runSqlUpdate(update)
    }

    private fun runSqlQuery(request: String): ResultSet? {
        var rs: ResultSet? = null
        try {
            rs = connection.createStatement().executeQuery(request)
        } catch (e: Exception) {
            println("Failed to run query \"$request\", error ${e.message}")
        }
        return rs
    }

    private fun runSqlUpdate(request: String) {
        try {
            connection.createStatement().executeUpdate(request)
        } catch (e: Exception) {
            println("Failed to run query \"$request\", error ${e.message}")
        }
    }
}