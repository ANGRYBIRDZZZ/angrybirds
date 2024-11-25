package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoseScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture loseTexture;
    private Texture looseTexture;
    private BitmapFont buttonFont;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public LoseScreen(Game game, int level) {
        this.game = game;

        // Initialize essential components
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);

        // Load textures
        try {
            loseTexture = new Texture(Gdx.files.internal("lose.png"));
            looseTexture = new Texture(Gdx.files.internal("loose.png"));
        } catch (Exception e) {
            System.err.println("Error loading textures: " + e.getMessage());
        }

        // Load font
        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(1.5f);

        // Create Buttons
        TextButton restartButton = createButton("Restart", buttonFont);
        TextButton homeButton = createButton("Homepage", buttonFont);
        TextButton exitButton = createButton("Exit", buttonFont);

        // Add Listeners
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, level));
                dispose();
            }
        });

        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
                dispose();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to stage
        stage.addActor(restartButton);
        stage.addActor(homeButton);
        stage.addActor(exitButton);

        // Layout buttons
        layoutButtons(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE; // White text
        return new TextButton(text, style);
    }

    private void layoutButtons(float screenWidth, float screenHeight) {
        float buttonWidth = 0.2f * screenWidth;
        float buttonHeight = 0.08f * screenHeight;
        float spacing = 20;

        float rowY = screenHeight * 0.05f; // Vertical position for the button row
        float startX = (screenWidth - (buttonWidth * 3 + spacing * 2)) / 2; // Center the buttons

        // Position buttons horizontally
        for (int i = 0; i < stage.getActors().size; i++) {
            Actor button = stage.getActors().get(i);
            float x = startX + i * (buttonWidth + spacing);
            button.setSize(buttonWidth, buttonHeight);
            button.setPosition(x, rowY);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw the background texture
        if (loseTexture != null) {
            batch.draw(loseTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        // Draw the "loose" texture
        if (looseTexture != null) {
            float looseImageHeight = Gdx.graphics.getHeight() * 0.15f;
            float looseImageWidth = looseImageHeight * looseTexture.getWidth() / looseTexture.getHeight();
            batch.draw(looseTexture, (Gdx.graphics.getWidth() - looseImageWidth) / 2,
                Gdx.graphics.getHeight() - looseImageHeight, looseImageWidth, looseImageHeight);
        }

        batch.end();

        // Draw a black background for each button
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        for (Actor actor : stage.getActors()) {
            float x = actor.getX(); // Button's X position
            float y = actor.getY(); // Button's Y position
            float width = actor.getWidth(); // Button's width
            float height = actor.getHeight(); // Button's height

            // Draw a rectangle slightly larger than the button
            shapeRenderer.rect(x - 5, y - 2, width + 10, height ); // Reduced padding
            // Padding around the button
        }

        shapeRenderer.end();

        // Draw buttons
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        layoutButtons(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        if (loseTexture != null) loseTexture.dispose();
        if (looseTexture != null) looseTexture.dispose();
        buttonFont.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }
}
