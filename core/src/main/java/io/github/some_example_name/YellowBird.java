package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class YellowBird extends Bird {
    public YellowBird(float x, float y) {
        super(new Texture("assets/yellowbird.png"), x, y); // Load the texture specific to YellowBird
    }
}
