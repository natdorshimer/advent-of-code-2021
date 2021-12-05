#![allow(unused)]

mod aoc;
#[cfg(test)]
mod tests {
  use super::*;

  #[test]
  fn day_4_part_1() {
    let answer = aoc::day_4_part_1_answer("src/aoc/day_4/input_test.txt");
    assert_eq!(answer, 4512);
  }
  #[test]
  fn day_4_part_2() {
    let answer = aoc::day_4_part_2_answer("src/aoc/day_4/input_test.txt");
    assert_eq!(answer, 1924);
  }
}
