/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.reporting.engine.classic.core.util;

/**
 * A Array-List for integer objects. Ints can be added to the list and will be stored in an int-array.
 * <p/>
 * Using this list for storing ints is much faster than creating java.lang.Integer objects and storing them in an
 * ArrayList.
 * <p/>
 * This list is not synchronized and does not implement the full List interface. In fact, this list can only be used to
 * add new values or to clear the complete list.
 *
 * @author Thomas Morgner
 */
public class FloatList {
  /**
   * An empty array used to avoid object creation.
   */
  private static final float[] EMPTY_ARRAY = new float[0];
  /**
   * The array holding the list data.
   */
  private float[] data;
  /**
   * The size of the list.
   */
  private int size;
  /**
   * The number of free slots added on every resize.
   */
  private int increment;

  /**
   * Creates a new IntList with the given initial capacity. The capacity will also be used as increment value when
   * extending the capacity of the list.
   *
   * @param capacity
   *          the initial capacity.
   */
  public FloatList( final int capacity ) {
    data = new float[capacity];
    increment = capacity;
  }

  /**
   * Ensures, that the list backend can store at least <code>c</code> elements. This method does nothing, if the new
   * capacity is less than the current capacity.
   *
   * @param c
   *          the new capacity of the list.
   */
  private void ensureCapacity( final int c ) {
    if ( data.length <= c ) {
      final float[] newData = new float[Math.max( data.length + increment, c + 1 )];
      System.arraycopy( data, 0, newData, 0, size );
      data = newData;
    }
  }

  /**
   * Adds the given int value to the list.
   *
   * @param value
   *          the new value to be added.
   */
  public void add( final float value ) {
    ensureCapacity( size );
    data[size] = value;
    size += 1;
  }

  /**
   * Returns the value at the given index.
   *
   * @param index
   *          the index
   * @return the value at the given index
   * @throws IndexOutOfBoundsException
   *           if the index is greater or equal to the list size or if the index is negative.
   */
  public float get( final int index ) {
    if ( index >= size || index < 0 ) {
      throw new IndexOutOfBoundsException( String.valueOf( index ) );
    }
    return data[index];
  }

  /**
   * Clears the list.
   */
  public void clear() {
    size = 0;
  }

  /**
   * Returns the number of elements in this list.
   *
   * @return the number of elements in the list
   */
  public int size() {
    return size;
  }

  /**
   * Copys the list contents into a new array.
   *
   * @return the list contents as array.
   */
  public float[] toArray() {
    if ( size == 0 ) {
      return FloatList.EMPTY_ARRAY;
    }

    final float[] retval = new float[size];
    System.arraycopy( data, 0, retval, 0, size );
    return retval;
  }
}
