package com.shc.ld33.game.entities;

import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class Thorns extends Entity2D
{
    public Thorns(Vector2 position)
    {
        super(Resources.Sprites.THORNS, new Rectangle(Resources.Textures.THORNS.getWidth() - 20,
                                                      Resources.Textures.THORNS.getHeight() - 10));

        getPolygon().translate(10, 25);
        
        setPosition(position);
        getVelocity().x = -4;
    }

    @Override
    public void update(float delta)
    {
        if (getPosition().x < -getWidth())
            destroy();
    }
}

