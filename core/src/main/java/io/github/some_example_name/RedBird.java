package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RedBird extends Bird {
    public RedBird(World world, Texture texture, float x, float y) {
        super(world, texture, x, y);
        this.body.getFixtureList().get(0).setRestitution(0.1f);
    }

    public RedBird(World world, float x, float y, float velocityX, float velocityY, String texture) {
        super(world, x, y, velocityX, velocityY, "redbird1.png");
        this.body.getFixtureList().get(0).setRestitution(0.1f);
    }
}
