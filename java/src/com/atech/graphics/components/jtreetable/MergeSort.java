
package com.atech.graphics.components.jtreetable;



/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

/**
 * An implementation of MergeSort, needs to be subclassed to provide a 
 * comparator.
 *
 * @version %I% %G%
 *
 * @author Scott Violet
 */

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy
 *
*/



public abstract class MergeSort extends Object {
    protected Object           toSort[];
    protected Object           swapSpace[];

    public void sort(Object array[]) {
	if(array != null && array.length > 1)
	{
	    int             maxLength;
  
	    maxLength = array.length;
	    swapSpace = new Object[maxLength];
	    toSort = array;
	    this.mergeSort(0, maxLength - 1);
	    swapSpace = null;
	    toSort = null;
	}
    }

    public abstract int compareElementsAt(int beginLoc, int endLoc);

    protected void mergeSort(int begin, int end) {
	if(begin != end)
	{
	    int           mid;

	    mid = (begin + end) / 2;
	    this.mergeSort(begin, mid);
	    this.mergeSort(mid + 1, end);
	    this.merge(begin, mid, end);
	}
    }

    protected void merge(int begin, int middle, int end) {
	int           firstHalf, secondHalf, count;

	firstHalf = count = begin;
	secondHalf = middle + 1;
	while((firstHalf <= middle) && (secondHalf <= end))
	{
	    if(this.compareElementsAt(secondHalf, firstHalf) < 0)
		swapSpace[count++] = toSort[secondHalf++];
	    else
		swapSpace[count++] = toSort[firstHalf++];
	}
	if(firstHalf <= middle)
	{
	    while(firstHalf <= middle)
		swapSpace[count++] = toSort[firstHalf++];
	}
	else
	{
	    while(secondHalf <= end)
		swapSpace[count++] = toSort[secondHalf++];
	}
	for(count = begin;count <= end;count++)
	    toSort[count] = swapSpace[count];
    }
}

