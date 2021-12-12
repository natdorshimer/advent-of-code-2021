package advent.of.code

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
  val input = readFileToString(Day6.fileName)!!

  @Test
  fun part1Test() {
    assertEquals(5934, Day6.part1Answer(input))
  }

  @Test
  fun part2Test() {
    assertEquals(26984457539, Day6.part2Answer(input))
  }
}

class Day7Test {
  val input = readFileToString(Day7.fileName)!!

  @Test
  fun part1Test() {
    assertEquals(37, Day7.part1Answer(input))
  }

  @Test
  fun part2Test() {
    assertEquals(168, Day7.part2Answer(input))
  }
}


class Day8Test {
  val input = readFileToString(Day8.fileName)!!

  @Test
  fun part1Test() {
    assertEquals(26, Day8.part1Answer(input))
  }

  @Test
  fun part2Test() {
    assertEquals(61229, Day8.part2Answer(input))
  }
}

class Day9Test {
  val input = readFileToString(Day9.fileName)!!

  @Test
  fun part1Test() {
    assertEquals(15, Day9.part1Answer(input))
  }

  @Test
  fun part2Test() {
    assertEquals(1134, Day9.part2Answer(input))
  }
}

class Day10Test {
  val input = readFileToString(Day10.fileName)!!

  @Test
  fun part1Test() {
    assertEquals(26397, Day10.part1Answer(input))
  }

  @Test
  fun part2Test() {
    assertEquals(288957, Day10.part2Answer(input))
  }
}