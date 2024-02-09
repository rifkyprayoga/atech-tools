using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATechTools.GUI.WindowList
{
    public interface WindowListClientI
    {
        bool IsDisposed { get; }

        bool Focus();

        void BringToFront();

        void RefreshList();

    }
}
