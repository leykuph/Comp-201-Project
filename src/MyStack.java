public class MyStack<T> {

    private Object[] data;
    private int top;

    // O(1)
    public MyStack(int capacity) {
        data = new Object[capacity];
        top = -1;
    }

    // O(1)
    public void push(T item) {
        data[++top] = item;
    }

    // O(1)
    public T pop() {
        return (T) data[top--];
    }

    // O(1)
    public boolean isEmpty() {
        return top < 0;
    }


}
