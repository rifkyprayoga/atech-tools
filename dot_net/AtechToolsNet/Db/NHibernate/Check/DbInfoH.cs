
// TODO: Auto-generated Javadoc
/**
 * This file is part of ATech Tools library.
 * 
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
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
 * @author Andy
 * 
 */

/* * @author Hibernate CodeGenerator */

using System;
namespace ATechTools.Db.NHibernate.Check
{



public class DbInfoH //: Serializable
{

    private long _id;
    public virtual long Id { get { return _id; } set { _id = value; } }

    private string _key;
    public virtual string Key
    {
        get { return this._key; }
        set { this._key = value; }
    }

    private string _value;
    public virtual string Value
    {
        get { return this._value; }
        set { this._value = value; }
    }

    private int _type;
    public virtual int Type
    {
        get { return this._type; }
        set { this._type = value; }
    }

    private string _description;
    public virtual string Description
    {
        get { return this._description; }
        set { this._description = value; }
    }


    public override int GetHashCode()
    {
        return this._id.GetHashCode();
    }


    /** 
     * toString
     */
    public override String ToString()
    {
        return "" + GetHashCode();
    }

    public override bool Equals(object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;

        DbInfoH that = obj as DbInfoH;
        if (that == null)
        {
            return false;
        }
        else
        {
            if (this.Key != that.Key) return false;
            return true;
        }
    }


}
}