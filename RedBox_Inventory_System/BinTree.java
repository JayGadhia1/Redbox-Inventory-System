import java.io.PrintWriter;

public class BinTree<T extends Comparable<T>> {
    private Node<T> root;

    public BinTree() {
        root = null;
    }

    // Insert a new node into the tree
    public void insert(T payload) {
        root = insertRecursive(root, payload);
    }

    private Node<T> insertRecursive(Node<T> current, T payload) {
        if (current == null) {
            return new Node<>(payload);
        }

        if (payload.compareTo(current.getPayload()) < 0) {
            current.setLeft(insertRecursive(current.getLeft(), payload));
        } else if (payload.compareTo(current.getPayload()) > 0) {
            current.setRight(insertRecursive(current.getRight(), payload));
        }

        return current;
    }

    // Search for a node in the tree
    public Node<T> search(T payload) {
        return searchRecursive(root, payload);
    }

    private Node<T> searchRecursive(Node<T> current, T payload) {
        if (current == null || current.getPayload().equals(payload)) {
            return current;
        }

        if (payload.compareTo(current.getPayload()) < 0) {
            return searchRecursive(current.getLeft(), payload);
        } else {
            return searchRecursive(current.getRight(), payload);
        }
    }

    // Delete a node from the tree
    public void delete(T payload) {
        root = deleteRecursive(root, payload);
    }

    private Node<T> deleteRecursive(Node<T> current, T payload) {
        if (current == null) {
            System.out.println("Node not found for deletion: " + payload);
            return null;
        }
    
        System.out.println("Current node during deletion: " + current.getPayload() + ", Target payload: " + payload);
    
        if (payload.compareTo(current.getPayload()) == 0) {
            System.out.println("Node to delete found: " + current.getPayload());
            // Node to delete found
            if (current.getLeft() == null && current.getRight() == null) {
                System.out.println("Deleting node with no children.");
                return null;
            } else if (current.getRight() == null) {
                System.out.println("Deleting node with only left child.");
                return current.getLeft();
            } else if (current.getLeft() == null) {
                System.out.println("Deleting node with only right child.");
                return current.getRight();
            } else {
                System.out.println("Deleting node with two children.");
                T smallestValue = findSmallestValue(current.getRight());
                System.out.println("Smallest value in right subtree: " + smallestValue);
                current.setPayload(smallestValue);
                current.setRight(deleteRecursive(current.getRight(), smallestValue));
                return current;
            }
        } 
    
        if (payload.compareTo(current.getPayload()) < 0) {
            current.setLeft(deleteRecursive(current.getLeft(), payload));
            return current;
        } else {
            current.setRight(deleteRecursive(current.getRight(), payload));
            return current;
        }
    }
          

    private T findSmallestValue(Node<T> root) {
        return root.getLeft() == null ? root.getPayload() : findSmallestValue(root.getLeft());
    }

    // Print the tree in-order
    public void printTree() {
        printInOrder(root);
    }

    private void printInOrder(Node<T> node) {
        if (node != null) {
            printInOrder(node.getLeft());
            System.out.println(node.getPayload());
            printInOrder(node.getRight());
        }
    }

    // Print the tree pre-order
    public void printTreePreOrder(PrintWriter writer) {
        printPreOrder(root, writer);
    }

    private void printPreOrder(Node<T> node, PrintWriter writer) {
        if (node != null) {
            writer.println(node.getPayload());
            printPreOrder(node.getLeft(), writer);
            printPreOrder(node.getRight(), writer);
        }
    }
}
