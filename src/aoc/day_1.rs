use std::fs;
use std::str::FromStr;
use itertools::Itertools;

pub fn day_1_part_1_answer(file_name: &str) -> usize {
  let lines = fs::read_to_string(file_name).unwrap();

  lines
      .split_whitespace()
      .map(u32::from_str)
      .filter_map(|it| it.ok())
      .tuple_windows::<(u32, u32)>()
      .map(|(first, second)| second.cmp(&first))
      .filter(|cmp| cmp.is_gt())
      .count()

}

pub fn day_1() {
  let file_name = "src/resources/day_1_input.txt";
  println!("Day 1 Part 1 Answer: {}", day_1_part_1_answer(file_name));
  println!("Day 1 Part 2 Answer: {}", day_1_part_2_answer(file_name));
}

pub fn day_1_part_2_answer(file_name: &str) -> usize {
  let lines = fs::read_to_string(file_name).unwrap();
  let nums =
    lines
      .split_whitespace()
      .map(u32::from_str)
      .filter_map(|it| it.ok())
      .collect::<Vec<u32>>();

  let window_size = 3;

  nums
    .windows(window_size)
    .map(|window| window.iter().sum())
    .tuple_windows::<(u32, u32)>()
    .map(|(first, second)| second.cmp(&first))
    .filter(|ord| ord.is_gt())
    .count()
}
