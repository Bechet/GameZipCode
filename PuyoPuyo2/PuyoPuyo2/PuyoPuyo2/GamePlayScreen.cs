using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;

namespace PuyoPuyo2
{
    class GamePlayScreen : GameScreen
    {
        private Map map1;
        private Map map2;

        private int numberOfColor = 4;
        private int counterKeyPressed1;
        private int counterKeyPressed2;
        private int elapse;
        private int frame;
        private int counterElapse;

        private int counterElapseCarbuncle;
        private int elapseCarbuncle;
        private int frameCarbuncle;

        private Color gameStartColor;

        private bool bKeyPressed1;
        private bool bKeyPressed2;
        private bool bgameStart;

        private Texture2D imageGameStartCounter;
        private Texture2D imageCarbuncle;
        private Texture2D imageWhiteSquare;
        private Texture2D imagePuyos;

        private KeyboardState keyState;


        public override void Initialise(ContentManager Content)
        {
            /* Frame Variables */
            this.counterElapseCarbuncle = 0;
            this.frameCarbuncle = 0;
            this.elapseCarbuncle = 20;

            this.counterKeyPressed1 = 0;
            this.counterKeyPressed2 = 0;
            this.bKeyPressed1 = false;
            this.bKeyPressed2 = false;
            this.bgameStart = false;

            this.gameStartColor = new Color(0, 0, 0);
            elapse = 80;
            this.counterElapse = 0;
            frame = 3;

            List<Puyo> lp = new List<Puyo>();
            Random rnd = new Random();
            for (int i = 0; i < 100; i++)
            {
                int randomColor = rnd.Next(numberOfColor);
                lp.Add(new Puyo(3, 0, randomColor));
                randomColor = rnd.Next(numberOfColor);
                lp.Add(new Puyo(4, 0, randomColor));
            }
            this.map1 = new Map(lp, 50, 50);
            this.map2 = new Map(lp, 500, 50);

            base.Initialise(Content);
        }

        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content);
            imageGameStartCounter = Content.Load<Texture2D>("counter");
            this.imageCarbuncle = Content.Load<Texture2D>("CarbuncleSprite200x200V2");
            this.imageWhiteSquare = Content.Load<Texture2D>("WhiteSquare");
            this.imagePuyos = Content.Load<Texture2D>("Puyos");
            this.map1.LoadContent(Content);
            this.map2.LoadContent(Content);

        }

        public override void Draw(SpriteBatch spriteBatch)
        {

            this.map1.Draw(spriteBatch);
            this.map2.Draw(spriteBatch);
            if (!bgameStart)
            {
                animationStartGame(spriteBatch);
            }
            else
            {
                spriteBatch.Draw(imageCarbuncle, new Rectangle(350, 150, 100, 100), new Rectangle(frameCarbuncle*200, 0, 200, 200), Color.White);
                /* Draw the nextPuyo in game */
                int puyoNext11 = this.map1.listePuyo[0].color;
                int puyoNext12 = this.map1.listePuyo[1].color;
                int puyoNext21 = this.map2.listePuyo[0].color;
                int puyoNext22 = this.map2.listePuyo[1].color;

                spriteBatch.Draw(imageWhiteSquare, new Rectangle(330, 80, 32, 32 * 2), new Rectangle(0, 0, 1, 1), Color.LightGray);
                spriteBatch.Draw(imageWhiteSquare, new Rectangle(500-25-32, 80, 32, 32 * 2), new Rectangle(0, 0, 1, 1), Color.LightGray);
                spriteBatch.Draw(imagePuyos, new Rectangle(330, 80, 32, 32), new Rectangle(0, puyoNext11*32, 32, 32), Color.LightGray);
                spriteBatch.Draw(imagePuyos, new Rectangle(330, 80+32, 32, 32), new Rectangle(0, puyoNext12 * 32, 32, 32), Color.LightGray);
                spriteBatch.Draw(imagePuyos, new Rectangle(500 - 25 - 32, 80, 32, 32), new Rectangle(0, puyoNext21 * 32, 32, 32), Color.LightGray);
                spriteBatch.Draw(imagePuyos, new Rectangle(500 - 25 - 32, 80+32, 32, 32), new Rectangle(0, puyoNext22 * 32, 32, 32), Color.LightGray);
            }
            base.Draw(spriteBatch);
        }

        public override void update(GameTime gametime)
        {
            if (bgameStart) //activate after the animation.
            {
                #region Carbuncle Animation
                this.counterElapseCarbuncle++;
                if (counterElapseCarbuncle > this.elapseCarbuncle)
                {
                    frameCarbuncle = (frameCarbuncle + 1) % 8;
                    this.counterElapseCarbuncle = 0;
                }
                else
                {
                    this.counterElapseCarbuncle++;
                }
                #endregion

                #region OjamaPuyo

                if (map1.listOP.Count() != 0)
                {
                    int nbOjamaPuyo = 0;
                    nbOjamaPuyo += CalculateNbOjamaPuyo(map1.listOP.takeHead());

                    if (nbOjamaPuyo > this.map1.nbOjamaPuyo) //if the number of ojamapuyo the player 1 has done is bigger than what he has...
                    {
                        nbOjamaPuyo -= this.map1.nbOjamaPuyo;
                        this.map1.nbOjamaPuyo = 0;
                        this.map2.nbOjamaPuyo += nbOjamaPuyo;
                    }
                    else
                    {
                        this.map1.nbOjamaPuyo -= nbOjamaPuyo;
                    }
                    map1.UpdateOjamaPuyo();
                    map2.UpdateOjamaPuyo();
                }

                if (map2.listOP.Count() != 0)
                {
                    int nbOjamaPuyo = 0;
                    nbOjamaPuyo += CalculateNbOjamaPuyo(map2.listOP.takeHead());

                    if (nbOjamaPuyo > this.map2.nbOjamaPuyo) //if the number of ojamapuyo the player 2 has done is bigger than what he has...
                    {
                        nbOjamaPuyo -= this.map2.nbOjamaPuyo;
                        this.map2.nbOjamaPuyo = 0;
                        this.map1.nbOjamaPuyo += nbOjamaPuyo;
                    }
                    else
                    {
                        this.map2.nbOjamaPuyo -= nbOjamaPuyo;
                    }
                    map1.UpdateOjamaPuyo();
                    map2.UpdateOjamaPuyo();
                }



                #endregion

                #region Keyboard
                keyState = Keyboard.GetState();
                if (!this.bKeyPressed1)
                {
                    if (keyState.IsKeyDown(Keys.D))
                    {
                        map1.moveToTheRight();
                        bKeyPressed1 = true;
                    }
                    if (keyState.IsKeyDown(Keys.Q))
                    {
                        map1.moveToTheLeft();
                        bKeyPressed1 = true;
                    }
                    if (keyState.IsKeyDown(Keys.S))
                    {
                        map1.moveToTheBottom();
                        bKeyPressed1 = true;
                    }
                    if (keyState.IsKeyDown(Keys.C))
                    {
                        map1.turnRight();
                        bKeyPressed1 = true;
                    }
                    if (keyState.IsKeyDown(Keys.X))
                    {
                        map1.turnLeft();
                        bKeyPressed1 = true;
                    }
                }
                else
                {
                    this.counterKeyPressed1++;
                    if (this.counterKeyPressed1 > 5)
                    {
                        this.counterKeyPressed1 = 0;
                        this.bKeyPressed1 = false;
                    }
                }

                if (!bKeyPressed2)
                {
                    if (keyState.IsKeyDown(Keys.Right))
                    {
                        map2.moveToTheRight();
                        bKeyPressed2 = true;
                    }
                    if (keyState.IsKeyDown(Keys.Left))
                    {
                        map2.moveToTheLeft();
                        bKeyPressed2 = true;
                    }
                    if (keyState.IsKeyDown(Keys.Down))
                    {
                        map2.moveToTheBottom();
                        bKeyPressed2 = true;
                    }
                    if (keyState.IsKeyDown(Keys.D1))
                    {
                        map2.turnRight();
                        bKeyPressed2 = true;
                    }
                    if (keyState.IsKeyDown(Keys.D2))
                    {
                        map2.turnLeft();
                        bKeyPressed2 = true;
                    }
                }
                else
                {
                    this.counterKeyPressed2++;
                    if (this.counterKeyPressed2 > 8)
                    {
                        this.counterKeyPressed2 = 0;
                        this.bKeyPressed2 = false;

                    }
                }
                #endregion
                map1.Update(gametime);
                map2.Update(gametime);


            }

            base.update(gametime);
        }

        private int CalculateNbOjamaPuyo(OjamaPuyo o)
        {
            return o.combo * o.nbPuyo + o.Color;
        }

        private void animationStartGame(SpriteBatch spritebatch)
        {
            if (this.counterElapse > this.elapse)
            {
                this.counterElapse = 0;
                this.frame--;
                this.gameStartColor.A = 255;
            }
            else
            {
                this.counterElapse++;
                this.gameStartColor.A -= 3;
            }
            if (this.frame == 0)
            {
                this.bgameStart = true;
            }
            else
            {
                spritebatch.Draw(imageGameStartCounter, new Rectangle(300,200,200,200), new Rectangle(this.frame * 32, 0, 32, 32), this.gameStartColor);
            }
        }

    }
}
