use std::fs;
use std::str::FromStr;

pub fn day_1() {
  let lines = fs::read_to_string("src/aoc/day_1/input.txt").unwrap();
  let nums =
    lines
      .split_whitespace()
      .map(u32::from_str)
      .filter_map(|it| it.ok());

  let mut refs = nums.clone();
  refs.next();

  let len = nums
    .zip(refs)
    .map(|(first, second)| second.cmp(&first))
    .filter(|cmp| cmp.is_gt())
    .count();

  println!("Day 1 Answer: {}", len);
}
