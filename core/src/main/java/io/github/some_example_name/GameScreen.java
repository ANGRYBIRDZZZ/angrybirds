package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.*;
import com.badlogic.gdx.Input; // Add this import
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {
    private Game game;
    private int level;
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private BitmapFont buttonFont;
    private ShapeRenderer shapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    private RedBird redBird1;
    private RedBird redBird2;
    private RedBird redBird3;
    private YellowBird yellowBird1;
    private YellowBird yellowBird2;
    private YellowBird yellowBird3;
    private BlueBird blueBird1;
    private BlueBird blueBird2;
    private BlueBird blueBird3;
    private OrthographicCamera camera;
    private Texture slingshotLeft;
    private Texture slingshotRight;
    World world;
    private Viewport viewport;
    List<Wood> woodBlocks = new ArrayList<>();
    private List<Pig> pigs1;
    private List<Glass> glassBlocks;
    private List<Pig> pigs3;
    private List<Metal> metalBlocks;
    private List<Pig> pigs2;
    private WinScreen winScreen;
    private boolean showWinScreen = false;
    private boolean isDragging = false;
    private Vector2 slingPosition;// Position of the slingshot
    private Vector2 dragPosition=new Vector2(0,0); // Position while dragging
    private ArrayList<Bird> birds;
    public static ArrayList<Block> toRemoveblocks = new ArrayList<>();
    public static ArrayList<Pig> toRemovepigs = new ArrayList<>();
    public ArrayList<Pig> pigs;



    public GameScreen(Game game, int level) {
        this.game = game;
        this.level = level;
        camera = new OrthographicCamera();
        shapeRenderer=new ShapeRenderer();
        camera.setToOrtho(false, 900, 600);
        camera.update();
        viewport = new FitViewport(5000, 2000, camera);
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
        background = new Texture(Gdx.files.internal("assets/level" + level + ".jpg"));
        slingshotLeft = new Texture(Gdx.files.internal("assets/slingshotleft.png"));
        slingshotRight = new Texture(Gdx.files.internal("assets/slingshotright.png"));
        birds = new ArrayList<>();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ConcertOneRegular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();
        pigs = new ArrayList<>();

        createButtons();

        if (level == 1) {
            slingPosition=new Vector2(370,490);
            world=new World(new Vector2(0,-15),true);
            Texture redBirdTexture1 = new Texture(Gdx.files.internal("assets/redbird1.png"));
            Texture redBirdTexture2 = new Texture(Gdx.files.internal("assets/redbird2.png"));
            Texture redBirdTexture3 = new Texture(Gdx.files.internal("assets/redbird3.png"));

            redBird1 = new RedBird(world,redBirdTexture1,370, 490);
            redBird2 = new RedBird(world,redBirdTexture2,100, 280);
            redBird3 = new RedBird(world,redBirdTexture3, 150,280);

            birds.add(redBird1);
            birds.add(redBird2);
            birds.add(redBird3);

            //Texture woodTexture1 = new Texture(Gdx.files.internal("assets/wood1.png"));
            Texture woodTexture2 = new Texture(Gdx.files.internal("assets/wood2.png"));
            Texture woodTexture3 = new Texture(Gdx.files.internal("assets/wood3.png"));
            Texture woodTexture4 = new Texture(Gdx.files.internal("assets/wood4.png"));
            //Texture woodTexture5 = new Texture(Gdx.files.internal("assets/wood5.png"));
            Texture woodTexture6 = new Texture(Gdx.files.internal("assets/wood6.png"));

            //woodBlocks.add(new Wood(world,woodTexture1, 1075, 280));
            woodBlocks.add(new Wood(world,woodTexture2, 1225, 355));
            woodBlocks.add(new Wood(world,woodTexture3, 1400, 425));
            woodBlocks.add(new Wood(world,woodTexture4, 1375, 280));
            //woodBlocks.add(new Wood(world,woodTexture5, 1300, 280));
            woodBlocks.add(new Wood(world,woodTexture6, 1450, 280));

            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            //Texture pigTexture2 = new Texture(Gdx.files.internal("assets/pig2.png"));
            Texture pigTexture3 = new Texture(Gdx.files.internal("assets/pig3.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));

            pigs.add(new SmallPig(world,pigTexture1, 1350, 450));
            //pis1.add(new Pig(world,pigTexture2, 1350, 450));
            //pigs1.add(new Pig(world,pigTexture3, 1475, 300));
            pigs.add(new MediumPig(world,pigTexture4, 1250, 500));
            pigs.add(new LargePig(world,pigTexture5, 1475, 300));
        }

        if (level == 2) {
            slingPosition=new Vector2(370,450);
            world=new World(new Vector2(0,-10),true);
            Texture yellowBirdTexture1 = new Texture(Gdx.files.internal("assets/yellowbird1.png"));
            Texture yellowBirdTexture2 = new Texture(Gdx.files.internal("assets/yellowbird2.png"));
            Texture yellowBirdTexture3 = new Texture(Gdx.files.internal("assets/yellowbird3.png"));

            yellowBird1 = new YellowBird(world,yellowBirdTexture1, 370, 450);
            yellowBird2 = new YellowBird(world,yellowBirdTexture2, 100, 260);
            yellowBird3 = new YellowBird(world,yellowBirdTexture3, 150, 260);

            Texture metalTexture1 = new Texture(Gdx.files.internal("assets/metal5.png"));
            Texture metalTexture2 = new Texture(Gdx.files.internal("assets/metal2.png"));
            Texture metalTexture3 = new Texture(Gdx.files.internal("assets/metal3.png"));
            Texture metalTexture4 = new Texture(Gdx.files.internal("assets/metal5.png"));
            Texture metalTexture5 = new Texture(Gdx.files.internal("assets/metal5.png"));

            birds.add(yellowBird1);
            birds.add(yellowBird2);
            birds.add(yellowBird3);

            metalBlocks = new ArrayList<>();

            metalBlocks.add(new Metal(world,metalTexture1, 1125, 260)); // Adjust x and y as needed
            metalBlocks.add(new Metal(world,metalTexture2, 1250, 260)); // Adjust x and y as needed
            metalBlocks.add(new Metal(world,metalTexture3, 1300, 600)); // Adjust x and y as needed
            metalBlocks.add(new Metal(world,metalTexture4, 1425, 260)); // Adjust x and y as needed
            metalBlocks.add(new Metal(world,metalTexture5, 1425, 475)); // Adjust x and y as needed

            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture2 = new Texture(Gdx.files.internal("assets/pig2.png"));
            //Texture pigTexture3 = new Texture(Gdx.files.internal("assets/pig3.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));

            pigs.add(new SmallPig(world,pigTexture1, 1260, 425)); // Adjust x and y as needed
            //pigs2.add(new Pig(world,pigTexture2, 1500, 260)); // Adjust x and y as needed
            //pigs2.add(new Pig(world,pigTexture3, 1405, 480)); // Adjust x and y as needed
            pigs.add(new MediumPig(world,pigTexture4, 1110, 610)); // Adjust x and y as needed
            pigs.add(new LargePig(world,pigTexture5, 1260, 275)); // Adjust x and y as needed


        }

        if (level == 3) {
            slingPosition=new Vector2(375,350);
            world=new World(new Vector2(0,-20),true);
            Texture blueBirdTexture1 = new Texture(Gdx.files.internal("assets/bluebird1.png"));
            Texture blueBirdTexture2 = new Texture(Gdx.files.internal("assets/bluebird2.png"));
            Texture blueBirdTexture3 = new Texture(Gdx.files.internal("assets/bluebird3.png"));

            blueBird1 = new BlueBird(world,blueBirdTexture1, 380, 350);
            blueBird2 = new BlueBird(world,blueBirdTexture2, 140, 165);
            blueBird3 = new BlueBird(world,blueBirdTexture3, 180, 165);

            glassBlocks = new ArrayList<>();

            Texture glassTexture1 = new Texture(Gdx.files.internal("assets/glass1.png"));
            Texture glassTexture2 = new Texture(Gdx.files.internal("assets/glass2.png"));
            Texture glassTexture3 = new Texture(Gdx.files.internal("assets/glass5.png"));
            Texture glassTexture4 = new Texture(Gdx.files.internal("assets/glass2.png"));
            Texture glassTexture5 = new Texture(Gdx.files.internal("assets/glass5.png"));

            //glasses.add(new Glass(world,glassTexture1, 1400, 175));
            glassBlocks.add(new Glass(world,glassTexture2, 1150, 175));
            glassBlocks.add(new Glass(world,glassTexture3, 1520, 500));
            glassBlocks.add(new Glass(world,glassTexture4, 1520, 475));
            glassBlocks.add(new Glass(world,glassTexture5, 1300, 325));

            birds.add(blueBird1);
            birds.add(blueBird2);
            birds.add(blueBird3);

            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture2 = new Texture(Gdx.files.internal("assets/pig2.png"));
            Texture pigTexture3 = new Texture(Gdx.files.internal("assets/pig3.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));

            pigs.add(new SmallPig(world,pigTexture1, 1300, 325)); // Adjust x and y as needed
            //pigs3.add(new Pig(world,pigTexture2, 1400, 325)); // Adjust x and y as needed
            //pigs3.add(new Pig(world,pigTexture3, 1410, 205)); // Adjust x and y as needed
            pigs.add(new MediumPig(world,pigTexture4, 1100, 325)); // Adjust x and y as needed
            pigs.add(new LargePig(world,pigTexture5, 1500, 400)); // Adjust x and y as needed

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
        world.setContactListener(new Collisions());
        for (Block block : toRemoveblocks) {
            if ( block instanceof Metal) {
                metalBlocks.remove(block);
                block.texture.dispose();
                world.destroyBody(block.body);
            } else if (block instanceof Wood) {
                woodBlocks.remove(block);
                block.texture.dispose();
                world.destroyBody(block.body);
            } else if (block instanceof Glass) {
                glassBlocks.remove(block);
                block.texture.dispose();
                world.destroyBody(block.body);
            }
        }

        for (Pig pig : toRemovepigs) {
            pigs.remove(pig);
            pig.texture.dispose();
            world.destroyBody(pig.body);
        }

        toRemoveblocks.clear();
        toRemovepigs.clear();

        //handleInput(redBird1);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        createGround();
        createSlingshot();
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (Gdx.input.isKeyPressed(Input.Keys.W) && !showWinScreen) {
            showWinScreen = true;
            winScreen = new WinScreen();
        }

        if (!showWinScreen) {
            if (level == 1) {
                batch.draw(slingshotRight, 400, 290, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);
                if (redBird1 != null) redBird1.draw(batch, 100, 100);
                if (redBird2 != null) redBird2.draw(batch, 100, 100);
                if (redBird3 != null) redBird3.draw(batch, 100, 100);

                batch.draw(slingshotLeft, 365, 400, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);
                for (Wood block : woodBlocks) {
                    block.draw(batch);
                }
                float pigWidth = 115;  // Set desired width
                float pigHeight = 100; // Set desired height

                for (Pig pig : pigs) {
                    pig.draw(batch, pigWidth, pigHeight);
                }
            }

            if (level == 2) {
                batch.draw(slingshotRight, 400, 270, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);

                if (yellowBird1 != null) yellowBird1.draw(batch, 100, 100);
                if (yellowBird2 != null) yellowBird2.draw(batch, 100, 100);
                if (yellowBird3 != null) yellowBird3.draw(batch, 100, 100);

                batch.draw(slingshotLeft, 365, 380, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);

                for (Metal block : metalBlocks) {
                    block.draw(batch);
                }

                float pigWidth = 115;
                float pigHeight = 100;

                for (Pig pig : pigs) {
                    pig.draw(batch, pigWidth, pigHeight);
                }
            }

            if (level == 3) {
                batch.draw(slingshotRight, 400, 170, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);

                if (blueBird1 != null) blueBird1.draw(batch, 70, 70);
                if (blueBird2 != null) blueBird2.draw(batch, 70, 70);
                if (blueBird3 != null) blueBird3.draw(batch, 70, 70);

                batch.draw(slingshotLeft, 365, 280, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);

                float glassWidth = 150;
                float glassHeight = 150;

                for (Glass glass : glassBlocks) {
                    glass.draw(batch);
                }

                float pigWidth = 115;
                float pigHeight = 100;

                for (Pig pig : pigs) {
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

        world.step(1 / 60f, 6, 2);
        world.step(1 / 60f, 6, 2);
        world.step(1 / 60f, 6, 2);

        batch.end();
        handleInput();

        stage.act(delta);
        stage.draw();
        debugRenderer.render(world, camera.combined);
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

    private void createGround() {
        // Define a static body for the ground
        BodyDef groundBodyDef = new BodyDef();
        if (level == 1) {
            groundBodyDef.position.set(50, 70);
        } else if (level ==2) {
            groundBodyDef.position.set(50, 60);
        } else {
            groundBodyDef.position.set(35, 20);
        }

         // Set the ground's position (centered)
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        // Create the ground body in the world
        Body groundBody =this.world.createBody(groundBodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(800, 70); // Half-width and half-height of the ground

        // Create a fixture and attach the shape to the ground body
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 1000f; // Adjust friction as needed
        groundFixtureDef.restitution = 0.5f; // No bounce for the ground

        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape
        groundShape.dispose();
    }
    private void createSlingshot() {
        // Left arm of the slingshot
        BodyDef bodyDef = new BodyDef();
        if (level == 1) {
            bodyDef.position.set(195,150); // Set the ground's position (centered)
        } else if (level == 2) {
            bodyDef.position.set(195,130); // Set the ground's position (centered)
        } else {
            bodyDef.position.set(195,80); // Set the ground's position (centered)
        }
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Create the ground body in the world
        Body groundBody =this.world.createBody(bodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(7, 75); // Half-width and half-height of the ground

        // Create a fixture and attach the shape to the ground body
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f; // Adjust friction as needed
        groundFixtureDef.restitution = 0f; // No bounce for the ground

        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape
        groundShape.dispose();

    }

    private void handleInput() {
        if (birds.isEmpty()) return; // No birds left to launch

        Bird bird = birds.get(0); // Get the first bird in the list
        // Ensure the game uses an OrthographicCamera
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());

            if (!isDragging) {
                if (bird.getBounds().contains(touchPos.x, touchPos.y)) {
                    isDragging = true; // Start dragging
                }
            }

            if (isDragging) {
                // Update drag position
                dragPosition.set(touchPos.x, touchPos.y);
                bird.body.setTransform(touchPos.x/2f, touchPos.y/2f, bird.body.getAngle());


            }
        } else {
            if (isDragging) {
                isDragging = false; // Stop dragging

                // Calculate and apply launch velocity
                Vector2 launchForce = slingPosition.cpy().sub(dragPosition).scl(900000000f);
                launchForce.y*=30;
                launchForce.x*=20;// Scale multiplier as needed
                bird.body.applyLinearImpulse(launchForce, bird.body.getWorldCenter(), true);

                birds.remove(0);
                if (!birds.isEmpty()){
                birds.get(0).body.setTransform(slingPosition.x/1.8f-5,slingPosition.y/1.8f+200,bird.body.getAngle());
                birds.get(0).x=slingPosition.x;
                birds.get(0).y=slingPosition.y;}



            }
        }
        SpriteBatch sb=new SpriteBatch();
        sb.begin();
        sb.draw(new Texture("yellowbird1.png"),bird.x,bird.y);
        sb.end();
    }


}
