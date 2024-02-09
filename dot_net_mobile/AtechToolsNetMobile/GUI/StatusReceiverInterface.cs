using System;

using System.Collections.Generic;
using System.Text;

namespace AtechToolsNetMobile.GUI
{
    public interface StatusReceiverInterface
    {

        void SetStatusInProcent(int procent);

        void SetStatusMessage(string stat_msg);

        void SetTitle(string title);

    }
}
