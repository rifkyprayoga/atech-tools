using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace ATechTools.Db.Application
{
    public class ModuleHelp
    {
        private string moduleID;
        private string moduleName;
        private string helpFileName;
        private string helpURLBase;
        private string helpUNCBase;
        private string languageID;


        public string ModuleName
        {
            get { return moduleName; }
            set { moduleName = value; }
        }

        public string LanguageID
        {
            get { return languageID; }
            set { languageID = value; }
        }

        public string HelpFileName
        {
            get { return helpFileName; }
            set { helpFileName = value; }
        }


        public string HelpURLBase
        {
            get { return helpURLBase; }
            set { helpURLBase = value; }
        }

        public string HelpUNCBase
        {
            get { return helpUNCBase; }
            set { helpUNCBase = value; }
        }

        public string ModuleID
        {
            get { return moduleID; }
            set { moduleID = value; }
        }


        public string GetFullUrl()
        {
            StringBuilder sb = new StringBuilder();

            sb.Append(this.helpURLBase);

            if (this.HelpURLBase.Substring(this.HelpURLBase.Length - 1, 1) != @"/")
            {
                sb.Append(@"/");
            }

            if (this.languageID.Length != 0)
                sb.Append(this.languageID + @"/");

            sb.Append(this.helpFileName);

            return sb.ToString();
        }


        public string GetFullUNCPath()
        {
            StringBuilder sb = new StringBuilder();

            sb.Append(this.helpUNCBase);

            if (this.helpUNCBase.Substring(this.helpUNCBase.Length - 1, 1) != @"\")
            {
                sb.Append(@"\");
            }

            if (this.languageID.Length != 0)
                sb.Append(this.languageID + @"\");

            return Path.GetFullPath(sb.ToString());
            return sb.ToString();
        }


    }
}
