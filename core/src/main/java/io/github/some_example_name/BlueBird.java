package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class BlueBird extends Bird {
    public BlueBird(World world, Texture texture, float x, float y) {
        super(world,texture, x, y);
        this.body.getFixtureList().get(0).setRestitution(0.1f);
    }

    public BlueBird(World world, float x, float y, float velocityX, float velocityY, String texture) {
        super(world, x, y, velocityX, velocityY, "bluebird1.png");
        this.body.getFixtureList().get(0).setRestitution(0.1f);
    }
}
