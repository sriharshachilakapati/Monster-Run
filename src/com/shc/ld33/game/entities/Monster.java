package com.shc.ld33.game.entities;

import com.shc.ld33.game.states.IntroState;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.geom2d.Rectangle;
import com.shc.silenceengine.scene.entity.Entity2D;
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.TimeUtils;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class Monster extends Entity2D
{
    private boolean inJump     = false;
    private boolean isOnGround = false;

    private GameTimer jumpTimer;

    public Monster(Vector2 position)
    {
        super(Resources.Sprites.MONSTER, new Rectangle(Resources.Textures.MONSTER.getWidth(),
                                                       Resources.Textures.MONSTER.getHeight()));
        setCenter(position);

        jumpTimer = new GameTimer(1, TimeUtils.Unit.SECONDS);
        jumpTimer.setCallback(() ->
        {
            if (isDestroyed())
                return;
            
            inJump = false;
        });
    }
    
    @Override
    public void update(float delta)
    {
        getVelocity().y = (float) Math.sin(TimeUtils.currentSeconds() * 2) / 2;
        getVelocity().x = (float) Math.sin(Math.toRadians(90) + TimeUtils.currentSeconds() * 2) / 2;
        
        if (isOnGround && Keyboard.isClicked(Keyboard.KEY_SPACE))
        {
            inJump = true;
            jumpTimer.start();

            Resources.Sounds.JUMP.play();
        }
        
        if (inJump)
            getVelocity().y -= 8;
        
        getVelocity().y += 4;
        
        if (inJump)
            rotateTo(90, 135 * delta);
        else
            rotateTo(0, 135 * delta);
        
        if (getPosition().y > 600)
        {
            Game.setGameState(new IntroState());
            Resources.Sounds.GAME_OVER.play();
        }
        
        isOnGround = false;
    }
    
    private void rotateTo(float angle, float stepAngle)
    {
        int rotation = (int) getRotation();
        
        while (rotation > 360) rotation -= 360;
        while (rotation < 0) rotation += 360;
        
        if (rotation == angle)
            return;
        
        if (rotation < angle)
            rotate(stepAngle);
        else
            rotate(-stepAngle);
    }
    
    @Override
    public void collision(Entity2D other)
    {
        if (other instanceof Grass)
        {
            isOnGround = true;
            inJump = false;
            
            getPosition().y = other.getPosition().y - getHeight();
        }
        else if (other instanceof Thorns)
        {
            Game.setGameState(new IntroState());
            Resources.Sounds.GAME_OVER.play();
        }
        else if (other instanceof Floor)
        {
            float distance = getCenter().y * getCenter().y + other.getCenter().y * other.getCenter().y;

            if (distance > 50 * 50)
            {
                Game.setGameState(new IntroState());
                Resources.Sounds.GAME_OVER.play();
            }
        }
    }
}
