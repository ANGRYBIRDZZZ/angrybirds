package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {
    private Texture texture; // Texture for the bird
    private float x; // X position
    private float y; // Y position

    public Bird(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
