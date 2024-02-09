using System;
using System.Collections.Generic;
using System.Text;

namespace AtechToolsNetMobile.GUI.Config
{
    public interface ConfigurableGUIInterface
    {

        string ModuleName
        { 
            get;
        }

        string LoadModuleConfig();

    }
}
