package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameScreen implements Screen {
    private Game game;
    private int level;
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private BitmapFont buttonFont;
    private ShapeRenderer shapeRenderer; // For drawing rectangles

    public GameScreen(Game game, int level) {
        this.game = game;
        this.level = level;

        // Initialize SpriteBatch, Stage, and ShapeRenderer
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        // Load the background image for the level
        background = new Texture(Gdx.files.internal("assets/level" + level + ".jpg"));

        // Create and set up the font using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        // Create the buttons with the correct font and style
        createButtons();

        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    private void createButtons() {
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = buttonFont; // Ensure font is set correctly

        // Set font color based on the level (Black for level 4, White for others)
        if (level == 4) {
            buttonStyle.fontColor = Color.BLACK;
        } else {
            buttonStyle.fontColor = Color.WHITE;
        }

        // Create the Back button
        createButton("Back", 20, Gdx.graphics.getHeight() - 50, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game)); // Navigate to ChooseLevelScreen
            }
        });

        // Use the same button style for other buttons
        createButton("Pause", Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 50, buttonStyle);
        createButton("Save", Gdx.graphics.getWidth() - 120, 20, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });
        createButton("Home", 20, 20, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });
        createButton("Exit", 150, 20, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
    private void createButton(String text, float x, float y, TextButtonStyle style) {
        createButton(text, x, y, style, null);
    }

    private void createButton(String text, float x, float y, TextButtonStyle style, ClickListener listener) {
        TextButton button = new TextButton(text, style); // Use provided style with font
        button.setPosition(x, y);
        if (listener != null) button.addListener(listener);
        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the buffer

        // Draw the background image
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw rectangles behind buttons
        drawButtonBackgrounds();

        // Draw the stage containing buttons
        stage.act(delta);
        stage.draw();
    }

    private void drawButtonBackgrounds() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Set rectangle color based on the level
        if (level == 4) {
            shapeRenderer.setColor(Color.WHITE); // White rectangles for level 4
        } else {
            shapeRenderer.setColor(Color.BLACK); // Black rectangles for other levels
        }

        // Draw rectangles behind each button
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextButton) {
                TextButton button = (TextButton) stage.getActors().get(i);
                shapeRenderer.rect(button.getX() - 10, button.getY() - 10, button.getWidth() + 20, button.getHeight() + 20);
            }
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {}

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
        background.dispose();
        buttonFont.dispose();
        shapeRenderer.dispose();
    }
}
