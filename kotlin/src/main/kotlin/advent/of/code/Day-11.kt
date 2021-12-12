package advent.of.code

operator fun Point.plus(right: Point) =
  (first + right.first) to (second + right.second)

private val directions = listOf(
  1 to 0,
  1 to 1,
  0 to 1,
  -1 to 1,
  -1 to 0,
  -1 to -1,
  0 to -1,
  1 to -1,
)

private data class Data<T>(
  val position: Point,
  val value: T
)

private fun<T> getAdjacents(
  matrix: List<List<T>>,
  pos: Point
): List<Data<T>> {
  val adjacents = directions.map{ it + pos }

  return adjacents.mapNotNull { point ->
    matrix
      .getOrNull(point.first)
      ?.getOrNull(point.second)
      ?.let {
        Data(
          position = point,
          value = it
        )
      }
  }
}

data class Octopus(
  val energyLevel: Int,
  var hasFlashed: Boolean,
) {
  fun nextStep() = Octopus(energyLevel + 1, hasFlashed)
  fun canFlash() = energyLevel > 9 && !hasFlashed
}

operator fun<T> List<List<T>>.get(pos: Point): T = this[pos.first][pos.second]
operator fun<T> List<MutableList<T>>.set(pos: Point, value: T) {
  this[pos.first][pos.second] = value
}


//Modifies octopus elements in the list by increasing energy levels and marking flash
fun computeFlashes(
  matrix: List<MutableList<Octopus>>,
  pos: Point,
  flashedOn: Boolean = false,
): Int {
  if(flashedOn) matrix[pos] = matrix[pos].nextStep()

  val currentOctopus = matrix[pos]

  return if(currentOctopus.canFlash()) {
    currentOctopus.hasFlashed = true
    getAdjacents(matrix, pos)
      .sumOf {
        computeFlashes(matrix, it.position, true)
      } + 1
  } else 0
}

fun getNumberOfFlashes(input: String): Int {
  val octoMatrix =
    input
      .lines()
      .map { line ->
        line.map{ char -> Octopus(char.digitToInt(), false)}.toMutableList()
      }.toList()

  return (1..100).sumOf {
    octoMatrix.forEach { octopuses ->
      octopuses.forEachIndexed { index, octopus ->
        octopuses[index] = if(octopus.hasFlashed) {
          Octopus(1, false)
        } else {
          octopus.nextStep()
        }
      }
    }

    octoMatrix.flatMapIndexed { rowNum, row ->
      row.mapIndexed { colNum, _ ->
        computeFlashes(octoMatrix, rowNum to colNum)
      }
    }.sum()
  }
}

fun findFirstSimultaneousFlash(input: String): Int {
  val octoMatrix =
    input
      .lines()
      .map { line ->
        line.map{ char -> Octopus(char.digitToInt(), false) }.toMutableList()
      }.toList()

  (1..Int.MAX_VALUE).forEach { step ->
    octoMatrix.forEach { octopuses ->
      octopuses.forEachIndexed { index, octopus ->
        octopuses[index] = if(octopus.hasFlashed) {
          Octopus(1, false)
        } else {
          octopus.nextStep()
        }
      }
    }

    octoMatrix.flatMapIndexed { rowNum, row ->
      row.mapIndexed { colNum, _ ->
        computeFlashes(octoMatrix, rowNum to colNum)
      }
    }

    if(octoMatrix.all { it.all(Octopus::hasFlashed) }) return step
  }

  throw Exception("Bad input: should have simultaneously flashed at some point")
}
object Day11 {
  val fileName = "day-11-input.txt"

  fun part1Answer(input: String): Int = getNumberOfFlashes(input)
  fun part2Answer(input: String): Int = findFirstSimultaneousFlash(input)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 10 Part 1: ${part1Answer(it)}")
      println("Day 10 Part 2: ${part2Answer(it)}")
    }
  }
}