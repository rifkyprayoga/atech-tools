using System;
using System.Collections.Generic;
using System.Text;

namespace AtechToolsNet.GUI.Selector
{
    public interface SelectorInterface
    {

        string ObjectDescription
        { get;  }


        /*
        int GetColumnWidth(int column_index);

        string GetColumnHeader(int column_index);
        */
        string GetColumnValue(int column_index);

        /*
        int ColumnCount
        {
            get;
        }*/

    }
}
