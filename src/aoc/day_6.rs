use std::collections::HashMap;
use std::fs;
use std::str::FromStr;
use itertools::Itertools;
use crate::aoc::MissingFileErr;

pub fn day_6() {
  let file_name = "src/resources/day_6_input.txt";
  println!("Day 6 answer: {}", day_6_part_1_answer(file_name));
  println!("Day 6 answer: {}", day_6_part_2_answer(file_name));
}


#[derive(Hash, Eq, PartialEq, Clone)]
struct MemoKey(LanternFish, Days);
type Days = u64;


fn get_num_fishies_fish_makes(
  fish: LanternFish,
  days: u64,
  memo: &mut HashMap<MemoKey, u64>
) -> u64 {

  let key = MemoKey(fish.clone(), days);
  if let Some(num_fishies) = memo.get(&key) {
    return num_fishies.clone()
  }

  if days == 0 {
    memo.insert(key, 1);
    return 1

  }

  return if fish.timer == 0 {
    let amount =
      get_num_fishies_fish_makes(LanternFish::new_baby(), days-1, memo) +
      get_num_fishies_fish_makes(fish.reset(), days-1, memo);

    memo.insert(key, amount.clone());
    amount
  } else {
    get_num_fishies_fish_makes(fish.subtract_day(), days-1, memo)
  };
}

fn get_total_number_fishies_created(mut fishies: Vec<LanternFish>, days: u64) -> u64 {
  let mut num_can_create: HashMap<MemoKey, u64> = HashMap::new();

  fishies
    .iter()
    .fold(0u64, |amount, fish| {
      amount + get_num_fishies_fish_makes(fish.clone(), days.clone(), &mut num_can_create)
  })
}


#[derive(Clone, Hash, Eq, PartialEq)]
struct LanternFish {
  timer: u64
}

impl LanternFish {
  fn new_baby() -> LanternFish {
    LanternFish { timer: 8 }
  }

  fn reset(&self) -> LanternFish {
    LanternFish { timer: 6 }
  }

  fn subtract_day(&self) -> LanternFish {
    LanternFish { timer: self.timer - 1 }
  }

  fn new(timer: u64) -> LanternFish {
    LanternFish { timer }
  }
}

fn parse_input_file(file_name: &str) -> Result<Vec<LanternFish>, MissingFileErr> {
  let input =
    fs::read_to_string(file_name)
      .map_err(|_| MissingFileErr(file_name.to_string()))?;

  let fish = input
    .split(',')
    .map(u64::from_str)
    .filter_map(Result::ok)
    .map(LanternFish::new)
    .collect_vec();

  Ok(fish)
}

pub fn day_6_part_1_answer(file_name: &str) -> u64 {
  let mut fishies = parse_input_file(file_name).unwrap();
  get_total_number_fishies_created(fishies, 80)
}

pub fn day_6_part_2_answer(file_name: &str) -> u64 {
  let mut fishies = parse_input_file(file_name).unwrap();

  get_total_number_fishies_created(fishies, 256)
}
