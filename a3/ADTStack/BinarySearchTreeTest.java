package ADTStack;

public class BinarySearchTreeTest {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            failed++;
            System.out.println("FAIL: " + description);
        }
    }

    public static void main(String[] args) {
        testEmptyTreeStartsEmpty();
        testSingleInsertionAndSearch();
        testInsertsSeveralKeysAndPreservesOrdering();
        testSearchMissingKeyReturnsNull();
        testDuplicateKeyUpdatesValue();
        testInsertRejectsNullAndEmptyKeys();
        testDeleteLeafRemovesNode();
        testDeleteSingleChildReLinks();
        testDeleteTwoChildrenUsesSuccessor();
        testDeleteMissingKeyLeavesTreeUnchanged();
        testDeleteFromEmptyTreeDoesNotThrow();
        testDeleteAllClearsTree();
        testDisplayTreeOnEmptyTree();
        testDisplayTreeOnSingleNode();
        testDisplayTreeMatchesWorkedExample();

        System.out.println();
        System.out.println("SUMMARY: " + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testEmptyTreeStartsEmpty() {
        BinarySearchTree tree = new BinarySearchTree();
        check("a new tree is empty", tree.isEmpty());
    }

    private static void testSingleInsertionAndSearch() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("alpha", 1);
        check("tree is not empty after inserting one key", !tree.isEmpty());
        check("search returns the inserted value", tree.search("alpha") != null && tree.search("alpha") == 1);
    }

    private static void testInsertsSeveralKeysAndPreservesOrdering() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("beta", 2);
        tree.insert("alpha", 1);
        tree.insert("gamma", 3);
        check("search finds the smallest key", tree.search("alpha") != null && tree.search("alpha") == 1);
        check("search finds the middle key", tree.search("beta") != null && tree.search("beta") == 2);
        check("search finds the largest key", tree.search("gamma") != null && tree.search("gamma") == 3);
    }

    private static void testSearchMissingKeyReturnsNull() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("x", 10);
        check("search for a missing key returns null", tree.search("missing") == null);
    }

    private static void testDuplicateKeyUpdatesValue() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("dup", 1);
        tree.insert("dup", 99);
        check("duplicate insertion updates the existing value", tree.search("dup") != null && tree.search("dup") == 99);
    }

    private static void testInsertRejectsNullAndEmptyKeys() {
        BinarySearchTree tree = new BinarySearchTree();
        boolean nullThrows = false;
        boolean emptyThrows = false;
        try {
            tree.insert(null, 1);
        } catch (IllegalArgumentException e) {
            nullThrows = true;
        }
        try {
            tree.insert("", 1);
        } catch (IllegalArgumentException e) {
            emptyThrows = true;
        }
        check("insert rejects a null key", nullThrows);
        check("insert rejects an empty key", emptyThrows);
    }

    private static void testDeleteLeafRemovesNode() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.delete("b");
        check("deleting a leaf removes it from the tree", tree.search("b") == null);
    }

    private static void testDeleteSingleChildReLinks() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("b", 2);
        tree.insert("a", 1);
        tree.delete("b");
        check("deleting a node with one child relinks the child", tree.search("a") != null && tree.search("a") == 1);
        check("deleted node is no longer found", tree.search("b") == null);
    }

    private static void testDeleteTwoChildrenUsesSuccessor() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("b", 2);
        tree.insert("a", 1);
        tree.insert("d", 4);
        tree.insert("c", 3);
        tree.delete("b");
        check("deleting a node with two children preserves remaining keys",
                tree.search("a") != null && tree.search("c") != null && tree.search("d") != null);
    }

    private static void testDeleteMissingKeyLeavesTreeUnchanged() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.delete("missing");
        check("deleting a missing key leaves the tree unchanged", tree.search("a") != null && tree.search("b") != null);
    }

    private static void testDeleteFromEmptyTreeDoesNotThrow() {
        BinarySearchTree tree = new BinarySearchTree();
        boolean ok = true;
        try {
            tree.delete("x");
        } catch (Exception e) {
            ok = false;
        }
        check("deleting from an empty tree does not throw", ok);
    }

    private static void testDeleteAllClearsTree() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("x", 1);
        tree.deleteAll();
        check("deleteAll empties the tree", tree.isEmpty());
        check("display after deleteAll reports the tree is empty", "Tree is empty".equals(tree.displayTree()));
    }

    private static void testDisplayTreeOnEmptyTree() {
        BinarySearchTree tree = new BinarySearchTree();
        check("empty tree display returns the required message", "Tree is empty".equals(tree.displayTree()));
    }

    private static void testDisplayTreeOnSingleNode() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("root", 7);
        check("single-node display contains a single line", "||==> root:7".equals(tree.displayTree()));
    }

    private static void testDisplayTreeMatchesWorkedExample() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert("j", 5);
        tree.insert("i", 4);
        tree.insert("h", 3);
        tree.insert("g", 2);
        String expected = "||==> j:5\n"
                + "    ||==> i:4\n"
                + "        ||==> h:3\n"
                + "            ||==> g:2";
        check("displayTree matches the assignment's worked example structure", expected.equals(tree.displayTree()));
    }
}
