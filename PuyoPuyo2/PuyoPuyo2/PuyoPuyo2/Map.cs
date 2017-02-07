using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Content;

namespace PuyoPuyo2
{
    class Map
    {
        public const int TAILLEPUYO = 32;
        public const int NOMBREPUYOX = 8;
        public const int NOMBREPUYOY = 16;

        private int counter;
        public int fallingSpeed;

        public int combo;
        private int elapse;
        private int frame;
        private int elapseCounter;

        private int pixelElapse;
        private const int PIXELFALLINGSPEED = 16;
        private int pixelElapseCounterFirstPuyo;
        private int pixelElapseCounterSecondPuyo;

        private int xShift; //decalage
        private int yShift; //decalage

        public int[,] matrixPuyoColor;        //matrix for the color of the puyo
        public int[,] matrixPuyoSharp; //Matrix for the shape of the puyo
        private int[,] matrixPuyoChecked;
        private int[,] matrixElapseCounter;
        private List<int> listOjamaPuyo; //for sprite image on the playscreen

        public List<Puyo> listePuyo;
        private List<List<Puyo>> listePuyoToErase;

        public Puyo puyoFirst;
        public Puyo puyoSecond;

        private bool bPuyoFirstAdded;
        private bool bPuyoSecondAdded;

        public ListOjamaPuyo listOP;
        public int nbOjamaPuyo;


        private Texture2D spritePuyo;
        private Texture2D spriteCadre;
        private Texture2D imageBackground;
        private Texture2D imagePuyoDisappearing;
        private Texture2D imageOjamaPuyo;

        public bool canMouve;
        private bool bAnimationComboMode;
        private bool bDownPressed;
        private bool bCanPuyoFirstGoDown;
        private bool bCanPuyoSecondGoDown;

        public Map(List<Puyo> lp, int xShift, int yShift)
        {
            this.xShift = xShift;
            this.yShift = yShift;

            this.elapse = 70;
            this.elapseCounter = 0;
            this.combo = 0;
            this.frame = 1;

            this.canMouve = true;
            this.bAnimationComboMode = false;
            this.bCanPuyoFirstGoDown = false;
            this.bCanPuyoSecondGoDown = false;

            this.bPuyoFirstAdded = false;
            this.bPuyoSecondAdded = false;

            this.fallingSpeed = 40;
            this.counter = 0;

            pixelElapse = 32;
            pixelElapseCounterFirstPuyo = 0;
            pixelElapseCounterSecondPuyo = 0;

            listOjamaPuyo = new List<int>();
            for (int i = 0; i < 5; i++)
            {
                this.listOjamaPuyo.Add(0);
            }
            listePuyo = new List<Puyo>();
            foreach (Puyo p in lp)
            {
                Puyo puyo = new Puyo(p.x, p.y, p.color);
                listePuyo.Add(puyo);
            }


            //this.listePuyo = lp;
            this.listePuyoToErase = new List<List<Puyo>>();

            matrixPuyoColor = new int[NOMBREPUYOX, NOMBREPUYOY];
            matrixPuyoSharp = new int[NOMBREPUYOX, NOMBREPUYOY];
            matrixPuyoChecked = new int[NOMBREPUYOX, NOMBREPUYOY];
            matrixElapseCounter = new int[NOMBREPUYOX, NOMBREPUYOY];

            for (int i = 0; i < NOMBREPUYOX; i++)
            {
                for (int j = 0; j < NOMBREPUYOY; j++)
                {
                    matrixPuyoColor[i, j] = -1;
                    matrixPuyoSharp[i, j] = -1;
                }
            }

            listOP = new ListOjamaPuyo();
            this.nbOjamaPuyo = 0; // number of OjamaPuyos that this "map" has 

            newTurn();
        }

        public void LoadContent(ContentManager Content)
        {
            spritePuyo = Content.Load<Texture2D>("Puyos");
            spriteCadre = Content.Load<Texture2D>("Cadre8x16");
            imageBackground = Content.Load<Texture2D>("WhiteSquare");
            imagePuyoDisappearing = Content.Load<Texture2D>("PuyosDisappearing");
            imageOjamaPuyo = Content.Load<Texture2D>("OjamaPuyo");
        }

