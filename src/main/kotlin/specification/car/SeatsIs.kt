package specification.car

import model.Car
import specification.CompositeSpecification

class SeatsIs(private val seat: Int) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = seat == candidate.seats
}