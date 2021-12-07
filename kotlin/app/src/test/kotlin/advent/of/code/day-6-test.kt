package advent.of.code

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
  val fileName = "/day-6-input.txt"

  @Test
  fun day6Part1Test() {
    val input = readFileToString<Day6Test>(fileName)!!
    assertEquals(5934, getTotalFishiesMade(input, 80))
  }
}