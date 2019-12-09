package main.requests

import base.model.Car
import main.service.CarService

class MainSource(val service: CarService) : CarSource() {

    override suspend fun getData(minLimit: Int, maxLimit: Int): List<Car> = service.searchCar(minLimit, maxLimit)
}
