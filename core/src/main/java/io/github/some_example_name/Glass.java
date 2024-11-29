package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Glass extends Block {

    public Glass(World world, Texture texture, float x, float y) {
        super(world, x, y, texture.getWidth(), texture.getHeight());
        this.texture = texture;
        this.health =1;
    }

    public Glass(World world, float x, float y, float height, float width, int health, Texture texture) {
        super(world, x, y, height, width, health, texture);
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
