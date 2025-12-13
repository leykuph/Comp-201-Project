public class MyStack<T> {

    private Object[] data;
    private int top;

    public MyStack(int capacity) {
        data = new Object[capacity];
        top = -1;
    }

    public void push(T item) {
        data[++top] = item;
    }

    public T pop() {
        return (T) data[top--];
    }

    public boolean isEmpty() {
        return top < 0;
    }
}
