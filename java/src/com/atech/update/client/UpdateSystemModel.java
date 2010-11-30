package com.atech.update.client;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.table.AbstractTableModel;

import com.atech.i18n.I18nControlAbstract;
import com.atech.update.config.ComponentEntry;
import com.atech.update.config.ComponentGroup;
import com.atech.update.config.ComponentInterface;
import com.atech.update.config.UpdateConfiguration;
import com.atech.utils.ATDataAccessAbstract;

/**
 *  This file is part of ATech Tools library.
 *  
 *  UpdateSystemModel - model for UpdateSystem dialog
 *  Copyright (C) 2008  Andy (Aleksander) Rozman (Atech-Software)
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
 *  @author Andy {andy@atech-software.com}
 *
*/


public class UpdateSystemModel extends AbstractTableModel
{

    private static final long serialVersionUID = -5137364088682476453L;
    ArrayList<ComponentInterface> data;
    ATDataAccessAbstract m_da = null;
    
    
/*
    public UpdateSystemModel(ArrayList<UpdateObject> data)
    {
        this.data = data;

    }
*/

    
    /**
     * Constructor
     * 
     * @param uconf 
     * 
     * @param data
     * @param da
     */
    public UpdateSystemModel(UpdateConfiguration uconf /*     ArrayList<ComponentInterface> data*/, ATDataAccessAbstract da )
    {
        this.data = uconf.getUpdateTable();
        this.m_da = da;
        
        fixColumnNames();
    }

    private void fixColumnNames()
    {
        I18nControlAbstract ic = m_da.getI18nControlInstance();
        
        if (ic==null)
            return;
        

        for(int i=0; i<column_names.length; i++)
        {
            column_names[i] = ic.getMessage(column_names[i]);
        }
        
        //String column_names[] = { "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "COMMENT" };
        
        
    }
    
    
    
/*    
    public UpdateSystemModel(UpdateConfiguration uconf)
    {
        //	this.update_system = usys;
    }
  */  
    

    //String column_names[] = { "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "UPDATE", "COMMENT" };
    
    String column_names[] = { "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "COMMENT" };
    float column_widths[] = { 0.35f, 0.15f, 0.15f, 0.15f, 0.2f };

    /**
     *  Returns a default name for the column using spreadsheet conventions:
     *  A, B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
     *  returns an empty string.
     *
     * @param column  the column being queried
     * @return a string containing the default name of <code>column</code>
     */
    public String getColumnName(int column)
    {
        return this.column_names[column];

    }
    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount()
    {
        return this.column_names.length;
    }
    
    /**
     * Get Column Width
     * 
     * @param index index of column
     * @param width width of table
     * 
     * @return
     */
    public int getColumnWidth(int index, int width)
    {
        return (int)(column_widths[index] * width);
    }
    
    
    
    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount()
    {
        return this.data.size();
    }
    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (columnIndex==-1)
        {
            return this.data.get(rowIndex);
        }
        else
            return this.data.get(rowIndex).getColumnValue(columnIndex);
    }
    
    
    
    /**
     * Update Model
     * 
     * @param uc_new
     */
    public void updateModel(UpdateConfiguration uc_new)
    {
        //System.out.println("Update Model.");
        
        // reset settings
        for(Enumeration<ComponentGroup> en = uc_new.groups.elements(); en.hasMoreElements(); )
        {
            
            ComponentGroup cg = en.nextElement();  
            
            for(int j=0; j<cg.children.size(); j++)
            {
                ComponentEntry ce = cg.children.get(j);
                ce.copyToServerSettings();
            }
        }
        
        // update current records
        for(int i=0; i<this.data.size(); i++)
        {
            if (this.data.get(i) instanceof ComponentEntry)
            {
                ComponentEntry ce = (ComponentEntry)this.data.get(i);
                
                if (uc_new.components_ht.containsKey(ce.name))
                {
                    uc_new.components_ht.get(ce.name).setVersionSettings(ce);
                    //ce.setVersionSettings(uc_new.components_ht.get(ce.name));
                    //System.out.println("Found");                    
                }
                //else
                //    System.out.println("Not found");
                
            }
        }
        
        this.data.clear();
        this.data.addAll(uc_new.getUpdateTable());
        
    }
    
    
}


/*
{


    // Names of the columns.
    //static protected String[]  cNames = {"Name", "Size", "Type", "Modified"};

    String column_names[] = { "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "UPDATE", "COMMENT" };

//    String column_names_types[] = { TreeTableModel.class, "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "UPDATE", "COMMENT" };


    // Types of the columns.
//    static protected Class[]  cTypes = {TreeTableModel.class, String.class, String.class, String.class};

    // The the returned file length for directories. 
//    public static final Integer ZERO = new Integer(0); 

    /*
    public UpdateSystemModel() { 
        super(new FileNode(new File(File.separator))); 
    }
    */
/*
    public UpdateSystemModel(UpdateObject uo) 
    { 
        super(uo); 
    }


    //
    // Some convenience methods. 
    //
/*
    protected File getFile(Object node) 
    {
        FileNode fileNode = ((FileNode)node); 
        return fileNode.getFile();       
    }
*/
  /*  protected UpdateObject getObject(Object node) 
    {
        UpdateObject fileNode = ((UpdateObject)node); 
        return fileNode;       
    }


    protected Object[] getChildren(Object node) 
    {
        UpdateObject fileNode = ((UpdateObject)node); 
        return fileNode.getChildren(); 
    }
*/
    //
    // The TreeModel interface
    //