        public void Draw(SpriteBatch spriteBatch)
        {

            /* Draw the background */
            spriteBatch.Draw(imageBackground, new Vector2(xShift, yShift), new Rectangle(0, 0, NOMBREPUYOX * TAILLEPUYO, NOMBREPUYOY * TAILLEPUYO), Color.White);

            /*Draw the squares */
            spriteBatch.Draw(spriteCadre, new Vector2(xShift, yShift), Color.Gray);

            /* Draw the 2 puyo (puyoFirst and puyoSecond) */
            spriteBatch.Draw(spritePuyo, new Vector2(this.puyoFirst.x * TAILLEPUYO + xShift, this.puyoFirst.y * TAILLEPUYO + yShift + this.pixelElapseCounterFirstPuyo), new Rectangle(0, puyoFirst.color * TAILLEPUYO, 32, 32), Color.White);
            spriteBatch.Draw(spritePuyo, new Vector2(this.puyoSecond.x * TAILLEPUYO + xShift, this.puyoSecond.y * TAILLEPUYO + yShift + this.pixelElapseCounterSecondPuyo), new Rectangle(0, puyoSecond.color * TAILLEPUYO, 32, 32), Color.White);

            /* Draw puyo in game */
            int puyoIJ = 0;
            for (int i = 0; i < NOMBREPUYOX; i++)
            {
                for (int j = 0; j < NOMBREPUYOY; j++)
                {
                    puyoIJ = matrixPuyoColor[i, j];
                    if (puyoIJ != -1)
                    {
                        int c = matrixPuyoColor[i, j];
                        int s = matrixPuyoSharp[i, j];
                        //spriteBatch.Draw(spritePuyo, new Vector2(i * TAILLEPUYO + xShift, j * TAILLEPUYO + yShift), new Rectangle(s*TAILLEPUYO, c* TAILLEPUYO, (s+1) * TAILLEPUYO, (c+1)*TAILLEPUYO), Color.White);
                        spriteBatch.Draw(spritePuyo, new Vector2(i * TAILLEPUYO + xShift, j * TAILLEPUYO + yShift), new Rectangle(s * TAILLEPUYO, c * TAILLEPUYO, 32, 32), Color.White);
                    }
                }
            }

            if (bAnimationComboMode)
            {
                foreach (List<Puyo> lp in listePuyoToErase)
                {
                    foreach (Puyo p in lp)
                    {
                        spriteBatch.Draw(this.imagePuyoDisappearing, new Rectangle(p.x * TAILLEPUYO + xShift, p.y * TAILLEPUYO + yShift, 32, 32), new Rectangle(frame * TAILLEPUYO, p.color * TAILLEPUYO, 32, 32), Color.White);
                    }
                }
            }

            foreach (int i in listOjamaPuyo)
            {
                spriteBatch.Draw(this.imageOjamaPuyo, new Vector2(i * TAILLEPUYO + xShift + 32, yShift - 32), new Rectangle(this.listOjamaPuyo[i] * TAILLEPUYO, 0, 32, 32), Color.White);
            }


        }
    

        public void UpdateMatrixPuyoShape() //Called once puyo is down
        {
            for (int i = 0; i < NOMBREPUYOX; i++)
            {
                for (int j = 0; j < NOMBREPUYOY; j++)
                {
                    int numberOfBrother = 0;
                    int puyoColor = matrixPuyoColor[i, j];
                    if (puyoColor != -1)
                    {
                        if (j < NOMBREPUYOY - 1)
                        {
                            if (this.matrixPuyoColor[i, j + 1] == puyoColor)
                            {
                                numberOfBrother = numberOfBrother + 1;
                            }
                        }
                        if (j > 0)
                        {
                            if (this.matrixPuyoColor[i, j - 1] == puyoColor)
                            {
                                numberOfBrother = numberOfBrother + 2;
                            }
                        }
                        if (i < NOMBREPUYOX - 1)
                        {
                            if (this.matrixPuyoColor[i + 1, j] == puyoColor)
                            {
                                numberOfBrother = numberOfBrother + 4;
                            }
                        }
                        if (i > 0)
                        {
                            if (this.matrixPuyoColor[i - 1, j] == puyoColor)
                            {
                                numberOfBrother = numberOfBrother + 8;
                            }
                        }
                        this.matrixPuyoSharp[i, j] = numberOfBrother;
                    }
                }
            }
        }

