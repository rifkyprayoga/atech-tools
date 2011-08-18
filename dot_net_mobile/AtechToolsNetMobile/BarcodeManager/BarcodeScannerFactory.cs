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

//namespace Microsoft.Samples.Barcode
namespace ATechToolsMobile.BarcodeManager
{
  /// <summary>
  /// This is the abstract barcode scanner class factory.
  /// </summary>
  public abstract class BarcodeScannerFactory 
  {
    public abstract BarcodeScanner GetBarcodeScanner();
  }
}

