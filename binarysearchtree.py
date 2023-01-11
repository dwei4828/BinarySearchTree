class Node():

    def __init__(self, val=None):
        self.val = val
        self.left = None
        self.right = None

    def __iter__(self):
        return self.iterHelper(self)

    def iterHelper(self, root):
        if root:
            for val in self.iterHelper(root.left):
                yield val
            yield root.val
            for val in self.iterHelper(root.right):
                yield val

class BinarySearchTree():

    def __init__(self, name, root):
        self.root = root
        self.name = name

    def __iter__(self):
        return self.iterHelper(self.root)

    def iterHelper(self, root):
        if root:
            for val in self.iterHelper(root.left):
                yield val
            yield root.val
            for val in self.iterHelper(root.right):
                yield val

    def insert(self, val, root):
        if root.val != None :
            if val < root.val:
                if root.left:
                    self.insert(val,root.left)
                else:
                    root.left = Node(val)
            else:
                if root.right:
                    self.insert(val,root.right)
                else:
                    root.right = Node(val)
        else:
            root.val = val


    def add_all(self, *tup):
        for v in tup:
            self.insert(v, self.root)

    def __str__(self):
        ret = ""
        if self.root.val == None:
            return ret
        else:
            ret += "[" + self.name + "]"
            ret += self.strHelper(self.root)
            return ret

    def strHelper(self, root):
        res = ""
        if root != None:
            res += str(root.val)
            if root.left:
                res += " L:(" + self.strHelper(root.left)
                res += ")"
            if root.right:
                res += " R:(" + self.strHelper(root.right)
                res += ")"
        return res


class Merger():

    def __init__(self, name):
        self.name = name

    @classmethod
    def merge(cls, t1, t2):
        list = []
        l1 = iter(t1)
        l2 = iter(t2)
        e1 = next(l1)
        e2 = next(l2)
        while True:
            if e1 < e2:
                try:
                    list.append(e1)
                    e1 = next(l1)
                except StopIteration:
                    while True:
                        try:
                            list.append(e2)
                            e2 = next(l2)
                        except StopIteration:
                            break
                    break
            else:
                try:
                    list.append(e2)
                    e2 = next(l2)
                except StopIteration:
                    while True:
                        try:
                            list.append(e1)
                            e1 = next(l1)
                        except StopIteration:
                            break
                    break
        return list




if __name__ == "__main__":
    tree = BinarySearchTree(name="Oak", root=Node())
    tree.add_all(5, 3, 9, 0) # adds the elements in the order 5, 3, 9, and then 0
    print(tree)

if __name__ == "__main__":
    t1 = BinarySearchTree(name="Oak", root=Node())
    t2 = BinarySearchTree(name="Birch", root=Node())
    t1.add_all(5, 3, 9, 0)
    t2.add_all(1, 0, 10, 2, 7)
    for x in t1.root:
        print(x)
    for x in t2.root:
        print(x)
    print(Merger.merge(t1,t2))