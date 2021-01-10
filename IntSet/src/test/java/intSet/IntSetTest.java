package intSet;

/* If your IDE does not recognise annotations or assertions manually import the following */
import org.junit.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a dummy class. Feel free to delete it and generate your own using JUnit.
 * Important: make sure to fix your package directory yourself if you delete this!
 *
 * You can run the tests by clicking the green arrows next to "public class IntSetTest"
 */
public class IntSetTest {

	private IntSet set;
    //  Test methods go here

	public IntSetTest() {
	}

	@Before
	public void setUp() {
		set = new IntSet(3);
	}

	@After
	public void after() {
		set.clear();
	}

	/**
	 * Tests the constructor. Test cases :
	 * - if the set is created correctly
	 * - if the set indeed is empty
	 * - if the input capacity is >0
	 * In case that the capacity is less than 0, throws an exception
	 */

	@Test
	public void testConstructor() {
		assertNotNull(set);
		assertEquals(3, set.getCapacity());
		assertEquals(0, set.getCount());
		assertThrows(UnsupportedOperationException.class, ()-> set = new IntSet(-5));
	}

	/**
	 * Test the isEmpty method. Test Cases :
	 * - set is empty
	 * - set is not empty if we add something
	 */

	@Test
	public void testIsEmpty() {
		assertEquals(set.getCount(),0);
		set.add(1);
		assertNotEquals(0, set.getCount());
		assertFalse(set.isEmpty());
	}

	/**
	 * Test the has method. Test cases :
	 * - set is empty, it shouldn't have anything
	 * - set contains the added element
	 */

	@Test
	public void testHas() {
		if (set.isEmpty()) {
			assertFalse(set.has(99));
		}
		set.add(5);
		assertTrue(set.has(5),"The has method is not working properly.");
	}
	/**
	 * Test the add method. Test Cases :
	 * - if the method actually adds the element in the set.
	 * - if user is not trying to add an already existing element in the set.
	 * - if the user is trying to add an element to a set that is already full.
	 */

	@Test
	public void testAdd() {
		set.add(45);
		assertTrue(set.has(45));
		int count = set.getCount();
		set.add(45);
		set.add(45);
		assertEquals(count, set.getCount(), "There are duplicates");
		assertThrows(UnsupportedOperationException.class, ()-> { //Set full? -> error!
			set.add(10);
			set.add(-14);
			set.add(34);
			set.add(89);
			set.add(54);
		});
	}

	/**
	 * Test the remove method. Test cases :
	 * - if the method actually removes element
	 * - if the user tries to remove an element non-existing in the set.
	 * - if the set is empty and the user tries to remove an element.
	 */

	@Test
	public void testRemove() {
		set.remove(0);
		set.add(14);
		set.add(13);
		set.add(16);
		set.remove(14);
		assertFalse(set.has(14));
		int count = set.getCount();
		set.remove(14);
		assertEquals(count, set.getCount());
	}

	/**
	 * Test the getArray method. Test cases:
	 * - if the method returns an empty array.
	 * - if the method returns the array with the actual values of it
	 */
	@Test
	public void getArray() {
		int [] array = new int[set.getCount()];
		//returns an empty array
		assertArrayEquals(array, set.getArray());
		//add elements to the set and array;
		set.add(1);
		set.add(-3);
		set.add(32);
		array = new int[set.getCount()];
		array[0] = 1;
		array[1] = -3;
		array[2] = 32;
		assertArrayEquals(array, set.getArray());
	}

	/**
	 * Test the union method. Test Cases :
	 * - if the second set is null
	 * - if the method actually returns the correct union of two sets
	 * - if first set is empty
	 * - if second set is empty
	 * - if both sets are empty
	 */

	@Test
	public void testUnion() {
		// what happens if the second set is null
		assertThrows(NullPointerException.class, ()-> {
			IntSet resultingSet;
			resultingSet = set.union(null); //second set null test
		});
		// the method actually returns the correct union of two sets?
		IntSet secondSet = new IntSet(3);
		IntSet resultingSet;
		set.add(1);
		set.add(-1);
		set.add(0);
		secondSet.add(2);
		secondSet.add(-3);
		int [] array = {-3,-1,0,1,2};
		resultingSet = set.union(secondSet);
		int [] resultingArray = resultingSet.getArray();
		Arrays.sort(resultingArray);
		assertArrayEquals(array, resultingArray);
		// if the first set is empty
		set.clear();
		array = new int[2];
		array[0] = -3;
		array[1] = 2;
		resultingSet = set.union(secondSet);
		resultingArray = resultingSet.getArray();
		Arrays.sort(resultingArray);
		assertArrayEquals(array, resultingArray);
		// if the second set is empty
		set.add(1);
		set.add(-1);
		set.add(0);
		secondSet.clear();
		array = new int[3];
		array[0] = -1;
		array[2] = 1;
		resultingSet = set.union(secondSet);
		resultingArray = resultingSet.getArray();
		Arrays.sort(resultingArray);
		assertArrayEquals(array, resultingArray);
		// if both sets are empty
		set.clear();
		array = new int [0];
		resultingSet = set.union(secondSet);
		resultingArray = resultingSet.getArray();
		assertArrayEquals(array, resultingArray);
	}

	/**
	 * Test the intersect method. Test cases :
	 * - if the second set is null.
	 * - if both sets are empty
	 * - if the intersection method returns the correct intersection of two sets.
	 * - if there are no common elements.
	 * - if one of the sets is empty.
	 */

