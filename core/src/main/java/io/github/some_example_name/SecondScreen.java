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
    private Game game;  // Reference to the main game
    private SpriteBatch batch;  // For drawing text
    private BitmapFont font;  // Font for the text
    private Stage stage;  // For managing UI elements
    private ShapeRenderer shapeRenderer;  // For drawing shapes

    public SecondScreen(Game game) {
        this.game = game;

        // Initialize SpriteBatch and Stage
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        // Load and generate the font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;  // Set font size to 24
        font = generator.generateFont(parameter);
        generator.dispose();  // Dispose of the generator after use

        // Create buttons
        TextButton backButton = createButton("Back");
        TextButton exitButton = createButton("Exit");

        // Set button positions
        backButton.setPosition(20, Gdx.graphics.getHeight() - backButton.getHeight() - 20); // Top left
        exitButton.setPosition(20, 20); // Bottom left

        // Add button listeners
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ResumedScreen(game)); // Go back to ResumedScreen when clicked
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
        stage.addActor(exitButton);

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font; // Set button font
        style.fontColor = Color.BLACK; // Set font color to black
        return new TextButton(text, style);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Clear the screen with a blue background
        Gdx.gl.glClearColor(0, 0, 1, 1); // Set RGBA to make the background opaque
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Prepare the text to be displayed
        String message = "To be Implemented";

        // Measure the dimensions of the text manually
        float textWidth = 0;
        for (char c : message.toCharArray()) {
            textWidth += font.getData().getGlyph(c).width; // Sum the widths of each character
        }

        float textHeight = font.getCapHeight(); // Get text height

        // Calculate centered position
        float x = (Gdx.graphics.getWidth() - textWidth) / 2; // Center horizontally
        float y = (Gdx.graphics.getHeight() + textHeight) / 2; // Center vertically

        // Draw the text in white at the center
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, message, x, y); // Use calculated positions
        batch.end();

        // Draw white rectangles behind buttons
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE); // Set color to white

        // Iterate through all actors in the stage
        for (int i = 0; i < stage.getActors().size; i++) {
            Actor actor = stage.getActors().get(i);
            if (actor instanceof TextButton) { // Check if the actor is a TextButton
                TextButton button = (TextButton) actor; // Cast to TextButton
                float buttonX = button.getX() - 10; // Adjust for padding
                float buttonY = button.getY() - 10; // Adjust for padding
                shapeRenderer.rect(buttonX, buttonY, button.getWidth() + 20, button.getHeight() + 20); // Draw rectangle behind the button
            }
        }

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
        font.dispose();  // Dispose of the font
        stage.dispose(); // Dispose of the stage
        shapeRenderer.dispose(); // Dispose of the shape renderer
    }
}
