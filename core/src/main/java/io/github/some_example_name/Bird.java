package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    protected Texture texture;
    protected float x;
    protected float y;
    public Body body;
    public int height;
    public int width;

    public Bird(World world, Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 1.9f, y / 1.9f);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(20);
        if (this instanceof BlueBird) {
            shape.setRadius(14);
        }
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.5f;
        body.setUserData(this);
        Vector2 gravityForce = new Vector2(0, -200f);
        this.body.applyForceToCenter(gravityForce, true);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Bird(World world, float x, float y, float velocityX, float velocityY, String texture) {
        this.texture = new Texture(texture);
        this.x = x;
        this.y = y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 1.9f, y / 1.9f);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape shape = new CircleShape();
        shape.setRadius(20);
        if (this instanceof BlueBird) {
            shape.setRadius(14);
        }
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;
        body.setUserData(this);
        this.body.setLinearVelocity(velocityX, velocityY);
        Vector2 gravityForce = new Vector2(0, -200f);
        this.body.applyForceToCenter(gravityForce, true);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, this.body.getPosition().x * 2.15f - width / 2f - 10, this.body.getPosition().y * 1.95f - 40, width, height);
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(x - 10, y - 30, 70, 70);
    }

}
