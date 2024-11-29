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
    private Texture heading;

    public HomePage(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("assets/angrybirdsss.png"));
        heading = new Texture(Gdx.files.internal("assets/angrybird.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;

        buttonFont1 = generator.generateFont(parameter);
        generator.dispose();

        // Create buttons
        TextButton startButton = createButton("New Game", buttonFont1);
        TextButton resumeButton = createButton("Resume", buttonFont1);
        TextButton exitButton = createButton("Exit", buttonFont1);

        // Add listeners to buttons
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game));
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen currentGameScreen = new GameScreen(game);
                game.setScreen(new ResumedScreen(game, currentGameScreen));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to the stage
        stage.addActor(startButton);
        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);

        // Layout buttons
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
        float buttonWidth = 0.15f * screenWidth; // Button width as a percentage of screen width
        float buttonHeight = 0.05f * screenHeight; // Button height as a percentage of screen height

        float buttonSpacing = 10; // Space between buttons
        float verticalOffset = screenHeight * 0.3f; // Offset from the top of the screen (30% down)

        // Position the buttons
        float centerY = screenHeight - verticalOffset - (3 * buttonHeight + 2 * buttonSpacing) -300;

        stage.getActors().get(0).setSize(buttonWidth, buttonHeight); // Start button
        stage.getActors().get(1).setSize(buttonWidth, buttonHeight); // Resume button
        stage.getActors().get(2).setSize(buttonWidth, buttonHeight); // Exit button

        stage.getActors().get(0).setPosition(screenWidth / 2 - buttonWidth / 2 -20, centerY + 2 * (buttonHeight + buttonSpacing)); // Start button
        stage.getActors().get(1).setPosition(screenWidth / 2 - buttonWidth / 2 -20, centerY + buttonHeight + buttonSpacing); // Resume button
        stage.getActors().get(2).setPosition(screenWidth / 2 - buttonWidth / 2 -20, centerY); // Exit button
    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Draw the background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the heading texture at the top
        float headingWidth = Gdx.graphics.getWidth() * 0.8f; // Scale it to 80% of screen width
        float headingHeight = headingWidth * (heading.getHeight() / (float) heading.getWidth()); // Maintain aspect ratio
        float headingX = (Gdx.graphics.getWidth() - headingWidth) / 2; // Center the heading
        float headingY = Gdx.graphics.getHeight() - headingHeight - 20; // Offset 20px from the top
        batch.draw(heading, headingX, headingY, headingWidth, headingHeight);

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
        heading.dispose();
    }
}
