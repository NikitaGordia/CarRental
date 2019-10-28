package main.utils.specification.car

import main.model.Car
import main.utils.specification.CompositeSpecification

class ColorIs(private val color: String) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = color == candidate.color
}

class MileageIs(private val mileages: Int) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = mileages == candidate.mileage
}

class ModelIs(private val model: String) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = model == candidate.model
}

class NumberplateIs(private val plate: String) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = plate == candidate.numberplate
}

class ProducerIs(private val producer: String) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = producer == candidate.producer
}

class SeatsIs(private val seat: Int) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = seat == candidate.seats
}

class TypeIs(private val type: String) : CompositeSpecification<Car>() {
    override fun isSatisfiedBy(candidate: Car) = type == candidate.type
}