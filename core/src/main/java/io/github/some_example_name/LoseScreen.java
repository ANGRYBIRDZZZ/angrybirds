package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private BitmapFont buttonFont;
    private BitmapFont titleFont;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    public LoseScreen(Game game, int level) {
        this.game = game;

        Music freebird = Gdx.audio.newMusic(Gdx.files.internal("loseSound.mp3"));
        freebird.setVolume(0.5f);
        freebird.play();

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);

        try {
            loseTexture = new Texture(Gdx.files.internal("lose.png"));
        } catch (Exception e) {
            System.err.println("Error loading textures: " + e.getMessage());
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter titleParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParams.size = 200;
        titleFont = generator.generateFont(titleParams);

        FreeTypeFontGenerator.FreeTypeFontParameter buttonParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonParams.size = 35;
        buttonFont = generator.generateFont(buttonParams);

        generator.dispose();

        TextButton restartButton = createButton("Restart", buttonFont);
        TextButton homeButton = createButton("Homepage", buttonFont);
        TextButton exitButton = createButton("Exit", buttonFont);

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

        stage.addActor(restartButton);
        stage.addActor(homeButton);
        stage.addActor(exitButton);

        layoutButtons(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        return new TextButton(text, style);
    }

    private void layoutButtons(float screenWidth, float screenHeight) {
        float buttonWidth = 0.2f * screenWidth;
        float buttonHeight = 0.08f * screenHeight;
        float spacing = 20;

        float rowY = screenHeight * 0.05f;
        float startX = (screenWidth - (buttonWidth * 3 + spacing * 2)) / 2;

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

        if (loseTexture != null) {
            batch.draw(loseTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        titleFont.setColor(Color.BLACK);
        float textWidth = titleFont.getRegion().getRegionWidth();
        float x = (Gdx.graphics.getWidth() - textWidth) / 2 - 25;
        float y = Gdx.graphics.getHeight() - 125;
        titleFont.draw(batch, "BATTLE LOST!", x, y);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        for (Actor actor : stage.getActors()) {
            x = actor.getX();
            y = actor.getY();
            float width = actor.getWidth();
            float height = actor.getHeight();
            shapeRenderer.rect(x - 2, y - 2, width, height);
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
        buttonFont.dispose();
        titleFont.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }
}
