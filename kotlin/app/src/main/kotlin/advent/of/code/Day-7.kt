package advent.of.code

import java.lang.Integer.min
import kotlin.math.abs


fun mostEfficientFuel(input: String): Int {
  val crabPositions =
    input
      .splitToSequence(',')
      .map(String::toInt)
      .sorted()
      .toList()

  val max = crabPositions.maxOf{ it }
  return (0..max).fold(Int.MAX_VALUE) { minFuelCost, pos ->
    val fuelCost = crabPositions
      .asSequence()
      .map{ crabPos -> abs(crabPos - pos) }
      .sum()

    min(minFuelCost, fuelCost)
  }
}

fun getIncreasingFuelCosts(distance: Int): Int {
  //Gauss's Formula
  //1 + 2 + 3... n = (n+1)*(n)/2
  return (distance)*(distance+1)/2
}

fun mostEfficientFuelIncreasing(input: String): Int {
  val crabPositions =
    input
      .splitToSequence(',')
      .map(String::toInt)
      .sorted()
      .toList()

  val max = crabPositions.maxOf{ it }
  return (0..max).fold(Int.MAX_VALUE) { minFuelCost, pos ->
    val fuelCost = crabPositions
      .asSequence()
      .map{ crabPos -> getIncreasingFuelCosts(abs(crabPos - pos)) }
      .sum()

    min(minFuelCost, fuelCost)
  }
}

object Day7 {
  private val fileName = "day-7-input.txt"

  fun part1Answer(input: String): Int = mostEfficientFuel(input)

  fun part2Answer(input: String): Int = mostEfficientFuelIncreasing(input)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 7 Part 1: ${part1Answer(it)}")
      println("Day 7 Part 2: ${part2Answer(it)}")
    }
  }
}