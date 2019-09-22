package specification.car

import model.Car
import specification.CompositeSpecification

class MileageIs(private val mileages: Int) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = mileages == candidate.mileage
}