	@Test
	public void testIntersection() {
		// what happens if the second set is null
		assertThrows(NullPointerException.class, ()-> {
			IntSet resultingSet;
			resultingSet = set.intersection(null); //second set null test
		});
		// if both sets are empty
		IntSet resultingSet;
		IntSet secondSet = new IntSet(5);
		int [] array = {};
		resultingSet = set.intersection(secondSet);
		assertArrayEquals(array, resultingSet.getArray());
		// if it works properly
		set.add(3);
		set.add(14);
		set.add(24);
		secondSet.add(3);
		secondSet.add(2);
		secondSet.add(-3);
		secondSet.add(13);
		secondSet.add(-23);
		int [] intersection = {3};
		resultingSet = set.intersection(secondSet);
		assertArrayEquals(intersection, resultingSet.getArray());
		// there are no common elements;
		set.clear();
		secondSet.clear();
		set.add(1);
		set.add(-1);
		secondSet.add(3);
		secondSet.add(4);
		secondSet.add(9);
		intersection = new int [0];
		resultingSet = set.intersection(secondSet);
		assertArrayEquals(intersection, resultingSet.getArray());
		//if one of the sets is empty
		set.clear();
		intersection = new int[0];
		resultingSet = set.intersection(secondSet);
		assertArrayEquals(intersection, resultingSet.getArray());
	}

	/**
	 * Test the difference method. Test cases :
	 * - if the second set is null.
	 * - if there are no differences (A\B = empty set).
	 * - if the difference method returns the correct difference of the two sets
	 * - if the first set is empty
	 * - if the second is empty
	 */

	@Test
	public void testDifference(){
		//if the second set is null
		assertThrows(NullPointerException.class, ()-> {
			IntSet resultingSet;
			resultingSet = set.difference(null); //second set null test
		});
		IntSet resultingSet;
		IntSet secondSet = new IntSet(5);
		// if there are no differences (A\B = empty set).
		set.add(1);
		set.add(2);
		secondSet.add(1);
		secondSet.add(2);
		int [] array = {};
		resultingSet = set.difference(secondSet);
		assertArrayEquals(array, resultingSet.getArray());
		// if the difference method returns the correct difference of the two sets
		set.clear();
		secondSet.clear();
		set.add(1);
		set.add(2);
		set.add(3);
		secondSet.add(1);
		secondSet.add(4);
		secondSet.add(5);
		array = new int[2];
		array[0] = 2;
		array[1] = 3;
		resultingSet = set.difference(secondSet);
		assertArrayEquals(array, resultingSet.getArray());
		//if the first set is empty
		set.clear();
		array = new int [0];
		resultingSet = set.difference(secondSet);
		assertArrayEquals(array, resultingSet.getArray());
		// if the second set is empty
		secondSet.clear();
		set.add(1);
		set.add(2);
		set.add(3);
		array = new int[3];
		array[0] = 1;
		array[2] = 3;
		array[1] = 2;
		resultingSet = set.difference(secondSet);
		assertArrayEquals(array, resultingSet.getArray());
	}

	/**
	 * Test the symmetricDifference method. Test cases :
	 * - if the second set is null.
	 * - if the result of symmetricDifference is an empty set
	 * - if the symmetricDifference method returns the correct symmetricDifference of the two sets
	 * - if one of the sets is empty
	 */

	@Test
	public void testSymmetricDifference() {
		// if the second set is null
		assertThrows(NullPointerException.class, ()-> {
			IntSet resultingSet;
			resultingSet = set.symmetricDifference(null); //second set null test
		});
		// if the result of symmetricDifference is an empty set
		IntSet resultingSet;
		IntSet secondSet = new IntSet(5);
		set.add(1);
		set.add(2);
		secondSet.add(1);
		secondSet.add(2);
		int [] array = {};
		resultingSet = set.symmetricDifference(secondSet);
		assertArrayEquals(array, resultingSet.getArray());
		set.clear();
		secondSet.clear();
		// if the symmetricDifference method returns the correct symmetricDifference of the two sets
		set.add(1);
		set.add(2);
		set.add(3);
		secondSet.add(4);
		secondSet.add(5);
		secondSet.add(6);
		int [] difference = {1,2,3,4,5,6};
		resultingSet = set.symmetricDifference(secondSet);
		assertArrayEquals(difference, resultingSet.getArray());
		// if one of the sets is empty
		secondSet.clear();
		difference = new int[3];
		difference[0] = 1;
		difference[1] = 2;
		difference[2] = 3;
		resultingSet = set.symmetricDifference(secondSet);
		assertArrayEquals(difference, resultingSet.getArray());
	}

	/**
	 * Test the toString method. Test cases:
	 * -if the set is empty the method should return "{}".
	 * -if the set contains 1 element (x) the method should return "{x}".
	 * -if the set contains 2 or more elements (x,y,z) the method should return "{x, y, z}".
	 * - if the String representation is corresponding to the actual set.
	 */

	@Test
	public void testToString() {
		String str = set.toString();
		// Empty set
		assertEquals("{}",str);
		// One element set
		set.add(1);
		str = set.toString();
		assertEquals("{1}",str);
		// Two or more elements set
		set.add(2);
		set.add(3);
		str = set.toString();
		assertEquals("{1, 2, 3}",str);
		set.clear();
	}
}
