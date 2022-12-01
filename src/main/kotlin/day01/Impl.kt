package day01

import readInput

fun main() {
    fun sumArray(input: List<String>): List<Int> {
        val sumArray = mutableListOf<Int>()
        input.fold(0) { acc: Int , s: String ->
            if (s == "") {
                sumArray.add(0)
                0
            } else {
                val sum = acc + s.toInt()
                sumArray.add(sum)
                sum
            }
        }

        return sumArray
    }

    fun highestSumFilter(sumArray: List<Int>): List<Int> {
        val highestSums = mutableListOf<Int>()

        sumArray.forEachIndexed { idx , value ->
            if (idx == sumArray.lastIndex && value != 0) highestSums.add(value)
            if (value == 0) highestSums.add(sumArray[idx - 1])
        }

        return highestSums
    }

    fun part1(input: List<String>): Int = highestSumFilter(sumArray(input)).max()

    fun part2(input: List<String>, highestXElements: Int): Int = highestSumFilter(sumArray(input)).sorted().takeLast(highestXElements).sum()

    val testInput = readInput("day01/test")
    check(part1(testInput) == 24000)
    val input = readInput("day01/real")

    // Result part 1
    println(part1(input))

    // Result part 2
    println(part2(input, 3))
}