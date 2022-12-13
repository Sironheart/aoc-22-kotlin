package day12

import java.util.LinkedList
import java.util.Queue
import readInput

fun main() {

    fun List<String>.find(chars: List<Char>) = let { map ->
        val indexes = mutableListOf<Pair<Int, Int>>()

        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c in chars) indexes += Pair(y, x)
            }
        }

        return@let indexes
    }

    fun List<String>.normalize() = List(size) { y ->
        List(first().count()) { x ->
            when (this[y][x]) {
                'S' -> 'a'
                'E' -> 'z'
                else -> this[y][x]
            }
        }
    }

    fun List<List<Char>>.shortestPath(
        starts: List<Pair<Int, Int>>,
        end: Pair<Int, Int>,
    ) = let { map ->
        val height = map.size
        val width = map.first().count()

        val dist = List(height) { MutableList(width) { -1 } }
        val queue: Queue<Pair<Int, Int>> = LinkedList()

        for (start in starts) {
            dist[start.first][start.second] = 0
            queue.add(Pair(start.first, start.second))
        }

        while (queue.isNotEmpty()) {
            val (y, x) = queue.poll()

            if (x - 1 >= 0 && dist[y][x - 1] == -1 && map[y][x - 1] - map[y][x] <= 1) {
                queue += Pair(y, x - 1)
                dist[y][x - 1] = dist[y][x] + 1
            }

            if (x + 1 < width && dist[y][x + 1] == -1 && map[y][x + 1] - map[y][x] <= 1) {
                queue += Pair(y, x + 1)
                dist[y][x + 1] = dist[y][x] + 1
            }

            if (y - 1 >= 0 && dist[y - 1][x] == -1 && map[y - 1][x] - map[y][x] <= 1) {
                queue += Pair(y - 1, x)
                dist[y - 1][x] = dist[y][x] + 1
            }

            if (y + 1 < height && dist[y + 1][x] == -1 && map[y + 1][x] - map[y][x] <= 1) {
                queue += Pair(y + 1, x)
                dist[y + 1][x] = dist[y][x] + 1
            }
        }

        dist[end.first][end.second]
    }

    fun part1(input: List<String>) = input.let { map ->
        map.normalize().shortestPath(
            starts = map.find(listOf('S')),
            end = map.find(listOf('E')).single(),
        )
    }

    fun part2(input: List<String>) = input.let { map ->
        map.normalize().shortestPath(
            starts = map.find(listOf('S', 'a')),
            end = map.find(listOf('E')).single(),
        )
    }

    val input = readInput("day12/real")
    println(part1(input))
    println(part2(input))
}

