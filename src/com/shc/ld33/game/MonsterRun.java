package com.shc.ld33.game;

import com.shc.ld33.game.states.PlayState;
import com.shc.silenceengine.core.Display;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.opengl.GL3Context;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class MonsterRun extends Game
{
    @Override
    public void preInit()
    {
        Display.setTitle("MonsterRun");
        Display.setSize(800, 600);
    }
    
    @Override
    public void init()
    {
        Resources.init();
        GL3Context.clearColor(Color.GRAY);
        
        setGameState(new PlayState());
    }
    
    @Override
    public void dispose()
    {
        Resources.dispose();
    }
    
    public static void main(String[] args)
    {
        new MonsterRun().start();
    }
}
