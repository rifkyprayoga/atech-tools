

using System;

using System.Collections.Generic;
using System.Text;

namespace AtechToolsNetMobile.Thread
{

      public class ProgressEventArgs : EventArgs
      {

          private int _maxValue = 100;

          private int _value = 0;

            private string _text = string.Empty;

       


      public int MaxValue
      {
            get { return _maxValue; }
      }

      public int Value

      {

      get { return _value; }

      }

      public string Text

      {

      get { return _text; }

      }

      public ProgressEventArgs(string text, int value, int maxValue)

      {

      _text = text;

      _value = value;

      _maxValue = maxValue;

      }

          public override string ToString()
          {
              return _text;
          }

      }
}
