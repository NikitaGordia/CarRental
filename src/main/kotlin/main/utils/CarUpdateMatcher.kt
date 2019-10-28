package main.utils

import base.model.Car
import base.model.CarUpdate
import main.utils.specification.EmptySpecification
import main.utils.specification.car.*
import utils.specification.car.*

class CarUpdateMatcher {
    companion object {
        fun checkMatch(dbCarRecord: Car, carUpdate: CarUpdate): Boolean =
            with(carUpdate) {
                EmptySpecification<Car>()
                    .and(ProducerIs(producer ?: dbCarRecord.producer))
                    .and(ModelIs(model ?: dbCarRecord.model))
                    .and(MileageIs(mileage ?: dbCarRecord.mileage))
                    .and(NumberplateIs(numberplate ?: dbCarRecord.numberplate))
                    .and(SeatsIs(seats ?: dbCarRecord.seats))
                    .and(TypeIs(type ?: dbCarRecord.type))
                    .and(ColorIs(color ?: dbCarRecord.color))
                    .isSatisfiedBy(dbCarRecord)
            }
    }
}