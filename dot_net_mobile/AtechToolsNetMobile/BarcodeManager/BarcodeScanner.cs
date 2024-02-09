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

namespace ATechToolsMobile.BarcodeManager
{
	/// <summary>
	/// This is the abstract barcode scanner class.
	/// </summary>
	public abstract class BarcodeScanner : IDisposable
	{		
		public BarcodeScanner()
		{
			// If we can initialize the scanner...
			if ( Initialize() ) 
				// ...start a scan on the scanner
				this.Start();
		}

		// Methods that need to be implemented in subclass.
		public abstract bool Initialize();
		public abstract void Start();
		public abstract void Stop();
		public abstract void Terminate();

		// Event delegate and handler
		public delegate void BarcodeScanEventHandler(object sender, BarcodeScannerEventArgs e);
		public event BarcodeScanEventHandler BarcodeScan;

		/// <summary>
		/// Event that calls the delegate.
		/// </summary>
		protected virtual void OnBarcodeScan(BarcodeScannerEventArgs e) 
		{
			if (BarcodeScan != null) 
				// Invokes the delegate. 
				BarcodeScan(this, e);
		}

		public abstract void Dispose(bool disposing);

    public virtual void Dispose()
    {
      Dispose(true);
      GC.SuppressFinalize(this);
    }

		~BarcodeScanner()
		{
			Dispose(false);
		}
  }
}
