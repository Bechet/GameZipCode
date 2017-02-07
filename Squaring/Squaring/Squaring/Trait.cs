using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework;

namespace Squaring
{
    class Trait
    {
        int x, y, distance;
        double angle;
        Color couleur;

        public Trait(int xa, int xb, int ya, int yb, Color color)
        {
            this.x = xa;
            this.y = ya;
            couleur = color;
            angle = Math.Atan2(yb - ya, xb - xa);

            distance = (int) Math.Sqrt((yb - ya) * (yb - ya) + (xb - xa) * (xb - xa));

        }

        public void Afficher(SpriteBatch spritebatch, Texture2D image)
        {
            spritebatch.Draw(image, new Rectangle(x,y-1, distance, 3), new Rectangle(1, 1, distance, 3), couleur, (float)angle, new Vector2(0, 1), SpriteEffects.None, 0);
        }
    }
}
