import java.sql.Connection
import java.sql.DriverManager

class Db(val connection: Connection) {

    companion object {
        const val DB_NAME = "CarRental"
        const val USER_NAME = "root"
        const val USER_PASSWORD = "carrental"

        private var db: Db? = null

        fun getIntence(): Db {
            if (db == null) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver")
                    db = Db(DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/$DB_NAME?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                        USER_NAME,
                        USER_PASSWORD
                    ))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return db ?: throw Exception("Database isn't initialized")
        }
    }

    init {
        TODO("Initialize car table if it not exists")
    }

    fun createCar(): Any {
        TODO()
    }

    fun getCar(id: Int): Any {
        TODO()
    }

    fun deleteCar(id: Int) {
        TODO()
    }

    fun updateCar(user: Unit) {
        TODO()
    }
}