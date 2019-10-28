package eva.db

import base.model.Car
import base.model.toCarModel
import base.model.toPricePair
import eva.model.PricePair
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
            "CREATE TABLE IF NOT EXISTS EvaCar( " +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "producer TEXT," +
                    "model TEXT," +
                    "mileage INT," +
                    "numberplate TEXT," +
                    "seats INT," +
                    "type TEXT," +
                    "color TEXT," +
                    "price INT)"
        )
    }

    fun getAllCars(): List<PricePair> = runSqlQuery("SELECT * FROM Car")?.toPricePair() ?: emptyList()

    fun getCar(id: Int): Car =
        runSqlQuery("SELECT * FROM Car WHERE id = $id")?.toCarModel()?.get(0)
            ?: throw Exception("Car with id=$id doesn't exist")

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