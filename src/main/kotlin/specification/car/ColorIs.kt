package specification.car

import model.Car
import specification.CompositeSpecification

class ColorIs(private val color: String) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = color == candidate.color
}