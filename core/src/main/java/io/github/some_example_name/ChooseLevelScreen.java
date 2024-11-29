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
    private Game game;
    private SpriteBatch batch;
    private Stage stage;
    private BitmapFont buttonFont;
    private Texture background;
    private Texture episodeImage;
    private ShapeRenderer shapeRenderer;

    private Texture level1Texture, level2Texture, level3Texture;

    public ChooseLevelScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("assets/pigface.png"));
        episodeImage = new Texture(Gdx.files.internal("assets/episode.png"));
        episodeImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        level1Texture = new Texture(Gdx.files.internal("assets/button1.png"));
        level1Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        level2Texture = new Texture(Gdx.files.internal("assets/button2.png"));
        level2Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        level3Texture = new Texture(Gdx.files.internal("assets/button3.png"));
        level3Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40;

        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        createLevelButton(level1Texture, "Level 1", 1, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2+50);
        createLevelButton(level2Texture, "Level 2", 2, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2+50);
        createLevelButton(level3Texture, "Level 3", 3, (3 * Gdx.graphics.getWidth()) / 4, Gdx.graphics.getHeight() / 2+50);

        TextButton backButton = createButton("Back", buttonFont);
        backButton.setPosition(20, 20);
        backButton.setSize(100, 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });

        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void createLevelButton(Texture texture, String levelText, final int levelNumber, float xPosition, float yPosition) {
        TextButton levelButton = createButton("", buttonFont);

        levelButton.setSize(texture.getWidth() + 20, texture.getHeight() + 20);

        levelButton.setPosition(xPosition - levelButton.getWidth() / 2, yPosition - levelButton.getHeight() / 2);

        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, levelNumber));
            }
        });

        stage.addActor(levelButton);

        levelButton.getLabel().setText(levelText);
        levelButton.getLabel().setColor(Color.WHITE);
    }

    private TextButton createButton(String text, BitmapFont font) {
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        return new TextButton(text, style);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float episodeScale = 3.5f;

        float episodeX = (Gdx.graphics.getWidth() - episodeImage.getWidth() * episodeScale) / 2;
        float episodeY = Gdx.graphics.getHeight() - episodeImage.getHeight() * episodeScale - 58;
        batch.draw(episodeImage, episodeX, episodeY, episodeImage.getWidth() * episodeScale, episodeImage.getHeight() * episodeScale);

        float scale = 3f;

        batch.draw(level1Texture, (Gdx.graphics.getWidth() / 4) - (level1Texture.getWidth() * scale / 2),
            Gdx.graphics.getHeight() / 2+50 - (level1Texture.getHeight() * scale / 2),
            level1Texture.getWidth() * scale, level1Texture.getHeight() * scale);

        batch.draw(level2Texture, (Gdx.graphics.getWidth() / 2) - (level2Texture.getWidth() * scale / 2),
            Gdx.graphics.getHeight() / 2+50 - (level2Texture.getHeight() * scale / 2),
            level2Texture.getWidth() * scale, level2Texture.getHeight() * scale);

        batch.draw(level3Texture, (3 * Gdx.graphics.getWidth() / 4) - (level3Texture.getWidth() * scale / 2),
            Gdx.graphics.getHeight() / 2+50 - (level3Texture.getHeight() * scale / 2),
            level3Texture.getWidth() * scale, level3Texture.getHeight() * scale);

        batch.end();

        stage.act(delta);
        stage.draw();
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
        episodeImage.dispose();
        level1Texture.dispose();
        level2Texture.dispose();
        level3Texture.dispose();
        buttonFont.dispose();
        shapeRenderer.dispose();
    }
}
