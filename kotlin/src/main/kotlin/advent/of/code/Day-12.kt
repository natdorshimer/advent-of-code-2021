package advent.of.code

typealias Path = List<String>

fun pathsThroughCaves(
  input: String,
  smallCaveAllowance: Int = 0
): Int {
  val paths =
    input
      .lines()
      .fold(mutableMapOf<String, Path>()) { paths, line ->
        paths.apply {
          val (first, second) = line.split('-')
          paths[first] = listOf(second) + paths[first].orEmpty()
          paths[second] = listOf(first) + paths[second].orEmpty()
        }
      }.toMap()

  return pathsToEnd(
    "start",
    paths,
    smallCaveAllowance,
  ).filter{ it.isNotEmpty() && it.last() == "end" }
    .toSet()
    .size
}

fun pathsToEnd(
  position: String,
  paths: Map<String, Path>,
  smallCaveAllowance: Int = 0,
  canNoLongerVisit: List<String> = listOf(),
): List<Path> {
  if(position == "end") return listOf(listOf("end"))
  return paths[position]?.asSequence()
    ?.filter{ it != "start" }
    ?.filter{ it !in canNoLongerVisit || smallCaveAllowance > 0 }
    ?.map { nextPath ->
      val isSmallCave = nextPath == nextPath.lowercase()

      val allowance =
        if(isSmallCave && nextPath in canNoLongerVisit)
          smallCaveAllowance.dec()
        else smallCaveAllowance

      val restrictedList = if(isSmallCave) canNoLongerVisit.plus(nextPath) else canNoLongerVisit

      pathsToEnd(
        nextPath,
        paths,
        allowance,
        restrictedList,
      ).map{ listOf(position).plus(it) }
    }
    ?.reduceOrNull(List<Path>::plus)
    ?: listOf()
}

object Day12 {
  val fileName = "day-12-input.txt"

  fun part1Answer(input: String): Int = pathsThroughCaves(input)
  fun part2Answer(input: String): Int = pathsThroughCaves(input, smallCaveAllowance = 1)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 12 Part 1: ${part1Answer(it)}")
      println("Day 12 Part 2: ${part2Answer(it)}")
    }
  }
}