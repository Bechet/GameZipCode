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
    public class ScreenManager
    {
        #region Variables
        private static ScreenManager instance; //singleton

        Stack<GameScreen> screenStack = new Stack<GameScreen>(); //stacking up screens, used in the addScreen methodes

        private Vector2 dimension; //screen Dimension

        GameScreen currentScreen;
        GameScreen newScreen;

        ContentManager content; //content for each screen

        #endregion

        #region Propeties

        public static ScreenManager Instance
        {
            get
            {
                if (instance == null)
                    instance = new ScreenManager();
                return instance;
            }
        }
        public Vector2 Dimension
        {
            get { return dimension; }
            set { dimension = value; }
        }

        #endregion

        #region Main Methods

        public void Initialise()
        {
            currentScreen = new MenuScreen();
            currentScreen.Initialise(content);
        }

        public void LoadContent(ContentManager Content)
        {
            content = new ContentManager(Content.ServiceProvider, "Content");
            currentScreen.LoadContent(Content);
        }

        public void AddScreen(GameScreen screen)
        {
            newScreen = screen;
            screenStack.Push(newScreen);
            currentScreen.UnLoadContent();
            currentScreen = newScreen;
            currentScreen.LoadContent(content);
            currentScreen.Initialise(content);
        }

        public void Draw(SpriteBatch spritebatch)
        {
            currentScreen.Draw(spritebatch);
        }

        public void Update(GameTime gametime)
        {
            currentScreen.update(gametime);
        }


        #endregion
    }
}
