import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Zachary Seletsky
 * @userid zseletsky3
 * @GTID 903360808
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Entered collection is null");
        }

        for (T datum : data) {
            add(datum);
        }

    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            root = new AVLNode<>(data);
            size++;
        } else {
            if (root.getData().compareTo(data) != 0) {
                rAddHelper(root, data);

            }
        }
    }

    /**
     * Recursice helper method for adding to AVL
     * @param node current node
     * @param data data to be added
     */
    private void rAddHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new IllegalArgumentException("rAddhelper input is null");
        }
        if (node.getData().compareTo(data) == 0) {
            return;
        } else if (node.getData().compareTo(data) > 0) {
            if (node.getLeft() == null) {
                node.setLeft(new AVLNode<>(data));
                size++;
            } else {
                rAddHelper(node.getLeft(), data);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new AVLNode<>(data));
                size++;
            } else {
                rAddHelper(node.getRight(), data);
            }
        }
        updateHeight(node);
        updateBalanceFactor(node);
        applyAppropriateRotations(node);
    }

    /**
     * Rotates nodes accordingly
     * @param node node to be assessed
     */
    private void applyAppropriateRotations(AVLNode<T> node) {
        if (node == null) {
            return;
        }
        if (node.equals(root)) {
            if (node.getBalanceFactor() == 2) {
                if (node.getLeft().getBalanceFactor() == -1) {
                    root.setLeft(leftRotation(node.getLeft()));
                    //root.getLeft().setRight(null);
                    root = rightRotation(node);
                } else {
                    root = rightRotation(node);
                }
            } else if (node.getBalanceFactor() == -2) {
                if (node.getRight().getBalanceFactor() == 1) {
                    root.setRight(rightRotation(node.getRight()));
                    root = leftRotation(node);
                } else {
                    root = leftRotation(node);
                }
            }
        } else {
            if (node.getBalanceFactor() == 2) {
                if (node.getLeft().getBalanceFactor() == -1) {
                    node.setLeft(leftRotation(node.getLeft()));
                    node = rightRotation(node);
                } else {
                    node = rightRotation(node);
                }
            } else if (node.getBalanceFactor() == -2) {
                if (node.getRight().getBalanceFactor() == 1) {
                    node.setRight(rightRotation(node.getRight()));
                    node = leftRotation(node);
                } else {
                    node = leftRotation(node);
                }
            }
        }
        updateHeight(node);
        updateBalanceFactor(node);

    }

    /**
     * Updates height for node
     * @param node the node whose height is being updated
     */
    private void updateHeight(AVLNode<T> node) {
        if (node == null) {
            return;
        }
        if (node.getRight() == null && node.getLeft() == null) {
            node.setHeight(0);
        } else if (node.getLeft() == null) {
            node.setHeight(node.getRight().getHeight() + 1);
        } else if (node.getRight() == null) {
            node.setHeight(node.getLeft().getHeight() + 1);
        } else {
            if (node.getLeft().getHeight() > node.getRight().getHeight()) {
                node.setHeight(node.getLeft().getHeight() + 1);
            } else {
                node.setHeight(node.getRight().getHeight() + 1);
            }
        }
    }

    /**
     * Updates the balance factor of the node
     * @param node node whose balance factor is being updated
     */
    private void updateBalanceFactor(AVLNode<T> node) {
        if (node == null) {
            return;
        }
        if (node.getRight() == null && node.getLeft() == null) {
            node.setBalanceFactor(0);
        } else if (node.getLeft() == null) {
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else if (node.getRight() == null) {
            node.setBalanceFactor(node.getLeft().getHeight() + 1);
        } else {
            node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
        }
    }

    /**
     * Method that rotates nodes right
     * @param node head node of subtree to be rotated
     * @return the new head node of the subtree
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getLeft();
        if (temp.getRight() != null) {
            node.setLeft(temp.getRight());
        } else {
            node.setLeft(null);
        }

        temp.setRight(node);
        updateHeight(node);
        updateBalanceFactor(node);
        updateHeight(temp);
        updateBalanceFactor(temp);
        return temp;
    }

    /**
     * Method to rotate subtree left
     * @param node head of subtree to be rotated
     * @return new head of subtree
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> temp = node.getRight();
        if (temp.getRight() != null) {
            node.setRight(temp.getRight());
        } else {
            node.setRight(null);
        }
        temp.setLeft(node);
        updateHeight(node);
        updateBalanceFactor(node);
        updateHeight(temp);
        updateBalanceFactor(temp);
        return temp;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (root.getData().compareTo(data) == 0) { //if data is found in root
            T datum = root.getData();
            if (root.getLeft() == null && root.getRight() == null) { //if root has no children
                root = null;
            } else if (root.getLeft() == null) { //if root only has right child
                root = root.getRight();
            } else if (root.getRight() == null) { //if root only has left child
                root = root.getLeft();
            } else { //root has both children
                AVLNode<T> successor = root.getRight();
                AVLNode<T> tempLeft;
                if (root.getRight().getLeft() == null) { //right child has no left child
                    tempLeft = root.getLeft();
                    root = root.getRight();
                    root.setLeft(tempLeft);
                } else { //right child has left child
                    AVLNode<T> temp = successor;
                    successor = successor.getLeft();
                    while (successor.getLeft() != null) {
                        if (temp.getRight() != null && temp.getRight().getHeight() < temp.getLeft().getHeight()) {
                            temp.setHeight(temp.getHeight() - 1);
                        }
                        successor = successor.getLeft();
                        temp = temp.getLeft();
                    }
                    if (successor.getRight() != null) {
                        temp.setLeft(successor.getRight());
                        updateHeight(temp);
                        updateBalanceFactor(temp);
                    }
                    successor.setRight(root.getRight());
                    successor.setLeft(root.getLeft());
                    root = successor;
                    //updateHeight(root);
                    //updateBalanceFactor(root);
                }
            }
            updateHeight(root);
            updateBalanceFactor(root);
            applyAppropriateRotations(root);

            size--;
            return datum;
        } else {
            data = rRemove(root, data);
            size--;
            return data;
        }
    }

    /**
     * Recursive method to find and remove a node
     * @throws NoSuchElementException if the data does not exist in the AVL
     * @param node current node
     * @param data data to be removed
     * @return data that was removed
     */
    private T rRemove(AVLNode<T> node, T data) {
        //searching for data
        T datum = node.getData();
        if (node.getData().compareTo(data) > 0) { //if node data is greater than data
            if (node.getLeft() == null) { //data does not exist
                throw new NoSuchElementException();
            } else if (node.getLeft().getData().compareTo(data) == 0) { //data is found in left child
                datum = node.getLeft().getData();
                if (node.getLeft().getLeft() == null && node.getLeft().getRight() == null) { // removed node is a leaf
                    node.setLeft(null);
                } else if (node.getLeft().getLeft() == null) { //removed node has only right child
                    node.setLeft(node.getLeft().getRight());
                } else if (node.getLeft().getRight() == null) { //removed node has only left child
                    node.setLeft(node.getLeft().getLeft());
                } else { //removed node has both children
                    AVLNode<T> successor = node.getLeft().getRight();
                    if (node.getLeft().getRight().getLeft() == null) { //right child has no left child
                        successor = node.getLeft().getRight();
                        successor.setLeft(node.getLeft().getLeft());
                        node.setLeft(successor);
                    } else { //right child has left child
                        AVLNode<T> temp = node.getLeft().getRight();
                        successor = node.getLeft().getRight().getLeft();
                        while (successor.getLeft() != null) {
                            if (temp.getRight() != null && temp.getRight().getHeight() < temp.getLeft().getHeight()) {
                                temp.setHeight(temp.getHeight() - 1);
                            }
                            successor = successor.getLeft();
                            temp = temp.getLeft();
                        }
                        if (successor.getRight() != null) {
                            temp.setLeft(successor.getRight());
                            updateHeight(temp);
                            updateBalanceFactor(temp);
                        }
                        successor.setRight(node.getLeft().getRight());
                        successor.setLeft(node.getLeft().getLeft());
                        node.setLeft(successor);
                    }
                    updateHeight(successor);
                    updateBalanceFactor(successor);
                }
            } else { //continues searching for data in left branch
                datum = rRemove(node.getLeft(), data);
            }
        } else if (node.getData().compareTo(data) < 0) { //if node data is less than data
            if (node.getRight() == null) { //data does not exist
                throw new NoSuchElementException();
            } else if (node.getRight().getData().compareTo(data) == 0) { //data is found
                datum = node.getRight().getData();
                if (node.getRight().getLeft() == null && node.getRight().getRight() == null) { //removed node is a leaf
                    node.setRight(null);
                } else if (node.getRight().getLeft() == null) { //removed node has only right child
                    node.setRight(node.getRight().getRight());
                } else if (node.getRight().getRight() == null) { //removed node has only left child
                    node.setRight(node.getRight().getLeft());
                } else { //removed node has both children
                    AVLNode<T> successor = node.getRight().getRight();
                    if (node.getRight().getRight().getLeft() == null) { //right child has no left child
                        successor = node.getRight().getRight();
                        successor.setLeft(node.getRight().getLeft());
                        node.setRight(successor);
                    } else { //right child has left child
                        AVLNode<T> temp = node.getRight().getRight();
                        successor = node.getRight().getRight().getLeft();
                        while (successor.getLeft() != null) {
                            if (temp.getRight() != null && temp.getRight().getHeight() < temp.getLeft().getHeight()) {
                                temp.setHeight(temp.getHeight() - 1);
                            }
                            successor = successor.getLeft();
                            temp = temp.getLeft();
                        }
                        if (successor.getRight() != null) {
                            temp.setLeft(successor.getRight());
                            updateHeight(temp);
                            updateBalanceFactor(temp);
                        }
                        successor.setRight(node.getRight().getRight());
                        successor.setLeft(node.getRight().getLeft());
                        node.setRight(successor);
                    }
                    updateHeight(successor);
                    updateBalanceFactor(successor);
                }
            } else { //continues searching for data in right branch
                datum = rRemove(node.getRight(), data);
            }
        }

        updateHeight(node);
        updateBalanceFactor(node);
        applyAppropriateRotations(node);
        return datum;
    }



    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is nulL");
        }

        return rGet(root, data);
    }

    /**
     * Recursive method to retrieve data
     * @throws NoSuchElementException if the element does not exist in the AVL
     * @param node current node
     * @param data data to be retrieved
     * @return data found
     */
    private T rGet(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Element does not exist");
        }
        if (node.getData().compareTo(data) == 0) {
            return node.getData();
        }
        if (node.getData().compareTo(data) > 0) {
            return rGet(node.getLeft(), data);
        } else {
            return rGet(node.getRight(), data);
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return rContains(root, data);
    }

    /**
     * Recursive method to find if an element is in the AVL
     * @param node current node
     * @param data data that is being searched for
     * @return true if the AVL does contain the element, and false otherwise
     */
    private boolean rContains(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (node.getData().compareTo(data) == 0) {
            return true;
        }
        if (node.getData().compareTo(data) > 0) {
            return rContains(node.getLeft(), data);
        } else {
            return rContains(node.getRight(), data);
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<T>();
        return rDeepestBranches(root, list);
    }

    /**
     * Recursive method that lists all the elements in the deepest branches of the AVL
     * @param node current node
     * @param list list to add to
     * @return the list
     */
    private List<T> rDeepestBranches(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        }
        list.add(node.getData());
        if (node.getLeft() == null) {
            rDeepestBranches(node.getRight(), list);
        } else if (node.getRight() == null) {
            rDeepestBranches(node.getLeft(), list);
        } else if (node.getLeft().getHeight() > node.getRight().getHeight()) {
            rDeepestBranches(node.getLeft(), list);
        } else if (node.getLeft().getHeight() < node.getRight().getHeight()) {
            rDeepestBranches(node.getLeft(), list);
        } else {
            rDeepestBranches(node.getLeft(), list);
            rDeepestBranches(node.getRight(), list);
        }
        return list;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data thresholds are null");
        }
        if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data thresholds are incorrect");
        }
        List<T> list = new ArrayList<>();

        rSortedInBetweenHelper(root, data1, data2, list);
        return list;
    }

    /**
     * A recursive method to help list all elements within a preset range
     * @param node current node
     * @param data1 lower search parameter
     * @param data2 higher search parameter
     * @param list list to add to
     */
    private void rSortedInBetweenHelper(AVLNode<T> node, T data1, T data2, List<T> list) {
        if (node == null) {
            return;
        }
        if (node.getData().compareTo(data1) > 0 && node.getData().compareTo(data2) < 0) {
            rSortedInBetweenHelper(node.getLeft(), data1, data2, list);
            list.add(node.getData());
            rSortedInBetweenHelper(node.getRight(), data1, data2, list);
        } else if (node.getData().compareTo(data1) == 0 || node.getData().compareTo(data1) < 0) {
            rSortedInBetweenHelper(node.getRight(), data1, data2, list);
        } else {
            rSortedInBetweenHelper(node.getLeft(), data1, data2, list);
        }
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}