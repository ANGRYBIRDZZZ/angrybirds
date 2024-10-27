package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Block {
    private float x;
    private float y;

    public Block(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Abstract draw method to be implemented by subclasses
    public abstract void draw(SpriteBatch batch);

    // Abstract dispose method to allow cleanup in subclasses
    public abstract void dispose();
}
