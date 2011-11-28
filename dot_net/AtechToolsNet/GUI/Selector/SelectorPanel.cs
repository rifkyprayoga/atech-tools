using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;

namespace AtechToolsNet.GUI.Selector
{
    public interface SelectorPanel
    {

        Panel MainPanelObject { get; }

        void ResizeMainPanel(int width);
        



    }
}