/*
    public int getChildCount(Object node) 
    { 
        Object[] children = getChildren(node); 
        return(children == null) ? 0 : children.length;
    }

    public Object getChild(Object node, int i) 
    { 
        return getChildren(node)[i]; 
    }

    // The superclass's implementation would work, but this is more efficient. 
    public boolean isLeaf(Object node) 
    { 
        return getObject(node).isLeaf();
    }

    //
    //  The TreeTableNode interface. 
    //

    public int getColumnCount() 
    {
        return this.column_names.length;
        //return cNames.length;
    }

    public String getColumnName(int column) 
    {
        return this.column_names[column];
        //return cNames[column];
    }

    public Class getColumnClass(int column) 
    {
        if (column==0)
            return TreeTableModel.class;
        else
            return String.class;
        //return cTypes[column];
    }

    


    public Object getValueAt(Object node, int column) {
        //File file = getFile(node); 
        UpdateObject uo = getObject(node);

        if (column==-1)
        {
            return uo;
        }
        else
            return uo.getColumnValue(column);

        /*
        try
        {
            switch (column)
            {
                case 0:
                    return file.getName();
                case 1:
                    return file.isFile() ? new Integer((int)file.length()) : ZERO;
                case 2:
                    return file.isFile() ?  "File" : "Directory";
                case 3:
                    return new Date(file.lastModified());
            }
        }
        catch (SecurityException se)
        {
        }*/

        //return null; 
    






/*

    // Names of the columns.
    static protected String[]  cNames = {"Name", "Size", "Type", "Modified"};

    // Types of the columns.
    //static protected Class[]  cTypes = {TreeTableModel.class, Integer.class, String.class, Date.class};

    // The the returned file length for directories. 
    public static final Integer ZERO = new Integer(0); 

    //UpdateObject root = new UpdateObject();

    public UpdateSystemModel(UpdateObject root) 
    { 
        super(root);
        //this.root = root;
        //super(new FileNode(new File(File.separator))); 
    }

    //
    // Some convenience methods. 
    //
/*
    protected File getFile(Object node) {
        FileNode fileNode = ((FileNode)node); 
        return fileNode.getFile();       
    }

    protected Object[] getChildren(Object node) {
        FileNode fileNode = ((FileNode)node); 
        return fileNode.getChildren(); 
    }
*/
//
// The TreeModel interface
//

/**
 * Returns the child of <code>parent</code> at index <code>index</code>
 * in the parent's
 * child array.  <code>parent</code> must be a node previously obtained
 * from this data source. This should not return <code>null</code>
 * if <code>index</code>
 * is a valid index for <code>parent</code> (that is <code>index >= 0 &&
 * index < getChildCount(parent</code>)).
 *
 * @param   parent  a node in the tree, obtained from this data source
 * @return  the child of <code>parent</code> at index <code>index</code>
 */
/*  public Object getChild(Object parent, int index)
  {
      UpdateObject uo = (UpdateObject)parent;
      return uo.children.get(index);
  }
  /**
   * Returns the number of children of <code>parent</code>.
   * Returns 0 if the node
   * is a leaf or if it has no children.  <code>parent</code> must be a node
   * previously obtained from this data source.
   *
   * @param   parent  a node in the tree, obtained from this data source
   * @return  the number of children of the node <code>parent</code>
   */
/*    public int getChildCount(Object parent)
    {
        UpdateObject uo = (UpdateObject)parent;

        System.out.println(uo.name + " child count: " + uo.children.size());

        return uo.children.size();
    }

    // The superclass's implementation would work, but this is more efficient. 
    public boolean isLeaf(Object node) 
    { 
        UpdateObject uo = (UpdateObject)node;

        System.out.println(uo.name + " leaf: " + (uo.type==2));

        return (uo.type==2);
    }

    //
    //  The TreeTableNode interface. 
    //


    String column_names[] = { "NAME", "CURRENT_VERSION", "SERVER_VERSION", "UPDATEABLE", "COMMENT"
    };

    //UpdateObject ud_root = (UpdateObject)root;

    /**
     * Returns the value to be displayed for node <code>node</code>,
     * at column number <code>column</code>.
     */
/*public Object getValueAt(Object node, int column)
{
    UpdateObject uo = (UpdateObject)node;

    if ((uo.type==0) || (uo.type==1))
    {
        if ((column==0) || (column==3))
        {
            return uo.getColumnValue(column);
        }
        else
            return "";
    }
    else //if (uo.type==2)
    {
        return uo.getColumnValue(column);
    }

    //return uo.children(index);
}
/**
 * Returns the number ofs availible column.
 */
/*    public int getColumnCount()
    {
        return column_names.length;
    }
    /**
     * Returns the name for column number <code>column</code>.
     */
/*    public String getColumnName(int column)
    {
        return column_names[column];
    }


    public Class getColumnClass(int column) 
    {
        return String.class; //cTypes[column];
    }

} */



