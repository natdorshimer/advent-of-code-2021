package advent.of.code

typealias Point = Pair<Int, Int>

data class BasinData(
  val position: Point,
  val height: Int
)

fun getAdjacents(
  matrix: Array<IntArray>,
  pos: Point
): List<BasinData> {
  val adjacents = listOf(
    (pos.first + 1) to pos.second,
    (pos.first - 1) to pos.second,
    pos.first to (pos.second+1),
    pos.first to (pos.second-1)
  )

  return adjacents.mapNotNull { point ->
    matrix
      .getOrNull(point.first)
      ?.getOrNull(point.second)
      ?.let {
        BasinData(
          position = point,
          height = it
        )
      }
  }
}

fun getPointsInBasin(
  matrix: Array<IntArray>,
  pos: Point,
  basinPoints: Set<Point> = setOf()
): Set<Point> {
  val currentVal = matrix[pos.first][pos.second]
  return getAdjacents(matrix, pos)
    .filter { it.height in (currentVal + 1)..8 }
    .fold(basinPoints) { acc, adjacent ->
      acc + getPointsInBasin(matrix, adjacent.position, acc)
    } + setOf(pos)
}

fun sumRiskLevels(input: String): Int {
  val matrix = input
    .lines()
    .map{ line -> line.map(Char::digitToInt).toIntArray() }
    .toTypedArray()

  return matrix.flatMapIndexed { rowNumber, row ->
    row.mapIndexed { colNumber, value ->
      val point = rowNumber to colNumber

      if(isLowPoint(matrix, point, value))
        1 + value
      else 0
    }
  }.sum()
}

fun isLowPoint(
  matrix: Array<IntArray>,
  pos: Pair<Int, Int>,
  value: Int
) =
  getAdjacents(matrix, pos)
    .all{ adjacent -> adjacent.height > value }


fun biggestBasinsMultiplied(input: String): Int {
  val matrix = input
    .lines()
    .map{ line -> line.map(Char::digitToInt).toIntArray() }
    .toTypedArray()

  val basinSizes =
    matrix.flatMapIndexed { rowNumber, row ->
      row.mapIndexed { colNumber, value ->
        val point = rowNumber to colNumber
        if(isLowPoint(matrix, point, value))
          getPointsInBasin(matrix, point).size
        else 0
      }
    }

  return basinSizes
    .sortedDescending()
    .take(3)
    .reduce(Int::times)
}

object Day9 {
  val fileName = "day-9-input.txt"

  fun part1Answer(input: String): Int = sumRiskLevels(input)
  fun part2Answer(input: String): Int = biggestBasinsMultiplied(input)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 9 Part 1: ${part1Answer(it)}")
      println("Day 9 Part 2: ${part2Answer(it)}")
    }
  }
}