        public void AddPuyo(Puyo puyo)
        {
            this.matrixPuyoColor[puyo.x, puyo.y] = puyo.color;
        }

        public void Update(GameTime gametime)
        {
            gravity();

            if (bDownPressed)
            {
                if (bCanPuyoFirstGoDown || bCanPuyoSecondGoDown)
                {
                    if (bCanPuyoFirstGoDown)
                    {
                        if (pixelElapseCounterFirstPuyo < pixelElapse)
                        {
                            this.pixelElapseCounterFirstPuyo += PIXELFALLINGSPEED;
                        }
                        else
                        {
                            puyoFirst.y++;
                            this.pixelElapseCounterFirstPuyo = 0;
                            bCanPuyoFirstGoDown = false;
                        }
                    }
                    if (bCanPuyoSecondGoDown)
                    {
                        if (pixelElapseCounterSecondPuyo < pixelElapse)
                        {
                            this.pixelElapseCounterSecondPuyo += PIXELFALLINGSPEED;
                        }
                        else
                        {
                            puyoSecond.y++;
                            this.pixelElapseCounterSecondPuyo = 0;
                            bCanPuyoSecondGoDown = false;
                        }
                    }
                }
                else
                {
                    if (this.bPuyoFirstAdded && this.bPuyoSecondAdded)
                    {
                        this.bDownPressed = false;
                        this.bPuyoFirstAdded = false;
                        this.bPuyoSecondAdded = false;
                        this.checkPuyoErase();
                        this.UpdateMatrixPuyoShape();
                        this.newTurn();
                    }
                    if (puyoSecond.y == puyoFirst.y + 1 && puyoSecond.y < NOMBREPUYOY - 2 && matrixPuyoColor[puyoSecond.x, puyoSecond.y + 1] != -1)
                    {
                        AddPuyo(puyoSecond);
                        AddPuyo(puyoFirst);
                        this.bPuyoFirstAdded = true;
                        this.bPuyoSecondAdded = true;
                        checkPuyoErase();
                        this.UpdateMatrixPuyoShape();
                        this.newTurn();

                    }
                    else if (puyoFirst.y == puyoSecond.y + 1 && puyoFirst.y < NOMBREPUYOY - 2 && matrixPuyoColor[puyoFirst.x, puyoFirst.y + 1] != -1)
                    {
                        AddPuyo(puyoSecond);
                        AddPuyo(puyoFirst);
                        this.bPuyoFirstAdded = true;
                        this.bPuyoSecondAdded = true;
                        checkPuyoErase();
                        this.UpdateMatrixPuyoShape();
                        this.newTurn();
                    }
                    else
                    {
                        if (puyoFirst.y == NOMBREPUYOY - 1)
                        {

                            AddPuyo(puyoFirst);
                            bPuyoFirstAdded = true;
                        }
                        else
                        {
                            if (matrixPuyoColor[puyoFirst.x, puyoFirst.y + 1] == -1)
                            {
                                bCanPuyoFirstGoDown = true;
                            }
                            else
                            {
                                AddPuyo(puyoFirst);
                                bPuyoFirstAdded = true;
                            }
                        }
                        if (this.puyoSecond.y == NOMBREPUYOY - 1)
                        {
                            AddPuyo(puyoSecond);
                            bPuyoSecondAdded = true;
                        }
                        else
                        {
                            if (matrixPuyoColor[puyoSecond.x, puyoSecond.y + 1] == -1)
                            {
                                bCanPuyoSecondGoDown = true;
                            }
                            else
                            {
                                AddPuyo(puyoSecond);
                                bPuyoSecondAdded = true;
                            }
                        }
                    }
                }
            }
            if (!bAnimationComboMode)
            {
                if (this.listePuyoToErase.Count != 0)
                {
                    this.bAnimationComboMode = true;
                    this.combo++;
                    foreach (List<Puyo> lp in this.listePuyoToErase)
                    {
                        this.listOP.Add(combo, lp[0].color, lp.Count);
                    }
                    this.RemovePuyo(); //Remove Puyo from the MatrixPuyoColor
                }
                else
                {
                    this.combo = 0;
                }
                UpdateMatrixPuyoShape();
            }
            else
            {
                canMouve = false;
                if (elapse < this.elapseCounter)
                {
                    elapseCounter = 0;
                    bAnimationComboMode = false;
                    canMouve = true;

                    bool unchanged = false;
                    while (!unchanged)
                    {
                        unchanged = this.ReplacePuyo();
                    }
                    UpdateMatrixPuyoShape();
                    this.listePuyoToErase = new List<List<Puyo>>();
                    this.checkPuyoErase();

                }
                else
                {
                    if (this.elapseCounter > this.elapse * 2 / 3)
                    {
                        this.frame = 2;

                    }
                    else
                    {
                        if (elapseCounter % 2 == 0)
                        {
                            this.frame = (this.frame + 1) % 2;

                        }
                    }
                    this.elapseCounter++;
                }
            }
        }

