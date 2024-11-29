package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Block {
    protected Body body;
    public float width;
    public float height;
    public Texture texture;
    public int health;

    public Block(World world, float x, float y, float width, float height) {
        // Create the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2f,y/2f);
        this.body = world.createBody(bodyDef);
        this.width=width;
        this.height=height;
        // Create the rectangular shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width /4.5f, height /4f);

        // Define fixture properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.005f;
        fixtureDef.restitution = 0.2f; // Adjust for bounciness if needed

        body.setUserData(this);

        body.setFixedRotation(true);
        // Attach the fixture to the body
        this.body.createFixture(fixtureDef);

        // Clean up the shape
        shape.dispose();
    }
    public Block(World world, float x, float y, float height, float width, int health, Texture texture) {
        // Create the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2f,y/2f);
        this.body = world.createBody(bodyDef);
        this.width=width;
        this.height=height;
        this.health = health;
        this.texture = texture;
        // Create the rectangular shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width /4.5f, height /4f);

        // Define fixture properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.005f;
        fixtureDef.restitution = 0.2f; // Adjust for bounciness if needed

        body.setUserData(this);

        body.setFixedRotation(true);
        // Attach the fixture to the body
        this.body.createFixture(fixtureDef);

        // Clean up the shape
        shape.dispose();
    }
    // Method to create the Block's Body in Box2D
    public void createBody(World world, float x, float y, float width, float height) {
        // Create the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 2f, y / 2f);
        this.body = world.createBody(bodyDef);

        this.width = width;
        this.height = height;

        // Create the rectangular shape for the block
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 4.5f, height / 4f);

        // Define fixture properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0.005f;
        fixtureDef.restitution = 0.2f; // Adjust for bounciness if needed

        // Set the body data to this block object
        body.setUserData(this);

        body.setFixedRotation(true);
        // Attach the fixture to the body
        this.body.createFixture(fixtureDef);

        // Clean up the shape
        shape.dispose();
    }

    // Method to reinitialize the Block's Body (used when loading saved game data)
    public void setBody(World world, float x, float y, float velocityX, float velocityY) {
        // Destroy the current body if it exists
        if (body != null) {
            world.destroyBody(body);
        }

        // Recreate the body at the new position
        createBody(world, x, y, this.width, this.height);

        // Set the velocity to the saved value
        body.setLinearVelocity(velocityX, velocityY);
    }
    // Abstract draw method to be implemented by subclasses
    public void draw(SpriteBatch batch) {
        batch.draw(texture, this.body.getPosition().x*2.2f-45-width/2f, this.body.getPosition().y*2f-80, width, height);
    }
    // Abstract dispose method for cleanup
    public abstract void dispose();

    public void setBody(Body blockBody) {
    }
}
