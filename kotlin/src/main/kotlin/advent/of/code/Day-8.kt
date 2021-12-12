package advent.of.code


typealias Wire = Char
typealias Digit = Char

private fun hardOne(input: String): Int {

  /*
  1, 7, 4, 8 right off the bat
  can find six by looking at 8 and seeing which of 1 is broken off
  you can then record which of those wires is C or F
  3 is the 5 piece number with both wires from 1
  5 is the 5 piece number with the f wire from 1
  2 is the 5 piece number with the c wire from 1
  9 is the 6 piece number that contains all of 3
  0 is the remaining number 6 piece number
*/

  return input
    .lines()
    .sumOf { line ->
      val (inputSignals, outputSignals) = line.split('|')

      val wireCombinationsToDigit = mutableMapOf<Set<Wire>, Digit>()
      val wireMappings = mutableMapOf<Wire, Wire>() //input -> output
      val inputs = inputSignals.split(' ').map{ it.toSet() }

      inputs.forEach { signal ->
        val lengthValues = listOf(
          2 to '1',
          4 to '4',
          3 to '7',
          7 to '8'
        )
        lengthValues
          .firstOrNull{ (size, _) -> size == signal.size }
          ?.second
          ?.let{ wireCombinationsToDigit[signal] = it }
      }

      val six = inputs.first {
        val eight = wireCombinationsToDigit.entries.first{ (_, num) -> num == '8' }.key
        val one = wireCombinationsToDigit.entries.first{ (_, num) -> num == '1' }.key
        val subtraction = eight.subtract(it)
        val isSix = subtraction.size == 1 && one.contains(subtraction.toList().first())
        if(isSix) {
          val cWire = subtraction.toList().first()
          wireMappings['c'] = cWire
          wireMappings['f'] = one.subtract(setOf(cWire)).first()
        }
        isSix
      }
      wireCombinationsToDigit[six] = '6'

      val three = inputs.first {
        val one = wireCombinationsToDigit.entries.first{ (_, num) -> num == '1' }.key
        it.size == 5 && it.containsAll(one)
      }
      wireCombinationsToDigit[three] = '3'

      val five = inputs.first {
        it.size == 5
          && it.contains(wireMappings['f'])
          && wireCombinationsToDigit[it] != '3'
      }
      wireCombinationsToDigit[five] = '5'

      val two = inputs.first {
        it.size == 5
          && it.contains(wireMappings['c'])
          && wireCombinationsToDigit[it] != '3'
      }
      wireCombinationsToDigit[two] = '2'

      val nine = inputs.first {
        it.size == 6 && it.containsAll(three)
      }
      wireCombinationsToDigit[nine] = '9'

      val zero = inputs.first {
        it.size == 6 && !it.containsAll(nine) && !it.containsAll(six)
      }
      wireCombinationsToDigit[zero] = '0'

      outputSignals
        .splitToSequence(' ')
        .filter(String::isNotEmpty)
        .map(String::toSet)
        .mapNotNull(wireCombinationsToDigit::get)
        .fold("", String::plus)
        .toInt()
    }
}

private fun findNumberOfEasyDigits(input: String): Int {
  // 1, 4, 7, 8
  val easyNumberLengths = listOf(2, 4, 3, 7)
  return input
    .lines()
    .sumOf { line ->
      line
        .split('|')[1]
        .split(' ')
        .count { it.length in easyNumberLengths }
    }
}

object Day8 {
  val fileName = "day-8-input.txt"

  fun part1Answer(input: String): Int = findNumberOfEasyDigits(input)

  fun part2Answer(input: String): Int = hardOne(input)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 8 Part 1: ${part1Answer(it)}")
      println("Day 8 Part 2: ${part2Answer(it)}")
    }
  }
}