        private void gravity()
        {
            /* Gravity */
            if (matrixPuyoColor[3, 0] == -1 && matrixPuyoColor[4, 0] == -1)
            {
                if (canMouve && !bAnimationComboMode)
                {
                    counter++;
                    if (counter > this.fallingSpeed)
                    {
                        this.counter = 0;
                        this.puyoFirst.y++;
                        this.puyoSecond.y++;
                        /* Case the puyo falls too far */
                        if (puyoFirst.y > NOMBREPUYOY - 1 || puyoSecond.y > NOMBREPUYOY - 1)
                        {
                            this.puyoFirst.y--;
                            AddPuyo(puyoFirst);
                            this.puyoSecond.y--;
                            AddPuyo(puyoSecond);
                            //RemoveAndCheckPuyo();
                            this.checkPuyoErase();
                            newTurn();
                        }
                        else
                        {
                            if (this.matrixPuyoColor[puyoFirst.x, puyoFirst.y] != -1) //if there was no blank for the first puyo
                            {
                                //TODO, add animation here
                                this.canMouve = false;
                                this.puyoFirst.y--;
                                this.puyoSecond.y--;
                                AddPuyo(puyoFirst);
                                AscendPuyo(puyoSecond);
                                //RemoveAndCheckPuyo();
                                this.checkPuyoErase();
                                newTurn();
                            }

                            else if (this.matrixPuyoColor[puyoSecond.x, puyoSecond.y] != -1) //if there was no blank
                            {
                                //TODO, add animation here
                                this.canMouve = false;
                                this.puyoFirst.y--;
                                this.puyoSecond.y--;
                                AddPuyo(puyoSecond);
                                AscendPuyo(puyoFirst);
                                //RemoveAndCheckPuyo();
                                this.checkPuyoErase();
                                newTurn();
                            }

                        }

                    }
                }
            }
        }

        public void UpdateOjamaPuyo() //for the Ojamapuyo Sprite Effect
        {
            int nbOj = this.nbOjamaPuyo;
            this.listOjamaPuyo = new List<int>();
            int[] Ojama = new int[5];
            int[] nbOjama = new int[5];
            nbOjama[0] = 720;
            nbOjama[1] = 180;
            nbOjama[2] = 30;
            nbOjama[3] = 6;
            nbOjama[4] = 1;

            for (int i = 0; i < 5; i++)
            {
                while (nbOj >= nbOjama[i])
                {
                    Ojama[i]++;
                    nbOj -= nbOjama[i];
                }
                for (int j = 0; j < Ojama[i]; j++)
                {
                    this.listOjamaPuyo.Add(5-i);
                }
            }
            for (int i = 0; i < 5 - this.listOjamaPuyo.Count; i++)
            {
                this.listOjamaPuyo.Add(0);
            }
        }

        private void AscendPuyo(Puyo p)
        {
            int y = checkTheEnd(p);
            /*Animation*/

            int i = TAILLEPUYO * (y - p.y);
            Puyo pAux = p;
            AddPuyo(new Puyo(pAux.x, y, pAux.color));


        }

