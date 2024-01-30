public class Node<T> {
    T payload;
    Node<T> left, right;

    public Node(T payload) {
        this.payload = payload;
        left = null;
        right = null;
    }

    // Accessors
    public T getPayload() {
        return payload;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    // Mutators
    public void setPayload(T payload) {
        this.payload = payload;
    }
    
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}
