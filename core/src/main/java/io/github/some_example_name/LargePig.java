package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class LargePig extends Pig {
    public LargePig(World world, Texture texture, float x, float y) {
        super(world,texture, x, y);
        this.health = 3;
        this.height=125;
        this.width=150;
        this.body.getFixtureList().get(0).getShape().setRadius(27);
    }

    public LargePig(World world, float x, float y, float velocityX, float velocityY, int health, Texture texture) {
        super(world, x, y, velocityX, velocityY, health, texture);

        this.height=125;
        this.width=150;
        this.body.getFixtureList().get(0).getShape().setRadius(30);
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
