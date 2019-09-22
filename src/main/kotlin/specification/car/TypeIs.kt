package specification.car

import model.Car
import specification.CompositeSpecification

class TypeIs(private val type: String) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = type == candidate.type
}