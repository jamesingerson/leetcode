import java.util.Stack;

class MyQueue {
    // https://leetcode.com/problems/implement-queue-using-stacks/
    Stack<Integer> store;
    Stack<Integer> reverse;

    public MyQueue() {
        store = new Stack<>();
        reverse = new Stack<>();
    }

    public void push(int x) {
        store.push(x);
    }

    public int pop() {
        while (!store.empty()) {
            reverse.push(store.pop());
        }
        int popped = reverse.pop();
        while (!reverse.empty()) {
            store.push(reverse.pop());
        }
        return popped;
    }

    public int peek() {
        while (!store.empty()) {
            reverse.push(store.pop());
        }
        int popped = reverse.peek();
        while (!reverse.empty()) {
            store.push(reverse.pop());
        }
        return popped;
    }

    public boolean empty() {
        return store.empty();
    }

    // Notes:
    // You can make this more clever by using the second stack for
    // holding output. You can always peek before popping and make peek
    // cycle input into output if output is empty. The check for emptiness
    // needs to be updated to check both stacks in this case.
    // Some people cheesed this by technically fulfilling the problem definition
    // using ArrayList. It doesn't seem in the spirit of the problem to me.
}