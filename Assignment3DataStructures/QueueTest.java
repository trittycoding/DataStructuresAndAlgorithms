
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/// <summary>
/// QueueTest - A class for testing the Queue class
///
/// Assignment:     #3
/// Course:         ADEV-3001
/// Date Created:   March 20th, 2018
///
/// Revision Log
/// Who         When        Reason
/// ----------- ----------- ---------------
///
/// @author: Scott Wachal
/// @version 1.0
/// </summary>
public class QueueTest
{

    //region Constructor Tests
    /// <summary>
    /// Test the constructor to ensure the default values are set properly.
    /// </summary>
    @Test
    public void Constructor_head_and_tail_is_null_Test()
    {
        Queue<Point> queue = new Queue<Point>();
        assertEquals(queue.getHead(), null);
        assertEquals(queue.getTail(), null);
    }

    //endregion

    //region Public Methods Test

    //region GetSize()
    /// <summary>
    /// Test GetSize() to ensure it returns zero on empty queue.
    /// </summary>
    @Test
    public void GetSizeOnEmptyQueueTest()
    {
        Queue<Point> queue = new Queue<Point>();

        assertEquals(queue.getSize(), 0);
    }
    //endregion

    //region Enqueue()
    /// <summary>
    /// Test Enqueue() to ensure node is added to queue and size increases
    /// </summary>
    @Test
    public void Enqueue_increases_size_by_1_Test()
    {
        Point newPoint = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();

        assertEquals(queue.getSize(), 0);

        queue.enqueue(newPoint);

        assertEquals(queue.getSize(), 1);
    }

    /// <summary>
    /// Test Enqueue() to ensure node is added and is the new head/tail
    /// </summary>
    @Test
    public void Enqueue_Inserts_To_Head_Test()
    {
        Point newPoint = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();

        queue.enqueue(newPoint);

        Point headPoint = queue.getHead().getElement();
        Point tailPoint = queue.getHead().getElement();

        assertEquals(headPoint, newPoint);
        assertEquals(tailPoint, newPoint);
        assertEquals(queue.getHead().getNext(), null);
        assertEquals(queue.getTail().getNext(), null);
    }

    /// <summary>
    /// Test Enqueue() to ensure node is added to the queue.
    /// </summary>
    @Test
    public void Enqueue_Inserts_To_Tail_when_list_is_larger_Test()
    {
        Point point01 = new Point(3, 5);
        Point point02 = new Point(6, 7);
        Queue<Point> queue = new Queue<Point>();

        queue.enqueue(point02);
        queue.enqueue(point01);

        Point headPoint = queue.getHead().getElement();
        Point bottomPoint = queue.getTail().getElement();
        assertEquals(headPoint, point02);
        assertEquals(bottomPoint, point01);

        assertEquals(queue.getSize(), 2);
    }
    //endregion

    //region IsEmpty()
    /// <summary>
    /// Test IsEmpty() returns true on empty queue.
    /// </summary>
    @Test
    public void IsEmptyOnEmptyQueueTest()
    {
        Queue<Point> queue = new Queue<Point>();

        assertTrue(queue.isEmpty());
    }

    /// <summary>
    /// Test IsEmpty() returns false on a queue with elements.
    /// </summary>
    @Test
    public void IsEmptyOnQueueWithElements()
    {
        Point point01 = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point01);

