package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SmallPig extends Pig {
    private int health;

    public SmallPig(Texture texture, float x, float y) {
        super(texture, x, y);
        this.health = 10; // Default health for small pig
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
