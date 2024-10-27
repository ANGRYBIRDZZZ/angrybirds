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

public class SettingsPage implements Screen {
    private Game game; // Reference to the main game
    private SpriteBatch batch; // To draw textures
    private Texture background; // Background image
    private Stage stage; // For managing UI elements
    private BitmapFont buttonFont; // Font for buttons
    private ShapeRenderer shapeRenderer; // For drawing shapes

    public SettingsPage(Game game) {
        this.game = game;
        // Initialize SpriteBatch and Stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        // Load assets
        background = new Texture(Gdx.files.internal("assets/settingsss.png")); // Replace with your settings image path
        // Create fonts using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf")); // Replace with your font path
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24; // Set font size to 24

        buttonFont = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use

        // Create buttons
        TextButton backButton = createButton("Back", buttonFont);

        // Set button positions
        backButton.setPosition(20, 20); // Bottom left
        backButton.setSize(100, 50); // Set width and height

        // Add button listener
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game)); // Go back to HomePage when clicked
            }
        });

        // Add buttons to the stage
        stage.addActor(backButton);

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font; // Set button font
        style.fontColor = Color.DARK_GRAY; // Set font color
        return new TextButton(text, style);
    }

    public void show() {
        // Prepare your screen here.
    }
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen to white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the buffer

        // Draw the background texture
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 1, 1);

        TextButton backButton = (TextButton) stage.getActors().get(0); // Assuming the first actor is the back button
        shapeRenderer.rect(backButton.getX(), backButton.getY(), backButton.getWidth(), backButton.getHeight());
        shapeRenderer.end();

        // Draw the stage (which contains buttons)
        stage.act(delta); // Update the stage
        stage.draw(); // Draw the stage
    }
    public void resize(int width, int height) {
        // Resize your screen here.
    }
    public void pause() {}

    public void resume() {}

    public void hide() {}

    public void dispose() {
        batch.dispose();
        stage.dispose();
        background.dispose();
        buttonFont.dispose();
    }

}
