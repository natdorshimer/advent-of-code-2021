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


class Day8Test {
  private val fileName = "/day-8-input.txt"

  @Test
  fun day8Part1Test() {
    val input = readFileToString(fileName)!!
    assertEquals(26, Day8.part1Answer(input))
  }

  @Test
  fun day8Part2Test() {
    val input = readFileToString(fileName)!!
    assertEquals(61229, Day8.part2Answer(input))
  }
}

class Day9Test {
  private val fileName = "/day-9-input.txt"

  @Test
  fun day9Part1Test() {
    val input = readFileToString(fileName)!!
    assertEquals(15, Day9.part1Answer(input))
  }

  @Test
  fun day9Part2Test() {
    val input = readFileToString(fileName)!!
    assertEquals(1134, Day9.part2Answer(input))
  }
}