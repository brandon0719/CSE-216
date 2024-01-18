let rec repeat f n x = 
  if n = 0 then x
  else f(repeat f (n-1) x);;



let f list =
  let rec aux acc = function
    | [] -> acc
    | h::t -> aux (h::acc) t in aux [] list;;



let rec remove_n n list = match list with 
  | [] -> []
  | h::t -> if n = 0 then t else h:: remove_n(n-1) t;;



let rec last_element list = function
  |[] -> None
  |[x] -> Some x
  |h::t -> last_element t;;

  `

(*
let rec last_element list = match list with 
    | [] -> None
    | [x] -> Some x
    | h::t -> last_element t;;
*)

(*recitation 5*)
let rec nodes_and_leaves = function 
  | Leaf x -> ([], [x])
  | Tree {operator = p; left = x, right = y} ->
      let(left_nodes, left_leaves) = nodes_and_leaves x and 
      (r_nodes, r_leaves) = nodes_and_leaves y in 
      (P :: left_nodes @ right_nodes, l_leaves @ r_leaves);;

let rec size lst = match lst with
    | [] -> []
    | h :: t -> 1 + size t
    