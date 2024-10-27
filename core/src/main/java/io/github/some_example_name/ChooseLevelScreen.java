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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
    private Texture episodeImage; // Texture for episode image
    private ShapeRenderer shapeRenderer; // For drawing shapes

    // Textures for level buttons
    private Texture level1Texture, level2Texture, level3Texture;

    public ChooseLevelScreen(Game game) {
        this.game = game;

        // Initialize SpriteBatch and Stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer(); // Initialize ShapeRenderer

        // Load assets
        background = new Texture(Gdx.files.internal("assets/pigface.png")); // Background image
        episodeImage = new Texture(Gdx.files.internal("assets/episode.png")); // Episode image
        episodeImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Set filtering
        level1Texture = new Texture(Gdx.files.internal("assets/button1.png")); // Level 1 button texture
        level1Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        level2Texture = new Texture(Gdx.files.internal("assets/button2.png")); // Level 2 button texture
        level2Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        level3Texture = new Texture(Gdx.files.internal("assets/button3.png")); // Level 3 button texture
        level3Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Create fonts using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf")); // Replace with your font path
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40; // Set font size

        buttonFont = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use

        // Create buttons for levels 1, 2, and 3
        createLevelButton(level1Texture, "Level 1", 1, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2+50); // Positioned for Level 1
        createLevelButton(level2Texture, "Level 2", 2, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2+50); // Positioned for Level 2
        createLevelButton(level3Texture, "Level 3", 3, (3 * Gdx.graphics.getWidth()) / 4, Gdx.graphics.getHeight() / 2+50); // Positioned for Level 3

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

    private void createLevelButton(Texture texture, String levelText, final int levelNumber, float xPosition, float yPosition) {
        // Create a button that uses the texture
        TextButton levelButton = createButton("", buttonFont); // Create an empty button

        // Increase button size here
        levelButton.setSize(texture.getWidth() + 20, texture.getHeight() + 20); // Adjust the size

        levelButton.setPosition(xPosition - levelButton.getWidth() / 2, yPosition - levelButton.getHeight() / 2); // Center the button on the position

        // Add a listener to navigate to the corresponding GameScreen
        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, levelNumber)); // Navigate to GameScreen for this level
            }
        });

        // Add the button to the stage
        stage.addActor(levelButton);

        // Draw text over the button
        levelButton.getLabel().setText(levelText); // Set the level text
        levelButton.getLabel().setColor(Color.WHITE); // Set label color
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

        // Define a scaling factor for the episode image
        float episodeScale = 3.5f; // Adjust this value as needed

        // Draw the episode image at the top center with scaling
        float episodeX = (Gdx.graphics.getWidth() - episodeImage.getWidth() * episodeScale) / 2; // Center the image horizontally
        float episodeY = Gdx.graphics.getHeight() - episodeImage.getHeight() * episodeScale - 58; // Move it down by approximately 1 cm (about 38 pixels)
        batch.draw(episodeImage, episodeX, episodeY, episodeImage.getWidth() * episodeScale, episodeImage.getHeight() * episodeScale); // Draw the scaled image

        // Define scaling factor for button images
        float scale = 3f; // 20% increase

        // Draw the level button textures behind the labels with scaling
        batch.draw(level1Texture, (Gdx.graphics.getWidth() / 4) - (level1Texture.getWidth() * scale / 2),
            Gdx.graphics.getHeight() / 2+50 - (level1Texture.getHeight() * scale / 2),
            level1Texture.getWidth() * scale, level1Texture.getHeight() * scale); // Scaled dimensions

        batch.draw(level2Texture, (Gdx.graphics.getWidth() / 2) - (level2Texture.getWidth() * scale / 2),
            Gdx.graphics.getHeight() / 2+50 - (level2Texture.getHeight() * scale / 2),
            level2Texture.getWidth() * scale, level2Texture.getHeight() * scale); // Scaled dimensions

        batch.draw(level3Texture, (3 * Gdx.graphics.getWidth() / 4) - (level3Texture.getWidth() * scale / 2),
            Gdx.graphics.getHeight() / 2+50 - (level3Texture.getHeight() * scale / 2),
            level3Texture.getWidth() * scale, level3Texture.getHeight() * scale); // Scaled dimensions

        batch.end();

        // Draw the stage (which contains buttons and text)
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
        episodeImage.dispose(); // Dispose of episode image
        level1Texture.dispose(); // Dispose of level 1 texture
        level2Texture.dispose(); // Dispose of level 2 texture
        level3Texture.dispose(); // Dispose of level 3 texture
        buttonFont.dispose(); // Dispose of the button font
        shapeRenderer.dispose(); // Dispose of ShapeRenderer
    }
}
