use std::collections::HashMap;
use std::num::ParseIntError;

pub trait Then: Sized {
  fn then<R, F>(self, f: F) -> R
    where F: FnOnce(&Self) -> R;
}

impl<T> Then for T where T: Sized {
  fn then<R, F>(self, f: F) -> R
    where F: FnOnce(&Self) -> R
  {
    f(&self)
  }
}


pub fn binary_to_decimal(bin_str: &String) -> Result<usize, ParseIntError> {
  usize::from_str_radix(bin_str, 2)
}


pub fn to_frequency_map<T>(filtered_titles: &[T]) -> HashMap<T, u32> where
  T: std::hash::Hash,
  T: std::cmp::Eq,
  T: Clone {
  filtered_titles
    .iter()
    .fold(HashMap::new(), |mut map, key| {
      *map.entry(key.clone()).or_insert(0) += 1;
      map
    })
}


//should wrap this in a result later
pub fn transpose<T>(matrix: &Vec<Vec<T>>) -> Vec<Vec<T>> where T: Clone {
  let num_columns = matrix.first().map(|row| row.len()).unwrap_or(0);
  (0..num_columns).map(|it|
    matrix
      .iter()
      .map(|row| row.get(it).unwrap().clone())
      .collect::<Vec<T>>()
  ).collect()
}