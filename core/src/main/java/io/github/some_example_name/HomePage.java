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

/** First screen of the application. Displayed after the application is created. */
public class HomePage implements Screen {
    private Game game;  // Reference to the main game
    private SpriteBatch batch; // To draw textures
    private Texture background; // Background image
    private BitmapFont buttonFont1; // Font for buttons
    private BitmapFont buttonFont2; // Font for exit/settings buttons
    private Stage stage; // For managing UI elements
    private ShapeRenderer shapeRenderer; // Added ShapeRenderer instance

    public HomePage(Game game) {
        this.game = game;

        // Initialize SpriteBatch and Stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer(); // Initialize ShapeRenderer

        // Load assets
        background = new Texture(Gdx.files.internal("assets/angrybirdsss.jpg")); // Replace with your image path
        // Create fonts using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf")); // Replace with your font path
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24; // Set font size

        buttonFont1 = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use
        // Create buttons
        TextButton startButton = createButton("New Game", buttonFont1);
        TextButton resumeButton = createButton("Resume", buttonFont1);
        TextButton exitButton = createButton("Exit", buttonFont1);
        TextButton settingsButton = createButton("Settings", buttonFont1);

        // Set button positions
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - 75, Gdx.graphics.getHeight() / 2 + 30); // Centered
        resumeButton.setPosition(Gdx.graphics.getWidth() / 2 - 75, Gdx.graphics.getHeight() / 2 - 30); // Centered
        exitButton.setPosition(20, 20); // Bottom left
        settingsButton.setPosition(Gdx.graphics.getWidth() - 150, 20); // Bottom right
        startButton.setSize(150, 50); // Set a width and height
        resumeButton.setSize(150, 50);
        exitButton.setSize(100, 50);
        settingsButton.setSize(100, 50);

        // Add button listeners
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game)); // Go to next screen when clicked
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ResumedScreen(game)); // Placeholder for resume action
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the game
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsPage(game)); // Placeholder for settings action
            }
        });

        // Add buttons to the stage
        stage.addActor(startButton);
        stage.addActor(resumeButton);
        stage.addActor(exitButton);
        stage.addActor(settingsButton);

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text, BitmapFont font) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Button text cannot be null or empty.");
        }
        TextButtonStyle style = new TextButtonStyle();
        style.font = font; // Set button font
        style.fontColor = Color.BLACK;
        return new TextButton(text, style);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1); // Clear the screen to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Corrected buffer clear

        // Draw the background texture
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw rectangles behind buttons
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1); // Set color to black

        float paddingX = 20; // Horizontal padding
        float paddingY = 10; // Vertical padding

        // Draw rectangles for buttons
        for (Actor button : stage.getActors()) {
            shapeRenderer.rect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
        }

        shapeRenderer.end();
        // Draw the stage (which contains buttons)
        stage.act(delta); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        shapeRenderer.dispose(); // Dispose of ShapeRenderer
        background.dispose();
        buttonFont1.dispose();
    }
}
