using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PuyoPuyo2
{
    class OjamaPuyo
    {
        public int combo;
        public int Color;
        public int nbPuyo;

        public OjamaPuyo()
        {
            this.combo = 1;
            this.Color = 0;
            this.nbPuyo = 4;
        }

        public OjamaPuyo(int combo, int Color, int nbPuyo)
        {
            this.combo = combo;
            this.Color = Color;
            this.nbPuyo = nbPuyo;
        }
    }
}
