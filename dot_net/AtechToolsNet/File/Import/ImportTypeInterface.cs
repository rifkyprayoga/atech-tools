using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using ATechTools.Db;
using ATechTools.GUI;

namespace ATechTools.File.Import
{
    public interface ImportTypeInterface
    {

        string ImportFileExtension { get; }

        string ExportFileExtension { get; }

        string ImportFile(FileInfo fi, StatusReceiverInterface sri, DataLayerAbstract dl);

        void ExportData(object data, StatusReceiverInterface sri);

        string DelimiterFile { get;  }


    }
}
