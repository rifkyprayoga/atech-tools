using System;
using System.Collections.Generic;
using System.Text;

namespace AtechToolsNet.Utils
{
    interface IErrorReturn
    {
        /// <summary>
        /// Error was received.
        /// </summary>
        /// <returns></returns>
        bool WasErrorReceived();

        /// <summary>
        /// Return error code returned
        /// </summary>
        /// <returns></returns>
        int GetErrorCode();

        /// <summary>
        /// Return error description
        /// </summary>
        /// <returns></returns>
        string GetErrorDescription();

    }
}
