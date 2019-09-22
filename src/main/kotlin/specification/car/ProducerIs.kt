package specification.car

import model.Car

class ProducerIs(private val producer: String) : CarSpecification() {
    override fun isSatisfiedBy(candidate: Car) = producer == candidate.producer
}