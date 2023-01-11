import java.util.*;


public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
    String name;
    Node<T> root;

    public BinarySearchTree(String name) {
        this.name = name;
    }
    public void addAll(List<T> lst) {
        for (T val : lst) {
            insert(val);
        }
    }
    public void insert(T val) {
        Node<T> node = new Node<T>(val);
        if (root == null) {
            root = node;
        }
        else{
            Node<T> curr = root;
            while(curr != null) {
                if (curr.getValue().compareTo(val) > 0) {
                    if (curr.getLeft() == null) {
                        curr.setLeft(node);
                        break;
                    } else {
                        curr = curr.getLeft();
                    }
                } else if(curr.getValue().compareTo(val) < 0) {
                    if (curr.getRight() == null) {
                        curr.setRight(node);
                        break;
                    } else {
                        curr = curr.getRight();
                    }
                }
            }
        }
    }

    class BstIterator implements Iterator<T> {
        Stack<Node<T>> stack;
        public BstIterator(Node<T> root) {
            stack = new Stack<Node<T>>();
            iterHelper(root);
        }
        private void iterHelper(Node<T> root){
            if(root!=null){
                if(root.getRight()!= null){
                    iterHelper(root.getRight());
                }
                stack.push(root);
                if(root.getLeft()!= null){
                    iterHelper(root.getLeft());
                }
            }
        }
        public boolean hasNext() {
            return stack.size() > 0;
        }
        public T next() {
            Node<T> ret = stack.pop();
            return ret.getValue();
        }
    }
    public Iterator<T> iterator() {
        return new BstIterator(root);
    }

    public String toString(){
        String ret = "";
        if(root==null){
            return ret;
        }
        else{
            ret = ret +  "[" + name + "]";
            ret = ret + strHelper(root);
        }
        return ret;
    }

    public String strHelper(Node<T> root){
        String res = "";
        if(root != null){

            res = res + root.getValue().toString();
            if(root.getLeft()!=null){
                res = res + " L:(" + strHelper(root.getLeft());
                res = res + ")";
            }
            if(root.getRight()!=null){
                res = res + " R:(" + strHelper(root.getRight());
                res = res + ")";
            }
        }
        return res;
    }
    public static <T extends Comparable<T>> List<T> merge(BinarySearchTree<T> t1, BinarySearchTree<T> t2){
        List<T> list = new ArrayList<T>();
        Thread thread = new Thread() {
            public void run() {
                t1.forEach(list::add);
            }
        };
        Thread thread2 = new Thread(){
            public void run(){
                t2.forEach(list::add);
            }
        };
        thread.start();
        thread2.start();
        try{
            thread.join();
            thread2.join();
            list.sort(null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main(String... args) {
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
        t3.addAll(Arrays.asList("coconut", "apple", "banana", "plum", "durian",
                "no durians on this tree!", "tamarind"));
        System.out.println(t3); // see the expected output for exact format
        t3.forEach(System.out::println); // iteration in increasing order
    }
}
