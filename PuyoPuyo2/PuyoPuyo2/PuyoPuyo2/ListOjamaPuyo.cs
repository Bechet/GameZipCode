using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PuyoPuyo2
{
    class ListOjamaPuyo
    {
        public List<OjamaPuyo> listOjamaPuyo;

        public ListOjamaPuyo()
        {
            this.listOjamaPuyo = new List<OjamaPuyo>();
        }

        public OjamaPuyo takeHead()
        {
            OjamaPuyo o = this.listOjamaPuyo[0];
            this.listOjamaPuyo.RemoveAt(0);
            return o;
        }

        public void Add(OjamaPuyo o)
        {
            this.listOjamaPuyo.Add(o);
        }

        public void Add(int combo, int Color, int nbPuyo)
        {
            OjamaPuyo o = new OjamaPuyo(combo, Color, nbPuyo);
            this.listOjamaPuyo.Add(o);
        }

        public int Count()
        {
            return this.listOjamaPuyo.Count();
        }
    }
}
