package utils.specification

interface Specification<T> {
    fun isSatisfiedBy(candidate: T): Boolean
    fun and(other: Specification<T>): Specification<T>
    fun or(other: Specification<T>): Specification<T>
    fun not(other: Specification<T>): Specification<T>
    fun andNot(other: Specification<T>): Specification<T>
    fun orNot(other: Specification<T>): Specification<T>
}

abstract class CompositeSpecification<T> : Specification<T> {
    override fun and(other: Specification<T>) = AndSpecification(this, other)

    override fun or(other: Specification<T>) = OrSpecification(this, other)

    override fun not(other: Specification<T>) = NotSpecification(other)

    override fun andNot(other: Specification<T>) = AndNotSpecification(this, other)

    override fun orNot(other: Specification<T>) = OrNotSpecification(this, other)
}

class EmptySpecification<T>(private val default: Boolean = true): CompositeSpecification<T>() {
    override fun isSatisfiedBy(candidate: T): Boolean = default
}

class AndSpecification<T>(
    private val left: Specification<T>,
    private val right: Specification<T>
): CompositeSpecification<T>() {
    override fun isSatisfiedBy(candidate: T) = left.isSatisfiedBy(candidate) && right.isSatisfiedBy(candidate)
}

class OrSpecification<T>(
    private val left: Specification<T>,
    private val right: Specification<T>
): CompositeSpecification<T>() {
    override fun isSatisfiedBy(candidate: T) = left.isSatisfiedBy(candidate) || right.isSatisfiedBy(candidate)
}

class NotSpecification<T>(
    private val wrapped: Specification<T>
): CompositeSpecification<T>() {
    override fun isSatisfiedBy(candidate: T) = !wrapped.isSatisfiedBy(candidate)
}

class AndNotSpecification<T>(
    private val left: Specification<T>,
    private val right: Specification<T>
): CompositeSpecification<T>() {
    override fun isSatisfiedBy(candidate: T) = left.isSatisfiedBy(candidate) && !right.isSatisfiedBy(candidate)
}

class OrNotSpecification<T>(
    private val left: Specification<T>,
    private val right: Specification<T>
): CompositeSpecification<T>() {
    override fun isSatisfiedBy(candidate: T) = left.isSatisfiedBy(candidate) || !right.isSatisfiedBy(candidate)
}