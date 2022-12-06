package day06

import readInput

fun main() {

    fun part1(input: List<String>): Int = input.first().findFirstDifferentCharactersFollowing(4)

    fun part2(input: List<String>): Int = input.first().findFirstDifferentCharactersFollowing(14)

    val testInput1 = readInput("day06/test1")
    val testInput2 = readInput("day06/test2")
    val testInput3 = readInput("day06/test3")
    val testInput4 = readInput("day06/test4")
    val testInput5 = readInput("day06/test5")
    println(part1(testInput1))
    check(part1(testInput1) == 7)
    check(part1(testInput2) == 5)
    check(part1(testInput3) == 6)
    check(part1(testInput4) == 10)
    check(part1(testInput5) == 11)
    val input = readInput("day06/real")

    // Result part 1
    println(part1(input))

    // Result part 2
    println(part2(input))
}

fun String.findFirstDifferentCharactersFollowing(characterCount: Int): Int {
    var indexOfNotFollowingCharacters = -1
    this.windowed(characterCount) {
        if (indexOfNotFollowingCharacters != -1) return@windowed
        var duplicates = false
        for (i in 0 until characterCount) {
            if (duplicates) return@windowed
            for (j in 0 until characterCount) {
                if (i == j) continue
                if (it[i] == it[j]) duplicates = true
            }
        }
        if (!duplicates) indexOfNotFollowingCharacters = this.indexOf(it.toString())
    }

    return indexOfNotFollowingCharacters + characterCount
}

