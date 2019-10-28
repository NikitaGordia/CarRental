package main.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eva.model.PricePair
import java.net.URL

suspend fun <T> requestList(url: String): List<T> {
    val typeToken = object : TypeToken<Collection<T>>() {}.type
    return Gson().fromJson<List<T>>(URL("http://localhost:8081/price-list").readText(), typeToken)
}