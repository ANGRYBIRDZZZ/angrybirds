package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

public class HomePage implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont buttonFont1;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public HomePage(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("assets/angrybirdsss.jpg"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;

        buttonFont1 = generator.generateFont(parameter);
        generator.dispose();

        TextButton startButton = createButton("New Game", buttonFont1);
        TextButton resumeButton = createButton("Resume", buttonFont1);
        TextButton exitButton = createButton("Exit", buttonFont1);
        TextButton settingsButton = createButton("Settings", buttonFont1);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game));
            }
        });

        resumeButton.addListener(new ClickListener() {
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

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsPage(game));
            }
        });

        stage.addActor(startButton);
        stage.addActor(resumeButton);
        stage.addActor(exitButton);
        stage.addActor(settingsButton);

        Gdx.input.setInputProcessor(stage);

        layoutButtons(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private TextButton createButton(String text, BitmapFont font) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Button text cannot be null or empty.");
        }
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.BLACK;
        return new TextButton(text, style);
    }

    private void layoutButtons(float screenWidth, float screenHeight) {
        float buttonWidth = 0.15f * screenWidth;
        float buttonHeight = 0.05f * screenHeight;

        stage.getActors().get(0).setSize(buttonWidth, buttonHeight);
        stage.getActors().get(1).setSize(buttonWidth, buttonHeight);
        stage.getActors().get(2).setSize(0.1f * screenWidth, buttonHeight);
        stage.getActors().get(3).setSize(0.1f * screenWidth, buttonHeight);

        float buttonSpacing = 5;
        float bottomY = buttonSpacing;

        stage.getActors().get(1).setPosition(screenWidth / 2 - stage.getActors().get(1).getWidth() / 2, bottomY);

        stage.getActors().get(0).setPosition(screenWidth / 2 - stage.getActors().get(0).getWidth() / 2, bottomY + stage.getActors().get(1).getHeight() + buttonSpacing);

        stage.getActors().get(2).setPosition(buttonSpacing, buttonSpacing);
        stage.getActors().get(3).setPosition(screenWidth - stage.getActors().get(3).getWidth() - buttonSpacing, buttonSpacing);
    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);

        float yellowBackgroundWidth = 0.6f;
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
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        shapeRenderer.dispose();
        background.dispose();
        buttonFont1.dispose();
    }
}
