use std::fs;
use std::str::FromStr;
use itertools::Itertools;

pub fn day_2_part_1_answer(file_name: &str) -> u32{
  let input = fs::read_to_string(file_name).unwrap();
  let lines: Vec<&str> = input.lines().collect();
  let pos = parse_commands(lines)
    .into_iter()
    .fold(Position::default(), move_position_by_command);

  pos.horizontal*pos.depth
}

pub fn day_2() {
  let file_name = "src/resources/day_2_input.txt";
  println!("Day 2 Part 1 Answer: {}", day_2_part_1_answer(file_name));
  println!("Day 2 Part 2 Answer: {}", day_2_part_2_answer(file_name));
}

pub fn day_2_part_2_answer(file_name: &str) -> u32{
  let input = fs::read_to_string(file_name).unwrap();
  let lines: Vec<&str> = input.lines().collect();
  let pos = parse_commands(lines)
    .into_iter()
    .fold(SubmarinePosition::default(), move_submarine_position_by_command);

  pos.horizontal*pos.depth
}

fn move_position_by_command(
  position: Position,
  cmd: Command
) -> Position {
  let x = position.horizontal;
  let y = position.depth;
  match cmd {
    Command::Forward(v) => Position{
      horizontal: x + v,
      depth: y
    },
    Command::Down(v) => Position{
      horizontal: x,
      depth: y+v
    },
    Command::Up(v) => Position{
      horizontal: x,
      depth: y-v
    },
  }
}

fn move_submarine_position_by_command(
  position: SubmarinePosition,
  command: Command
) -> SubmarinePosition {
  let horizontal = position.horizontal;
  let depth = position.depth;
  let aim = position.aim;

  match command {
    Command::Forward(v) => SubmarinePosition {
      horizontal: horizontal+v,
      depth: depth + aim*v,
      aim
    },
    Command::Down(v) =>
      SubmarinePosition {
        horizontal,
        depth,
        aim: aim + v,
      },
    Command::Up(v) =>
      SubmarinePosition {
        horizontal,
        depth,
        aim: aim - v,
      }
  }
}


fn parse_commands(commands: Vec<&str>) -> Vec<Command> {
  commands
    .into_iter()
    .map(Command::from_str)
    .filter_map(|it| it.ok())
    .collect()
}


impl Command {
  fn from_str(input: &str) -> Result<Command, String> {
    //forward 5
    let (command, value_str): (&str, &str) =
      input.split_whitespace().next_tuple().ok_or("Not enough to create command")?;
    let value = u32::from_str(value_str)
      .ok()
      .ok_or("Could not convert value to an integer")?;

    match command {
      "forward" => Ok(Command::Forward(value)),
      "down" => Ok(Command::Down(value)),
      "up" => Ok(Command::Up(value)),
      cmd => Err(format!("Incorrect command {}", cmd).to_string())
    }
  }
}

#[derive(Default)]
struct Position {
  horizontal: u32,
  depth: u32
}

#[derive(Default)]
struct SubmarinePosition {
  horizontal: u32,
  depth: u32,
  aim: u32
}

enum Command {
  Forward(u32),
  Down(u32),
  Up(u32)
}