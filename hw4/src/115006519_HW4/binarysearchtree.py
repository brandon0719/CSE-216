'''
Brandon Wong
CSE 216 HW 4
Ritwik Banerjee
'''


class Node:
    def __init__(self, data=None):
        self.left = None
        self.right = None
        self.data = data
        self.array = []
        self.count = 0

    def __iter__(self):
        self.inorder(self)
        for data in self.array:
            if self.count < len(self.array):
                yield data
                self.count += 1
            else:
                raise StopIteration

    def inorder(self, root):
        if root is None:
            return
        self.inorder(root.left)
        self.array.append(root.data)
        self.inorder(root.right)
        return self.array


class BinarySearchTree:
    def __init__(self, name, root):
        self.root = None
        self.name = name

    def insert(self, value = None):
        self.root = self.insert_recursive(self.root, value)

    def insert_recursive(self, root, val):
        if root is None:
            root = Node(val)
            return root
        if val < root.data:
            root.left = self.insert_recursive(root.left, val)
        elif val > root.data:
            root.right = self.insert_recursive(root.right, val)
        return root

    def add_all(self, *data):
        if len(data) == 0:
            self.insert("")
        else:
            for element in data:
                self.insert(element)

    def __iter__(self):
        return self.root.__iter__()

    def __str__(self):
        return "[" + self.name + "] " + self.print(self.root)

    def print(self, root):
        if root is None:
            return ""
        elif root.left is not None and root.right is not None:
            return str(root.data) + " " + "L:(" + str(self.print(root.left)) + ")" + " " + "R:(" + str(
                self.print(root.right)) + ")"
        elif root.left is not None and root.right is None:
            return str(root.data) + " " + "L:(" + str(self.print(root.left)) + ")"
        elif root.left is None and root.right is not None:
            return str(root.data) + " " + "R:(" + str(self.print(root.right)) + ")"
        else:
            return str(root.data) + ""


if __name__ == "__main__":
    tree = BinarySearchTree(name="Oak", root=Node())

    tree.add_all(5, 3, 9, 0)  # adds the elements in the order 5, 3, 9, and then 0

    print(tree)
if __name__ == "__main__":

    t1 = BinarySearchTree(name="Oak", root=Node())

    t1.add_all(1, 0, 10, 2, 7)

    for x in t1.root:

        print(x)