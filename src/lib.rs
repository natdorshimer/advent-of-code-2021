mod aoc;


#[cfg(test)]
mod tests {
  use super::*;

  #[test]
  fn day_4_part_1() {
    let answer = aoc::day_4_part_1_answer("src/resources/test/day_4_input.txt");
    assert_eq!(answer, 4512);
  }
  #[test]
  fn day_4_part_2() {
    let answer = aoc::day_4_part_2_answer("src/resources/test/day_4_input.txt");
    assert_eq!(answer, 1924);
  }

  #[test]
  fn day_5_part_1() {
    let answer = aoc::day_5_part_1_answer("src/resources/test/day_5_input.txt");
    assert_eq!(answer, 5);
  }

  #[test]
  fn day_5_part_2() {
    let answer = aoc::day_5_part_2_answer("src/resources/test/day_5_input.txt");
    assert_eq!(answer, 12);
  }

  #[test]
  fn day_6_part_1() {
    let answer = aoc::day_6_part_1_answer("src/resources/test/day_6_input.txt");
    assert_eq!(answer, 5934);
  }

  #[test]
  fn day_6_part_2() {
    let answer = aoc::day_6_part_2_answer("src/resources/test/day_6_input.txt");
    assert_eq!(answer, 26984457539);
  }
}