        assertFalse(queue.isEmpty());
    }
    //endregion

    //region Front()
    /// <summary>
    /// Test Front() throws an exception when called on an empty queue.
    /// </summary>
    @Test
    public void Front_Throws_Exception_On_EmptyQueue_Test()
    {
        Queue<Point> queue = new Queue<Point>();

        assertThrows(IllegalArgumentException.class, () -> queue.front());
    }
    /// <summary>
    /// Test Front() to ensure it returns the front node.
    /// </summary>
    @Test
    public void Front_returns_head_in_list_of_1_Test()
    {
        Point point01 = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point01);

        Point returnedPoint = queue.front();
        Point headPoint = queue.getHead().getElement();
        Point tailPoint = queue.getTail().getElement();

        assertEquals(returnedPoint, point01);
        assertEquals(headPoint, returnedPoint);
        assertEquals(tailPoint, returnedPoint);
    }

    /// <summary>
    /// Test Front() to ensure it returns the head node.
    /// </summary>
    @Test
    public void Front_returns_head_in_larger_list_Test()
    {
        Point point01 = new Point(1, 5);
        Point point02 = new Point(2, 5);
        Point point03 = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point03);
        queue.enqueue(point02);
        queue.enqueue(point01);

        Point returnedPoint = queue.front();
        Point headPoint = queue.getHead().getElement();

        assertEquals(returnedPoint, point03);
        assertEquals(headPoint, returnedPoint);

        // check integrity of queue:
        Point secondPoint = queue.getHead().getNext().getElement();
        Point thirdPoint = queue.getHead().getNext().getNext().getElement();
        Node<Point> tailNode = queue.getHead().getNext().getNext();

        assertEquals(secondPoint, point02);
        assertEquals(thirdPoint, point01);
        assertEquals(thirdPoint, tailNode.getElement());

        // check that the tailNode still points to null!
        assertNull(tailNode.getNext());
    }

    /// <summary>
    /// Test Front() to make sure it only returns the element and does not remove the element.
    /// </summary>
    @Test
    public void Front_Does_Not_Remove_an_Element_Test()
    {
        Point newPoint = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(newPoint);

        Point returnedPoint = queue.front();

        assertEquals(queue.getSize(), 1);
    }

    //endregion

    //region Dequeue()
    /// <summary>
    /// Test Dequeue() to ensure it throws and exception when called on an empty queue.
    /// </summary>
    @Test
    public void Dequeue_Throws_Exception_On_EmptyQueue_Test()
    {
        Queue<Point> queue = new Queue<Point>();

        assertThrows(IllegalArgumentException.class, () -> queue.dequeue());
    }

    /// <summary>
    /// Test Dequeue() to ensure it reduces the size by 1
    /// </summary>
    @Test
    public void Dequeue_decreases_size_by_1_Test()
    {
        Point point01 = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point01);

        Point returnedPoint = queue.dequeue();

        assertEquals(queue.getSize(), 0);
    }

    /// <summary>
    /// Test Dequeue() to ensure it returns the front element.
    /// </summary>
    @Test
    public void Dequeue_returns_head_element_in_list_of_1_Test()
    {
        Point point01 = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point01);

        Node<Point> oldHead = queue.getHead();
        Point oldHeadPoint = oldHead.getElement();
        Point returnedPoint = queue.dequeue();
        Node<Point> newHead = queue.getHead();

        assertEquals(oldHeadPoint, returnedPoint);
        assertEquals(returnedPoint, point01);

        // list of 1 after a remove is an empty list
        assertTrue(queue.isEmpty());
    }


    /// <summary>
    /// Test Dequeue() sets head and tail to null when removing the last node in the queue
    /// </summary>
    @Test
    public void Dequeue_sets_head_and_tail_to_null_in_list_of_1_Test()
    {
        Point point = new Point(3, 5);
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point);

        // After an enqueue the size goes from 1 to 0
        assertEquals(queue.getSize(), 1);
        queue.dequeue();

        // Head and tail are null and size is 0
        assertEquals(queue.getHead(), null);
        assertEquals(queue.getTail(), null);
        assertEquals(queue.getSize(), 0);

        // list of 1 after a remove is an empty list
        assertTrue(queue.isEmpty());
    }


    /// <summary>
    /// Test Dequeue() to ensure it returns the head element, in a bigger list.
    /// </summary>
    @Test
    public void Dequeue_returns_head_element_in_larger_list_Test()
    {
        Point point01 = new Point(3, 5);
        Point point02 = new Point(2, 4);
        Point point03 = new Point(1, 3);

        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(point03);
        queue.enqueue(point02);
        queue.enqueue(point01);

        Node<Point> oldHead = queue.getHead();
        Point oldHeadPoint = oldHead.getElement();
        Point returnedPoint = queue.dequeue();
        Node<Point> newHead = queue.getHead();
        Node<Point> lastNode = newHead.getNext();
        Node<Point> tailNode = queue.getTail();

        assertEquals(oldHeadPoint, returnedPoint);
        assertEquals(returnedPoint, point03);
        assertEquals(newHead.getElement(), point02);
        assertEquals(lastNode.getElement(), point01);
        assertEquals(lastNode.getElement(), tailNode.getElement());
        assertNull(lastNode.getNext());

        assertEquals(queue.getSize(), 2);
    }
    //endregion

    //region Clear()
    /// <summary>
    /// Test Clear() to ensure it returns size of zero and null head.
    /// </summary>
    @Test
    public void Clear_on_populated_queue_sets_size_to_0_head_becomes_null_Test()
    {
        Queue<Point> queue = new Queue<Point>();
        queue.enqueue(new Point(3, 5));
        queue.enqueue(new Point(2, 4));
        queue.clear();
        assertNull(queue.getHead());
        assertTrue(queue.isEmpty());
    }
    //endregion

    //endregion

}