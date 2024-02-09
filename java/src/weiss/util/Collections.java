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
 * Instanceless class contains static methods that operate on collections.
 */
public class Collections
{
    private Collections()
    {
    }

    /**
     * Returns the maximum object in the collection, using default ordering
     * @param coll the collection.
     * @return the maximum object.
     * @throws NoSuchElementException if coll is empty.
     * @throws ClassCastException if objects in collection cannot be compared.
     */
    public static Object max(Collection coll)
    {
        return max(coll, DEFAULT_COMPARATOR);
    }

    /**
     * Returns the maximum object in the collection, using comparator.
     * @param coll the collection.
     * @param cmp the comparator.
     * @return the maximum object.
     * @throws NoSuchElementException if coll is empty.
     * @throws ClassCastException if objects in collection cannot be compared.
     */
    public static Object max(Collection coll, Comparator cmp)
    {
        if (coll.size() == 0)
            throw new NoSuchElementException();

        Iterator itr = coll.iterator();
        Object maxValue = itr.next();

        while (itr.hasNext())
        {
            Object current = itr.next();
            if (cmp.compare(current, maxValue) > 0)
            {
                maxValue = current;
            }
        }

        return maxValue;
    }

    /**
     * Returns a comparator that imposes the reverse of the
     * default ordering on a collection of objects that
     * implement the Comparable interface.
     * @return the comparator.
     */
    public static Comparator reverseOrder()
    {
        return new ReverseComparator();
    }

    private static class ReverseComparator implements Comparator
    {
        private static final long serialVersionUID = 8198486880862264438L;

        @SuppressWarnings("unchecked")
        public int compare(Object lhs, Object rhs)
        {
            return -((Comparable) lhs).compareTo(rhs);
        }
    }

    static class DefaultComparator implements Comparator
    {
        /**
         * 
         */
        private static final long serialVersionUID = 4349074562339521686L;

        @SuppressWarnings("unchecked")
        public int compare(Object lhs, Object rhs)
        {
            return ((Comparable) lhs).compareTo(rhs);
        }
    }

    static final Comparator DEFAULT_COMPARATOR = new DefaultComparator();
}
