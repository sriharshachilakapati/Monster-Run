package com.shc.ld33.game.states;

import com.shc.ld33.game.entities.Cloud;
import com.shc.ld33.game.entities.Floor;
import com.shc.ld33.game.entities.Grass;
import com.shc.ld33.game.entities.Monster;
import com.shc.ld33.game.entities.Thorns;
import com.shc.silenceengine.collision.broadphase.DynamicTree2D;
import com.shc.silenceengine.collision.colliders.SceneCollider2D;
import com.shc.silenceengine.core.Display;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Batcher;
import com.shc.silenceengine.graphics.Graphics2D;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Scene2D;
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TimeUtils;
import resources.Resources;

/**
 * @author Sri Harsha Chilakapati
 */
public class IntroState extends GameState
{
    private float bgX;

    private OrthoCam        camera;
    private Scene2D         scene;
    private SceneCollider2D collider;

    private GameTimer entitySpawnTimer;

    public IntroState()
    {
        camera = new OrthoCam(Display.getWidth(), Display.getHeight());

        scene = new Scene2D();

        spawnEntities(0, false);
        spawnEntities(800, false);

        collider = new SceneCollider2D(new DynamicTree2D());
        collider.setScene(scene);

        collider.register(Monster.class, Floor.class);
        collider.register(Monster.class, Grass.class);
        collider.register(Monster.class, Thorns.class);

        entitySpawnTimer = new GameTimer(3.5, TimeUtils.Unit.SECONDS);
        entitySpawnTimer.setCallback(() ->
        {
            if (Game.getGameState() == this)
            {
                spawnEntities(800, true);
                entitySpawnTimer.start();
            }
        });
        entitySpawnTimer.start();
    }

    private void spawnEntities(int startX, boolean placeThorns)
    {
        int height = MathUtils.random_range(400, 580);
        int width = MathUtils.random_range(300, 700);

        scene.addChild(new Cloud(new Vector2(startX, height - 400)));

        for (int i = startX, tileCounter = 0; i < startX + width; i += Resources.Textures.FLOOR.getWidth(), tileCounter++)
        {
            if (placeThorns && tileCounter > 1 && MathUtils.chance(10))
                scene.addChild(new Thorns(new Vector2(i, height - Resources.Textures.THORNS.getHeight())));

            scene.addChild(new Grass(new Vector2(i, height - 20)));

            for (int j = height; j < 600; j += Resources.Textures.FLOOR.getHeight())
                scene.addChild(new Floor(new Vector2(i, j), PlayState.SCORE));
        }
    }

    @Override
    public void update(float delta)
    {
        if (Keyboard.isPressed(Keyboard.KEY_ESCAPE))
            Game.end();
        
        if (Keyboard.isClicked(Keyboard.KEY_SPACE))
            Game.setGameState(new PlayState());

        scene.update(delta);
        collider.checkCollisions();

        bgX -= 4;

        if (bgX < -Display.getWidth())
            bgX = 0;
    }

    @Override
    public void render(float delta, Batcher batcher)
    {
        Graphics2D g2d = SilenceEngine.graphics.getGraphics2D();

        g2d.drawTexture(Resources.Textures.BACKGROUND, bgX, 0, Display.getWidth(), Display.getHeight());
        if (bgX < 0)
            g2d.drawTexture(Resources.Textures.BACKGROUND, bgX + Display.getWidth(), 0, Display.getWidth(), Display.getHeight());

        float x = Display.getWidth() / 2 - Resources.Textures.LOGO.getWidth() / 2;
        float y = Display.getHeight() / 2 - Resources.Textures.LOGO.getHeight() / 2;
        
        camera.apply();
        scene.render(delta);

        g2d.drawString("SCORE: " + PlayState.SCORE + "\nBEST: " + PlayState.BEST_SCORE, 10, 10);
        g2d.drawTexture(Resources.Textures.LOGO, x, y);
        
        String message = "Press SPACE to PLAY";
        x = Display.getWidth() - g2d.getFont().getWidth(message) - 10;
        y = 10;
        g2d.drawString(message, x, y);
    }

    @Override
    public void onLeave()
    {
        scene.destroy();
    }
}
