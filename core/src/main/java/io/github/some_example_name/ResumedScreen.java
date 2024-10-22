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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ResumedScreen implements Screen {
    private Game game; // Reference to the main game
    private SpriteBatch batch; // To draw textures
    private Texture background; // Background image
    private Stage stage; // For managing UI elements
    private BitmapFont buttonFont; // Font for buttons
    private ShapeRenderer shapeRenderer; // For drawing shapes

    public ResumedScreen(Game game) {
        this.game = game;

        // Initialize SpriteBatch and Stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        // Load assets
        background = new Texture(Gdx.files.internal("assets/angrybirdzzz.jpg")); // Replace with your background image path

        // Create fonts using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf")); // Replace with your font path
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24; // Set font size to 24
        buttonFont = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use

        // Create buttons
        TextButton backButton = createButton("Back", buttonFont); // Create Back button
        TextButton pauseButton = createButton("Pause", buttonFont);
        TextButton saveButton = createButton("Save", buttonFont);
        TextButton exitButton = createButton("Exit", buttonFont);

        // Set button positions
        backButton.setPosition(20, Gdx.graphics.getHeight() - backButton.getHeight() - 20); // Top left
        pauseButton.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50); // Top right
        saveButton.setPosition(Gdx.graphics.getWidth() - 100, 20); // Bottom right
        exitButton.setPosition(20, 20); // Bottom left

        // Add button listeners
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game)); // Go back to HomePage when clicked
            }
        });

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Pause functionality (not implemented yet)
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game)); // Go back to HomePage when clicked
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the game
            }
        });

        // Add buttons to the stage
        stage.addActor(backButton);
        stage.addActor(pauseButton);
        stage.addActor(saveButton);
        stage.addActor(exitButton);

        // Create Saved Game buttons
        createSavedGameButtons();

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font; // Set button font
        style.fontColor = Color.WHITE; // Set font color
        return new TextButton(text, style);
    }

    private void createSavedGameButtons() {
        int numberOfButtons = 4; // Total number of saved game buttons
        float buttonHeight = 50; // Height of each button (you can adjust this if needed)
        float spacing = 10; // Space between buttons

        // Calculate the total height of the buttons including spacing
        float totalHeight = numberOfButtons * buttonHeight + (numberOfButtons - 1) * spacing;
        float startY = (Gdx.graphics.getHeight() - totalHeight) / 2; // Start Y position to center the buttons

        for (int i = 1; i <= numberOfButtons; i++) {
            String buttonText = "Saved Game " + i;
            TextButton savedGameButton = createButton(buttonText, buttonFont);
            savedGameButton.setPosition(Gdx.graphics.getWidth() / 2 - savedGameButton.getWidth() / 2, startY + (i - 1) * (buttonHeight + spacing)); // Center horizontally and position vertically
            savedGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new SecondScreen(game)); // Go to SecondScreen when clicked
                }
            });
            stage.addActor(savedGameButton); // Add to stage
        }
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the buffer

        // Draw the background texture
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw rectangles behind buttons
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Set color to black for existing buttons

        // Draw rectangles behind existing buttons
        drawButtonBackgrounds();

        // Draw red rectangles behind saved game buttons
        shapeRenderer.setColor(Color.RED); // Set color to red
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextButton) {
                TextButton button = (TextButton) stage.getActors().get(i);
                if (button.getText().toString().startsWith("Saved Game")) {
                    shapeRenderer.rect(button.getX() - 10, button.getY() - 10, button.getWidth() + 20, button.getHeight() + 20); // Adjust size for padding
                }
            }
        }

        shapeRenderer.end();

        // Draw the stage (which contains buttons)
        stage.act(delta); // Update the stage
        stage.draw(); // Draw the stage
    }

    private void drawButtonBackgrounds() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextButton) {
                TextButton button = (TextButton) stage.getActors().get(i);
                shapeRenderer.rect(button.getX() - 10, button.getY() - 10, button.getWidth() + 20, button.getHeight() + 20); // Adjust size for padding
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here.
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
        background.dispose();
        buttonFont.dispose(); // Dispose of the button font if created here
    }
}
