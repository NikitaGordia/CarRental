package specification.car

import model.Car
import specification.CompositeSpecification

class ModelIs(private val model: String) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = model == candidate.model
}