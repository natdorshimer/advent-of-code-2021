package advent.of.code

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
  private val fileName = "/day-6-input.txt"

  @Test
  fun day6Part1Test() {
    val input = readFileToString(fileName)!!
    assertEquals(5934, Day6.part1Answer(input))
  }
}

class Day7Test {
  private val fileName = "/day-7-input.txt"

  @Test
  fun day7Part1Test() {
    val input = readFileToString(fileName)!!
    assertEquals(37, Day7.part1Answer(input))
  }

  @Test
  fun day7Part2Test() {
    val input = readFileToString(fileName)!!
    assertEquals(168, Day7.part2Answer(input))
  }
}