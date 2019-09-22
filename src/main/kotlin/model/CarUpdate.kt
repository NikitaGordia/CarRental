package model

data class CarUpdate(
    val id: Int,
    val producer: String? = null,
    val model: String? = null,
    val mileage: Int? = null,
    val numberplate: String? = null,
    val seats: Int? = null,
    val type: String? = null,
    val color: String? = null
)