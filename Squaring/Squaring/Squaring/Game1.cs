using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;


namespace Squaring
{

    /// <summary> 
    /// 17/09/16 Creating the project, implementing Square Class, Pion Class and the Terrain Class
    /// 22/09/16 Creating the Trait Class, which is necessary to the square drawing animation. Enables to play 1v1. Complete the 1.0 version playable.
    /// 29/09/16 Creating the IA Class, without implementing it 
    /// 06/10/16 Creating the MenuScreen Class, ScreenManager Class in order to create a menu screen and the game screen (which is implemented in game1.cs)
    /// 13/10/16 Creating the GameplayScreen Class in order to transfer all the game code of the Game1 class (main). 
    ///          Implementing the switch between screens (MenuScreen, OptionScreen, GameScreen) without interfering the value of the variables. (changing one option value in the option screen will not change the game).
    /// 16/10/16 Adding option in the OptionScreen Class (result). Showing mouse axes at the left down corner.
    /// </summary>





    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;

        public const int TAILLECARRE = 18;
        //public const int NOMBRECASEX = 50;
        //public const int NOMBRECASEY = 30;

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";

        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here
            ScreenManager.Instance.Initialise();

            //ScreenManager.Instance.Dimension = new Vector2(TAILLECARRE*NOMBRECASEX, TAILLECARRE*NOMBRECASEY);
            ScreenManager.Instance.Dimension = new Vector2(800,600);
            graphics.PreferredBackBufferWidth = (int)ScreenManager.Instance.Dimension.X;
            graphics.PreferredBackBufferHeight = (int)ScreenManager.Instance.Dimension.Y;
            graphics.ApplyChanges();
            base.Initialize();
        }



        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // TODO: use this.Content to load your game content here
            ScreenManager.Instance.LoadContent(Content);
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();

            // TODO: Add your update logic here
            ScreenManager.Instance.Update(gameTime);

            base.Update(gameTime);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);

            // TODO: Add your drawing code here
            spriteBatch.Begin();
            ScreenManager.Instance.Draw(spriteBatch);
            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}