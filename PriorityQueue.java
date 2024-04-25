import java.util.*;

// A priority queue.
// java -ea -jar testing.jar lab2test.Lab2GenTest Lab2
public class
PriorityQueue<E> {
	private ArrayList<E> heap = new ArrayList<E>();
	private HashMap<Integer, E> hash= new HashMap<>();
	private Comparator<E> comparator;

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}
	public HashMap<Integer, E> getHash(){
		return hash;
	}

	public ArrayList<E> getHeap(){
		return this.heap;
	}
     
	// Adds an item to the priority queue.
	public void add(E x) {
		heap.add(x);
		int index = heap.size()-1;
		hash.put(index, x);
		siftUp(index);
	}

	public void swap(int i, int j){
		E tmp = heap.get(i);
		E tmp2 = heap.get(j);
		heap.set(j, tmp);
		heap.set(i, tmp2);

		hash.put(j, tmp);
		hash.put(i, tmp2);
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
	/*
	public void deleteMinimum() {
		if (size() == 0){
			throw new NoSuchElementException();
		}

		heap.set(0, hash.get(hash.size()-1));

		heap.remove(hash.size()-1);
		hash.remove(hash.get(0));
		if (hash.size() > 0) {
			siftDown(0);
		}
	}

	 */

	public void deleteMinimum() {
		if (size() == 0) {
			throw new NoSuchElementException("Priority queue is empty");
		}

		// Remove the minimum element from the heap
		E removedElement = heap.get(0);
		int lastIndex = heap.size() - 1;
		E lastElement = heap.get(lastIndex);
		heap.set(0, lastElement);
		heap.remove(lastIndex);

		// Update the hash map
		hash.remove(0); // Remove the minimum element from the map

		if (heap.size() > 0) {
			hash.put(0, lastElement); // Update the index of the last element in the map
			siftDown(0); // Restore heap property by sifting down from the root
		}
	}


	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	public void siftDown(int index) {
		//E value = hash.get(index);
		
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E value = heap.get(index);
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
				//heap.set(index, childValue);
				//hash.put(index, childValue);
				swap(index, child);
				index = child;
			} else break;
		}
		//hash.put(index, value);
		//heap.set(index, value);
	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	public void siftUp(int index){

		while(index > 0){
			if (comparator.compare(hash.get(index), hash.get(parent(index))) < 0){
				swap(index, parent(index));
				index = (index-1)/2;
				//hash.put(index, heap.get(index));
			} else break;
		}
		heap.set(index, heap.get(index));
		hash.put(index,heap.get(index));
	}

	 */
	public void siftUp(int index) {
		E value = heap.get(index); // Store the value being sifted up

		while (index > 0) {
			int parentIndex = parent(index);
			E parentValue = heap.get(parentIndex);

			// If the value is less than its parent, swap and continue
			if (comparator.compare(value, parentValue) < 0) {
				swap(index, parentIndex);
				index = parentIndex;
			} else {
				break; // Stop sifting up if the value is in the correct position
			}
		}

		// Update the hash map with the final index of the value
		hash.put(heap.indexOf(value), value);
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