using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;

namespace Squaring
{
    class Terrain
    {
        #region Declarations
        public int[,] matrice;
        //public List<Joueur> listeJoueur;
        public int tailleMatrice = 15;
        Texture2D cadre;
        Texture2D Case;
        SpriteFont spriteFont;

        #endregion

        public Terrain(ContentManager content)
        {
            Case = content.Load<Texture2D>("case");
            cadre = content.Load<Texture2D>("cadre");
            spriteFont = content.Load<SpriteFont>("SpriteFont");
           // listeJoueur = new List<Joueur>();
        }

        public void NewMatrice()
        {
            matrice = new int[tailleMatrice, tailleMatrice];
        }

        public void AjouterPionMatrice(int numJoueur,Pion pion)
        {
            matrice[pion.X, pion.Y] = numJoueur+1;
        }

        public void Draw(SpriteBatch spriteBatch, int nombreJoueur)
        {
            int id;
            for (int x = 0; x < tailleMatrice; x++)
            {
                for (int y = 0; y < tailleMatrice; y++)
                {
                    id = matrice[x, y];
                    spriteBatch.Draw(Case, new Vector2(x * 18, y * 18), new Rectangle(id*18, 0, 18, 18), Color.White);
                }
            }
            for (int i = 0; i < nombreJoueur; i++)
            {
                spriteBatch.Draw(cadre, new Vector2(18 * 15, i*90), new Rectangle(0, 0, 180, 90), Color.Black);
                spriteBatch.DrawString(spriteFont, "Joueur :", new Vector2(18 * 15+15, i * 90 + 10), Color.Black);
                spriteBatch.DrawString(spriteFont, "Score :", new Vector2(18 * 15 + 15, i * 90 + 30), Color.Black);

            }
        }
    }
}
