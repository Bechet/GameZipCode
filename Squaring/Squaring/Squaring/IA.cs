using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;

namespace Squaring
{
    class IA
    {
        int type;
        Random rnd;

        public IA(int type)
        {
            this.type = type; //Power of the AI, type<=10
            rnd = new Random();
        }

        private Pion IAForte(int[,] matrice, Pion dernierPion, List<Pion> listePion)
        {
            //Defence, searching if the player has a "near" square
            int x1, x2, y1, y2;
            int tailleMatrice = matrice.Length;
            double alea;
            Pion pionAPlacer = null;
            bool found = false; // for only one pion
            foreach (Pion p in listePion)
            {
                if (!found)
                {
                    x1 = p.X - (p.Y - dernierPion.Y);
                    y1 = p.Y + (p.X - dernierPion.X);
                    if ((x1 < matrice.Length) && (x1 >= 0) && (y1 < tailleMatrice) && (y1 >= 0))
                    {
                        if (matrice[x1, y1] != 0) //TODO
                        {
                            x2 = dernierPion.X - (p.Y - dernierPion.Y);
                            y2 = dernierPion.Y + (p.X - dernierPion.X);

                            if ((x2 < tailleMatrice) && (x2 >= 0) && (y2 < tailleMatrice) && (y2 >= 0))
                            {
                                if (matrice[x2, y2] == 0) //Si il ne l'a pas encore posé...
                                {
                                    alea = rnd.Next();
                                    if (alea <= 0.1 * type) //Puissance de l'IA
                                    {
                                        pionAPlacer = new Pion(x2, y2);
                                        found = true;
                                    }

                                }
                            }
                        }
                    }
                }

            }
            return pionAPlacer;
        }
    }
}
