use std::collections::HashMap;
use std::fs;
use std::slice::Iter;
use std::str::FromStr;
use indexmap::map::IndexMap;
use itertools::Itertools;
use crate::aoc::MissingFileErr;


type Board = Vec<Vec<Position>>;
type InputNumbers = Vec<u32>;

struct Position {
  value: u32,
  marked: bool
}

struct BoardGameData {
  input: InputNumbers,
  boards: Vec<Board>,
}

pub fn day_4() {
  let file_name = "src/resources/day_4_input.txt";
  println!("Day 4 part 1 answer: {}", day_4_part_1_answer(file_name));
  println!("Day 4 part 2 answer: {}", day_4_part_2_answer(file_name));
}

pub fn day_4_part_2_answer(file_name: &str) -> u32 {
  let BoardGameData { input, mut boards }  =
    parse_input_file(file_name).unwrap();

  let mut win_condition_map: WinConditionMap = HashMap::new();

  type BoardNumber = usize;
  type Answer = u32;
  let mut winners: IndexMap<BoardNumber, Answer> = IndexMap::new();

  for input in input.iter() {
    for (board_number, board) in boards.iter_mut().enumerate() {
      let win_conditions =
        calculate_win_conditions(board, input.clone(), board_number.clone());

      for win_condition in win_conditions.iter() {
        let count = win_condition_map
          .entry(win_condition.clone())
          .or_insert(0);

        *count += 1;

        if *count == 5 {
          let unmarked_vals = get_unmarked(board);
          let sum: u32 = unmarked_vals.iter().sum();
          winners.entry(board_number.clone()).or_insert(sum * input.clone());
        }
      }
    }
  }

  winners.values().last().unwrap().clone()
}

pub fn day_4_part_1_answer(file_name: &str) -> u32 {
  let BoardGameData { input, mut boards }  =
    parse_input_file(file_name).unwrap();

  let mut win_condition_map: WinConditionMap = HashMap::new();

  for input in input.iter() {
    for (board_number, board) in boards.iter_mut().enumerate() {
      let win_conditions =
        calculate_win_conditions(board, input.clone(), board_number);

      for win_condition in win_conditions.iter() {

        let count = win_condition_map
          .entry(win_condition.clone())
          .or_insert(0);

        *count += 1;

        if *count == 5 {
          let unmarked_vals = get_unmarked(board);
          let sum: u32 = unmarked_vals.iter().sum();
          return sum * input.clone();
        }
      }
    }
  }

  panic!("Bad input: should have returned inside the loop")
}


fn get_unmarked(board: &Board) -> Vec<u32> {
  board
    .iter()
    .flat_map(|row|
      row
        .iter()
        .filter(|pos| !pos.marked)
        .map(|pos| pos.value)
        .collect_vec()
    ).collect_vec()
}

type Count = u32;
type WinConditionMap = HashMap<WinConditionKey, Count>;

#[derive(Clone, Eq, PartialEq, Hash)]
enum WinConditionKey {
  Row(WinConditionKeyData),
  Column(WinConditionKeyData)
}

#[derive(Clone, Eq, PartialEq, Hash)]
struct WinConditionKeyData {
  board_number: usize,
  position: usize,
}


impl Position {
  fn marked(value: u32) -> Position {
    Position {
      value,
      marked: true
    }
  }

  fn unmarked(value: u32) -> Position {
    Position {
      value,
      marked: false
    }
  }
}

fn calculate_win_conditions(
  board: &mut Board,
  input: u32,
  board_number: usize
) -> Vec<WinConditionKey> {
  let board_size = 5;
  let mut matches: Vec<WinConditionKey> = vec!();
  for row in 0..board_size {
    for col in 0..board_size {
      if board[row][col].value ==  input {
        board[row][col] = Position::marked(board[row][col].value.clone());
        let row_key = WinConditionKey::Row(
          WinConditionKeyData {
            board_number,
            position: row
          });

        let col_key = WinConditionKey::Column(
          WinConditionKeyData {
            board_number,
            position: col
          }
        );

        matches.push(row_key);
        matches.push(col_key);
      }
    }
  }
  matches
}


//Parsing for input


fn parse_input_file(file_name: &str) -> Result<BoardGameData, MissingFileErr> {
  let input =
    fs::read_to_string(file_name)
      .map_err(|_| MissingFileErr(file_name.to_string()))?;

  let mut lines =
    input
      .lines()
      .filter(|line| line.to_string() != "".to_string());

  //Input of form: "2,7,3,8,4..."
  let board_game_input: InputNumbers =
    lines
      .next()
      .map(|line| line.split(","))
      .unwrap()
      .map(u32::from_str)
      .filter_map(Result::ok)
      .collect_vec();

  let board_lines = lines.clone().collect_vec();
  let mut board_lines = board_lines.iter();

  let mut boards: Vec<Board> = vec!();
  while let Some(board) = get_next_board(&mut board_lines) {
    boards.push(board);
  }

  Ok(
    BoardGameData {
      input: board_game_input,
      boards
  })
}

fn get_next_board(board_lines: &mut Iter<&str>) -> Option<Board> {
  let board_game_size = 5;
  (0..board_game_size).fold(Some(vec!()), |mut board_game, _| {
    board_lines
      .next()
      .map(|it| it.split_whitespace())
      .map(|nums_str| nums_str.map(|it| {
        Position::unmarked(u32::from_str(it).unwrap()) }).collect_vec())
      .map(|line| {
        board_game.as_mut().map(|it| it.push(line));
        board_game
      }).flatten()
  })
}