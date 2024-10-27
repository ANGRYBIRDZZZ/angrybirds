package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pig {
    private Texture texture;
    private float x;
    private float y;

    public Pig(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

}

