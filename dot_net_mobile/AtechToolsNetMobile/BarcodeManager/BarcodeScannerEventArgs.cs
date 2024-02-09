//------------------------------------------------------------------------------
/// <copyright from='1997' to='2005' company='Microsoft Corporation'>
///		Copyright (c) Microsoft Corporation. All Rights Reserved.
///
///   This source code is intended only as a supplement to Microsoft
///   Development Tools and/or on-line documentation.  See these other
///   materials for detailed information regarding Microsoft code samples.
/// </copyright>
//------------------------------------------------------------------------------
using System;

// namespace Microsoft.Samples.Barcode
namespace ATechToolsMobile.BarcodeManager
{
	/// <summary>
	/// This is the event arguments for the Barcode Scanner class event BarcodeScan.
	/// </summary>
  public class BarcodeScannerEventArgs : EventArgs
  {
    public BarcodeScannerEventArgs(string data)
    {
      this.data = data;
    } 
    private string data;
    public string Data
    {
      get { return data; }
    }
  }
}
