// -----------------------------------------------------------------------
// <copyright file="DatabaseObjectChanged.cs" company="Atech Software">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

namespace AtechTools.Core.Db
{

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface DatabaseObjectChanged
    {
        [Browsable(false)]
        bool Changed
        { get; set; }

        [Browsable(false)]
        bool Deleted
        { get; set; }

        [Browsable(false)]
        bool IsNew
        { get; set; }

    }
}
