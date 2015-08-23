package com.shc.ld33.game;

import com.shc.ld33.game.states.IntroState;
import com.shc.ld33.game.states.PlayState;
import com.shc.silenceengine.core.Display;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.graphics.TrueTypeFont;
import com.shc.silenceengine.graphics.opengl.GL3Context;
import com.shc.silenceengine.io.FilePath;
import com.shc.silenceengine.utils.FileUtils;
import resources.Resources;

import java.io.BufferedWriter;

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

        FilePath scoreFile = FilePath.getExternalFile("highscore.txt");
        if (scoreFile.exists())
            PlayState.BEST_SCORE = Integer.parseInt(FileUtils.readLinesToString(scoreFile).trim());
    }
    
    @Override
    public void init()
    {
        Resources.init();
        GL3Context.clearColor(Color.GRAY);
        
        setGameState(new IntroState());

        Graphics2D g2d = SilenceEngine.graphics.getGraphics2D();
        g2d.setColor(Color.BLACK);
        g2d.getFont().setSizeAndStyle(30, TrueTypeFont.STYLE_BOLD);
    }
    
    @Override
    public void dispose()
    {
        FilePath scoreFile = FilePath.getExternalFile("highscore.txt");
        
        try (BufferedWriter writer = new BufferedWriter(scoreFile.getWriter()))
        {
            writer.write("" + PlayState.BEST_SCORE);
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        Resources.dispose();
    }
    
    public static void main(String[] args)
    {
        new MonsterRun().start();
    }
}
