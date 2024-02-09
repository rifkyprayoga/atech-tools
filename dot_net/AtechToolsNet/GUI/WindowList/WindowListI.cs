using System;
using System.Collections.Generic;
using System.Text;

namespace ATechTools.GUI.WindowList
{
    public interface WindowListI
    {

        void CreateWLItem();

        void DisposeWLItem();

        void ForceDisposeWL();

        void FocusWLForm();

        void BringToFront();

        string WindowsListBaseId
        {
            get;
        }

        string WindowsListId
        {
            get;
            set;
        }

        string WindowsListDescription
        {
            get;
        }





    }
}
