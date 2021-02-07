import java.util.*

val Pair<Int, Int>.a: Int get() = first
val Pair<Int, Int>.b: Int get() = second

fun <T, R> Sequence<T>.reductions(initial: R, operation: (acc: R, T) -> R) : Sequence<R> = sequence {
    var last = initial
    forEach {
        last = operation(last, it)
        yield(last)
    }
}

class Atom(val data: IntArray) {

    override fun toString() = Arrays.toString(this.data)

    operator fun get(index: Atom) = Atom(data.sliceArray(index.data.toList()))

    operator fun set(index: Atom, value: Atom) {
        (index.data zip value.data).map { pair: Pair<Int, Int> ->
            val (index, value) = pair
            this.data[index] = value
        }
    }

    operator fun plus(b: Int) = Atom(data.map { it + b }.toIntArray())
    operator fun plus(other: Atom) = Atom((data zip other.data).map { it.a + it.b }.toIntArray())

    operator fun minus(b: Int) = Atom(data.map { it - b }.toIntArray())
    operator fun minus(other: Atom) = Atom((data zip other.data).map { it.a - it.b }.toIntArray())

    operator fun times(b: Int) = Atom(data.map { it * b }.toIntArray())
    operator fun times(other: Atom) = Atom((data zip other.data).map { it.a * it.b }.toIntArray())

    operator fun div(b: Int) = Atom(data.map { it / b }.toIntArray())
    operator fun div(other: Atom) = Atom((data zip other.data).map { it.a / it.b }.toIntArray())

    operator fun rem(b: Int) = Atom(data.map { it % b }.toIntArray())
    operator fun rem(other: Atom) = Atom((data zip other.data).map { it.a % it.b }.toIntArray())

    //Todo - implement this like rl.range
    operator fun rangeTo(b: Int): Atom = TODO("Not specified yet") //Atom(data.map { it % b }.toIntArray())
    operator fun rangeTo(other: Atom): Atom = TODO("Not specified yet") //Atom((data zip other.data).map { it.a % it.b }.toIntArray())

    fun cumsum(): Atom {
        var last = 0
        return Atom(data.map {
            last += it
            last
        }.toIntArray())
    }

    fun repeat(count: Int): Atom {
        return Atom(this.data.flatMap {
            value ->
            IntRange(1, count).map { value }
        }.toIntArray())
    }

    fun repeat(counts: IntArray): Atom {
        return Atom(this.data.zip(counts).flatMap {
            pair ->
            val (value, count) = pair
            IntRange(1, count).map { value }
        }.toIntArray())
    }

    fun repeat(counts: Atom): Atom {
        return this.repeat(counts.data)
    }
}

fun main(args: Array<String>) {
    val x = Atom(arrayOf(1, 2, 3, 4).toIntArray())
    val y = Atom(arrayOf(1, 3).toIntArray())
    println("x = $x")
    println("y = $y")
    println("x[y] = ${x[y]}")
    println("x + 1 = ${x + 1}")
    println("x + x = ${x + x}")
    println("x - 1 = ${x - 1}")
    println("x - x = ${x - x}")
    println("x * 1 = ${x * 1}")
    println("x * x = ${x * x}")
    println("x / 2 = ${x / 2}")
    println("x / (x * 2) = ${x / (x * 2)}")
    println("x % 2 = ${x % 2}")
    println("x % (x * 2) = ${x % Atom(arrayOf(2, 2, 2, 2).toIntArray())}")
    x[y] = y + 2; println("x[y] = y + 2; x = ${x}")
    try {
        x[y]..2
    } catch (ex: NotImplementedError) {
        println("TODO - Think of a useful way of using ..")
    }
    try {
        x[y]..y
    } catch (ex: NotImplementedError) {
        println("TODO - Think of a useful way of using ..")
    }
    println(x)
    println(x.cumsum())
    //SequenceOf
    println(sequenceOf(0, 1, 2, 3, 4).reductions(0) { last, it -> last + it }.toList()) //Cumsum
    println(sequenceOf(0, 1, 2, 3, 4).reduce { last, it -> last + it }) //Sum
    println(x.repeat(3))
    println(x.repeat(x))
}