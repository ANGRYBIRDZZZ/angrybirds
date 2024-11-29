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
        // Define the body for the pig
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs should move when hit
        bodyDef.position.set(x/1.8f-60,y/1f); // Initial position
        this.body = world.createBody(bodyDef);

        // Define the shape for the pig (circle or rectangle)
        CircleShape shape = new CircleShape();
        shape.setRadius(25); // Adjust the radius based on your texture size

        // Define the fixture for the pig
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f; // Adjust density for desired physics behavior
        fixtureDef.friction = 0.25f; // Friction for realistic movement
        fixtureDef.restitution = 0.05f; // Bounciness for some impact response

        body.setUserData(this);

        body.setFixedRotation(true);
        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use
        shape.dispose();
    }
    public Pig(World world, float x, float y, float velocityX, float velocityY, int health, Texture texture) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        // Define the body for the pig
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs should move when hit
        bodyDef.position.set(x/1.8f-60,y/1f); // Initial position
        this.body = world.createBody(bodyDef);

        // Define the shape for the pig (circle or rectangle)
        CircleShape shape = new CircleShape();
        shape.setRadius(25); // Adjust the radius based on your texture size

        // Define the fixture for the pig
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f; // Adjust density for desired physics behavior
        fixtureDef.friction = 0.25f; // Friction for realistic movement
        fixtureDef.restitution = 0.1f; // Bounciness for some impact response

        body.setUserData(this);
        this.body.setLinearVelocity(velocityX, velocityY);
        body.setFixedRotation(true);
        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use
        shape.dispose();

    }
    // Method to create the pig's body (shared across the constructors)
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

    // New method to set the body (used when reinitializing)
    public void setBody(World world, float x, float y, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;

        // Remove old body if necessary (optional based on your game setup)
        if (this.body != null) {
            world.destroyBody(this.body);
        }

        createBody(world);  // Create a new body at the loaded position

        // Set the velocity
        this.body.setLinearVelocity(velocityX, velocityY);
    }


    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, this.body.getPosition().x*2.18f-50-width/15-width/3, this.body.getPosition().y*2f-55-height/100, width, height);

    }

    public void dispose() {
        texture.dispose();
    }

    public void setBody(Body pigBody) {
    }
}
