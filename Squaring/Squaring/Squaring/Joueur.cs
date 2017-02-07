using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;

namespace Squaring
{
    class Joueur
    {
        #region Declaration
        private int score;
        public List<Pion> listePion;
        public List<Square> listeSquare;
        #endregion

        #region SetGet
        public int Score
        {
            get
            {
                return score;
            }

            set
            {
                score = value;
            }
        }
        /*
        public int Numero
        {
            get
            {
                return numero;
            }

            set
            {
                numero = value;
            }
        }
        */
        #endregion

        public Joueur()
        {
            this.Score = 0;
            this.listePion = new List<Pion>();
            this.listeSquare = new List<Square>();
        }

        public void AjouterScore(int score)
        {
            this.Score += score;
        }


        public void AjouterPion(Pion pion)
        {
            listePion.Add(pion);
        }


    }
}
