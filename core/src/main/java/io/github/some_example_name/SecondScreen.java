package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SecondScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public SecondScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();

        TextButton backButton = createButton("Back");
        TextButton exitButton = createButton("Exit");

        backButton.setPosition(20, Gdx.graphics.getHeight() - backButton.getHeight() - 20);
        exitButton.setPosition(20, 20);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ResumedScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(backButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.BLACK;
        return new TextButton(text, style);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Clear the screen with a blue background
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        String message = "To be Implemented";

        float textWidth = 0;
        for (char c : message.toCharArray()) {
            textWidth += font.getData().getGlyph(c).width;
        }

        float textHeight = font.getCapHeight();

        float x = (Gdx.graphics.getWidth() - textWidth) / 2;
        float y = (Gdx.graphics.getHeight() + textHeight) / 2;

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, message, x, y);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        for (int i = 0; i < stage.getActors().size; i++) {
            Actor actor = stage.getActors().get(i);
            if (actor instanceof TextButton) {
                TextButton button = (TextButton) actor;
                float buttonX = button.getX() - 10;
                float buttonY = button.getY() - 10;
                shapeRenderer.rect(buttonX, buttonY, button.getWidth() + 20, button.getHeight() + 20);
            }
        }

        shapeRenderer.end();

        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }
}
