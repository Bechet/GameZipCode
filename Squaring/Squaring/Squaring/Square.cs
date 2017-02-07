using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;

namespace Squaring
{
    class Square
    {
        private Pion p1, p2, p3, p4;
        public List<Trait> listetrait;

        public Square(Pion p1, Pion p2, Pion p3, Pion p4, Color couleur)
        {
            listetrait = new List<Trait>();
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;

            listetrait.Add(new Trait(p1.X * 18 + 9, p2.X * 18 + 9, p1.Y * 18 + 9, p2.Y * 18 + 9, couleur));
            listetrait.Add(new Trait(p2.X * 18 + 9, p3.X * 18 + 9, p2.Y * 18 + 9, p3.Y * 18 + 9, couleur));
            listetrait.Add(new Trait(p3.X * 18 + 9, p4.X * 18 + 9, p3.Y * 18 + 9, p4.Y * 18 + 9, couleur));
            listetrait.Add(new Trait(p4.X * 18 + 9, p1.X * 18 + 9, p4.Y * 18 + 9, p1.Y * 18 + 9, couleur));

        }

        #region internal get set
        internal Pion P1
        {
            get
            {
                return p1;
            }

            set
            {
                p1 = value;
            }
        }

        internal Pion P2
        {
            get
            {
                return p2;
            }

            set
            {
                p2 = value;
            }
        }

        internal Pion P3
        {
            get
            {
                return p3;
            }

            set
            {
                p3 = value;
            }
        }

        internal Pion P4
        {
            get
            {
                return p4;
            }

            set
            {
                p4 = value;
            }
        }
        #endregion

        public void AfficherSquare(SpriteBatch spritebach, Texture2D image)
        {
            foreach (Trait trait in listetrait)
            {
                trait.Afficher(spritebach, image);
            }
        }

    }
}
