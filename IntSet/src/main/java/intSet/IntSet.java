package intSet;

import java.util.ArrayList;

/**
 * Representation of a finite set of integers.
 * 
 * @invariant getCount() >= 0
 * @invariant getCount() <= getCapacity()
 */
public class IntSet {

	private final ArrayList<Integer> set;
	private final int capacity;

	/**
	 * Creates a new set with 0 elements.
	 * 
	 * @param capacity
	 *            the maximal number of elements this set can have
	 * @pre capacity >= 0
	 * @post getCount() == 0
	 * @post getCapacity() == capacity
	 */
	public IntSet(int capacity) {
		if (capacity < 0) {
			throw new UnsupportedOperationException("Impossible to create a set with negative capacity!");
		}
		this.capacity = capacity;
		set = new ArrayList<>(capacity);
	}

	/**
	 * Test whether the set is empty.
	 * @return getCount() == 0 || @return set.isEmpty()
	 */
	public boolean isEmpty() {
		return getCount() == 0;
	}

	/**
	 * Test whether a value is in the set
	 * 
	 * @return exists int v in getArray() such that v == value
	 */
	public boolean has(int value) {
		return set.contains(value);
	}

	/**
	 * Adds a value to the set.
	 * 
	 * @pre getCount() < getCapacity()
	 * @post has(value)
	 * @post !this@pre.has(value) implies (getCount() == this@pre.getCount() + 1)
	 * @post this@pre.has(value) implies (getCount() == this@pre.getCount())
	 */
	public void add(int value) {
		if (getCapacity() == getCount()) {
			throw new UnsupportedOperationException("Set reached it's maximum capacity, unable to add!");
		} else if (!has(value) && (getCapacity()>getCount())) {
			set.add(value);
		}
	}

	/**
	 * Removes a value from the set.
	 * 
	 * @post !has(value)
	 * @post this@pre.has(value) implies (getCount() == this@pre.getCount() - 1)
	 * @post !this@pre.has(value) implies (getCount() == this@pre.getCount())
	 */
	public void remove(int value) {
		if (has(value)) {
			set.remove((Integer) value);
		}
	}

	/**
	 * Returns the union of this set and another set.
	 * 
	 * @param other
	 *            the set to union this set with
	 * @return the union
	 * @pre other != null
	 * @post forall int v: has(v) implies return.has(v)
	 * @post forall int v: other.has(v) implies return.has(v)
	 * @post forall int v: return.has(v) implies (has(v) or other.has(v))
	 */
	public IntSet union(IntSet other) {
		if (other != null) {
			IntSet resultingSet = new IntSet(getCapacity() + other.getCount());
			//Adding all the elements from the second set
			for (int i = 0; i < other.getCount(); i++) {
				resultingSet.add(other.getElement(i));
			}
			//Adding all the elements from the first set, which aren't in the second.
			for (int i = 0; i < getCount(); i++) {
				if (!resultingSet.has(set.get(i))) {
					resultingSet.add(set.get(i));
				}
			}
			return resultingSet;
		} else throw new NullPointerException(" The second set is null.");
	}

	/**
	 * Returns the intersection of this set and another set.
	 *
	 * @param other
	 *            the set to intersect this set with
	 * @return the intersection
	 * @pre other != null
	 * @post forall int v: (has(v) and other.has(v)) implies return.has(v)
	 * @post forall int v: return.has(v) implies (has(v) and other.has(v))
	 */

	public IntSet intersection(IntSet other) {
		if (other != null) {
			int size = 0;
			for (int i = 0; i < getCount(); i++) {
				if (other.has(set.get(i))) {
					size++;
				}
			}
			//Optimizing the resultingSet capacity
			IntSet resultingSet = new IntSet(size);
			for (int i = 0; i < getCount(); i++) {
				if (other.has(set.get(i))) {
					resultingSet.add(set.get(i));
				}
			}
			return resultingSet;
		} else {
			throw new NullPointerException("Second set is null!");
		}
	}


	/**
	 * Returns the difference of this set and another set.
	 *
	 * @param other
	 *            the set to intersect this set with
	 * @return the intersection
	 * @pre other != null
	 * @post forall int v: (has(v) and other.has(v)) implies return.has(v)
	 * @post forall int v: return.has(v) implies (has(v) and other.has(v))
	 */

	public IntSet difference(IntSet other) {
		if (other != null) {
			IntSet resultingSet;
			int differentElements = 0;
			for (int i = 0; i < getCount(); i++) {
				if (!other.has(set.get(i))) {
					differentElements++;
				}
			}
			//In order to optimize the resultingSet capacity(set will containt only elements that differ)
			resultingSet = new IntSet(differentElements);
			for (int i = 0; i < getCount(); i++) {
				if (!other.has(set.get(i))) {
					resultingSet.add(set.get(i));
				}
			}
			return resultingSet;
		} else {
			throw new NullPointerException("Second set is null!");
		}
	}

	public IntSet symmetricDifference(IntSet other) {
		if (other != null) {
			IntSet resultingSet;
			int commonElements = 0;
			for (int i = 0; i < getCount(); i++) {
				if (other.has(set.get(i))) {
					commonElements++;
				}
			}
			// In order to optimize the resultingSet capacity
			resultingSet = new IntSet(getCount() + other.getCount() - commonElements);
			for (int i = 0; i < getCount(); i++) {
				if (!other.has(set.get(i))) {
					resultingSet.add(set.get(i));
				}
			}
			for (int i = 0; i < other.getCount(); i++) {
				if (!has(other.getElement(i))) {
					resultingSet.add(other.getElement(i));
				}
			}
			return resultingSet;
		} else {
			throw new NullPointerException(" The second set is null.");
		}
	}

	/**
	 * Returns a representation of this set as an array
	 * 
	 * @post return.length == getCount()
	 * @post forall int v in return: has(v)
	 */
	public int[] getArray() {
		if (set != null) {
			int[] arr = new int[getCount()];
			for (int i = 0; i < getCount(); i++) {
				arr[i] = set.get(i);
			}
			return arr;
		} else {
			throw new NullPointerException("The set is null");
		}
	}

	/**
	 * Returns the number of elements in the set.
	 */
	public int getCount() {
		return set.size();
	}

	/**
	 * Returns the maximal number of elements in the set.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the set capacity
	 */

	public int getElement(int index) {
		return set.get(index);
	}

	/**
	 * Returns a string representation of the set. The empty set is represented
	 * as {}, a singleton set as {x}, a set with more than one element like {x,
	 * y, z}.
	 */
	public String toString() {
		String str = set.toString();
		str = str.replaceAll("\\[", "{").replaceAll("]", "}");
		return str;
	}

	public void clear() {
		if (set != null) {
			set.removeAll(set);
		}
	}
}
