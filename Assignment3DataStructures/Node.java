

// A class that accepts any generic type of data. Returns the object in same format as stored.
public class Node<T> {
    // Characteristics of a Node - previous and next node
    // Element represents an Object of custom type
    private Node<T> previousNode;
    private Node<T> nextNode;
    private T element;

    // Overloaded constructor - initializes an element (custom object)
    // and the previous/next nodes in the linked list
    public Node(T element, Node previousNode, Node nextNode){
        setElement(element);
        setNext(nextNode);
        setPrevious(previousNode);
    }

    // Constructor that is initialized by element only
    public Node(T element){
        setElement(element);
        this.previousNode = null;
        this.nextNode = null;
    }

    // Default constructor - previous, next and element are null
    public Node(){
        this.element = null;
        this.previousNode = null;
        this.nextNode = null;
    }

    // Returns the given node's stored object
    public T getElement(){
        return this.element;
    }

    // Returns the given node's previous node in the linked list
    public Node<T> getPrevious(){
        return this.previousNode;
    }

    // Returns the given node's next node in the linked list
    public Node<T> getNext(){
        return this.nextNode;
    }

    // Sets the given node's previous node in the linked list
    public void setPrevious(Node<T> previousNode){
        this.previousNode = previousNode;
    }

    // Sets the given node's next node in the linked list
    public void setNext(Node<T> nextNode){
        this.nextNode = nextNode;
    }

    // Sets the given node's element to the passed parameter
    public void setElement(T element){
        this.element = element;
    }
}
