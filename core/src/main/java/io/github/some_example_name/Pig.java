package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {
    private Texture texture;
    public Body body;
    public float x;
    public float y;

    public Pig(World world, Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        // Define the body for the pig
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Pigs should move when hit
        bodyDef.position.set(x/1.85f-60,y/1.2f); // Initial position
        this.body = world.createBody(bodyDef);

        // Define the shape for the pig (circle or rectangle)
        CircleShape shape = new CircleShape();
        shape.setRadius(27); // Adjust the radius based on your texture size

        // Define the fixture for the pig
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f; // Adjust density for desired physics behavior
        fixtureDef.friction = 0.3f; // Friction for realistic movement
        fixtureDef.restitution = 0.5f; // Bounciness for some impact response

        body.setFixedRotation(true);
        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use
        shape.dispose();
    }

    public void draw(SpriteBatch batch, float width, float height) {
        batch.draw(texture, this.body.getPosition().x*2.18f-95, this.body.getPosition().y*2f-60, width, height);

    }

    public void dispose() {
        texture.dispose();
    }
}
