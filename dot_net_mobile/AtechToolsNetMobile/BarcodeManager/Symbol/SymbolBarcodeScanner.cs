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
using Symbol;
using Symbol.Barcode;

//namespace Microsoft.Samples.Barcode.SymbolScanner
namespace ATechToolsMobile.BarcodeManager.Symbol
{
	/// <summary>
	/// This is the barcode scanner class for Symbol devices.
	/// </summary>
	public class SymbolBarcodeScanner : BarcodeScanner 
	{
		private Reader symbolReader = null;
		private ReaderData symbolReaderData = null;

		/// <summary>
		/// Initiates the Symbol scanner.
		/// </summary>
		/// <returns>Whether initialization was successful</returns>
		public override bool Initialize() 
		{
			// If scanner is already present then fail initialize
			if ( symbolReader != null ) 
				return false;

			// Create new scanner, first available scanner will be used.
			symbolReader = new Reader();

			// Create scanner data
			symbolReaderData = new ReaderData(ReaderDataTypes.Text, ReaderDataLengths.DefaultText);
  
			// Create event handler delegate
            symbolReader.ReadNotify +=new EventHandler(symbolReader_ReadNotify);

			// Enable scanner, with wait cursor
			symbolReader.Actions.Enable();

			// Setup scanner
			symbolReader.Parameters.Feedback.Success.BeepTime = 0;
			symbolReader.Parameters.Feedback.Success.WaveFile = "\\windows\\alarm3.wav";

			return true;
		}

		/// <summary>
		/// Start a Symbol scan.
		/// </summary>
		public override void Start() 
		{
			// If we have both a scanner and data
			if ( ( symbolReader != null ) && ( symbolReaderData != null ) ) 
				// Submit a scan
				symbolReader.Actions.Read(symbolReaderData);
		}

		/// <summary>
		/// Stop a Symbol scan.
		/// </summary>
		public override void Stop() 
		{
			// If we have a scanner
			if ( symbolReader != null ) 
				// Flush (Cancel all pending scans)
				symbolReader.Actions.Flush();
				//symbolReader.ReadNotify -= BarcodeScannerEventHandler;
		}

		/// <summary>
		/// Terminates the Symbol scanner.
		/// </summary>
		public override void Terminate()
		{
			// If we have a scanner
			if ( symbolReader != null ) 
			{
				// Disable the scanner
				symbolReader.Actions.Disable();

				// Free it up
				symbolReader.Dispose();

				// Indicate we no longer have one
				symbolReader = null;
			}

			// If we have a scanner data object
			if ( symbolReaderData != null ) 
			{
				// Free it up
				symbolReaderData.Dispose();

				// Indicate we no longer have one
				symbolReaderData = null;
			}
		}

		/// <summary>
		/// Event that fires when a Symbol scanner has performed a scan.
		/// </summary>
		private void symbolReader_ReadNotify(object sender, EventArgs e) 
		{
			ReaderData readerData = symbolReader.GetNextReaderData();

			// If it is a successful scan (as opposed to a failed one)
			if ( readerData.Result == Results.SUCCESS ) 
			{
				// Raise scan event to caller (with data)
				OnBarcodeScan(new BarcodeScannerEventArgs(readerData.Text));

				// Start the next scan
				Start();
			}
		}

    #region IDisposable Members
    public override void Dispose(bool disposing)
    {
      if (disposing)
      {
        // Code to clean up managed resources
        Terminate();
      }
      // Code to clean up unmanaged resources
    }
		#endregion
	}
}
