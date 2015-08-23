package com.shc.ld33.game.entities;

import com.shc.ld33.game.states.PlayState;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class Floor extends Entity2D
{
    private int platformNumber;
    
    public Floor(Vector2 position, int platformNumber)
    {
        super(Resources.Sprites.FLOOR, new Rectangle(Resources.Textures.FLOOR.getWidth(),
                                                     Resources.Textures.FLOOR.getHeight()));
        
        setPosition(position);
        getVelocity().x = -4;
        
        this.platformNumber = platformNumber;
    }
    
    @Override
    public void update(float delta)
    {
        if (getPosition().x < -getWidth())
        {
            destroy();
            PlayState.SCORE = platformNumber;
        }
    }
}
