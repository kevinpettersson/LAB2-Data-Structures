import java.util.*;

// A priority queue.
public class
PriorityQueue<E> {
	private ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}

	public ArrayList<E> getHeap(){
		return this.heap;
	}
     
	// Adds an item to the priority queue.
	public void add(E x) {
		heap.add(x);
		int index = heap.size()-1;
		// To maintain the heap property.
		siftUp(index);
		hash.put(index, x);
	}

	public void swap(int i, int j){
		E tmp = heap.get(i);
		E tmp2 = heap.get(j);
		//E tmp3 = hash.get(i);
		//E tmp4 = hash.get(j);

		heap.set(j, tmp);
		heap.set(i, tmp2);
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public void deleteMinimum() {
		if (size() == 0){
			throw new NoSuchElementException();
		}
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		if (heap.size() > 0) {
			siftDown(0);
		}
	}

	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	public void siftDown(int index) {
		E value = heap.get(index);
		
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childValue = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}
			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) > 0) {
				heap.set(index, childValue);
				index = child;
			} else break;
		}
		heap.set(index, value);
	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	public void siftUp(int index){

		while(index > 0){
			if (comparator.compare(heap.get(index), heap.get(parent(index))) < 0){
				swap(index, parent(index));
				index = (index-1)/2;
				//hash.put(index, heap.get(index));
			} else break;
		}
	}

	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}
}