package fcfs.adt;

/**
 * A small, self-contained test harness for {@link Node} and
 * {@link QueueImplementation}.
 *
 * <p>It uses no external testing library so it can be run with a plain
 * {@code java -cp project/out fcfs.adt.QueueImplementationTest}. Each check
 * prints a PASS or FAIL line, and a summary line is printed last so the
 * result is the final thing announced when the output is read aloud.</p>
 *
 * <p>Part 2 owns the cases below, which cover {@code Node}, {@code enqueue}
 * and {@code getFront}. Part 3 appends cases for {@code dequeue},
 * {@code isEmpty} and {@code clear} to this same file.</p>
 */
public class QueueImplementationTest {

    /** Running count of checks that passed. */
    private static int passed = 0;
    /** Running count of checks that failed. */
    private static int failed = 0;

    /**
     * Records one check: prints PASS when the condition holds, otherwise FAIL.
     *
     * @param description What the check is verifying.
     * @param condition   The result being asserted; true means success.
     */
    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            failed++;
            System.out.println("FAIL: " + description);
        }
    }

    /**
     * Runs every test in turn and prints a final summary.
     *
     * <p>If a test throws an unexpected {@link RuntimeException} — for
     * example, a Part 3 stub that still throws {@code UnsupportedOperationException}
     * during red-state TDD — the throw is caught and counted as a failure
     * rather than being allowed to end the run early, so the summary is
     * still the last thing printed.</p>
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        try {
            testNodeHoldsDataAndLink();
            testGetFrontOnEmptyThrows();
            testGetFrontReturnsOnlyEntry();
            testGetFrontReturnsFirstEntry();
            testGetFrontDoesNotConsume();
        } catch (RuntimeException e) {
            // A test that throws is a failure, not a reason to skip the summary.
            failed++;
            System.out.println("FAIL: a test threw an unexpected " + e);
        }

        System.out.println();
        System.out.println("Summary: " + passed + " passed, " + failed + " failed.");
    }

    /** A node holds its entry and links to the node placed behind it. */
    private static void testNodeHoldsDataAndLink() {
        Node<String> first = new Node<>("P1");
        Node<String> second = new Node<>("P2");

        check("a new node returns the entry it was built with",
              "P1".equals(first.getData()));
        check("a new node has nothing behind it",
              first.getNext() == null);

        first.setNext(second);
        check("setNext links the given node behind this one",
              first.getNext() == second);
    }

    /** A queue with nothing in it has no front to read. */
    private static void testGetFrontOnEmptyThrows() {
        QueueImplementation<String> queue = new QueueImplementation<>();
        try {
            queue.getFront();
            // Reaching this line means no exception was thrown, which is a failure.
            check("getFront on an empty queue throws IllegalStateException", false);
        } catch (IllegalStateException e) {
            check("getFront on an empty queue throws IllegalStateException", true);
        }
    }

    /** One enqueue makes the entry readable at the front. */
    private static void testGetFrontReturnsOnlyEntry() {
        QueueImplementation<String> queue = new QueueImplementation<>();
        queue.enqueue("P1");
        check("getFront returns the only entry after one enqueue",
              "P1".equals(queue.getFront()));
    }

    /** The head does not drift as the tail grows: first in stays at the front. */
    private static void testGetFrontReturnsFirstEntry() {
        QueueImplementation<String> queue = new QueueImplementation<>();
        queue.enqueue("P1");
        queue.enqueue("P2");
        queue.enqueue("P3");
        check("getFront returns the first entry, not the last, after three enqueues",
              "P1".equals(queue.getFront()));
    }

    /** Reading the front is not the same as removing it. */
    private static void testGetFrontDoesNotConsume() {
        QueueImplementation<String> queue = new QueueImplementation<>();
        queue.enqueue("P1");
        queue.enqueue("P2");
        queue.enqueue("P3");
        String firstRead = queue.getFront();
        String secondRead = queue.getFront();
        check("two consecutive getFront calls return the same entry",
              "P1".equals(firstRead) && firstRead.equals(secondRead));
    }
}// end QueueImplementationTest
