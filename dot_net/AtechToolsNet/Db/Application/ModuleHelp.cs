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
        private string helpURLBase;
        private string helpUNCBase;

        private string id;
        private string parentID;
        private string name;

        public string Name
        {
            get { return name; }
            set { name = value; }
        }
        private string description;

        public string Description
        {
            get { return description; }
            set { description = value; }
        }


        public string ParentID
        {
            get { return parentID; }
            set { parentID = value; }
        }
        private string helpFileName;
        private string languageID;
        private List<ModuleHelp> children;
        private object treeNode;
        private ModuleInstance moduleInstance;


        public string Id
        {
            get { return id; }
            set { id = value; }
        }


        public void AddChild(ModuleHelp hp)
        {
            if (this.children == null)
                this.children = new List<ModuleHelp>();

            this.children.Add(hp);
        }


        public override string ToString()
        {
            if (this.moduleInstance != null)
                return this.moduleInstance.ToString();
            else
                return this.name;
        }



        public ModuleInstance ModuleInstance
        {
            get { return moduleInstance; }
            set { moduleInstance = value; }
        }

        public object TreeNode
        {
            get { return treeNode; }
            set { treeNode = value; }
        }

        public List<ModuleHelp> Children
        {
            get { return children; }
            set { children = value; }
        }


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
            //return sb.ToString();
        }



        public ModuleHelp Clone()
        {
            ModuleHelp mh = new ModuleHelp();
            mh.description = this.description;
            mh.helpFileName = this.helpFileName;
            mh.helpUNCBase = this.helpUNCBase;
            mh.helpURLBase = this.helpURLBase;
            mh.id = this.id;
            mh.languageID = this.languageID;
            mh.moduleID = this.moduleID;
            mh.moduleInstance = this.moduleInstance;
            mh.moduleName = this.moduleName;
            mh.name = this.name;
            mh.parentID = this.parentID;

            return mh;
                    
            //throw new NotImplementedException();
        }
    }
}
