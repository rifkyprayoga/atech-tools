package weiss.util;

import java.io.Serializable;

/**
 *  This file is part of ATech Tools library.
 *  
 *  This is imported library Weiss Util. This is just for usage with HttpClient 
 *  (See longer/original comment under this one) 
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *
 */


/**
 * HashSet implementation.
 * Matches are based on equals; and hashCode must be consistently defined.
 */
public class HashSet extends AbstractCollection implements Set
{
    /**
     * 
     */
    private static final long serialVersionUID = -4020132014858638730L;
    /**
     * Construct an empty HashSet.
     */
    public HashSet( )
    {
        allocateArray( DEFAULT_TABLE_SIZE );
        clear( );
    }
    
    /**
     * Construct a HashSet from any collection.
     * @param other 
     */
    public HashSet( Collection other )
    {
        allocateArray( other.size( ) * 2 );
        clear( );
        
        Iterator itr = other.iterator( );
        while( itr.hasNext( ) )
            add( itr.next( ) );    
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return currentSize;
    }
    
    
    /**
     * This method is not part of standard Java 1.2.
     * Like contains, it checks if x is in the set.
     * If it is, it returns the reference to the matching
     * object; otherwise it returns null.
     * @param x the object to search for.
     * @return if contains(x) is false, the return value is null;
     * otherwise, the return value is the object that causes
     * contains(x) to return true.
     */
    public Object getMatch( Object x )
    {
        int currentPos = findPos( x );
        if( array[ currentPos ] == null )
            return null;
        return array[ currentPos ].element;
    }
    
    /**
     * Tests if some item is in this collection.
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    public boolean contains( Object x )
    {
        return isActive( array, findPos( x ) );
    }
    
    /**
     * Tests if item in pos is active.
     * @param pos a position in the hash table.
     * @param arr the HashEntry array (can be oldArray during rehash).
     * @return true if this position is active.
     */
    private static boolean isActive( HashEntry [ ] arr, int pos )
    {
        return arr[ pos ] != null && arr[ pos ].isActive;
    }
    
    /**
     * Adds an item to this collection.
     * @param x any object.
     * @return true if this item was added to the collection.
     */
    public boolean add( Object x )
    {
        int currentPos = findPos( x );
        if( isActive( array, currentPos ) )
            return false;
        
        array[ currentPos ] = new HashEntry( x, true );
        currentSize++;
        occupied++;
        modCount++;
        
        if( occupied > array.length / 2 )        
            rehash( );
                
        return true;                   
    }
    
    /**
     * Private routine to perform rehashing.
     * Can be called by both add and remove.
     */
    private void rehash( )
    {
        HashEntry [ ] oldArray = array;
        
            // Create a new, empty table
        allocateArray( nextPrime( 4 * size( ) ) );
        currentSize = 0;
        occupied = 0;
        
            // Copy table over
        for( int i = 0; i < oldArray.length; i++ )
            if( isActive( oldArray, i ) ) 
                add( oldArray[ i ].element );
    }
    
    /**
     * Removes an item from this collection.
     * @param x any object.
     * @return true if this item was removed from the collection.
     */
    public boolean remove( Object x )
    {
        int currentPos = findPos( x );
        if( !isActive( array, currentPos ) )
            return false;
        
        array[ currentPos ].isActive = false;
        currentSize--;
        modCount++;    
        
        if( currentSize < array.length / 8 )
            rehash( );
    
        return true;
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void clear( )
    {
        currentSize = occupied = 0;
        modCount++;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }
    
    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public Iterator iterator( )
    {
        return new HashSetIterator( );
    }
    
    /**
     * This is the implementation of the HashSetIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the HashSet.
     */
    private class HashSetIterator implements Iterator
    {
        private int expectedModCount = modCount;
        private int currentPos = -1;
        private int visited = 0;       
        
        public boolean hasNext( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );
            
            return visited != size( );    
        }
        
        public Object next( )
        {
            if( !hasNext( ) )
                throw new NoSuchElementException( );
                          
            do
            {
                currentPos++;
            } while( currentPos < array.length && !isActive( array, currentPos ) );
                            
            visited++;
            return array[ currentPos ].element;    
        }
        
        public void remove( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );              
            if( currentPos == -1 || !isActive( array, currentPos ) )
                throw new IllegalStateException( );
    
            array[ currentPos ].isActive = false;
            currentSize--;
            visited--;
            modCount++;
            expectedModCount++;
        }
    }
    
    private static class HashEntry implements Serializable
    {
        /**
         * 
         */
        private static final long serialVersionUID = -5769017138327230423L;
        public Object  element;   // the element
        public boolean isActive;  // false if marked deleted

        @SuppressWarnings("unused")
        public HashEntry( Object e )
        {
            this( e, true );
        }

        public HashEntry( Object e, boolean i )
        {
            element  = e;
            isActive = i;
        }
    }
    
    
    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( Object x )
    {
        int collisionNum = 0;
        int currentPos = ( x == null ) ? 0 : Math.abs( x.hashCode( ) % array.length );

        while( array[ currentPos ] != null )
        {
            if( x == null )
            {
                if( array[ currentPos ].element == null )
                    break;
            }
            else if( x.equals( array[ currentPos ].element ) )   
                break; 
            
            currentPos += 2 * ++collisionNum - 1;  // Compute ith probe
            if( currentPos >= array.length )       // Implement the mod
                currentPos -= array.length;
        }

        return currentPos;
    }
    
    
    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray( int arraySize )
    {
        array = new HashEntry[ arraySize ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
    
    private static final int DEFAULT_TABLE_SIZE = 101;
    
    private int currentSize = 0;
    private int occupied = 0;
    private int modCount = 0;
    private HashEntry [ ] array;
}
