package utils

import model.Car
import model.CarUpdate
import specification.Specification
import specification.car.*

class CarUpdateMatcher {
    companion object {
        fun checkMatch(dbCarRecord: Car, carUpdate: CarUpdate): Boolean {
            var spec: Specification<Car>? = null

            fun initOrAddSpec(newSpec: CarSpecification) {
                spec = spec?.and(newSpec) ?: newSpec
            }

            with(carUpdate) {
                producer?.let { initOrAddSpec(ProducerIs(producer)) }
                model?.let { initOrAddSpec(ModelIs(model)) }
                mileage?.let { initOrAddSpec(MileageIs(mileage)) }
                numberplate?.let { initOrAddSpec(NumberplateIs(numberplate)) }
                seats?.let { initOrAddSpec(SeatsIs(seats)) }
                type?.let { initOrAddSpec(TypeIs(type)) }
                color?.let { initOrAddSpec(ColorIs(color)) }
            }

            return spec?.isSatisfiedBy(dbCarRecord) ?: false
        }
    }
}