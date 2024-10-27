package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class RedBird extends Bird {
    public RedBird(float x, float y) {
        super(new Texture("assets/redbird1.png"), x, y); // Load the texture specific to RedBird
    }
}
