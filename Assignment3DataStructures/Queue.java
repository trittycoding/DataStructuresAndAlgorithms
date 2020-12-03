import java.util.NoSuchElementException;

//A class representing a Queue
public class Queue<T> {
    // Queue class maintains a head, tail and size
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // Constructor - sets initial values
    public Queue(){
        this.clear();
    }

    // Creates a new node with a new element and adds to tail
    public void enqueue(T element){
        Node<T> newNode = new Node<T>(element);

        if(isEmpty()){
            this.head = newNode;
            this.tail = newNode;
            size++;
        }

        else{
            Node<T> oldTail = this.getTail();
            this.tail = newNode;
            oldTail.setNext(newNode);
            size++;
        }
    }

    // Returns the next element to be removed at front of queue w/o removing
    public T front(){
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty");
        }
        return this.getHead().getElement();
    }

    // Returns the element at the back of the Queue
    public T back(){
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty");
        }
        return this.getTail().getElement();
    }

    // Removes the front element from the queue
    public T dequeue(){
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty");
        }
        Node<T> currentHead = this.getHead();
        T elementToBeRemoved = currentHead.getElement();

        // If size is one, then reset fields to initial values
        if(size == 1){
            this.clear();
            return elementToBeRemoved;
        }

        this.head = getHead().getNext();
        currentHead.setNext(null);
        size--;
        return elementToBeRemoved;
    }

    // Resets the queue to initial values
    public void clear(){
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    // Checks to see if queue is empty
    public boolean isEmpty(){
        if(this.size == 0){
            return true;
        }
        else {
            return false;
        }
    }

    // Returns current tail
    public Node<T> getTail(){
        return this.tail;
    }

    // Returns the current head
    public Node<T> getHead(){
        return this.head;
    }

    // Returns the size of the queue
    public int getSize(){
        return this.size;
    }
}
