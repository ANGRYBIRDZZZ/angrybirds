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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.*;
import com.badlogic.gdx.Input; // Add this import


public class GameScreen implements Screen {
    private Game game;
    private int level;
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private BitmapFont buttonFont;
    private ShapeRenderer shapeRenderer;
    private Bird redBird1;
    private Bird redBird2;
    private Bird redBird3;
    private Bird yellowBird1;
    private Bird yellowBird2;
    private Bird yellowBird3;
    private BlueBird blueBird1;
    private BlueBird blueBird2;
    private BlueBird blueBird3;
    private Texture slingshotLeft;
    private Texture slingshotRight;
    List<Wood> woodBlocks = new ArrayList<>();
    private List<Pig> pigs1;
    private List<Glass> glasses;
    private List<Pig> pigs3;
    private List<Metal> metalBlocks;
    private List<Pig> pigs2;
    private WinScreen winScreen;
    private boolean showWinScreen = false;

    public GameScreen(Game game, int level) {
        this.game = game;
        this.level = level;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("assets/level" + level + ".jpg"));
        slingshotLeft = new Texture(Gdx.files.internal("assets/slingshotleft.png"));
        slingshotRight = new Texture(Gdx.files.internal("assets/slingshotright.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        createButtons();

        if (level == 1) {
            Texture redBirdTexture1 = new Texture(Gdx.files.internal("assets/redbird1.png"));
            Texture redBirdTexture2 = new Texture(Gdx.files.internal("assets/redbird2.png"));
            Texture redBirdTexture3 = new Texture(Gdx.files.internal("assets/redbird3.png"));

            redBird1 = new Bird(redBirdTexture1, 50, 280);
            redBird2 = new Bird(redBirdTexture2, 150, 280);
            redBird3 = new Bird(redBirdTexture3, 250, 280);

            Texture woodTexture1 = new Texture(Gdx.files.internal("assets/wood1.png"));
            Texture woodTexture2 = new Texture(Gdx.files.internal("assets/wood2.png"));
            Texture woodTexture3 = new Texture(Gdx.files.internal("assets/wood3.png"));
            Texture woodTexture4 = new Texture(Gdx.files.internal("assets/wood4.png"));
            Texture woodTexture5 = new Texture(Gdx.files.internal("assets/wood5.png"));
            Texture woodTexture6 = new Texture(Gdx.files.internal("assets/wood6.png"));

            woodBlocks.add(new Wood(woodTexture1, 1075, 280));
            woodBlocks.add(new Wood(woodTexture2, 1225, 355));
            woodBlocks.add(new Wood(woodTexture3, 1375, 425));
            woodBlocks.add(new Wood(woodTexture4, 1375, 280));
            woodBlocks.add(new Wood(woodTexture5, 1300, 280));
            woodBlocks.add(new Wood(woodTexture6, 1600, 280));

            pigs1 = new ArrayList<>();

            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture2 = new Texture(Gdx.files.internal("assets/pig2.png"));
            Texture pigTexture3 = new Texture(Gdx.files.internal("assets/pig3.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));

            pigs1.add(new Pig(pigTexture1, 1080, 350));
            pigs1.add(new Pig(pigTexture2, 1390, 450));
            pigs1.add(new Pig(pigTexture3, 1425, 300));
            pigs1.add(new Pig(pigTexture4, 1225, 500));
            pigs1.add(new Pig(pigTexture5, 1390, 565));
        }

        if (level == 2) {
            Texture yellowBirdTexture1 = new Texture(Gdx.files.internal("assets/yellowbird1.png"));
            Texture yellowBirdTexture2 = new Texture(Gdx.files.internal("assets/yellowbird2.png"));
            Texture yellowBirdTexture3 = new Texture(Gdx.files.internal("assets/yellowbird3.png"));

            yellowBird1 = new Bird(yellowBirdTexture1, 50, 260);
            yellowBird2 = new Bird(yellowBirdTexture2, 150, 260);
            yellowBird3 = new Bird(yellowBirdTexture3, 250, 260);

            Texture metalTexture1 = new Texture(Gdx.files.internal("assets/metal1.png"));
            Texture metalTexture2 = new Texture(Gdx.files.internal("assets/metal2.png"));
            Texture metalTexture3 = new Texture(Gdx.files.internal("assets/metal3.png"));
            Texture metalTexture4 = new Texture(Gdx.files.internal("assets/metal4.png"));
            Texture metalTexture5 = new Texture(Gdx.files.internal("assets/metal5.png"));

            metalBlocks = new ArrayList<>();

            metalBlocks.add(new Metal(metalTexture1, 1125, 260)); // Adjust x and y as needed
            metalBlocks.add(new Metal(metalTexture2, 1200, 260)); // Adjust x and y as needed
            metalBlocks.add(new Metal(metalTexture3, 1250, 400)); // Adjust x and y as needed
            metalBlocks.add(new Metal(metalTexture4, 1425, 260)); // Adjust x and y as needed
            metalBlocks.add(new Metal(metalTexture5, 1100, 475)); // Adjust x and y as needed

            pigs2 = new ArrayList<>();

            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture2 = new Texture(Gdx.files.internal("assets/pig2.png"));
            Texture pigTexture3 = new Texture(Gdx.files.internal("assets/pig3.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));

            pigs2.add(new Pig(pigTexture1, 1260, 425)); // Adjust x and y as needed
            pigs2.add(new Pig(pigTexture2, 1500, 260)); // Adjust x and y as needed
            pigs2.add(new Pig(pigTexture3, 1405, 480)); // Adjust x and y as needed
            pigs2.add(new Pig(pigTexture4, 1110, 610)); // Adjust x and y as needed
            pigs2.add(new Pig(pigTexture5, 1260, 275)); // Adjust x and y as needed


        }

        if (level == 3) {
            Texture blueBirdTexture1 = new Texture(Gdx.files.internal("assets/bluebird1.png"));
            Texture blueBirdTexture2 = new Texture(Gdx.files.internal("assets/bluebird2.png"));
            Texture blueBirdTexture3 = new Texture(Gdx.files.internal("assets/bluebird3.png"));

            blueBird1 = new BlueBird(blueBirdTexture1, 135, 165);
            blueBird2 = new BlueBird(blueBirdTexture2, 205, 165);
            blueBird3 = new BlueBird(blueBirdTexture3, 275, 165);

            glasses = new ArrayList<>();

            Texture glassTexture1 = new Texture(Gdx.files.internal("assets/glass1.png"));
            Texture glassTexture2 = new Texture(Gdx.files.internal("assets/glass2.png"));
            Texture glassTexture3 = new Texture(Gdx.files.internal("assets/glass3.png"));
            Texture glassTexture4 = new Texture(Gdx.files.internal("assets/glass4.png"));
            Texture glassTexture5 = new Texture(Gdx.files.internal("assets/glass5.png"));

            glasses.add(new Glass(glassTexture1, 1400, 175));
            glasses.add(new Glass(glassTexture2, 1250, 175));
            glasses.add(new Glass(glassTexture3, 1100, 175));
            glasses.add(new Glass(glassTexture4, 1250, 475));
            glasses.add(new Glass(glassTexture5, 1250, 325));

            pigs3 = new ArrayList<>();

            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture2 = new Texture(Gdx.files.internal("assets/pig2.png"));
            Texture pigTexture3 = new Texture(Gdx.files.internal("assets/pig3.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));

            pigs3.add(new Pig(pigTexture1, 1115, 190)); // Adjust x and y as needed
            pigs3.add(new Pig(pigTexture2, 1400, 325)); // Adjust x and y as needed
            pigs3.add(new Pig(pigTexture3, 1410, 205)); // Adjust x and y as needed
            pigs3.add(new Pig(pigTexture4, 1125, 325)); // Adjust x and y as needed
            pigs3.add(new Pig(pigTexture5, 1260, 625)); // Adjust x and y as needed

        }

        winScreen = new WinScreen();

        Gdx.input.setInputProcessor(stage);
    }

    private void createButtons() {
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = buttonFont;

        buttonStyle.fontColor = level == 4 ? Color.BLACK : Color.WHITE;

        createButton("Back", 20, Gdx.graphics.getHeight() - 50, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game));
            }
        });

        createButton("Pause", Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 50, buttonStyle);
        createButton("Save", Gdx.graphics.getWidth() - 120, 20, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });
        createButton("Home", 20, 20, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomePage(game));
            }
        });
        createButton("Exit", 150, 20, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void createButton(String text, float x, float y, TextButtonStyle style) {
        createButton(text, x, y, style, null);
    }

    private void createButton(String text, float x, float y, TextButtonStyle style, ClickListener listener) {
        TextButton button = new TextButton(text, style);
        button.setPosition(x, y);
        if (listener != null) button.addListener(listener);
        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.isKeyPressed(Input.Keys.W) && !showWinScreen) {
            showWinScreen = true;
            winScreen = new WinScreen();
        }

        if (!showWinScreen) {
            if (level == 1) {
                if (redBird1 != null) redBird1.draw(batch, 100, 100);
                if (redBird2 != null) redBird2.draw(batch, 100, 100);
                if (redBird3 != null) redBird3.draw(batch, 100, 100);

                batch.draw(slingshotLeft, 365, 400, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);
                batch.draw(slingshotRight, 400, 290, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);
                for (Wood block : woodBlocks) {
                    block.draw(batch);
                }
                float pigWidth = 115;  // Set desired width
                float pigHeight = 100; // Set desired height

                for (Pig pig : pigs1) {
                    pig.draw(batch, pigWidth, pigHeight);
                }
            }

            if (level == 2) {
                if (yellowBird1 != null) yellowBird1.draw(batch, 100, 100);
                if (yellowBird2 != null) yellowBird2.draw(batch, 100, 100);
                if (yellowBird3 != null) yellowBird3.draw(batch, 100, 100);

                batch.draw(slingshotLeft, 365, 380, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);
                batch.draw(slingshotRight, 400, 270, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);

                for (Metal block : metalBlocks) {
                    block.draw(batch, block.getTexture().getWidth(), block.getTexture().getHeight());
                }

                float pigWidth = 115;
                float pigHeight = 100;

                for (Pig pig : pigs2) {
                    pig.draw(batch, pigWidth, pigHeight);
                }
            }

            if (level == 3) {
                if (blueBird1 != null) blueBird1.draw(batch, 70, 70);
                if (blueBird2 != null) blueBird2.draw(batch, 70, 70);
                if (blueBird3 != null) blueBird3.draw(batch, 70, 70);

                batch.draw(slingshotLeft, 365, 280, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);
                batch.draw(slingshotRight, 400, 170, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);

                float glassWidth = 150;
                float glassHeight = 150;

                for (Glass glass : glasses) {
                    glass.draw(batch, glassWidth, glassHeight);
                }

                float pigWidth = 115;
                float pigHeight = 100;

                for (Pig pig : pigs3) {
                    pig.draw(batch, pigWidth, pigHeight);
                }
            }
        } else {
            winScreen.render(batch);

            if (Gdx.input.isKeyPressed(Input.Keys.H)) {
                showWinScreen = false;
                winScreen.dispose();
            }
        }

        batch.end();

        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        shapeRenderer.dispose();
        background.dispose();
        buttonFont.dispose();
        slingshotLeft.dispose();
        slingshotRight.dispose();

        if (level == 1) {
            if (redBird1 != null) redBird1.getTexture().dispose();
            if (redBird2 != null) redBird2.getTexture().dispose();
            if (redBird3 != null) redBird3.getTexture().dispose();
        } else if (level == 2) {
            if (yellowBird1 != null) yellowBird1.getTexture().dispose();
            if (yellowBird2 != null) yellowBird2.getTexture().dispose();
            if (yellowBird3 != null) yellowBird3.getTexture().dispose();
        } else if (level == 3) {
            if (blueBird1 != null) blueBird1.getTexture().dispose();
            if (blueBird2 != null) blueBird2.getTexture().dispose();
            if (blueBird3 != null) blueBird3.getTexture().dispose();
        }
    }
}
