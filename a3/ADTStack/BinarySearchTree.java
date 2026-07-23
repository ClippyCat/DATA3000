package ADTStack;

/**
 * Binary search tree that stores the calculator's variables.
 * Keys are variable names, values are integers.
 * Left subtree = smaller keys, right subtree = greater keys.
 */
public class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a variable. If the key already exists, its value
     * is updated instead of adding a duplicate node.
     */
    public void insert(String key, int value) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Variable name cannot be null or empty.");
        }
        root = insertNode(root, key, value);
    }

    // Recursive helper: finds the right empty spot and attaches the new node.
    private Node insertNode(Node node, String key, int value) {
        if (node == null) {
            return new Node(key, value);
        }

        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = insertNode(node.left, key, value);
        } else if (comparison > 0) {
            node.right = insertNode(node.right, key, value);
        } else {
            node.value = value; // key already exists, update the value
        }
        return node;
    }

    /**
     * Searches for a key and returns its value, or null if not found.
     * Starts at the root and goes left or right based on the comparison.
     */
    public Integer search(String key) {
        if (key == null) {
            return null;
        }

        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison == 0) {
                return current.value;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null; // key not found
    }

    /**
     * Deletes the node with the given key. Handles all three cases:
     * no children, one child, and two children.
     */
    public void delete(String key) {
        if (key == null) {
            return;
        }
        root = deleteNode(root, key);
    }

    // Recursive helper for delete.
    private Node deleteNode(Node node, String key) {
        if (node == null) {
            return null; // key not found
        }

        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = deleteNode(node.left, key);
        } else if (comparison > 0) {
            node.right = deleteNode(node.right, key);
        } else {
            // Found the node to delete.
            if (node.left == null && node.right == null) {
                return null; // no children
            } else if (node.left == null) {
                return node.right; // only a right child
            } else if (node.right == null) {
                return node.left; // only a left child
            } else {
                // Two children: copy the smallest key from the right
                // subtree (in-order successor) into this node, then
                // delete that node from the right subtree.
                Node successor = findMin(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = deleteNode(node.right, successor.key);
            }
        }
        return node;
    }

    // Smallest key in a subtree = keep going left.
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Deletes all nodes from the tree.
     */
    public void deleteAll() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // Needed by Part 3 for displayTree.
    public Node getRoot() {
        return root;
    }

    /**
     * Displays the tree in a hierarchical structure.
     * Uses preorder traversal and indentation to show the tree shape.
     */
    public String displayTree() {
        if (isEmpty()) {
            return "Tree is empty";
        }

        StringBuilder sb = new StringBuilder();
        buildDisplay(root, "", sb);
        return sb.toString();
    }

    private void buildDisplay(Node node, String indent, StringBuilder sb) {
        if (node == null) {
            return;
        }

        buildDisplay(node.right, indent + "    ", sb); // right first (deeper indent)

        if (sb.length() > 0) {
            sb.append("\n");
        }
        sb.append(indent).append("||==> ").append(node.key).append(":").append(node.value);

        buildDisplay(node.left, indent + "    ", sb); // then left
    }
}// end BinarySearchTree
