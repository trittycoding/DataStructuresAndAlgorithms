import java.util.NoSuchElementException;

//Class representing a stack
public class Stack<T> {
    // Nodes in a stack class only have attributes of an element and previous pointer
    private Node<T> head;
    private int size;

    // Constructor, initializes the size to zero and head to null
    public Stack(){
        this.clear();
    }

    // Gets current head
    public Node<T> getHead() {
        return head;
    }

    // Returns size of Stack
    public int getSize() {
        return size;
    }


    // Resets Stack to initial values
    public void clear(){
        this.head = null;
        this.size = 0;
    }

    // Creates a new node using the new element and adds it to top of stack
    public void push(T element){
        Node<T> newNode = new Node<T>(element);
        if(isEmpty()){
            this.head = newNode;
            this.size = size + 1;
        }

        else {
            Node<T> oldHead = this.getHead();
            this.head = newNode;
            newNode.setPrevious(oldHead);
            this.size = size+1;
        }
    }

    // Returns top element on the stack without removing from stack
    public T top(){
        if(this.isEmpty()){
            throw new NoSuchElementException("top() not allowed on Empty Stack!");
        }

        return getHead().getElement();
    }

    // Returns top element on stack, removes that element
    public T pop(){
        if(isEmpty()){
            throw new NoSuchElementException("pop() not allowed on Empty Stack!");
        }

        if(getSize() == 1){
            T element = getHead().getElement();
            clear();
            return element;
        }

        else {
            Node<T> currentHead = getHead();
            T element = currentHead.getElement();
            Node<T> nextInLine = currentHead.getPrevious();
            this.head = nextInLine;
            this.size = size-1;
            return element;
        }
    }

    // Checks to see if list is empty
    public boolean isEmpty(){
        if(getSize() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    // Clones the stack with same head pointer and size
    public Stack<T> copy(){
        Stack<T> clonedStack = new Stack<T>();
        clonedStack.head = this.head;
        clonedStack.size = this.size;
        return clonedStack;
    }

}
