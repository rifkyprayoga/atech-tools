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

//namespace Microsoft.Samples.Barcode.SymbolScanner

namespace ATechToolsMobile.BarcodeManager.Symbol

{
  /// <summary>
  /// This is the barcode scanner class factory for Symbol devices.
  /// </summary>
  public class SymbolBarcodeScannerFactory : BarcodeScannerFactory 
  {
    public override BarcodeScanner GetBarcodeScanner() 
    {
      return new SymbolBarcodeScanner();
    }
  }
}
