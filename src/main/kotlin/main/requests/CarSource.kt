package main.requests

import adam.service.AdamCarService
import base.model.Car
import kotlinx.coroutines.withTimeout
import main.utils.guardAsync
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

abstract class CarSource {

    companion object {

        private const val TIME_LIMIT_SECONDS = 20L
    }

    private val cache : MutableMap<String, Pair<List<Car>, Date>> = HashMap()

    abstract suspend fun getData(minLimit: Int, maxLimit: Int): List<Car>

    suspend fun getCars(minLimit: Int, maxLimit: Int): List<Car> =
        guardAsync {
            withTimeout(TimeUnit.SECONDS.toMillis(TIME_LIMIT_SECONDS)) {
                cache["$minLimit $maxLimit"]?.takeIf {
                    it.second.day < Date().day || (it.second.month != Date().month && it.second.day > Date().day)
                }?.first ?: withTimeout(TimeUnit.SECONDS.toMillis(TIME_LIMIT_SECONDS)) {
                    getData(minLimit, maxLimit).also {
                        cache["$minLimit $maxLimit"] = Pair(it, Date())
                    }
                }
            }
        } ?: cache["$minLimit $maxLimit"]?.first ?: listOf()
}