using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;

namespace Squaring
{
    public class GameplayScreen : GameScreen
    {
        #region Declaration
        public const int TAILLECARRE = 18;
        public const int NOMBRECASEX = 25;
        public const int NOMBRECASEY = 15;

        //SpriteBatch spriteBatch;
        private Terrain terrain;
        //private Texture2D Curseur; implemented on the classe GameScreen
        private Texture2D trait;
        private Texture2D cadre;
        private Texture2D cadrePourDernierPion;
        private Texture2D newGameIcone;
        //private MouseState Souris; implemented on the classe GameScreen
        //private int xSourisMatrice, ySourisMatrice; implemented on the classe GameScreen
        private int numeroJoueur; //Numero du joueur qui joue mtnt.
        public int nombreJoueur = 2;
        private List<Joueur> listeJoueur;
        private List<Color> listeColor;
        private Square square;
        private SpriteFont spriteFontVariable;
        private Pion dernierPion;
        private int numeroJoueurGagne;
        #endregion



        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        public override void Initialise(ContentManager Content)
        {
            // TODO: Add your initialization logic here
            terrain = new Terrain(Content); //content
            //Souris = new MouseState(); implemented on the classe GameScreen
            listeColor = new List<Color> { Color.Red, Color.Blue, Color.Moccasin, Color.Peru, Color.LemonChiffon, Color.Purple };
            base.Initialise(Content);
            NouvellePartie();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content); //content != null after

            //Curseur = content.Load<Texture2D>("Curseur"); implemented on the classe GameScreen
            trait = content.Load<Texture2D>("Trait");
            cadre = content.Load<Texture2D>("cadre");
            cadrePourDernierPion = content.Load<Texture2D>("CadrePourDernierPion");
            newGameIcone = content.Load<Texture2D>("Newgameicone");
            spriteFontVariable = content.Load<SpriteFont>("SpriteFontScore");
            //spriteBatch = new SpriteBatch(GraphicsDevice); implemented on the classe GameScreen

            // TODO: use this.Content to load your game content here
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        public override void UnLoadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        #region Private function

        private void NouvellePartie()
        {
            terrain.NewMatrice();
            listeJoueur = new List<Joueur>();
            for (int i = 0; i < nombreJoueur; i++)
            {
                Joueur joueur = new Joueur();
                listeJoueur.Add(joueur);
            }
            numeroJoueur = 0;
            numeroJoueurGagne = 0;
            dernierPion = null;
        }

        private void VerifCarre(Pion pion)
        {
            int x1, y1, x2, y2;
            Joueur joueur = listeJoueur[numeroJoueur];
            foreach (Pion p in joueur.listePion)
            {
                x1 = p.X - (p.Y - pion.Y);
                y1 = p.Y + (p.X - pion.X);
                if ((x1 < terrain.tailleMatrice) && (x1 >= 0) && (y1 < terrain.tailleMatrice) && (y1 >= 0))
                {
                    if (terrain.matrice[x1, y1] == numeroJoueur + 1)
                    {
                        x2 = pion.X - (p.Y - pion.Y);
                        y2 = pion.Y + (p.X - pion.X);

                        if ((x2 < terrain.tailleMatrice) && (x2 >= 0) && (y2 < terrain.tailleMatrice) && (y2 >= 0))
                        {
                            if (terrain.matrice[x2, y2] == numeroJoueur + 1)
                            {
                                joueur.AjouterScore((int)(Math.Pow((p.Y - pion.Y), 2) + Math.Pow((p.X - pion.X), 2)));
                                Pion px = new Pion(1, 1);
                                square = new Square(p, pion, new Pion(x2, y2), new Pion(x1, y1), listeColor[numeroJoueur]);
                                joueur.listeSquare.Add(square);
                            }
                        }
                    }

                }

            }
        }

        #endregion

        #region Affichage


        private void DrawAll(SpriteBatch spriteBatch)
        {
            terrain.Draw(spriteBatch, nombreJoueur);
            AfficherSquares(spriteBatch);
            AfficherScore(spriteBatch);
            AfficherDernierPion(spriteBatch);
            AfficherIcone(spriteBatch);
            //spriteBatch.Draw(Curseur, new Vector2(Souris.X, Souris.Y), Color.White); implemented on the classe GameScreen
        }

        private void AfficherIcone(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(newGameIcone, new Vector2(TAILLECARRE * (NOMBRECASEX - 2), TAILLECARRE * (NOMBRECASEY - 2)), Color.White);
        }

        private void AfficherSquares(SpriteBatch spriteBatch)
        {
            foreach (Joueur joueur in listeJoueur)
            {
                foreach (Square square in joueur.listeSquare)
                {
                    square.AfficherSquare(spriteBatch, trait);
                }
            }
        }

        private void AfficherDernierPion(SpriteBatch spriteBatch)
        {
            if (dernierPion != null)
                spriteBatch.Draw(cadrePourDernierPion, new Rectangle(dernierPion.X * 18, dernierPion.Y * 18, 18, 18), Color.Yellow);
        }

        private void AfficherScore(SpriteBatch spriteBatch)
        {
            int i = 0;
            foreach (Joueur joueur in listeJoueur)
            {
                spriteBatch.DrawString(spriteFontVariable, (i + 1).ToString(), new Vector2(18 * 15 + 113, i * 90 + 10), Color.Black);
                spriteBatch.DrawString(spriteFontVariable, joueur.Score.ToString(), new Vector2(18 * 15 + 100, i * 90 + 30), Color.Black);
                i++;
            }
        }

        #endregion


        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        public override void update(GameTime gameTime)
        {

            // TODO: Add your update logic here

            //Souris = Mouse.GetState(); implemented on the classe GameScreen
            xSourisMatrice = (int)(Souris.X) / 18;
            ySourisMatrice = (int)(Souris.Y) / 18;

            if (Souris.LeftButton == ButtonState.Pressed)
            {
                if ((Souris.X >= 0) && (Souris.Y >= 0) && (Souris.X < 18 * terrain.tailleMatrice) && (Souris.Y < 18 * terrain.tailleMatrice) && numeroJoueurGagne == 0)
                {
                    if (terrain.matrice[xSourisMatrice, ySourisMatrice] == 0) //si case vide
                    {
                        Pion pion = new Pion(xSourisMatrice, ySourisMatrice);
                        dernierPion = pion;
                        terrain.AjouterPionMatrice(numeroJoueur, pion); //on ajoute le pion dans la matrice
                        VerifCarre(pion);
                        listeJoueur[numeroJoueur].AjouterPion(pion);    //on ajoute le pion dans la liste des pions selon le joueur
                        numeroJoueur = (numeroJoueur + 1) % listeJoueur.Count; //on change de joueur
                        if (listeJoueur[numeroJoueur].Score > 100)
                        {
                            numeroJoueurGagne = numeroJoueur + 1;
                        }
                    }
                }
                else if (Souris.X >= TAILLECARRE * (NOMBRECASEX - 2) && Souris.X < TAILLECARRE * NOMBRECASEX && Souris.Y > TAILLECARRE * (NOMBRECASEY - 2) && Souris.Y <= TAILLECARRE * NOMBRECASEY)
                {
                    NouvellePartie();
                }
            }
            base.update(gameTime);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        public override void Draw(SpriteBatch spriteBatch)
        {

            // TODO: Add your drawing code here
            DrawAll(spriteBatch);
            base.Draw(spriteBatch);

        }
    }
}

