package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LargePig extends Pig {
    private int health;

    public LargePig(Texture texture, float x, float y) {
        super(texture, x, y);
        this.health = 30; // Default health for large pig
    }

    public void takeHit(int damage) {
        if (health > 0) {
            health -= damage;
        }
    }

    public boolean isBroken() {
        return health <= 0;
    }

    @Override
    public void draw(SpriteBatch batch, float width, float height) {
        if (!isBroken()) {
            super.draw(batch, width, height);
        }
    }
}
