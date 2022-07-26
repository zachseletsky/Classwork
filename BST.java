import java.util.*;

/**
 * Your implementation of a BST.
 *
 * @author Zachary Seletsky
 * @version 1.0
 * @userid zseletsky3
 * @GTID 903360808
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection passed in is null");
        }

        for (T datum: data) {
            if (datum == null) {
                throw new IllegalArgumentException("A data element in the collection is null");
            }
            add(datum);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be added is null");
        }

        if (size == 0) {
            root = new BSTNode(data);
            size++;
        } else {
            rAddHelper(root, data);
        }
    }

    /**
     *
     * @param curr
     * @param data
     */
    private BSTNode<T> rAddHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            return rAddHelper(curr.getLeft(), data);
        } else {
            return rAddHelper(curr.getRight(), data);
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid \"data to remove\" input");
        }
        BSTNode<T> toRemove = removeHelper(root, data);
        T removedData = toRemove.getData();
        BSTNode replace = toRemove;
        boolean cont = true;

        if (toRemove.getLeft() == null && toRemove.getRight() != null) {

        }
        if (toRemove.getLeft() != null && toRemove.getRight() != null) {
            removeHelper(toRemove);
        } else if (toRemove.getLeft())
        while (cont) {
            if (replace.getLeft())
        }
    }

    /**
     * A recursive helper method that searches for the BST Node to be removed, and with the help of
     *  another recursive helper method, replaces the removed BST Node.
     *
     * @param curr BST Node passed in
     * @param data data to search for
     * @return the data from the removed BST Node
     */
    private T removeHelper(BSTNode<T> curr, T data) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            throw new NoSuchElementException("Element does not exist in BST");
        }
        boolean isRight = false;
        BSTNode<T> left = curr.getLeft();
        BSTNode<T> right = curr.getRight();
        T removedData = curr.getData();
        BSTNode<T> toReplace = curr;
        if (left == null) {
            if (right != null){
                if (right.getData().equals(data)) {
                    removedData = right.getData();
                    toReplace = right;
                    isRight = true;
                } else {
                    removeHelper(right, data);
                }
            }
        } else if (right == null) {
            if (left.getData().equals(data)) {
                removedData = left.getData();
                toReplace = left;
            } else {
                removeHelper(left, data);
            }
        } else if (curr.getData().compareTo(data) > 0) {
            if (left.getData().equals(data)) {
                removedData = left.getData();
                toReplace = left;
            } else {
                removeHelper(left, data);
            }
        } else {
            if (right.getData().equals(data)) {
                removedData = right.getData();
                toReplace = right;
                isRight = true;
            } else {
                removeHelper(right, data);
            }
        }
        if (toReplace.getRight() == null && toReplace.getLeft() == null) {
            return removedData;
        } else if (toReplace.getRight() == null) {
            if (isRight) {
                curr.setRight(toReplace.getLeft());
            } else {
                curr.setLeft(toReplace.getLeft());
            }
        }else if (toReplace.getLeft() == null) {
            if (isRight) {
                curr.setRight(toReplace.getRight());
            } else {
                curr.setLeft(toReplace.getLeft());
            }
        } else {
            if (toReplace.getRight().getLeft() == null) {
                if (isRight) {
                    curr.setRight(toReplace.getRight());
                } else {
                    curr.setLeft(toReplace.getRight());
                }
            } else {
                BSTNode<T> first = toReplace.getRight();
                BSTNode<T> second = first.getLeft();
                while (second.getLeft() != null) {
                    first = second;
                    second = second.getLeft();
                }
                if (second.getRight() != null) {
                    first.setLeft(second.getRight());
                }
                if (isRight) {
                    curr.setRight(second);
                } else {
                    curr.setLeft(second);
                }
                second.setRight(toReplace.getRight());
                second.setLeft(toReplace.getLeft());
            }
        }
        return removedData;
    }

    /*
     * A recursive helper method that finds the proper node to replace the removed node.
     * Only used when the removed node has two children, and it's right child does not have a left child
     * @param curr current node that is being searched
     * @param data
     * @return the replacement node for the removed node

    private BSTNode<T> removeHelper2(BSTNode<T> curr, T data) {

        if (curr.getLeft() == null) {
            return curr;
        }
        BSTNode<T> replacement = curr;
        while (curr.getLeft() != null) {
            if (curr.getData().compareTo(replacement.getData()) < 0) {
                replacement = curr;
            }
            curr = curr.getLeft();
        }

        if (curr.getLeft() == null && curr.getRight() == null) {

        }
        if (curr.getLeft().getData().compareTo(data) > 0)

        if (curr.getLeft() == null && curr.getRight() == null) {
            return curr;
        }
        if (curr.getLeft() == null && curr.getRight() != null) {
            removeHelper(curr.getRight());
        } else if (curr.getLeft() != null && curr.getRight() == null) {
            removeHelper(curr.getLeft());
        } else {
            removeHelper(curr.getLeft());
        }

    }
*/
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data parameter is null");
        }
        return getHelp(root, data);
    }

    private T getHelp(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (curr.getData().compareTo(data) == 0) {
           return curr.getData();
        }
        if (curr.getData().compareTo(data) > 0) {
            getHelp(curr.getLeft(), data);
        } else {
            getHelp(curr.getRight(), data);
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input is null");
        }

        return containsHelp(root, data);
    }

    private boolean containsHelp(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().equals(data)) {
            return true;
        }
        if (curr.getData().compareTo(data) > 0) {
            containsHelp(curr.getLeft(), data);
        } else {
            containsHelp(curr.getRight(), data);
        }
        return false;
    }
    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorderHelper(root, list);
        return list;

    }

    private void preorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            preorderHelper(curr.getLeft(), list);
            preorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     *
     * @param curr
     * @param list
     */
    private void inorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            inorderHelper(curr.getLeft(), list);
            list.add(curr.getData());
            inorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    private void postorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            postorderHelper(curr.getLeft(), list);
            postorderHelper(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();

        if (size == 0) {
            return list;
        }
        queue.add(root);

        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.remove();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return heightHelper(root);

    }

    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight()));
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        List<T> list = new ArrayList<>();
        return list;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
