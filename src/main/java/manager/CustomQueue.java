package manager;

public class CustomQueue<T> {
    private T[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public CustomQueue() {
        this.capacity = 10;
        this.queue = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(T item) {
        if (size == capacity) {
            resize();
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new QueueEmptyException("Queue is empty");
        }
        T item = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        T[] newQueue = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % capacity];
        }
        queue = newQueue;
        front = 0;
        rear = size - 1;
        capacity = newCapacity;
    }

    // Alternative to iterator - provide direct access to elements
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new QueueIndexOutOfBoundsException();
        }
        return queue[(front + index) % capacity];
    }

    public int getSize() {
        return size;
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