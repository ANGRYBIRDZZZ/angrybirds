package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    public Texture texture;
    public Body body;
    public float x;
    public float y;
    public int health;
    public int width, height;

    public Pig(World world, Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 1.8f - 60, y / 1f);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(25);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.05f;

        body.setUserData(this);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Pig(World world, float x, float y, float velocityX, float velocityY, int health, Texture texture) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 1.8f - 60, y / 1f);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(25);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.1f;

        body.setUserData(this);
        this.body.setLinearVelocity(velocityX, velocityY);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 1.8f - 60, y / 1f);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(25);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.1f;

        body.setUserData(this);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void setBody(World world, float x, float y, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;

        if (this.body != null) {
            world.destroyBody(this.body);
        }

        createBody(world);
        this.body.setLinearVelocity(velocityX, velocityY);
    }

    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, this.body.getPosition().x * 2.18f - 50 - width / 15 - width / 3, this.body.getPosition().y * 2f - 55 - height / 100, width, height);
    }

    public void dispose() {
        texture.dispose();
    }

    public void setBody(Body pigBody) {
    }
}
