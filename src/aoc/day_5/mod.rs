use core::ops;
use std::cmp::{max, min};
use std::collections::{HashMap, HashSet};
use std::fs;
use std::ops::{Range, RangeFrom};
use std::str::FromStr;
use itertools::Itertools;
use crate::aoc::MissingFileErr;

#[derive(Eq, Hash, PartialEq, Clone, Copy)]
struct Point {
  x: i32,
  y: i32,
}

impl Point {
  fn new(x: i32, y: i32) -> Point {
    Point { x, y }
  }

  fn get_direction_to(&self, point: &Point, include_diagonals: bool) -> Option<Point> {
    let direction = point.clone() - self.clone();
    let dir_x = direction.x;
    let dir_y = direction.y;

    let is_diagonal = include_diagonals && i32::abs(dir_x) == i32::abs(dir_y);
    let is_horizontal = dir_y == 0 && dir_x != 0;
    let is_vertical = dir_x == 0 && dir_y != 0;

    if is_diagonal || is_horizontal || is_vertical {
      Some(Point::new(dir_x.signum(), dir_y.signum()))
    } else {
      None
    }
  }
}


impl ops::Add<Point> for Point {
  type Output = Point;
  fn add(self, _rhs: Point) -> Point {
    Point::new(self.x+_rhs.x, self.y+_rhs.y)
  }
}

impl ops::Sub<Point> for Point {
  type Output = Point;
  fn sub(self, _rhs: Point) -> Point {
    Point::new(self.x-_rhs.x, self.y-_rhs.y)
  }
}


struct Line {
  start: Point,
  end: Point,
}

impl Line {
  fn get_points_on_line(&self, include_diagonals: bool) -> Vec<Point> {
    let mut start = self.start.clone();
    let end = &self.end;

    let direction = start.get_direction_to(end, include_diagonals);
    direction.as_ref().map(|dir| {
      let mut points: Vec<Point> = vec!();
      points.push(start.clone());
      while start != *end {
        start = start + *dir;
        points.push(start.clone());
      }

      points
    }).unwrap_or(vec!())
  }

  fn new(point1: Point, point2: Point) -> Line {
    Line {
      start: point1,
      end: point2,
    }
  }
}

fn get_number_of_points_visited(file_name: &str, include_diagonals: bool) -> u32 {
  type Count = u32;
  let mut visited: HashMap<Point, Count> = HashMap::new();
  let lines = parse_input_file(file_name).unwrap();

  lines
    .iter()
    .for_each(|line| {
      line
        .get_points_on_line(include_diagonals)
        .into_iter()
        .for_each(|point| {
          *visited
            .entry(point)
            .or_insert(0) += 1;
        })
    });

  visited
    .values()
    .filter(|count| **count >= 2)
    .count() as u32
}

pub fn day_5_part_1_answer(file_name: &str) -> u32 {
  get_number_of_points_visited(file_name, false)
}

pub fn day_5_part_2_answer(file_name: &str) -> u32 {
  get_number_of_points_visited(file_name, true)
}

fn parse_input_file(file_name: &str) -> Result<Vec<Line>, MissingFileErr> {
  let input =
    fs::read_to_string(file_name)
      .map_err(|_| MissingFileErr(file_name.to_string()))?;

  let point_split = " -> ";
  let point_pairs = input.lines().map(
    |line| {
        line
          .split(point_split)
          .map(|point| {
            let (x, y) = point
              .split(',')
              .map(i32::from_str)
              .filter_map(Result::ok)
              .collect_tuple::<(i32, i32)>()
              .unwrap();

            Point::new(x, y)
          })
          .collect_tuple::<(Point, Point)>().unwrap()
    }
  );

  let lines = point_pairs
    .map(|(point1, point2)| Line::new(point1, point2))
    .collect_vec();

  Ok(lines)
}


pub fn day_5() {
  let file = "src/aoc/day_5/input.txt";
  println!("Day 5 part 1: {}", day_5_part_1_answer(file));
  println!("Day 5 part 2: {}", day_5_part_2_answer(file));
}