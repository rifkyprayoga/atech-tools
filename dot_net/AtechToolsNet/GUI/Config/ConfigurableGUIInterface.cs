using System;
using System.Collections.Generic;
using System.Text;

namespace ATechTools.GUI.Config
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
