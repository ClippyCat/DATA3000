package ADTStack;

/**
 * A node in the BST. Stores one variable (key = name, value = number)
 * and links to left and right children.
 */
public class Node {
    String key;
    int value;
    Node left;
    Node right;

    public Node(String key, int value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}// end Node
