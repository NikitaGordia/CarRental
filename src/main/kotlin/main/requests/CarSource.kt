package main.requests

import base.model.Car

abstract class CarSource<T> {
    abstract fun getData(minLimit: Int, maxLimit: Int): List<T>
    abstract fun toCars(list: List<T>): List<Car>
    fun getCars(minLimit: Int, maxLimit: Int) = toCars(getData(minLimit, maxLimit))
}