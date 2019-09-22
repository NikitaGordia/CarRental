package specification.car

import model.Car
import specification.CompositeSpecification

class NumberplateIs(private val plate: String) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = plate == candidate.numberplate
}