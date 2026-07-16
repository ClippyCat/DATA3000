package ADTStack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * An array-based implementation of {@link StackInterface}.
 *
 * <p>The stack stores its entries in a plain array and tracks the position of
 * the top entry with an integer index. The array grows (doubles) when it runs
 * out of room and shrinks (halves) when it becomes mostly empty, so a long run
 * of pushes and pops does not waste memory. Growth by doubling keeps the
 * average (amortised) cost of a push at O(1).</p>
 *
 * @param <T> the type of elements held in this stack
 */
public class ArrayStack<T> implements StackInterface<T> {

    /** Capacity the stack starts with, and the smallest it will ever shrink to. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Backing storage for the entries. Index 0 is the bottom of the stack. */
    private T[] array;

    /** Index of the current top entry; -1 means the stack is empty. */
    private int topIndex;

    /**
     * Creates an empty stack with the default capacity.
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        // Java does not allow "new T[...]" directly because the element type is
        // erased at runtime, so we create an Object[] and cast it. The cast is
        // safe: nothing outside this class can see the array, and we only ever
        // store values of type T into it.
        array = (T[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;
    }

    /**
     * Adds a new entry to the top of this stack, growing the backing array
     * first if it is full.
     *
     * @param newEntry the object to be added to the stack
     */
    @Override
    public void push(T newEntry) {
        // If the array is full, double its capacity before adding.
        if (topIndex + 1 == array.length) {
            resizeTo(array.length * 2);
        }
        topIndex++;
        array[topIndex] = newEntry;
    }

    /**
     * Removes and returns this stack's top entry.
     *
     * @return the object that was at the top of the stack
     * @throws EmptyStackException if the stack is empty before the operation
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T top = array[topIndex];
        array[topIndex] = null; // release the reference so it can be garbage collected
        topIndex--;
        shrinkIfSparse();       // give memory back if the stack is now mostly empty
        return top;
    }

    /**
     * Retrieves, but does not remove, this stack's top entry.
     *
     * @return the object at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return array[topIndex];
    }

    /**
     * Detects whether this stack is empty.
     *
     * @return true if the stack has no entries, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return topIndex < 0;
    }

    /**
     * Removes all entries from this stack and resets it to its default
     * capacity.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        // Reallocating at the default size both empties the stack and returns
        // any memory an earlier growth spurt had claimed.
        array = (T[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;
    }

    /**
     * Copies the current entries into a new array of the given capacity and
     * makes it the backing array.
     *
     * @param newCapacity the size of the replacement array
     */
    private void resizeTo(int newCapacity) {
        // Arrays.copyOf keeps the element type of the source array, so the
        // result is still a T[] and no unchecked cast is needed here.
        array = Arrays.copyOf(array, newCapacity);
    }

    /**
     * Halves the backing array when the stack is using a quarter or less of it,
     * never dropping below the default capacity. Waiting until it is a quarter
     * full (rather than half) avoids repeatedly growing and shrinking when the
     * size hovers around a capacity boundary.
     */
    private void shrinkIfSparse() {
        int size = topIndex + 1;
        if (array.length > DEFAULT_CAPACITY && size <= array.length / 4) {
            int newCapacity = Math.max(DEFAULT_CAPACITY, array.length / 2);
            resizeTo(newCapacity);
        }
    }
}//end ArrayStack
