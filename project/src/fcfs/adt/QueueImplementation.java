package fcfs.adt;

/**
 * A queue backed by a singly linked chain of {@link Node} objects, built
 * without the {@code java.util} package.
 *
 * <p>References to both ends of the chain are kept so that neither operation
 * has to walk it: {@code front} makes reading and removing at the head
 * constant-time, and {@code back} makes adding at the tail constant-time.</p>
 *
 * @param <T> The type of elements in the queue.
 */
public class QueueImplementation<T> implements QueueInterface<T> {

    /** The node at the front of the queue, or null when the queue is empty. */
    private Node<T> front;

    /** The node at the back of the queue, or null when the queue is empty. */
    private Node<T> back;

    /**
     * Creates an empty queue.
     */
    public QueueImplementation() {
        this.front = null;
        this.back = null;
    }

    /**
     * Adds a new entry to the back of the queue.
     *
     * <p>Runs in constant time: the back reference means the chain is never
     * walked to find the tail.</p>
     *
     * <p>{@code null} entries are permitted and stored as-is.</p>
     *
     * @param newEntry An object to be added.
     */
    @Override
    public void enqueue(T newEntry) {
        Node<T> newNode = new Node<>(newEntry);

        if (back == null) {
            // The chain is empty, so this node becomes the front as well.
            front = newNode;
        } else {
            // Link the current tail onto the new node.
            back.setNext(newNode);
        }

        back = newNode;  // The new node is always the new tail.
    }

    /**
     * Retrieves the entry at the front of the queue without removing it.
     *
     * @return The object at the front of the queue.
     * @throws IllegalStateException if the queue is empty.
     */
    @Override
    public T getFront() {
        if (front == null) {
            throw new IllegalStateException("Cannot read the front of an empty queue.");
        }
        return front.getData();
    }

    // Removes and returns the entry at the front of the queue.
    // Throws IllegalStateException if the queue is empty.
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from an empty queue.");
        }

        T frontData = front.getData();
        front = front.getNext();

        // if we just removed the last node, back needs to be reset too
        if (front == null) {
            back = null;
        }

        return frontData;
    }

    // Returns true if the queue has no entries in it.
    @Override
    public boolean isEmpty() {
        return front == null;
    }

    // Removes every entry from the queue.
    @Override
    public void clear() {
        front = null;
        back = null;
    }
}// end QueueImplementation
