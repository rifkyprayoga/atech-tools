using System;

using System.Collections.Generic;
using System.Text;
//using System.Windows.Forms;

namespace ATechTools.GUI
{
    /// <summary>
    /// Prevents a form being moved.
    /// </summary>
/*    public sealed class UnmovableForm : NativeWindow
    {
        /// <summary>
        /// Represents all the positional characteristics of a window.
        /// </summary>
        private struct WindowPos
        {
            public int hwnd;
            public int hWndInsertAfter;
            public int x;
            public int y;
            public int cx;
            public int cy;
            public int flags;
        }

        private const int WM_WINDOWPOSCHANGING = 0x46;

        /// <summary>
        /// The form to be immobilised.
        /// </summary>
        private Form target;

        public UnmovableForm(Form target)
        {
            this.target = target;
            this.target.HandleCreated += new EventHandler(target_HandleCreated);
            this.target.HandleDestroyed += new EventHandler(target_HandleDestroyed);


            
        }

        void target_HandleCreated(object sender, EventArgs e)
        {
            // Listent to the target forms message queue.
            AssignHandle(this.target.Handle);
        }

        void target_HandleDestroyed(object sender, EventArgs e)
        {
            ReleaseHandle();
        }

        protected override void WndProc(ref Message m)
        {
            if (m.Msg == WM_WINDOWPOSCHANGING)
            {
                WindowPos pos = (WindowPos)Marshal.PtrToStructure(m.LParam, typeof(WindowPos));

                // Reset the new location to the same as the current location.
                pos.x = this.target.Left;
                pos.y = this.target.Top;

                Marshal.StructureToPtr(pos, m.LParam, true);
            }

            base.WndProc(ref m);
        }
    }
*/
}
