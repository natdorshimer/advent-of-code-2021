use std::cmp::Ordering;
use std::collections::HashMap;
use std::fs;
use itertools::Itertools;
use crate::aoc::{binary_to_decimal, Then, to_frequency_map, transpose};

enum Measurement {
  Gamma,
  Epsilon,
  Oxygen,
  CO2Scrubber
}

fn sorted_by_greater(freq1: &(&char, &u32), freq2: &(&char, &u32)) -> Ordering {
  freq2.1.cmp(freq1.1)
}

type FrequencyMapSortByFn = fn(freq1: &(&char, &u32), freq2: &(&char, &u32)) -> Ordering;
fn sorted_by_less(freq1: &(&char, &u32), freq2: &(&char, &u32)) -> Ordering {
  freq1.1.cmp(freq2.1)
}

fn get_sorting_fn(measurement: &Measurement) -> FrequencyMapSortByFn {
  match measurement {
    Measurement::Gamma | Measurement::Oxygen => sorted_by_greater,
    Measurement::Epsilon | Measurement::CO2Scrubber => sorted_by_less
  }
}

fn get_char_by_rating(chars: &Vec<char>, rating: &Measurement) -> char {
  to_frequency_map(chars)
      .iter()
      .sorted_by(get_sorting_fn(rating))
      .next_tuple::<((&char, &u32), (&char, &u32))>()
      .map(|(highest, lowest)|
        if highest.1 == lowest.1 {
          match rating {
            Measurement::Oxygen => '1',
            Measurement::CO2Scrubber => '0',
            _ => panic!("not meant to be used with others")
          }
        } else {
          highest.0.clone()
        })
      .unwrap().clone()
}

pub fn day_3() {
  let file_name = "src/resources/day_3_input.txt";
  println!("Day 3 Part 1 Answer: {}", day_3_part_1_answer(file_name));
  println!("Day 3 Part 2 Answer: {}", day_3_part_2_answer(file_name));
}

pub fn day_3_part_1_answer(file_name: &str) -> u64 {
  let input = fs::read_to_string(file_name).unwrap();
  let lines: Vec<String> = input.lines().map(|it| it.to_string()).collect();
  let input_char_grid: Vec<Vec<char>> =
    lines
      .iter()
      .map(|it| it.chars().collect_vec())
      .collect();

  let columns = transpose(&input_char_grid);

  let vertical_bins: Vec<String> = columns
    .iter()
    .map(|column| column.iter().collect::<String>())
    .collect();


  let get_char_by_frequency_measurement = |freqs: HashMap<char, u32>, m: Measurement|
    freqs
      .iter()
      .sorted_by(get_sorting_fn(&m))
      .next()
      .map(|(char, _)| char.clone());

  let vertical_binary_frequencies = vertical_bins
    .iter()
    .map(|bin| bin.chars().collect_vec())
    .map(|bin_chars| to_frequency_map(&bin_chars));

  let gamma = vertical_binary_frequencies.clone()
    .flat_map(|freqs| get_char_by_frequency_measurement(freqs, Measurement::Gamma))
    .collect::<String>()
    .then(binary_to_decimal)
    .unwrap();

  let epsilon = vertical_binary_frequencies.clone()
    .flat_map(|freqs| get_char_by_frequency_measurement(freqs, Measurement::Epsilon))
    .collect::<String>()
    .then(binary_to_decimal)
    .unwrap();

  (gamma * epsilon) as u64
}

fn filter_by_rating(
  lines: &Vec<String>,
  column: usize,
  rating: &Measurement
) -> Vec<String> {
  let input_char_grid: Vec<Vec<char>> =
    lines
      .iter()
      .map(|it| it.chars().collect_vec())
      .collect();

  let columns = transpose(&input_char_grid);
  let char_to_filter = get_char_by_rating(&columns[column], rating);
  lines
    .clone()
    .into_iter()
    .filter(|str| str.chars().nth(column).unwrap() == char_to_filter)
    .collect::<Vec<String>>()
}

fn get_rating_from_input(lines: &Vec<String>, rating: &Measurement) -> usize {
  let num_columns = lines.first().unwrap().len();
  (0..num_columns).fold(lines.clone(), |acc, column| {
    match acc.len() {
      0 | 1 => acc,
      _ => filter_by_rating(&acc, column, rating)
    }
  })
    .first()
    .unwrap() // Bad input in this case
    .then(|it| binary_to_decimal(it))
    .unwrap()
}

pub fn day_3_part_2_answer(file_name: &str) -> u64 {
  let input = fs::read_to_string(file_name).unwrap();
  let lines: Vec<String> = input.lines().map(|it| it.to_string()).collect();

  let oxygen_value = get_rating_from_input(&lines, &Measurement::Oxygen);
  let co2_value = get_rating_from_input(&lines, &Measurement::CO2Scrubber);

  (oxygen_value*co2_value) as u64
}