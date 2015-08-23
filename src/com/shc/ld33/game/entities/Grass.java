package com.shc.ld33.game.entities;

import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class Grass extends Entity2D
{
    public Grass(Vector2 position)
    {
        super(Resources.Sprites.GRASS, new Rectangle(Resources.Textures.GRASS.getWidth(),
                                                     Resources.Textures.GRASS.getHeight()));
        
        setPosition(position);
        setDepth(-1);
        getVelocity().x = -4;
    }

    @Override
    public void update(float delta)
    {
        if (getPosition().x < -getWidth())
            destroy();
    }
}
