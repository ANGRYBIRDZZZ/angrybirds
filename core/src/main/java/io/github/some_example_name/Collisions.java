package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Collisions implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object instanceA = contact.getFixtureA().getBody().getUserData();
        Object instanceB = contact.getFixtureB().getBody().getUserData();

        if (instanceA instanceof Bird && instanceB instanceof Pig) {
            Bird bird = (Bird)instanceA;
            Pig pig = (Pig)instanceB;
            pig.health -=1;
            if (pig.health <= 0) {
                GameScreen.toRemovepigs.add(pig);
            }
        }

        if (instanceA instanceof Pig && instanceB instanceof Bird) {
            Pig pig = (Pig)instanceA;
            Bird bird = (Bird)instanceB;
            pig.health -=1;
            System.out.println("fart");
            if (pig.health <= 0) {
                GameScreen.toRemovepigs.add(pig);
            }
        }

        if (instanceA instanceof Bird && instanceB instanceof Block) {
            Bird bird = (Bird)instanceA;
            Block block = (Block)instanceB;
            block.health -=1;

            if (block.health <= 0) {
                GameScreen.toRemoveblocks.add(block);
            }
        }

        if (instanceA instanceof Block && instanceB instanceof Bird) {
            Block block = (Block)instanceA;
            Bird bird = (Bird)instanceB;
            block.health -=1;

            if (block.health <= 0) {
                GameScreen.toRemoveblocks.add(block);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
