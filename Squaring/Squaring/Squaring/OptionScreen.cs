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
    class OptionScreen : GameScreen
    {

        private KeyboardState keyState;
        private SpriteFont font;
        private Texture2D imageBackground;
        private Texture2D imageIncreaseButton;
        private Color color = new Color(50, 20, 100, 255);   //for fade animation
        private List<String> listOptionString = new List<String>();
        private List<String> listGameSize = new List<string>();
        private List<String> listMaxScore = new List<string>();
        private List<String> listMode = new List<string>();
        private List<List<String>> listAllItem = new List<List<string>>();
        private List<int> listAllIndex = new List<int>();
        private List<int> listAllNumberItem = new List<int>();
        private int numberItemListOptionString;
        private int numberItemListGameSize;
        private int numberItemListMaxScore;
        private int numberItemListMode;
        private int itemSelected = 0;               //fade animation for the menu item chosen (new game ...) + entering game : 0 => new game, 1 => option
        private int theOnePressedIs = 0;            //0 : none, //1 : Keys.Down, //2 : Keys.Up, 3 : enter/x
        private int theOneClickedIs = 0;
        private int indexListGameSize = 0;
        private int indexListMaxScore = 0;
        private int indexListMode = 0;
        private bool fading = true;
        private bool boolKeyPressed = false;
        private bool boolButtonClicked = false;
        private string keyPressed;

        public int mod(int x, int m) //replace % which is not working for negative int...
        {
            int r = x % m;
            return r < 0 ? r + m : r;
        }

        #region Main Methods

        public override void Initialise(ContentManager Content)
        {
            //Add choosing option here, initialising all lists
            listOptionString.Add("Taille :"); listOptionString.Add("Score a atteindre :"); listOptionString.Add("Mode");
            listOptionString.Add("Back to menu");
            listGameSize.Add("    10x10    "); listGameSize.Add("   15x15   "); listGameSize.Add("   20x20   ");
            for (int i = 100; i <= 400; i += 50)
            {
                listMaxScore.Add("    " + i.ToString() + "     ");
            }
            listMode.Add("Player Vs IA"); listMode.Add("Player Vs Player");
            listAllItem.Add(listGameSize); listAllItem.Add(listMaxScore); listAllItem.Add(listMode);

            //Initialising indexes
            numberItemListOptionString = listOptionString.Count();
            numberItemListGameSize = listGameSize.Count();
            numberItemListMaxScore = listMaxScore.Count();
            numberItemListMode = listMode.Count();

            //Adding all NumberItem
            listAllNumberItem.Add(numberItemListGameSize);
            listAllNumberItem.Add(numberItemListMaxScore);
            listAllNumberItem.Add(numberItemListMode);

            //Adding all index
            listAllIndex.Add(indexListGameSize);
            listAllIndex.Add(indexListMaxScore);
            listAllIndex.Add(indexListMode);
            base.Initialise(Content);
        }

        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content);
            if (font == null)
            {
                font = content.Load<SpriteFont>("SpriteFont");

            }
            imageBackground = content.Load<Texture2D>("blueSquareBackground");
            imageIncreaseButton = content.Load<Texture2D>("increaseButton");
        }

        public override void UnLoadContent()
        {
            base.UnLoadContent();
        }



        public override void update(GameTime gameTime)
        {
            #region Keybord
            keyState = Keyboard.GetState();
            if (!boolKeyPressed) //if none of the key is pressed
            {
                if (keyState.IsKeyDown(Keys.Down) || theOnePressedIs == 1)
                {
                    theOnePressedIs = 1;
                    if (keyState.IsKeyUp(Keys.Down))
                    {
                        keyPressed = "DOWN";
                        theOnePressedIs = 0;
                        boolKeyPressed = true;
                    }
                }
                else if (keyState.IsKeyDown(Keys.Up) || theOnePressedIs == 2)
                {
                    theOnePressedIs = 2;
                    if (keyState.IsKeyUp(Keys.Up))
                    {
                        keyPressed = "UP";
                        theOnePressedIs = 0;
                        boolKeyPressed = true;
                    }
                }
                else if (keyState.IsKeyDown(Keys.Left) || theOnePressedIs == 3)
                {
                    theOnePressedIs = 3;
                    if (keyState.IsKeyUp(Keys.Left))
                    {
                        keyPressed = "LEFT";
                        theOnePressedIs = 0;
                        boolKeyPressed = true;
                    }
                }
                else if (keyState.IsKeyDown(Keys.Right) || theOnePressedIs == 4)
                {
                    theOnePressedIs = 4;
                    if (keyState.IsKeyUp(Keys.Right))
                    {
                        keyPressed = "RIGHT";
                        theOnePressedIs = 0;
                        boolKeyPressed = true;
                    }
                }
                else if (keyState.IsKeyDown(Keys.C) || theOnePressedIs == 5)
                {
                    theOnePressedIs = 5;
                    if (keyState.IsKeyUp(Keys.C))
                    {
                        keyPressed = "C";
                        theOnePressedIs = 0;
                        boolKeyPressed = true;
                    }
                }
                else if (keyState.IsKeyDown(Keys.Enter) || keyState.IsKeyDown(Keys.X) || theOnePressedIs == 30)
                {
                    theOnePressedIs = 30;
                    if (keyState.IsKeyUp(Keys.Enter) || keyState.IsKeyUp(Keys.X))
                    {
                        keyPressed = "ENTER";
                        theOnePressedIs = 0;
                        boolKeyPressed = true;
                    }
                }
            }

            #endregion

            #region Case
            if (boolKeyPressed || boolButtonClicked)
            {
                switch (keyPressed)
                {
                    case "LEFT":
                        listAllIndex[itemSelected] = mod((listAllIndex[itemSelected] - 1), listAllNumberItem[itemSelected]);
                        #region mistake
                        /*Faillure, not working 
                        switch (itemSelected)
                        {
                            case 0: //Change the map size
                                indexListGameSize = mod((indexListGameSize-1),numberItemListGameSize);
                                break;
                            case 1: //Change the max score
                                indexListMaxScore = mod((indexListMaxScore - 1), numberItemListMaxScore);
                                break;
                            case 2://Change PVP, IA
                                indexListMode = mod((indexListMode - 1), numberItemListMode);
                                break;
                        }
                        */
                        #endregion
                        break;
                    case "RIGHT":
                        listAllIndex[itemSelected] = mod((listAllIndex[itemSelected] + 1), listAllNumberItem[itemSelected]);
                        #region mistake
                        /* Faillure, not working 
                        switch (itemSelected)
                        {
                            case 0: //Change the map size
                                indexListGameSize = mod((indexListGameSize + 1), numberItemListGameSize);
                                break;
                            case 1: //Change the max score
                                indexListMaxScore = mod((indexListMaxScore + 1), numberItemListMaxScore);
                                break;
                            case 2://Change PVP, IA
                                indexListMode = mod((indexListMode + 1), numberItemListMode);
                                break;
                        }
                        */
                        #endregion
                        break;
                    case "UP":
                        itemSelected = mod((itemSelected - 1), numberItemListOptionString);
                        color.A = 250;
                        theOnePressedIs = 0;
                        break;
                    case "DOWN":
                        itemSelected = mod((itemSelected + 1), numberItemListOptionString);
                        color.A = 250;
                        theOnePressedIs = 0;
                        break;
                    case "ENTER":
                    case "X":

                        break;
                    case "C":
                        ScreenManager.Instance.AddScreen(new MenuScreen());
                        break;

                }
                boolKeyPressed = false;
                boolButtonClicked = false;
                theOnePressedIs = 0;
                theOneClickedIs = 0;
            }

            #endregion

            #region Mouse
            if (!boolButtonClicked) //if not clicked yet...
            {
                if (Souris.LeftButton == ButtonState.Pressed || theOneClickedIs != 0)
                {
                    for (int i = 0; i < listOptionString.Count() - 1; i++) //-1 is for "back to menu"
                    {
                        if ((Souris.X >= 500 && Souris.X < 560 && Souris.Y >= 200 + i * 50 && Souris.Y < 230 + i * 50) || theOneClickedIs == i+1)// <= left triangle clicked
                        {
                            theOneClickedIs = i+1;
                            if (Souris.LeftButton == ButtonState.Released)
                            {
                                itemSelected = i;
                                keyPressed = "LEFT";
                                boolButtonClicked = true;
                                break;
                            }
                        }
                        else if (Souris.X >= 690 && Souris.X < 750 && Souris.Y >= 200 + i * 50 && Souris.Y < 230 + i * 50 || theOneClickedIs == i+ listOptionString.Count()+1)// <= left triangle clicked
                        {
                            theOneClickedIs = i + listOptionString.Count()+1;
                            if (Souris.LeftButton == ButtonState.Released)
                            {
                                itemSelected = i;
                                keyPressed = "RIGHT";
                                boolButtonClicked = true;
                                break;
                            }
                        }
                    }
                    if (Souris.X >= 130 && Souris.X < 300 && Souris.Y >= 340 && Souris.Y < 380)
                    {
                        //TODO SAVING
                        ScreenManager.Instance.AddScreen(new MenuScreen());
                    }
                }


            }
    
            #endregion

            #region Color, fade animation
            if (color.A > 240)
            {
                fading = true;
            }
            else if (color.A < 50)
            {
                fading = false;
            }
            if (fading)
                color.A -= 5;
            else
                color.A += 5;

            #endregion
            base.update(gameTime);
        }



        private void DrawString(SpriteBatch spriteBatch)
        {
            String result;
            int i = 1;
            foreach (String item in listOptionString)
            {
                //Drawing the item in the listOptionString
                if (i == itemSelected + 1)
                    spriteBatch.DrawString(font, item, new Vector2(150, i * 50 + 150), color, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
                else
                    spriteBatch.DrawString(font, item, new Vector2(150, i * 50 + 150), Color.Black, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);

                //Drawing the result in front of the item

                if (i != numberItemListOptionString)
                {
                    spriteBatch.Draw(imageIncreaseButton, new Rectangle(500, i * 50 + 150, 250, 40), Color.Yellow);
                    result = listAllItem[i - 1][listAllIndex[i - 1]];
                    spriteBatch.DrawString(font, result, new Vector2(650-result.Length*14/2, i * 50 + 155), Color.Black, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
                }
                i++;
            }
        }

        public override void Draw(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(imageBackground, new Rectangle(0, 0, 800, 600), Color.White);
            DrawString(spriteBatch);

            base.Draw(spriteBatch);
        }

        #endregion


    }
}


