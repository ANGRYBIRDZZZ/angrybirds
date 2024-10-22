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

public class ChooseLevelScreen implements Screen {
    private Game game; // Reference to the main game
    private SpriteBatch batch; // To draw textures
    private Stage stage; // For managing UI elements
    private BitmapFont buttonFont; // Font for buttons
    private Texture background; // Background image
    private ShapeRenderer shapeRenderer; // For drawing shapes

    public ChooseLevelScreen(Game game) {
        this.game = game;

        // Initialize SpriteBatch and Stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer(); // Initialize ShapeRenderer

        // Load assets
        background = new Texture(Gdx.files.internal("assets/pigface.png")); // Replace with your image path

        // Create fonts using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf")); // Replace with your font path
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24; // Set font size

        buttonFont = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use

        // Create buttons for levels
        for (int i = 1; i <= 5; i++) {
            final int levelNumber = i; // Capture the level number for the listener
            TextButton levelButton = createButton("Level " + i, buttonFont);
            // Center the button horizontally
            levelButton.setPosition((Gdx.graphics.getWidth() - levelButton.getWidth()) / 2,
                Gdx.graphics.getHeight() / 2 + (i - 3) * 60); // Adjust position for vertical arrangement

            // Add a listener to navigate to the corresponding GameScreen
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, levelNumber)); // Navigate to GameScreen for this level
                }
            });

            // Add the button to the stage
            stage.addActor(levelButton);
        }

        // Create Back button
        TextButton backButton = createButton("Back", buttonFont);
        backButton.setPosition(20, 20); // Bottom left
        backButton.setSize(100, 50); // Set width and height
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game)); // Go back to HomePage when clicked
            }
        });

        // Add Back button to the stage
        stage.addActor(backButton);

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font; // Set button font
        style.fontColor = Color.WHITE; // Set font color
        return new TextButton(text, style);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the buffer

        // Draw the background texture
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Draw rectangles behind each level button
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Set rectangle color to black

        for (int i = 1; i <= 5; i++) {
            TextButton levelButton = (TextButton) stage.getActors().get(i - 1); // Get the level button
            float buttonX = levelButton.getX();
            float buttonY = levelButton.getY();
            float buttonWidth = levelButton.getWidth();
            float buttonHeight = levelButton.getHeight();

            // Increase width and height of the rectangles
            float rectangleWidth = buttonWidth + 27; // Increase to 40 pixels wider
            float rectangleHeight = buttonHeight + 27; // Increase to 40 pixels taller

            // Center the rectangle behind the button
            float rectangleX = buttonX - 15; // Shift left by 20 pixels
            float rectangleY = buttonY - 15; // Shift down by 20 pixels

            // Draw the rectangle behind the button
            shapeRenderer.rect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
        }

        shapeRenderer.end();

        // Draw red rectangle behind the Back button
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED); // Set rectangle color to red

        TextButton backButton = (TextButton) stage.getActors().get(5); // Get the Back button
        float backButtonX = backButton.getX();
        float backButtonY = backButton.getY();
        float backButtonWidth = backButton.getWidth();
        float backButtonHeight = backButton.getHeight();

        // Draw the red rectangle behind the Back button
        shapeRenderer.rect(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        shapeRenderer.end();

        // Draw the stage (which contains buttons)
        stage.act(delta); // Update the stage
        stage.draw(); // Draw the stage
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
        stage.dispose();
        background.dispose();
        buttonFont.dispose(); // Dispose of the button font
        shapeRenderer.dispose(); // Dispose of ShapeRenderer
    }
}
