package ADTStack;

import java.util.EmptyStackException;

/**
 * A small, self-contained test harness for {@link ArrayStack}.
 *
 * <p>It uses no external testing library so it can be run with a plain
 * {@code java ADTStack.ArrayStackTest}. Each check prints a PASS or FAIL line,
 * and a summary line reports the totals so a failure is easy to spot when the
 * output is read aloud.</p>
 */
public class ArrayStackTest {

    /** Running count of checks that passed. */
    private static int passed = 0;
    /** Running count of checks that failed. */
    private static int failed = 0;

    /**
     * Records one check: prints PASS when the condition holds, otherwise FAIL.
     *
     * @param description what the check is verifying
     * @param condition   the result being asserted; true means success
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
     * @param args not used
     */
    public static void main(String[] args) {
        testStartsEmpty();
        testEmptyStackThrows();
        testPushPeekPop();
        testLifoOrder();
        testGrowth();
        testClear();
        testShrinkAfterDrain();
        testAllowsNull();

        System.out.println();
        System.out.println("SUMMARY: " + passed + " passed, " + failed + " failed.");
        // A non-zero exit code lets an automated build flag a failing run.
        if (failed > 0) {
            System.exit(1);
        }
    }

    /** A brand-new stack should report that it is empty. */
    private static void testStartsEmpty() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        check("a new stack is empty", stack.isEmpty());
    }

    /** pop and peek on an empty stack must throw EmptyStackException. */
    private static void testEmptyStackThrows() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        boolean popThrew = false;
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            popThrew = true;
        }
        check("pop on an empty stack throws EmptyStackException", popThrew);

        boolean peekThrew = false;
        try {
            stack.peek();
        } catch (EmptyStackException e) {
            peekThrew = true;
        }
        check("peek on an empty stack throws EmptyStackException", peekThrew);
    }

    /** After a single push, peek returns the value and pop removes it. */
    private static void testPushPeekPop() {
        ArrayStack<String> stack = new ArrayStack<>();
        stack.push("hello");
        check("stack is not empty after a push", !stack.isEmpty());
        check("peek returns the pushed value without removing it", "hello".equals(stack.peek()));
        check("peek did not remove the value", !stack.isEmpty());
        check("pop returns the pushed value", "hello".equals(stack.pop()));
        check("stack is empty after popping the only value", stack.isEmpty());
    }

    /** Values must come off the stack in last-in, first-out order. */
    private static void testLifoOrder() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        check("pop returns 3 first (last in)", stack.pop() == 3);
        check("pop returns 2 next", stack.pop() == 2);
        check("pop returns 1 last (first in)", stack.pop() == 1);
        check("stack is empty after LIFO drain", stack.isEmpty());
    }

    /** Pushing well past the initial capacity must grow the array correctly. */
    private static void testGrowth() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }
        check("top is the last value pushed after growth", stack.peek() == 99);

        boolean orderHeld = true;
        for (int i = 99; i >= 0; i--) {
            if (stack.pop() != i) {
                orderHeld = false;
            }
        }
        check("all 100 values pop back in correct order after growth", orderHeld);
    }

    /** clear must empty the stack and leave it reusable. */
    private static void testClear() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(10);
        stack.push(20);
        stack.clear();
        check("stack is empty after clear", stack.isEmpty());
        stack.push(42);
        check("stack works again after clear", !stack.isEmpty() && stack.peek() == 42);
    }

    /**
     * Growing then draining the stack must not corrupt it: the internal
     * shrink logic runs during the pops and the stack must stay correct.
     */
    private static void testShrinkAfterDrain() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        for (int i = 0; i < 50; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 50; i++) {
            stack.pop();
        }
        check("stack is empty after grow-then-drain", stack.isEmpty());

        // Reuse after shrinking back down must still behave.
        stack.push(7);
        check("stack is usable after shrinking back down", stack.peek() == 7);
    }

    /** The interface does not forbid null entries, so they must be stored. */
    private static void testAllowsNull() {
        ArrayStack<String> stack = new ArrayStack<>();
        stack.push(null);
        check("stack is not empty after pushing null", !stack.isEmpty());
        check("pop returns the stored null", stack.pop() == null);
    }
}//end ArrayStackTest
