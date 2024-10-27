package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WinScreen {
    private Texture winTexture;

    public WinScreen() {
        winTexture = new Texture(Gdx.files.internal("assets/win.png"));
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(winTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draw the win image
        batch.end();
    }

    public void dispose() {
        winTexture.dispose(); // Dispose of the texture
    }
}