        private int checkTheEnd(Puyo p)
        {
            int i = p.y;
            while (i < NOMBREPUYOY - 1 && matrixPuyoColor[p.x, i + 1] == -1)
            {
                i++;
            }
            return i;
        }

        private void newTurn()
        {
            this.puyoFirst = listePuyo.First();
            this.listePuyo.RemoveAt(0);
            this.puyoSecond = listePuyo.First();
            this.listePuyo.RemoveAt(0);

            this.canMouve = true;
        }

        public void moveToTheRight()
        {
            if (this.canMouve)
            {
                if (this.puyoFirst.x < NOMBREPUYOX - 1 && this.puyoSecond.x < NOMBREPUYOX - 1 && matrixPuyoColor[puyoFirst.x + 1, puyoFirst.y] == -1 && matrixPuyoColor[puyoSecond.x + 1, puyoSecond.y] == -1)
                {
                    this.puyoSecond.x++;
                    this.puyoFirst.x++;
                }
            }
        }

        public void moveToTheLeft()
        {
            if (this.canMouve)
            {
                if (this.puyoFirst.x > 0 && this.puyoSecond.x > 0 && matrixPuyoColor[puyoFirst.x - 1, puyoFirst.y] == -1 && matrixPuyoColor[puyoSecond.x - 1, puyoSecond.y] == -1)
                {
                    this.puyoSecond.x--;
                    this.puyoFirst.x--;
                }
            }
        }

        public void moveToTheBottom()
        {
            if (this.canMouve)
            {
                this.canMouve = false;
                this.bDownPressed = true;
                /*
                int first = checkTheEnd(this.puyoFirst);
                int second = checkTheEnd(this.puyoSecond);

                if (this.puyoFirst.y == this.puyoSecond.y + 1 && this.puyoFirst.x == this.puyoSecond.x)
                {
                    second--;
                }
                else if (this.puyoFirst.y == this.puyoSecond.y - 1 && this.puyoFirst.x == this.puyoSecond.x)
                {
                    first--;
                }

                this.puyoFirst.y = first;
                this.puyoSecond.y = second;
                AddPuyo(puyoFirst);
                AddPuyo(puyoSecond);
                this.canMouve = true;

                this.checkPuyoErase();

                newTurn();
                */
            }
        }

