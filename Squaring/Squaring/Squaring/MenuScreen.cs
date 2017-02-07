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
    public class MenuScreen : GameScreen
    {
        private KeyboardState keyState;
        private SpriteFont font;
        private Texture2D imageBackground;
        private Color color = new Color(50, 20, 100, 255);
        private List<String> listMenuString = new List<String>();
        private int itemSelected = 0; //fade animation for the menu item chosen (new game ...) + entering game : 0 => new game, 1 => option
        private bool fading = true;
        private int theOnePressedIs = 0; //0 : none, //1 : Keys.Down, //2 : Keys.Up, 3 : enter/x


        #region Main Methods

        public override void Initialise(ContentManager Content)
        {
            listMenuString.Add("New Game"); listMenuString.Add("Option");
            base.Initialise(Content);
        }

        public override void LoadContent(ContentManager Content)
        {
            base.LoadContent(Content);
            if (font == null)
            {
                font = content.Load<SpriteFont>("SpriteFont");
                imageBackground = content.Load<Texture2D>("blueSquareBackground");
            }

        }

        public override void UnLoadContent()
        {
            base.UnLoadContent();
        }

        public override void update(GameTime gameTime)
        {
            //Keybord
            keyState = Keyboard.GetState();
            if (keyState.IsKeyDown(Keys.K))
            {
                ScreenManager.Instance.AddScreen(new GameplayScreen());
            }
            if (keyState.IsKeyDown(Keys.Down) || theOnePressedIs == 1)
            {
                theOnePressedIs = 1;
                if (keyState.IsKeyUp(Keys.Down))
                {
                    itemSelected = (itemSelected + 1) % 2;
                    color.A = 250;
                    theOnePressedIs = 0;
                }
            }
            if (keyState.IsKeyDown(Keys.Up) || theOnePressedIs == 2)
            {
                theOnePressedIs = 2;
                if (keyState.IsKeyUp(Keys.Up))
                {
                    itemSelected = (itemSelected - 1) % 2;
                    color.A = 250;
                    theOnePressedIs = 0;
                }
            }
            if (keyState.IsKeyDown(Keys.Enter) || keyState.IsKeyDown(Keys.X) || theOnePressedIs == 3)
            {
                theOnePressedIs = 3;
                if (keyState.IsKeyUp(Keys.Enter) || keyState.IsKeyUp(Keys.X))
                {
                    theOnePressedIs = 0;
                    switch (itemSelected)
                    {
                        case 0: //New game
                            ScreenManager.Instance.AddScreen(new GameplayScreen());
                            break;
                        case 1:
                            ScreenManager.Instance.AddScreen(new OptionScreen());
                            break;
                    }
                }
            }

            #region Mouse
            if (Souris.LeftButton == ButtonState.Pressed)
            {
                if (Souris.X >= 330 && Souris.X < 440 && Souris.Y >= 290 && Souris.Y < 320)
                {
                    ScreenManager.Instance.AddScreen(new GameplayScreen());
                }
                else if (Souris.X >= 350 && Souris.X < 430 && Souris.Y >= 350 && Souris.Y < 370)
                {
                    ScreenManager.Instance.AddScreen(new OptionScreen());
                }
            }
            #endregion

            //Color, fade animation
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

            base.update(gameTime);
        }



        private void DrawString(SpriteBatch spriteBatch)
        {
            int i = 1;
            foreach (String item in listMenuString)
            {
                if (i == itemSelected + 1)
                    spriteBatch.DrawString(font, item, new Vector2(400 - (int)(item.Length * 14 / 2), i * 50 + 250), color, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
                else
                    spriteBatch.DrawString(font, item, new Vector2(400 - (int)(item.Length * 14 / 2), i * 50 + 250), Color.Black, 0, new Vector2(0, 0), 1, SpriteEffects.None, 0);
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
