package advent.of.code

private data class LanternFish(val timer: Int) {
  fun subtractDay() = LanternFish(timer - 1)

  companion object {
    fun baby() = LanternFish(8)
    fun reset() = LanternFish(6)
  }
}

private data class MemoKey(val fish: LanternFish, val daysLeft: Int)

private fun getNumberFishiesMade(
  fish: LanternFish,
  daysLeft: Int,
  stored: MutableMap<MemoKey, Long>
): Long {
  val key = MemoKey(fish, daysLeft)
  stored[key]?.let{ return it }

  val numCreated = if (daysLeft == 0) {
    1L
  } else if (fish.timer == 0) {
    getNumberFishiesMade(LanternFish.reset(), daysLeft-1, stored) + getNumberFishiesMade(LanternFish.baby(), daysLeft - 1, stored)
  } else {
    getNumberFishiesMade(fish.subtractDay(), daysLeft-1, stored)
  }

  return numCreated.also{ stored[key] = it }
}

fun getTotalFishiesMade(input: String, daysLeft: Int): Long {
  val fishies = input.split(",").map{ LanternFish(it.toInt()) }

  val storedVals = mutableMapOf<MemoKey, Long>()
  return fishies.fold(0) { numFishies, fish ->
    numFishies + getNumberFishiesMade(fish, daysLeft, storedVals)
  }
}

object Day6 {
  val fileName = "day-6-input.txt"

  fun part1Answer(input: String) = getTotalFishiesMade(input, 80)

  fun part2Answer(input: String) = getTotalFishiesMade(input, 256)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 6 Part 1: ${part1Answer(it)}")
      println("Day 6 Part 2: ${part2Answer(it)}")
    }
  }
}