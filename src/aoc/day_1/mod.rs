use std::fs;
use std::str::FromStr;

pub fn day_1_part_1() {
  let lines = fs::read_to_string("src/aoc/day_1/input.txt").unwrap();
  let nums =
    lines
      .split_whitespace()
      .map(u32::from_str)
      .filter_map(|it| it.ok())
      .collect::<Vec<u32>>();

  let len = nums
    .windows(2)
    .map(|window| (window[0], window[1]))
    .map(|(first, second)| second.cmp(&first))
    .filter(|cmp| cmp.is_gt())
    .count();

  println!("Day 1 Answer: {}", len);
}

pub fn day_1_part_2() {
  let lines = fs::read_to_string("src/aoc/day_1/input.txt").unwrap();
  let nums =
    lines
      .split_whitespace()
      .map(u32::from_str)
      .filter_map(|it| it.ok())
      .collect::<Vec<u32>>();

  let window_size = 3;
  let len = nums
    .windows(window_size)
    .map(|window| window.iter().sum())
    .collect::<Vec<u32>>()
    .windows(2)
    .map(|window| (window[0], window[1]))
    .map(|(first, second)| second.cmp(&first))
    .filter(|ord| ord.is_gt())
    .count();

  println!("Day 1 Answer Part 2: {}", len);
}
