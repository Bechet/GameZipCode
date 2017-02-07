using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Squaring
{
    class Pion
    {
        private int x, y;

        public Pion(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        #region GetSet
        public int X
        {
            get
            {
                return x;
            }

            set
            {
                x = value;
            }
        }

        public int Y
        {
            get
            {
                return y;
            }

            set
            {
                y = value;
            }
        }
        #endregion
    }
}
