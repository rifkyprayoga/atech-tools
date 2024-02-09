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
using ATechToolsMobile.BarcodeManager.Symbol;

//namespace Microsoft.Samples.Barcode
namespace ATechToolsMobile.BarcodeManager
{
	/// <summary>
	/// This is the barcode scanner facade class that return a generic barcode scanner object.
	/// </summary>
	public class BarcodeScannerFacade
	{
		/// <summary>
		/// Creates and returns a generic (device independent) barcode scanner object.
		/// </summary>
		/// <returns>Generic barcode scanner object</returns>
		public static BarcodeScanner GetBarcodeScanner()
		{
			BarcodeScannerFactory BarcodeScannerFactory = null;
		  BarcodeScanner BarcodeScanner = null;

			string oemInfo = GetOemInfo().ToUpper();

			// Is this a Symbol device?
			if (oemInfo.IndexOf("SYMBOL") > -1)
				BarcodeScannerFactory = new SymbolBarcodeScannerFactory();

            // Add Additional Factories

			// Is this an Intermec device?
			//if(oemInfo.IndexOf("Intermec") > -1)
			//	BarcodeScannerFactory = new IntermecScanner.IntermecBarcodeScannerFactory();

			// Create generic barcode reader object
			if(BarcodeScannerFactory != null)
  			BarcodeScanner = BarcodeScannerFactory.GetBarcodeScanner();

			return BarcodeScanner;
		}

    #region Get OEM Info (incl API declarations)
		/// <summary>
		/// Retrieves the OEM information from the device.
		/// </summary>
		/// <returns>OEM Information (manufacturer and model)</returns>
		private static string GetOemInfo()
		{
			string oemInfo = new string(' ', 50);
			int result = SystemParametersInfo(SPI_GETOEMINFO, 50, oemInfo, 0);
      oemInfo = oemInfo.Substring(0, oemInfo.IndexOf('\0'));
			return oemInfo;
		}

		[System.Runtime.InteropServices.DllImport("coredll.dll")]
		private static extern int SystemParametersInfo(int uiAction, int uiParam, string pvParam, int fWinIni);
		private const int SPI_GETOEMINFO = 258;
		#endregion
	}
}