package com.shc.ld33.game.entities;

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
    private boolean canAttack  = true;
    
    private GameTimer attackTimer;
    
    public Monster(Vector2 position)
    {
        super(Resources.Sprites.MONSTER, new Rectangle(Resources.Textures.MONSTER.getWidth(),
                                                       Resources.Textures.MONSTER.getHeight()));
        
        setCenter(position);
        
        attackTimer = new GameTimer(3, TimeUtils.Unit.SECONDS);
        attackTimer.setCallback(() ->
        {
            if (isDestroyed())
                return;
            
            canAttack = true;
        });
    }
    
    @Override
    public void update(float delta)
    {
        getVelocity().y = (float) Math.sin(TimeUtils.currentSeconds() * 2) / 2;
        getVelocity().x = (float) Math.sin(Math.toRadians(90) + TimeUtils.currentSeconds() * 2) / 2;
        
        if (Keyboard.isPressed('W') || Keyboard.isPressed(Keyboard.KEY_UP))
            getVelocity().y -= 4;
        
        if (Keyboard.isPressed('S') || Keyboard.isPressed(Keyboard.KEY_DOWN))
            getVelocity().y += 4;
        
        if (Keyboard.isPressed('A') || Keyboard.isPressed(Keyboard.KEY_LEFT))
            getVelocity().x -= 4;
        
        if (Keyboard.isPressed('D') || Keyboard.isPressed(Keyboard.KEY_RIGHT))
            getVelocity().x += 4;
        
        if (canAttack && ((int) (getRotation()) == 0) && Keyboard.isClicked(Keyboard.KEY_SPACE))
        {
            canAttack = false;
            attackTimer.start();
        }
        
        if (!canAttack)
            rotateTo(90, 135 * delta);
        else
            rotateTo(0, 135 * delta);
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
}
