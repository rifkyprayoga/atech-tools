package com.atech.utils.data;

import java.util.ArrayList;

/**
 * Created by andy on 06.04.15.
 */
public class SynchronizedList<E> extends ArrayList<E>
{

    public void addSynchronized(E listEntry)
    {
        this.workWithList(SynchronizedListOperation.Add, listEntry);
    }


    public boolean isNotEmpty()
    {
        return (Boolean) workWithList(SynchronizedListOperation.CheckIfNotEmpty, null);
    }


    public E getFirst()
    {
        return (E) workWithList(SynchronizedListOperation.GetFirst, null);
    }


    private synchronized Object workWithList(SynchronizedListOperation operation, E object)
    {
        switch (operation)
        {
            case Add:
                this.add(object);

            case CheckIfNotEmpty:
                return this.size() > 0;

            case GetFirst:
                {
                    E entry = this.get(0);
                    this.remove(0);

                    return entry;
                }
        }

        return null;
    }

}
