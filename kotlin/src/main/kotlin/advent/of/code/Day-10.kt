package advent.of.code

import java.util.ArrayDeque
import java.util.Stack


private val braces = mapOf(
  ')' to '(',
  ']' to '[',
  '}' to '{',
  '>' to '<',
)

private val leftBraces = braces.values
private val rightBraces = braces.keys

private fun incorrectScores(input: String): Int {
  val scoring = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
  )

  return input.lines().sumOf { line ->
    val charStack = Stack<Char>()

    line.forEach {
      when(it) {
        in leftBraces -> charStack.push(it)
        in rightBraces ->
          if(charStack.peek() != braces[it]) {
            return@sumOf scoring[it]!!
          } else {
            charStack.pop()
          }
      }
    }
    0
  }
}

private fun fixingIncompleteLines(input: String): Long {
  val scoring = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
  )

  val lineIsNotCorrupted = lambda@{ line: String ->
    val charStack = Stack<Char>()
    line.forEach {
      when (it) {
        in leftBraces -> charStack.push(it)
        in rightBraces ->
          if (charStack.peek() != braces[it]) {
            return@lambda false
          } else {
            charStack.pop()
          }
      }
    }
    true
  }

  val incompleteLines = input.lines().filter(lineIsNotCorrupted)
  val leftToRightMapping = leftBraces.zip(rightBraces).toMap()

  val scores =
    incompleteLines.map { line ->
      val charStack = ArrayDeque<Char>()
      line.forEach {
        when (it) {
          in leftBraces -> charStack.addLast(it)
          in rightBraces -> charStack.removeLast()
        }
      }
      charStack
        .descendingIterator()
        .asSequence()
        .fold(0L) { score, char ->
          score*5 + scoring[leftToRightMapping[char]]!!
        }
    }

  return scores.sorted()[scores.size/2]
}

object Day10 {
  val fileName = "day-10-input.txt"

  fun part1Answer(input: String): Int = incorrectScores(input)
  fun part2Answer(input: String): Long = fixingIncompleteLines(input)

  fun answers() {
    readFileToString(fileName)?.let{
      println("Day 10 Part 1: ${part1Answer(it)}")
      println("Day 10 Part 2: ${part2Answer(it)}")
    }
  }
}