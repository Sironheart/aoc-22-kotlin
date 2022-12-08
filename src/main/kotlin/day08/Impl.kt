package day08

import readInput

fun main() {
    fun part1(input: List<String>): Int =
        input.generateTreeMap().isVisibleFromOutside().sumOf { listIt -> listIt.count { it } }

    fun part2(input: List<String>): Int = input.generateTreeMap().canSeeXTrees().maxOf { it.max() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day08/test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("day08/real")
    println(part1(input))
    println(part2(input))
}

fun List<String>.generateTreeMap(): List<List<Int>> = this.map {
    it.map { c ->
        c.digitToInt()
    }
}

fun List<List<Int>>.isVisibleFromOutside(): List<List<Boolean>> = this.mapIndexed { indexY, row ->
    row.mapIndexed { indexX, tree ->
        if (indexY == this.lastIndex || indexY == this.indices.first || indexX == row.indices.first || indexX == row.lastIndex) true
        else {
            val treesUnderneath = this.lastIndex - indexY
            val treesMoreRight = row.lastIndex - indexX
            this[indexY].take(indexX).all { left -> tree > left } ||
                    this[indexY].takeLast(treesMoreRight).all { right -> tree > right } ||
                    this.take(indexY).all { up -> tree > up[indexX] } ||
                    this.takeLast(treesUnderneath).all { down -> tree > down[indexX] }
        }
    }
}

fun List<List<Int>>.canSeeXTrees(): List<List<Int>> = this.mapIndexed { indexY, row ->
    row.mapIndexed { indexX, tree ->
        var upSight = 0
        var downSight = 0
        var leftSight = 0
        var rightSight = 0

        val treesUnderneath = this.lastIndex - indexY
        val treesMoreRight = row.lastIndex - indexX

        val left = this[indexY].take(indexX).reversed()
        val right = this[indexY].takeLast(treesMoreRight)
        val up = this.take(indexY).map { it[indexX] }.reversed()
        val down = this.takeLast(treesUnderneath).map { it[indexX] }

        if (left.isNotEmpty()) {
            for (i in 0..left.lastIndex) {
                if (tree > left[i]) leftSight++
                else {
                    leftSight++
                    break
                }
            }
        }

        if (right.isNotEmpty()) {
            for (i in 0..right.lastIndex) {
                if (tree > right[i]) rightSight++
                else {
                    rightSight++
                    break
                }
            }
        }

        if (up.isNotEmpty()) {
            for (i in 0..up.lastIndex) {
                if (tree > up[i]) upSight++
                else {
                    upSight++
                    break
                }
            }
        }

        if (down.isNotEmpty()) {
            for (i in 0..down.lastIndex) {
                if (tree > down[i]) downSight++
                else {
                    downSight++
                    break
                }
            }
        }

        upSight * downSight * leftSight * rightSight
    }
}