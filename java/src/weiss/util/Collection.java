package weiss.util;

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
 * Collection interface; the root of all 1.2 collections.
 */
public interface Collection extends java.io.Serializable
{
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    int size();

    /**
     * Tests if this collection is empty.
     * @return true if the size of this collection is zero.
     */
    boolean isEmpty();

    /**
     * Tests if some item is in this collection.
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    boolean contains(Object x);

    /**
     * Adds an item to this collection.
     * @param x any object.
     * @return true if this item was added to the collection.
     */
    boolean add(Object x);

    /**
     * Removes an item from this collection.
     * @param x any object.
     * @return true if this item was removed from the collection.
     */
    boolean remove(Object x);

    /**
     * Change the size of this collection to zero.
     */
    void clear();

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    Iterator iterator();

    /**
     * Obtains a primitive array view of the collection.
     * @return the primitive array view.
     */
    Object[] toArray();
}
