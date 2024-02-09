using System;
using System.Collections.Generic;
using System.Text;

namespace ATechTools.Xml
{
    public class XmlTextWriterFormattedNoDeclaration : System.Xml.XmlTextWriter
    {
        public XmlTextWriterFormattedNoDeclaration(System.IO.TextWriter w)
            : base(w)
        {
            Formatting = System.Xml.Formatting.Indented;
        }

        public override void WriteStartDocument() { } // suppress
    }
}
