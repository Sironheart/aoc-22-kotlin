package day04

import readInput

fun main() {
    fun part1(input: List<String>): Int = input.splitAtComma().makeRanges().isInRanges().count { it }

    fun part2(input: List<String>): Int = input.splitAtComma().makeRanges().sharesPartOfRange().count { it }

    val testInput = readInput("day04/test")
    check(part1(testInput) == 2)
    val input = readInput("day04/real")

    // Result part 1
    println(part1(input))

    // Result part 2
    println(part2(input))
}

fun List<String>.splitAtComma() = this.map {
    val splitIndex = it.indexOf(",")
    val first = it.take(splitIndex)
    val last = it.takeLast(it.length - splitIndex - 1)

    listOf(first, last)
}

fun List<List<String>>.makeRanges() = this.map {
    it.map { text ->
        val seperator = text.indexOf("-")
        val initial = text.take(seperator).toInt()
        val finishing = text.takeLast(text.length - seperator - 1).toInt()
        initial..finishing
    }
}

fun List<List<IntRange>>.isInRanges() = this.map {
    val firstRange = it.first()
    val secondRange = it.last()

    firstRange.all { element -> secondRange.contains(element) } || secondRange.all { element -> firstRange.contains(element) }
}

fun List<List<IntRange>>.sharesPartOfRange() = this.map {
    val firstRange = it.first()
    val secondRange = it.last()

    firstRange.any { element -> secondRange.contains(element) } || secondRange.any { element -> firstRange.contains(element) }
}