package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Wood extends Block {
    private Texture texture;

    public Wood(Texture texture, float x, float y) {
        super(x, y);
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
