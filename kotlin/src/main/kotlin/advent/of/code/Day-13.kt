package advent.of.code

sealed class Fold(val foldLine: Int) {
  class HorizontalFold(foldLine: Int): Fold(foldLine)
  class VerticalFold(foldLine: Int): Fold(foldLine)
}

data class Day13Data(
  val points: List<Point>,
  val folds: List<Fold>
)

fun performFold(points: List<Point>, fold: Fold): List<Point> {
  return when(fold) {
    is Fold.VerticalFold -> {
      val upperHalf = points.filter{ it.second < fold.foldLine }
      val bottomHalf =
        points.filter{ it.second > fold.foldLine }

      val mirrored = bottomHalf
        .map{ it.first to 2*fold.foldLine - it.second }
        .filter{ it.second >= 0 }

      upperHalf.plus(mirrored).toSet().toList()
    }
    is Fold.HorizontalFold -> {
      val leftHalf = points.filter{ it.first < fold.foldLine }
      val rightHalf = points.filter{ it.first > fold.foldLine }

      val mirrored =
        rightHalf
          .map{ 2*fold.foldLine - it.first to it.second }
          .filter{ it.first >= 0 }

      leftHalf.plus(mirrored).toSet().toList()
    }
  }
}

fun dotsGridToString(points: List<Point>): String {
  val xMax = points.maxOf{ it.first }
  val yMax = points.maxOf{ it.second }

  val grid = List(yMax+1){ MutableList(xMax+1){ false } }

  points.forEach{ (x, y) ->
    grid[y][x] = true
  }

  return grid.joinToString("\n") { line ->
    line.joinToString(" "){ if(it) "#" else " " }
  }
}

fun parseInput(input: String): Day13Data {
  val pointsRegex = "\\w+[,]\\w+".toRegex() //ex: 234,31
  val points = pointsRegex.findAll(input)
    .map{ it.value }
    .map {
      val (x, y) = it.split(',')
      x.toInt() to y.toInt()
    }
    .toList()

  val foldsRegex = "\\w+[=]\\w+".toRegex()
  val folds = foldsRegex.findAll(input).map{ it.value }
    .map{ line ->
      val (direction, foldLine) = line.split('=')
      when(direction) {
        "x" -> Fold.HorizontalFold(foldLine.toInt())
        "y" -> Fold.VerticalFold(foldLine.toInt())
        else -> throw Exception("paper does not fold in this dimensions")
      }
    }.toList()

  return Day13Data(points, folds)
}

fun dotsVisibleAfterOneFold(input: String): Int {
  val (points, folds) = parseInput(input)
  return performFold(points, folds.first()).size
}

fun gridStringAfterEveryFold(input: String): String {
  val (points, folds) = parseInput(input)
  return folds
    .fold(points, ::performFold)
    .let(::dotsGridToString)
}

object Day13 {
  val fileName = "day-13-input.txt"

  fun part1Answer(input: String): Int = dotsVisibleAfterOneFold(input)
  fun part2Answer(input: String): String = gridStringAfterEveryFold(input)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 13 Part 1: ${part1Answer(it)}")
      println("Day 13 Part 2: \n${part2Answer(it)}\n")
    }
  }
}