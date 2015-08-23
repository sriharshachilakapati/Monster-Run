package com.shc.ld33.game.entities;

import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class Cloud extends Entity2D
{
    public Cloud(Vector2 position)
    {
        super(Resources.Sprites.CLOUD, new Rectangle(Resources.Textures.CLOUD.getWidth(),
                                                     Resources.Textures.CLOUD.getHeight()));
        
        setPosition(position);
        getVelocity().x = -5;
        
        setDepth(1);
    }
    
    @Override
    public void update(float delta)
    {
        if (getPosition().x < -getWidth())
            destroy();
    }
}
