using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using AtechTools.Core.GUI.Selector;

namespace AtechToolsNet.GUI.Selector
{
    public interface SelectorPanel
    {

        Panel MainPanelObject { get; }

        void ResizeMainPanel(int width);

    }
}
