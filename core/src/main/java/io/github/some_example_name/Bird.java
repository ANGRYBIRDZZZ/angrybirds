package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {
    protected Texture texture;
    protected float x;
    protected float y;

    public Bird(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        texture.dispose();
    }
}
