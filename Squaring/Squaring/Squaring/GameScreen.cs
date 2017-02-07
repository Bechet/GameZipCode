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
    public class GameScreen
    {
        protected ContentManager content;
        protected MouseState Souris;
        protected int xSourisMatrice;
        protected int ySourisMatrice;
        protected Texture2D Curseur;
        protected SpriteFont fontSourisXY;
            

        public virtual void Initialise(ContentManager Content)
        {
            Souris = new MouseState();
        }

        public virtual void LoadContent(ContentManager Content)
        {
            content = new ContentManager(Content.ServiceProvider, "Content");
            Curseur = content.Load<Texture2D>("Curseur");
            fontSourisXY = content.Load<SpriteFont>("SpriteFont");
        }
        public virtual void UnLoadContent()
        {
            content.Unload();
        }
        public virtual void update(GameTime gametime)
        {
            Souris = Mouse.GetState();
        }
        public virtual void Draw(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(Curseur, new Vector2(Souris.X, Souris.Y), Color.White);
            spriteBatch.DrawString(fontSourisXY, Souris.X.ToString(), new Vector2(0, 600 - 14*2), Color.Black);
            spriteBatch.DrawString(fontSourisXY, Souris.Y.ToString(), new Vector2(3*14+5, 600 - 14*2), Color.Black);

        }

    }
}
