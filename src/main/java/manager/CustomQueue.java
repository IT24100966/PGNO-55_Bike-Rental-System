package manager;

public class CustomQueue<T> {
    private T[] queueArray;
    private int front;
    private int rear;
    private int nItems;
    private int maxSize;

    public CustomQueue() {
        this(10); // Default size 10 as in original
    }

    public CustomQueue(int size) {
        maxSize = size;
        queueArray = (T[]) new Object[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }

    public void insert(T item) {
        if (isFull()) {
            resize();
        }

        if (rear == maxSize - 1) {
            rear = -1;
        }
        queueArray[++rear] = item;
        nItems++;
    }

    public T remove() {
        if (isEmpty()) {
            throw new QueueEmptyException("Queue is empty");
        }
        T temp = queueArray[front++];
        if (front == maxSize) {
            front = 0;
        }
        nItems--;
        return temp;
    }

    public boolean isEmpty() {
        return nItems == 0;
    }

    public boolean isFull() {
        return nItems == maxSize;
    }

    public int size() {
        return nItems;
    }


    private void resize() {
        int newCapacity = maxSize * 2;
        T[] newQueue = (T[]) new Object[newCapacity];

        for (int i = 0; i < nItems; i++) {
            newQueue[i] = queueArray[(front + i) % maxSize];
        }

        queueArray = newQueue;
        front = 0;
        rear = nItems - 1;
        maxSize = newCapacity;
    }

    // Method to maintain compatibility with BookingManager
    public T get(int index) {
        if (index < 0 || index >= nItems) {
            throw new QueueIndexOutOfBoundsException();
        }
        return queueArray[(front + index) % maxSize];
    }

    public int getSize() {
        return nItems;
    }

    // Custom exceptions
    public static class QueueEmptyException extends RuntimeException {
        public QueueEmptyException(String message) {
            super(message);
        }
    }

    public static class QueueIndexOutOfBoundsException extends RuntimeException {
        public QueueIndexOutOfBoundsException() {
            super("Index out of queue bounds");
        }
    }
}