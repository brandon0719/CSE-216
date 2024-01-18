
type ('a') graph = {
  nodes: 'a list;
  edges: ('a * 'a) list;
}


let g = {
  nodes = ['a', 'b', 'c', 'd'];
  edges = [('d','c'); ('a','b');('a','c'); ('c', 'b')]}
;;

let paths g a b = 
  assert(a <> b);
  list_path g a [b]
;;

let neighbors g a b cond =
  let edge l (b, c) = if b = a && cond c then c :: l 
                      else if c = a && cond b then b :: l
                      else l in 
  List.fold_left edge [] g.edges

let rec list_path g a to_b = match to_b with 
  | [] -> assert false
  | a' :: _ ->
      if a' = a then [to_b]
      else
        let n = neighbors g a' (fun c -> not(List.mem c to_b))
      in List.concat(List.map(fun c -> list_path g a (c:: to_b)) n)

(*Q2*)
let size lst = 
  let rec aux acc lst = match lst with
    | [] -> acc;
    | _ :: t aux(acc + 1) t
in aux 0 lst;;

(*Q3*)
let rec mirror trees = match tree with
  | Leaf x -> Leaf x
  | Tree {operator = op; left = left; right = right} ->
      let new_left = mirror right in 
      let new_right = mirror left in
      Tree {operator = op; left = new_left; right = new_right}
;;