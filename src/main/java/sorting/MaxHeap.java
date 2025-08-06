package sorting;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap {
    private int capacity = 5;
    private int[] elements;
    private int size;

    public MaxHeap() {
        elements = new int[capacity];
    }

    public boolean checkIfArrIsMaxHeap(long[] arr, long n)
    {
        for (int i = 0; i < n / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && arr[i] < arr[left]) {
                return false;
            }
            if (right < n && arr[i] < arr[right]) {
                return false;
            }
        }
        return true;
    }

    public void add(int itemToAdd) {
        ensureExtraCapacity();
        elements[size] = itemToAdd;
        size++;
        shiftUp();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        return elements[0];
    }

    public int remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        int maxItem = elements[0];
        elements[0] = elements[size - 1];
        size--;

        heapifyDown();

        return maxItem;
    }

    private void heapifyDown() {
        int index = 0;

        while (hasLeftChild(index)) {
            int largerChildIndex = getLeftChildIndex(index);

            if (hasRightChild(index) && rightChild(index) > leftChild(index)) {
                largerChildIndex = getRightChildIndex(index);
            }

            if (elements[index] >= elements[largerChildIndex]) {
                break;
            }

            swap(index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    private void shiftUp() {
        int index = size - 1;

        while (hasParent(index) && elements[index] > parent(index)) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    private void ensureExtraCapacity() {
        if (size == capacity) {
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private int leftChild(int index) {
        return elements[getLeftChildIndex(index)];
    }

    private int rightChild(int index) {
        return elements[getRightChildIndex(index)];
    }

    private int parent(int index) {
        return elements[getParentIndex(index)];
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private void swap(int index1, int index2) {
        int temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }
}