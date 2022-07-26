import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Zachary Seletsky
 * @version 1.0
 * @userid zseletsky3@gatech.edu
 * @GTID 903360808
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new IllegalArgumentException();
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> current;
            DoublyLinkedListNode<T> element = new DoublyLinkedListNode<>(data);
            int count = 0;
            current = head;
            while (count < index) {
                current = current.getNext();
                count++;
            }
            element.setNext(current);
            element.setPrevious(current.getPrevious());
            current.setPrevious(element);
            element.getPrevious().setNext(element);
            size++;
        }
    }


    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        DoublyLinkedListNode<T> element = new DoublyLinkedListNode<>(data);
        if (head == null) {
            head = element;
            tail = element;
        } else {
            DoublyLinkedListNode<T> curr = head;
            element.setNext(curr);
            curr.setPrevious(element);
            head = element;
            if (size == 1) {
                tail = curr;
            }
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        DoublyLinkedListNode<T> element = new DoublyLinkedListNode<>(data);
        if (head == null) {
            head = element;
            tail = element;
        } else if (tail == null) {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data);
            tail = curr;
            curr.setPrevious(head);
        } else {
            DoublyLinkedListNode<T> curr = tail;
            curr.setNext(element);
            element.setPrevious(curr);
            tail = element;
            if (size == 1) {
                head = curr;
            }
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> current;
            int count;
            count = 0;
            current = head;
            while (count < index) {
                current = current.getNext();
                count++;
            }
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            size--;
            return current.getData();
        }
    }




    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T front;
        if (size == 1) {
            front = head.getData();
            head = null;
            tail = null;
        } else {
            front = head.getData();
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return front;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T data = tail.getData();
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;
        return data;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        DoublyLinkedListNode<T> current;
        int count;
        count = 0;
        current = head;
        while (count < index) {
            current = current.getNext();
            count++;
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        DoublyLinkedListNode<T> curr = head;
        int index = -1;
        int count = 0;
        while (count < size) {
            if (curr.getData().equals(data)) {
                index = count;
            }
            curr = curr.getNext();
            count++;
        }
        if (index == -1) {
            throw new NoSuchElementException();
        }
        return removeAtIndex(index);
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        int count = 0;
        while (count < size) {
            arr[count] = curr.getData();
            curr = curr.getNext();
            count++;
        }
        return arr;

    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
