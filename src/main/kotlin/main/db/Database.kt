package main.db

import main.model.Car
import main.model.CarUpdate
import main.model.toCarList
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class Database(private val connection: Connection) {

    companion object {
        private const val DB_NAME = "CarRental"
        private const val USER_NAME = "root"
        private const val USER_PASSWORD = "carrental"

        private var db: Database? = null

        fun getInstance(): Database {
            if (db == null) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver")
                    db = Database(
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

    fun getAllCars(): List<Car> = runSqlQuery("SELECT * FROM Car")?.toCarList() ?: emptyList()

    fun getCar(id: Int): Car =
        runSqlQuery("SELECT * FROM Car WHERE id = $id")?.toCarList()?.get(0)
            ?: throw Exception("Car with id=$id doesn't exist")

    fun deleteCar(id: Int) = runSqlUpdate("DELETE FROM Car WHERE id = $id")

    fun updateCar(carUpdate: CarUpdate) = with(carUpdate) {
        val sqlUpdatePref = "UPDATE Car SET"
        val sqlUpdareSuff = "WHERE id = '$id'"
        val sqlUpdateFields = formCarUpdateFields(carUpdate)

        val update = "$sqlUpdatePref $sqlUpdateFields $sqlUpdareSuff"
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

    private fun formCarUpdateFields(carUpdate: CarUpdate) = with(carUpdate) {
        val initList = mutableListOf<String>()
        producer?.let { initList += "producer = '$producer'" }
        model?.let { initList += "model = '$model'" }
        mileage?.let { initList += "mileage = '$mileage'" }
        numberplate?.let { initList += "numberplate = '$numberplate'" }
        seats?.let { initList += "seats = '$seats'" }
        type?.let { initList += "type = '$type'" }
        color?.let { initList += "color = '$color'"}
        initList.joinToString(", ")
    }
}