package advent.of.code

import kotlin.math.abs


fun getIncreasingFuelCosts(distance: Int): Int {
  //Gauss's Formula
  //1 + 2 + 3... n = (n+1)*(n)/2
  return (distance)*(distance+1)/2
}

fun mostEfficientFuel(input: String, fuelCostIncreases: Boolean): Int {
  val crabPositions =
    input
      .splitToSequence(',')
      .map(String::toInt)
      .sorted()
      .toList()

  val max = crabPositions.maxOf{ it }
  return (0..max).minOf { pos ->
    crabPositions
      .asSequence()
      .map{ crabPos -> abs(crabPos - pos) }
      .map{ distance -> if(fuelCostIncreases) getIncreasingFuelCosts(distance) else distance }
      .sum()
  }
}

object Day7 {
  val fileName = "day-7-input.txt"

  fun part1Answer(input: String): Int = mostEfficientFuel(input, fuelCostIncreases = false)

  fun part2Answer(input: String): Int = mostEfficientFuel(input, fuelCostIncreases = true)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 7 Part 1: ${part1Answer(it)}")
      println("Day 7 Part 2: ${part2Answer(it)}")
    }
  }
}