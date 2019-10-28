package eva

import eva.db.Database
import eva.service.CarService
import eva.web.EvaWebGenerator

object Eva {

    fun create() = EvaWebGenerator(CarService(Database.getInstance())).generateWeb()
}