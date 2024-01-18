(*
CSE 216
Homework #1
Brandon Wong
*)
let rec compress lst = match lst with 
  | [] -> []
  | [x] -> [x]
  | h :: (t :: tail) -> 
    if h = t then compress tail
    else h :: compress tail
(*Number 1*)
let rec compress lst = match lst with
  | [] -> []
  | [x] -> [x]
  | h :: (t :: _ as tail) -> 
    if h = t then compress tail
    else h :: compress tail
  ;;

(*Number 2*)
let rec remove_if lst predicate = match lst with
  | [] -> []
  | h :: t ->
    if predicate h then remove_if t predicate
    else h :: remove_if t predicate
  ;;

(*Number 3*)
let rec slice lst i j =
  let rec get acc current_index = function
    | [] -> acc
    | h :: t ->
      if current_index >= i && current_index < j then
        get (h :: acc) (current_index+1) t
      else
        get acc (current_index+1) t
  in
  List.rev(get [] 0 lst)
  ;;
(*Number 4*)
(*helper func, checks h against the predicate: if true keeps the element, if false removes the element and continues with to the rest, t*)
let rec equals equiv_fun lst =
  match lst with
  | [] -> []
  | h :: t -> 
    if(equiv_fun h) then h :: (equals equiv_fun t)
    else equals equiv_fun t
  ;;
(*for each element in lst1, h, it uses remove_if from q2 to remove elements in lst2 that are equivalent to h.
  The result is a new list where equivalent elements have been removed.*)
let rec lstCompare lst1 lst2 = 
  match lst1 with
  | [] -> lst2
  | h :: t ->
    let x = (remove_if lst2 ((=) h)) in lstCompare t x
  ;;
let rec equivs equiv_fun lst = 
  match lst with 
  | [] -> [[]]
  | h :: t -> 
    let x = (equals(equiv_fun h) lst) in (*creates initial equivalence class and determines which elelments should be in the equivalence class*)
      let y = (lstCompare x t) in        (*removes equivalent elements from the equivalence class x in relation to the remaining elements in the list t. y will hold the updated equivalence class.*)
      match y with 
      | [] -> [x]
      | hd :: tl -> x :: (equivs equiv_fun y)
  ;;

(*Number 5*)
let is_prime n = 
  if n <= 1 then false
  else if n <= 3 then true
  else
    let rec divisorCheck div = 
      if div * div > n then true(*Goldbach conjecture*)
      else if n mod 2 = 0 then false
      else divisorCheck (div + 2) in n mod 2 = 1 && divisorCheck 3
  ;;
let rec find_prime_pair n start = 
  if start >= n then (0,0)
  else if is_prime start && is_prime(n-start) then (start, n-start) (*Prime pair*)
  else find_prime_pair n (start + 1)
let goldbachpair n =
  if n<=2 then (0,0)
  else
    find_prime_pair n 2

(*Number 6*)
let identical_on f g lst = 
  match lst with
  | [] -> true
  | h :: t ->
    if f h = g h then true
    else 
      false
  ;;

(*Number 7*)
let rec pairwisefilter cmp lst =
  match lst with
  | [] -> []
  | h :: snd :: t ->
    let new_tail = pairwisefilter cmp t in 
    (cmp h snd) :: new_tail
  | h::[] -> [h]        (*case: if there is only 1 element out of the pair*)
  ;;

(*Number 8*)
let rec polynomial a = fun n -> (*fun n is the inputted number*)
  match a with
  | [] -> 0
  | (x,y) :: t -> let remaining = polynomial t in
    let total = float_of_int n ** float_of_int y |> int_of_float in (total * x) + (remaining n)
;;

(*Number 9*) 
let rec suffixes lst = 
  match lst with
  | [] -> []
  | h :: t -> (h :: t) :: suffixes t
;;

(*Number 10*)
let rec power_set lst =
  match lst with
  | [] -> [[]]
  | h :: t ->
    let func = power_set t in 
    let rec prepend x = function
    | [] -> []
    | head :: tail -> (x::head) :: prepend x tail
  in
  func @ prepend h func
;;