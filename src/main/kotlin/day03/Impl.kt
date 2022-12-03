package day03

import readInput

fun main() {
    val priorityLow = ('a'..'z').toList()
    val priorityUp = ('A'..'Z').toList()

    fun findValue(letter: Char) =
        if (priorityLow.contains(letter)) priorityLow.indexOf(letter) + 1 else priorityUp.indexOf(letter) + 27

    fun part1(input: List<String>) = input.findDuplicates().sumOf { if (it.isEmpty()) 0 else it.sumOf { char -> findValue(char) } }

    fun part2(input: List<String>): Int = input.windowed(3, 3)
        .sumOf {
            it[0].toArrayOfChars().intersect(
                it[1].toArrayOfChars().toSet()
            ).intersect(
                it[2].toArrayOfChars().toSet()
            ).sumOf { c -> findValue(c) } }

    val testInput = readInput("day03/test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)
    val input = readInput("day03/real")

    // Result part 1
    println(part1(input))

    // Result part 2
    println(part2(input))
}

fun List<String>.findDuplicates(): List<Set<Char>> = this.map {
    val splitAt = it.length / 2
    val firstHalf = it.take(splitAt).toCharArray()
    val secondHalf = it.takeLast(splitAt)
    firstHalf.intersect(secondHalf.map { char -> char }.toSet())
}

fun String.toArrayOfChars() = this.map { c: Char -> c }