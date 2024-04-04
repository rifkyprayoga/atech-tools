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
 * Iterator interface.
 */
public interface Iterator
{
    /**
     * Tests if there are items not yet iterated over.
     * @return true if there are more items in the collection.
     */
    boolean hasNext();

    /**
     * Obtains the next (as yet unseen) item in the collection.
     * @return the next (as yet unseen) item in the collection.
     */
    Object next();

    /**
     * Remove the last item returned by next.
     * Can only be called once after next.
     */
    void remove();
}
