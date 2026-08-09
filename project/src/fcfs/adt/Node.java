package fcfs.adt;

/**
 * A single link in the singly linked chain that backs
 * {@link QueueImplementation}.
 *
 * <p>Each node stores one entry and a reference to the node behind it in the
 * chain. The node at the back of the queue has a {@code null} next
 * reference.</p>
 *
 * @param <T> The type of entry held in this node.
 */
public class Node<T> {

    /** The entry stored in this node. */
    private final T data;

    /** The node behind this one, or null when this node is last. */
    private Node<T> next;

    /**
     * Creates a node holding the given entry with no node behind it.
     *
     * @param data The entry to store.
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    /**
     * Retrieves the entry stored in this node.
     *
     * @return The stored entry.
     */
    public T getData() {
        return data;
    }

    /**
     * Retrieves the node behind this one in the chain.
     *
     * @return The next node, or null when this node is last.
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Links a node behind this one.
     *
     * @param next The node to place after this one.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}// end Node
