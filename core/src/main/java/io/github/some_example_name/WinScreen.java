package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WinScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont buttonFont;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public WinScreen(Game game) {
        this.game = game;

        Music freebird = Gdx.audio.newMusic(Gdx.files.internal("winSound.mp3"));
        freebird.setVolume(0.5f);
        freebird.play();

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("win.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;

        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        TextButton homeButton = createButton("Return to Homepage", buttonFont);
        TextButton levelButton = createButton("Choose Another Level", buttonFont);
        TextButton exitButton = createButton("Exit", buttonFont);

        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });

        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(homeButton);
        stage.addActor(levelButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);

        layoutButtons(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private TextButton createButton(String text, BitmapFont font) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Button text cannot be null or empty.");
        }
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.YELLOW;
        return new TextButton(text, style);
    }

    private void layoutButtons(float screenWidth, float screenHeight) {
        float buttonWidth = 0.2f * screenWidth;
        float buttonHeight = 0.05f * screenHeight;
        float spacing = 20;

        for (int i = 0; i < stage.getActors().size; i++) {
            Actor button = stage.getActors().get(i);
            button.setSize(buttonWidth, buttonHeight);
            float x = (screenWidth - buttonWidth) / 2;
            float y = screenHeight / 2 + (buttonHeight + spacing) * (stage.getActors().size / 2 - i - 1) - 250;
            button.setPosition(x, y);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);

        float yellowBackgroundWidth = 0.8f;
        float padding = 10;

        for (Actor button : stage.getActors()) {
            float rectWidth = button.getWidth() * yellowBackgroundWidth;
            float rectHeight = button.getHeight() - padding;

            float rectX = button.getX() + (button.getWidth() - rectWidth) / 2;
            float rectY = button.getY() + (padding / 2);

            shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);
        }

        shapeRenderer.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        layoutButtons(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        shapeRenderer.dispose();
        background.dispose();
        buttonFont.dispose();
    }
}