        public void turnRight()
        {
            if (this.canMouve)
            {
                #region Right Side
                if (this.puyoFirst.x == NOMBREPUYOX - 1)
                {
                    if (this.puyoSecond.y == this.puyoFirst.y + 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoFirst.x--;
                            this.puyoSecond.y--;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else if (this.puyoSecond.y == this.puyoFirst.y - 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x--;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            /* Optional */
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else
                    {
                        if (this.puyoFirst.y < NOMBREPUYOY - 1)
                        {
                            if (matrixPuyoColor[this.puyoFirst.x, this.puyoFirst.y + 1] == -1)
                            {
                                this.puyoSecond.x++;
                                this.puyoSecond.y++;
                            }
                            else
                            {
                                this.puyoFirst.y--;
                                this.puyoSecond.x++;
                            }
                        }
                        else
                        {
                            this.puyoFirst.y--;
                            this.puyoSecond.x++;
                        }
                    }
                }
                #endregion
                #region Left Side
                else if (this.puyoFirst.x == 0)
                {
                    if (this.puyoSecond.y == this.puyoFirst.y + 1)
                    {

                        if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x++;
                            this.puyoSecond.y--;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else if (this.puyoSecond.y == this.puyoFirst.y - 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoFirst.x++;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else
                    {
                        this.puyoSecond.y--;
                        this.puyoSecond.x--;
                    }
                }
                #endregion
                #region Other
                else
                {
                    if (puyoSecond.x > puyoFirst.x)
                    {
                        this.puyoSecond.x--;
                        this.puyoSecond.y--;
                    }
                    else if (puyoSecond.x < puyoFirst.x)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x, this.puyoFirst.y + 1] == -1)
                        {
                            this.puyoSecond.x++;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            this.puyoSecond.x++;
                            this.puyoSecond.y--;
                        }
                    }
                    else if (puyoSecond.y > puyoFirst.y)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x++;
                            this.puyoSecond.y--;
                        }
                        else
                        {
                            /* Optional */
                            if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                            {
                                this.puyoFirst.x--;
                                this.puyoSecond.y--;
                            }
                            else
                            {
                                int y = puyoFirst.y;
                                puyoFirst.y = puyoSecond.y;
                                puyoSecond.y = y;
                            }
                        }
                    }
                    else if (puyoSecond.y < puyoFirst.y)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x--;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            /* Optional */
                            if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                            {
                                this.puyoFirst.x++;
                                this.puyoSecond.y++;
                            }
                            else
                            {
                                int y = puyoFirst.y;
                                puyoFirst.y = puyoSecond.y;
                                puyoSecond.y = y;
                            }
                        }
                    }
                }
                #endregion
            }
        }

        public void turnLeft()
        {
            if (this.canMouve)
            {
                if (this.puyoFirst.x == NOMBREPUYOX - 1)
                {
                    if (this.puyoSecond.y == this.puyoFirst.y + 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x--;
                            this.puyoSecond.y--;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else if (this.puyoSecond.y == this.puyoFirst.y - 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoFirst.x--;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else
                    {
                        this.puyoSecond.x++;
                        this.puyoSecond.y--;
                    }
                }
                else if (this.puyoFirst.x == 0)
                {
                    if (this.puyoSecond.y == this.puyoFirst.y + 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoFirst.x++;
                            this.puyoSecond.y--;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else if (this.puyoSecond.y == this.puyoFirst.y - 1)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x++;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            int y = puyoFirst.y;
                            puyoFirst.y = puyoSecond.y;
                            puyoSecond.y = y;
                        }
                    }
                    else
                    {
                        if (matrixPuyoColor[this.puyoFirst.x, this.puyoFirst.y + 1] == -1)
                        {
                            this.puyoSecond.x--;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            this.puyoFirst.y--;
                            this.puyoSecond.x--;
                        }
                    }
                }
                else
                {
                    if (puyoSecond.x > puyoFirst.x)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x, this.puyoFirst.y + 1] == -1)
                        {
                            this.puyoSecond.x--;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            this.puyoSecond.x--;
                            this.puyoFirst.y--;
                        }
                    }
                    else if (puyoSecond.x < puyoFirst.x)
                    {
                        this.puyoSecond.x++;
                        this.puyoSecond.y--;
                    }
                    else if (puyoSecond.y > puyoFirst.y)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x--;
                            this.puyoSecond.y--;
                        }
                        else
                        {
                            /* Optional */
                            if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                            {
                                this.puyoFirst.x++;
                                this.puyoSecond.y--;
                            }
                            else
                            {
                                int y = puyoFirst.y;
                                puyoFirst.y = puyoSecond.y;
                                puyoSecond.y = y;
                            }
                        }
                    }
                    else if (puyoSecond.y < puyoFirst.y)
                    {
                        if (matrixPuyoColor[this.puyoFirst.x + 1, this.puyoFirst.y] == -1)
                        {
                            this.puyoSecond.x++;
                            this.puyoSecond.y++;
                        }
                        else
                        {
                            /* Optional */
                            if (matrixPuyoColor[this.puyoFirst.x - 1, this.puyoFirst.y] == -1)
                            {
                                this.puyoFirst.x--;
                                this.puyoSecond.y++;
                            }
                            else
                            {
                                int y = puyoFirst.y;
                                puyoFirst.y = puyoSecond.y;
                                puyoSecond.y = y;
                            }
                        }
                    }
                }
            }
        }

        private void checkPuyoErase()
        {
            this.matrixPuyoChecked = new int[NOMBREPUYOX, NOMBREPUYOY];
            for (int i = 0; i < NOMBREPUYOX; i++)
            {
                for (int j = 0; j < NOMBREPUYOY; j++)
                {
                    int puyoColor = matrixPuyoColor[i, j];
                    if (puyoColor != -1)
                    {
                        List<Puyo> lp = new List<Puyo>();
                        lp.Add(new Puyo(i, j, puyoColor));
                        AuxCheckPuyoErase(i, j, puyoColor, lp);
                        if (lp.Count >= 4)
                        {
                            this.listePuyoToErase.Add(lp);
                        }
                        lp = new List<Puyo>();
                    }
                }
            }
        }

        /* Recursive mode for searching the puyos */
        private void AuxCheckPuyoErase(int i, int j, int puyoColor, List<Puyo> lp)
        {
            if (i > 0)
            {
                if (puyoColor == this.matrixPuyoColor[i - 1, j])
                {
                    Puyo p = new Puyo(i - 1, j, puyoColor);
                    if (!isContainedIn(p, lp) && !isContainedIn(p, listePuyoToErase))
                    {
                        lp.Add(p);
                        AuxCheckPuyoErase(i - 1, j, puyoColor, lp);
                    }
                }
            }
            if (i < NOMBREPUYOX - 1)
            {
                if (puyoColor == this.matrixPuyoColor[i + 1, j])
                {
                    Puyo p = new Puyo(i + 1, j, puyoColor);
                    if (!isContainedIn(p, lp) && !isContainedIn(p, listePuyoToErase))
                    {
                        lp.Add(p);
                        AuxCheckPuyoErase(i + 1, j, puyoColor, lp);
                    }
                }
            }
            if (j > 0)
            {
                if (puyoColor == this.matrixPuyoColor[i, j - 1])
                {
                    Puyo p = new Puyo(i, j - 1, puyoColor);
                    if (!isContainedIn(p, lp) && !isContainedIn(p, listePuyoToErase))
                    {
                        lp.Add(p);
                        AuxCheckPuyoErase(i, j - 1, puyoColor, lp);
                    }
                }
            }
            if (j < NOMBREPUYOY - 1)
            {
                if (puyoColor == this.matrixPuyoColor[i, j + 1])
                {
                    Puyo p = new Puyo(i, j + 1, puyoColor);
                    if (!isContainedIn(p, lp) && !isContainedIn(p, listePuyoToErase))
                    {
                        lp.Add(p);
                        AuxCheckPuyoErase(i, j + 1, puyoColor, lp);
                    }
                }
            }
        }

        private void RemovePuyo()
        {
            /* Changing the matrix matrixPuyoColor */
            foreach (List<Puyo> lp in this.listePuyoToErase)
            {
                foreach (Puyo p in lp)
                {
                    this.matrixPuyoColor[p.x, p.y] = -1;
                    this.matrixPuyoSharp[p.x, p.y] = -1;
                }
            }
        }

        private bool ReplacePuyo()
        {
            bool unChanged = true;
            for (int i = NOMBREPUYOX - 1; i >= 0; i--)
            {
                for (int j = NOMBREPUYOY - 2; j >= 0; j--)
                {
                    if (this.matrixPuyoColor[i, j] != -1 && this.matrixPuyoColor[i, j + 1] == -1)
                    {
                        unChanged = false;
                        this.matrixPuyoColor[i, j + 1] = this.matrixPuyoColor[i, j];
                        this.matrixPuyoSharp[i, j + 1] = this.matrixPuyoSharp[i, j];

                        this.matrixPuyoColor[i, j] = -1;
                        this.matrixPuyoSharp[i, j] = 0;
                    }
                }
            }
            return unChanged;
        }

        public bool isContainedIn(Puyo puyo, List<Puyo> lp)
        {
            bool contains = false;
            foreach (Puyo p in lp)
            {
                if (!contains && p.x == puyo.x && p.y == puyo.y && p.color == puyo.color)
                {
                    contains = true;
                }
            }
            return contains;
        }

        public bool isContainedIn(Puyo puyo, List<List<Puyo>> llp)
        {
            bool contains = false;
            foreach (List<Puyo> lp in llp)
            {
                if (!contains)
                {
                    foreach (Puyo p in lp)
                    {
                        if (!contains && p.x == puyo.x && p.y == puyo.y && p.color == puyo.color)
                        {
                            contains = true;
                        }
                    }
                }
            }
            return contains;
        }

    }
}
