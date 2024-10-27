package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {
    protected Texture texture; // Texture for the bird
    protected float x; // X position
    protected float y; // Y position

    public Bird(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    // Updated draw method to accept width and height for scaling
    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, x, y, width, height); // Use width and height for drawing
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Texture getTexture() {
        return texture; // Access the texture
    }

    public void dispose() {
        texture.dispose(); // Dispose texture in the base class
    }
}
