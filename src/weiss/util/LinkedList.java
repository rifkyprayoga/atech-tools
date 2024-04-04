package weiss.util;

/**
 * This file is part of ATech Tools library.
 * 
 * This is imported library Weiss Util. This is just for usage with HttpClient
 * (See longer/original comment under this one)
 * 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * 
 * For additional information about this project please visit our project site
 * on http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 * 
 */

/*
 * LinkedList class implements a doubly-linked list.
 */
public class LinkedList extends AbstractCollection implements List
{
    /**
     * 
     */
    private static final long serialVersionUID = 4215074063593933883L;

    /**
     * Construct an empty LinkedList.
     */
    public LinkedList()
    {
        clear();
    }

    /**
     * Construct a LinkedList with same items as another Collection.
     * @param other 
     */
    public LinkedList(Collection other)
    {
        clear();
        Iterator itr = other.iterator();
        while (itr.hasNext())
        {
            add(itr.next());
        }
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size()
    {
        return theSize;
    }

    /**
     * Tests if some item is in this collection.
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    @Override
    public boolean contains(Object x)
    {
        return findPos(x) != NOT_FOUND;
    }

    /**
     * Returns the position of first item matching x in this collection,
     * or NOT_FOUND if not found.
     * @param x any object.
     * @return the position of first item matching x in this collection,
     * or NOT_FOUND if not found.
     */
    private Node findPos(Object x)
    {
        for (Node p = beginMarker.next; p != endMarker; p = p.next)
            if (x == null)
            {
                if (p.data == null)
                    return p;
            }
            else if (x.equals(p.data))
                return p;

        return NOT_FOUND;
    }

    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add(Object x)
    {
        addLast(x);
        return true;
    }

    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add(int idx, Object x)
    {
        Node p = getNode(idx);
        Node newNode = new Node(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    /**
     * Adds an item to this collection, at front.
     * Other items are slid one position higher.
     * @param x any object.
     */
    public void addFirst(Object x)
    {
        add(0, x);
    }

    /**
     * Adds an item to this collection, at end.
     * @param x any object.
     */
    public void addLast(Object x)
    {
        add(size(), x);
    }

    /**
     * Returns the first item in the list.
     * @return 
     * @throws NoSuchElementException if the list is empty.
     */
    public Object getFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        return getNode(0).data;
    }

    /**
     * Returns the last item in the list.
     * @return 
     * @throws NoSuchElementException if the list is empty.
     */
    public Object getLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        return getNode(size() - 1).data;
    }

    /**
     * Removes the first item in the list.
     * @return the item was removed from the collection.
     * @throws NoSuchElementException if the list is empty.
     */
    public Object removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        return remove(getNode(0));
    }

    /**
     * Removes the last item in the list.
     * @return the item was removed from the collection.
     * @throws NoSuchElementException if the list is empty.
     */
    public Object removeLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        return remove(getNode(size() - 1));
    }

    /**
     * Removes an item from this collection.
     * @param x any object.
     * @return true if this item was removed from the collection.
     */
    @Override
    public boolean remove(Object x)
    {
        Node pos = findPos(x);

        if (pos == NOT_FOUND)
            return false;
        else
        {
            remove(pos);
            return true;
        }
    }

    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public Object get(int idx)
    {
        return getNode(idx).data;
    }

    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public Object set(int idx, Object newVal)
    {
        Node p = getNode(idx);
        Object oldVal = p.data;

        p.data = newVal;
        return oldVal;
    }

    /**
     * Gets the Node at position idx, which must range from 0 to size( ).
     * @param idx index to search at.
     * @return internal node corrsponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    private Node getNode(int idx)
    {
        Node p;

        if (idx < 0 || idx > size())
            throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());

        if (idx < size() / 2)
        {
            p = beginMarker.next;
            for (int i = 0; i < idx; i++)
            {
                p = p.next;
            }
        }
        else
        {
            p = endMarker;
            for (int i = size(); i > idx; i--)
            {
                p = p.prev;
            }
        }

        return p;
    }

    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public Object remove(int idx)
    {
        return remove(getNode(idx));
    }

    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private Object remove(Node p)
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;

        return p.data;
    }

    /**
     * Change the size of this collection to zero.
     */
    @Override
    public void clear()
    {
        beginMarker = new Node("BEGINMARKER", null, null);
        endMarker = new Node("ENDMARKER", beginMarker, null);
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public Iterator iterator()
    {
        return new LinkedListIterator(0);
    }

    /**
     * Obtains a ListIterator object used to traverse the collection bidirectionally.
     * @return an iterator positioned prior to the requested element.
     * @param idx the index to start the iterator. Use size() to do complete
     * reverse traversal. Use 0 to do complete forward traversal.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public ListIterator listIterator(int idx)
    {
        return new LinkedListIterator(idx);
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the LinkedList.
     */
    private class LinkedListIterator implements ListIterator
    {
        private Node current;
        private Node lastVisited = null;
        private boolean lastMoveWasPrev = false;
        private int expectedModCount = modCount;

        public LinkedListIterator(int idx)
        {
            current = getNode(idx);
        }

        public boolean hasNext()
        {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
            return current != endMarker;
        }

        public Object next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            Object nextItem = current.data;
            lastVisited = current;
            current = current.next;
            lastMoveWasPrev = false;
            return nextItem;
        }

        public void remove()
        {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if (lastVisited == null)
                throw new IllegalStateException();

            LinkedList.this.remove(lastVisited);
            lastVisited = null;
            if (lastMoveWasPrev)
            {
                current = current.next;
            }
            expectedModCount++;
        }

        public boolean hasPrevious()
        {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
            return current != beginMarker.next;
        }

        public Object previous()
        {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if (!hasPrevious())
                throw new NoSuchElementException();

            current = current.prev;
            lastVisited = current;
            lastMoveWasPrev = true;
            return current.data;
        }
    }

    /**
     * This is the doubly-linked list node.
     */
    private static class Node
    {
        public Node(Object d, Node p, Node n)
        {
            data = d;
            prev = p;
            next = n;
        }

        public Object data;
        public Node prev;
        public Node next;
    }

    private static final Node NOT_FOUND = null;

    private int theSize;
    private Node beginMarker;
    private Node endMarker;
    private int modCount = 0;
}
