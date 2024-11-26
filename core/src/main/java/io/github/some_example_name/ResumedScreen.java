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
    private Game game;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private BitmapFont buttonFont;
    private ShapeRenderer shapeRenderer;
    private GameScreen gameScreen;

    public ResumedScreen(Game game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("assets/angrybirdzzz.jpg")); // Replace with your background image path

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf")); // Replace with your font path
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        TextButton backButton = createButton("Back", buttonFont);
        TextButton saveButton = createButton("Save", buttonFont);
        TextButton exitButton = createButton("Exit", buttonFont);

        backButton.setPosition(20, Gdx.graphics.getHeight() - backButton.getHeight() - 20);
        saveButton.setPosition(Gdx.graphics.getWidth() - 100, 20);
        exitButton.setPosition(20, 20);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.saveGameState();
                game.setScreen(new HomePage(game));
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(backButton);
        stage.addActor(saveButton);
        stage.addActor(exitButton);

        createSavedGameButtons();

        Gdx.input.setInputProcessor(stage);
    }
    public ResumedScreen(Game game) {
    }
    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        return new TextButton(text, style);
    }

    private void createSavedGameButtons() {
        int numberOfButtons = 4;
        float buttonHeight = 50;
        float spacing = 10;

        float totalHeight = numberOfButtons * buttonHeight + (numberOfButtons - 1) * spacing;
        float startY = (Gdx.graphics.getHeight() - totalHeight) / 2;

        for (int i = 1; i <= numberOfButtons; i++) {
            String buttonText = "Saved Game " + i;
            TextButton savedGameButton = createButton(buttonText, buttonFont);
            savedGameButton.setPosition(Gdx.graphics.getWidth() / 2 - savedGameButton.getWidth() / 2, startY + (i - 1) * (buttonHeight + spacing));
            savedGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new SecondScreen(game));
                }
            });
            stage.addActor(savedGameButton);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        drawButtonBackgrounds();

        shapeRenderer.setColor(Color.RED);
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextButton) {
                TextButton button = (TextButton) stage.getActors().get(i);
                if (button.getText().toString().startsWith("Saved Game")) {
                    shapeRenderer.rect(button.getX() - 10, button.getY() - 10, button.getWidth() + 20, button.getHeight() + 20);
                }
            }
        }

        shapeRenderer.end();

        stage.act(delta);
        stage.draw();
    }

    private void drawButtonBackgrounds() {
        for (int i = 0; i < stage.getActors().size; i++) {
            if (stage.getActors().get(i) instanceof TextButton) {
                TextButton button = (TextButton) stage.getActors().get(i);
                shapeRenderer.rect(button.getX() - 10, button.getY() - 10, button.getWidth() + 20, button.getHeight() + 20);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
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
        buttonFont.dispose();
    }
}
