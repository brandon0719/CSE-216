/**
 * Brandon Wong
 * CSE 216 HW4
 * Ritwik Banerjee
 */

import java.util.*;
import java.lang.Comparable;
//Node class
// Node of the binary tree
class Node <T>
{
    private T value;
    private Node<T> left;
    private Node<T> right;
    //constructor and getters and setters
    public Node(T value)
    {
        this.value = value;
    }
    public Node<T> getLeft() {
        return left;
    }
    public Node<T> getRight() {
        return right;
    }
    public T getValue() {
        return value;
    }
    public void setLeft(Node<T> left) {
        this.left = left;
    }
    public void setRight(Node<T> right) {
        this.right = right;
    }
    public void setValue(T value) {
        this.value = value;
    }
}
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
    Node<T> root;
    String name;
    List<T> list = new ArrayList<T>();
    public BinarySearchTree(String name) {
        this.name = name;
    }
    // Add to list functions
    private void insert(T newValue)  {
        root = insert_Recursive(root, newValue);
    }

    private Node insert_Recursive(Node root, T newValue) {
        //tree is empty
        if (root == null) {
            root = new Node(newValue);
            return root;
        }
        if(newValue.compareTo((T)root.getValue()) < 0) {
            root.setLeft(insert_Recursive(root.getLeft(), newValue));
        }
        else if(newValue.compareTo((T)root.getValue()) >= 0) {
            root.setRight(insert_Recursive(root.getRight(), newValue));
        }
        return  root;
    }
    public void addAll(List <T> data)
    {
        for(T value : data)
        {
            insert(value);
        }
    }
    // Print functions
    private String print(Node root) {
        if (root == null) {
            return "";
        }
        else if(root.getLeft() != null && root.getLeft() != null) {
            return root.getValue() + " " + "L:(" +print(root.getLeft())+ ")" + " "+ "R:(" +print(root.getRight())+ ")";
        }
        else if(root.getLeft() != null && root.getRight() == null) {
            return root.getValue() + " " + "L:(" +print(root.getLeft())+ ")";
        }
        else if(root.getLeft() == null && root.getRight() != null) {
            return root.getValue() + " " + "R:(" +print(root.getRight())+ ")";
        }
        else {
            return root.getValue() + "";
        }
    }
    public String toString(){
        return "["+ this.name+ "] " + print(root);
    }
    private ArrayList<Node<T>> inorder(Node<T> root, ArrayList<Node<T>> list){
        if(root.getLeft() != null)
            inorder(root.getLeft(), list);
        list.add(root);
        if(root.getRight() != null)
            inorder(root.getRight(), list);
        return list;
    }
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            List<Node<T>> BST = inorder(root, new ArrayList<>());

            @Override
            public boolean hasNext() {
                return BST.size() != 0;
            }

            @Override
            public T next() {
                return BST.remove(0).getValue();
            }

            @Override
            public void remove(){
                BST.remove(0);
            }
        };
        return iterator;
    }
    public static void main(String[] args) {
        // each tree has a name, provided to its constructor
        BinarySearchTree<Integer> t1 = new BinarySearchTree<>("Oak");
        // adds the elements to t1 in the order 5, 3, 0, and then 9
        t1.addAll(Arrays.asList(5, 3, 0, 9));
        BinarySearchTree<Integer> t2 = new BinarySearchTree<>("Maple");
        // adds the elements to t2 in the order 9, 5, and then 10
        t2.addAll(Arrays.asList(9, 5, 10));
        System.out.println(t1); // see the expected output for exact format
        t1.forEach(System.out::println); // iteration in increasing order
        System.out.println(t2); // see the expected output for exact format
        t2.forEach(System.out::println); // iteration in increasing order
        BinarySearchTree<String> t3 = new BinarySearchTree<>("Cornucopia");
        t3.addAll(Arrays.asList("coconut", "apple", "banana", "plum",
                "durian", "no durians on this tree!",
                "tamarind"));
        System.out.println(t3); // see the expected output for exact format
        t3.forEach(System.out::println); // iteration in increasing order
        // uncommenting the following line should cause a compile-error
        /* BinarySearchTree<List<String>> treeOfLists; */
    }
}


