package day05

import readInput

fun main() {
    var containerList = mutableListOf<MutableList<Container>>()
    fun part1(): String {
        var returnValue = ""
        containerList.forEach {
            if (it.isNotEmpty()) returnValue += it.last().type
        }

        return returnValue
    }

    fun part2(): String {
        var returnValue = ""
        containerList.forEach {
            if (it.isNotEmpty()) returnValue += it.last().type
        }

        return returnValue
    }

    val testInput = readInput("day05/test")
    containerList = testInput.parseToContainers(containerList)
    val testCommands = testInput.dropWhile { !it.startsWith("move") }
    executeCommands(containerList, testCommands)

    check(part1() == "CMZ")
    val input = readInput("day05/real")
    containerList = input.parseToContainers(containerList)
    val commands = input.dropWhile { !it.startsWith("move") }
    executeCommands(containerList, commands)

    // Result part 1
    println(part1())

    containerList = input.parseToContainers(containerList)
    executeCommandsPart2(containerList, commands)

    // Result part 2
    println(part2())
}

fun executeCommands(containers: MutableList<MutableList<Container>>, commands: List<String>) {
    commands.forEach {
        val movement = it.takeLast(6)

        val amount = (it.elementAt(5).toString() + it.elementAt(6).toString()).trim().toInt()
        val from = movement.first().toString().toInt() - 1
        val to = movement.last().toString().toInt() - 1

        for (i in 1..amount) {
            if (containers[from].isEmpty()) continue
            containers[to].add(containers[from].last())
            containers[from].removeAt(containers[from].lastIndex)
        }
    }
}

fun executeCommandsPart2(containers: MutableList<MutableList<Container>>, commands: List<String>) {
    commands.forEach {
        val movement = it.takeLast(6)

        val amount = (it.elementAt(5).toString() + it.elementAt(6).toString()).trim().toInt()
        val from = movement.first().toString().toInt() - 1
        val to = movement.last().toString().toInt() - 1

        containers[to].addAll(containers[from].takeLast(amount))
        for (i in 1..amount) {
            if (containers[from].isEmpty()) break
            containers[from].removeAt(containers[from].lastIndex)
        }
    }
}

fun List<String>.parseToContainers(containers: MutableList<MutableList<Container>>): MutableList<MutableList<Container>> {
    containers.clear()
    val parseLimit = this.indexOfFirst { it.contains("1") }
    val definition = this.take(parseLimit)
    val containerCount = this[parseLimit].trim().last().digitToInt()

    for (i in 1..containerCount) {
        containers.add(mutableListOf())
    }

    definition.forEachIndexed { _, line ->
        for (i in 1..containerCount) {
            val sign = line[4 * (i - 1) + 1].toString()
            if (sign.isBlank()) continue
            containers[i - 1].add(Container(sign))
        }
    }

    containers.forEach {
        it.reverse()
    }

    this.dropWhile {
        !it.startsWith("move")
    }

    return containers
}

data class Container(
    val type: String
) {
    override fun toString(): String {
        return type
    }
}

