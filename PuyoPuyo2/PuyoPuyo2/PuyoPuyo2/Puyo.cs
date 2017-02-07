using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;

namespace PuyoPuyo2
{
    class Puyo
    {
        public int x;
        public int y;
        public int color;


        public Puyo(int x, int y, int color)
        {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public void CopyPuyo(Puyo p)
        {
            this.x = p.x;
            this.y = p.y;
            this.color = p.color;
        }
    }
}

