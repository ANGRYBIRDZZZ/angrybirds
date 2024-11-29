package io.github.some_example_name;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import com.badlogic.gdx.Input; // Add this import
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
public class GameScreen implements Screen {
    private Game game;
    private int level;
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private BitmapFont buttonFont;
    private ShapeRenderer shapeRenderer;
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
    //private List<Glass> glassBlocks;
    List<Glass> glassBlocks = new ArrayList<>();
    private List<Pig> pigs3;
    //private List<Metal> metalBlocks;
    List<Metal> metalBlocks = new ArrayList<>();
    private List<Pig> pigs2;
    private boolean isPulling = false;
    private Vector2 slingPosition;// Position of the slingshot
    private Vector2 dragPosition=new Vector2(0,0); // Position while dragging
    private ArrayList<Bird> birds;
    public static ArrayList<Block> toRemoveblocks = new ArrayList<>();
    public static ArrayList<Pig> toRemovepigs = new ArrayList<>();
    public ArrayList<Pig> pigs;
    private boolean isPaused = false;
    public Bird launchedBird;

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
        PausedScreen pausedScreen = new PausedScreen(game, this);
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
            slingPosition=new Vector2(373,500);
            world=new World(new Vector2(0,-20),true);
            Texture redBirdTexture1 = new Texture(Gdx.files.internal("assets/redbird1.png"));
            Texture redBirdTexture2 = new Texture(Gdx.files.internal("assets/redbird2.png"));
            Texture redBirdTexture3 = new Texture(Gdx.files.internal("assets/redbird3.png"));
            redBird1 = new RedBird(world,redBirdTexture1,370, 490);
            redBird2 = new RedBird(world,redBirdTexture2,100, 280);
            redBird3 = new RedBird(world,redBirdTexture3, 150,280);
            birds.add(redBird1);
            birds.add(redBird2);
            birds.add(redBird3);
            Texture woodTexture2 = new Texture(Gdx.files.internal("assets/wood2.png"));
            Texture woodTexture3 = new Texture(Gdx.files.internal("assets/wood3.png"));
            Texture woodTexture4 = new Texture(Gdx.files.internal("assets/wood4.png"));
            Texture woodTexture6 = new Texture(Gdx.files.internal("assets/wood6.png"));
            woodBlocks.add(new Wood(world,woodTexture2, 1225, 355));
            woodBlocks.add(new Wood(world,woodTexture3, 1400, 425));
            woodBlocks.add(new Wood(world,woodTexture4, 1375, 280));
            woodBlocks.add(new Wood(world,woodTexture6, 1450, 280));
            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));
            pigs.add(new SmallPig(world,pigTexture1, 1350, 450));
            pigs.add(new MediumPig(world,pigTexture4, 1250, 500));
            pigs.add(new LargePig(world,pigTexture5, 1475, 300));
        }
        if (level == 2) {
            slingPosition=new Vector2(373,475);
            world=new World(new Vector2(0,-20),true);
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
            metalBlocks.add(new Metal(world,metalTexture1, 1125, 260));
            metalBlocks.add(new Metal(world,metalTexture2, 1250, 260));
            metalBlocks.add(new Metal(world,metalTexture3, 1300, 600));
            metalBlocks.add(new Metal(world,metalTexture4, 1425, 260));
            metalBlocks.add(new Metal(world,metalTexture5, 1425, 475));
            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));
            pigs.add(new SmallPig(world,pigTexture1, 1260, 400));
            pigs.add(new MediumPig(world,pigTexture4, 1110, 350));
            pigs.add(new LargePig(world,pigTexture5, 1260, 275));
        }
        if (level == 3) {
            slingPosition=new Vector2(373,375);
            world=new World(new Vector2(0,-20),true);
            Texture blueBirdTexture1 = new Texture(Gdx.files.internal("assets/bluebird1.png"));
            Texture blueBirdTexture2 = new Texture(Gdx.files.internal("assets/bluebird2.png"));
            Texture blueBirdTexture3 = new Texture(Gdx.files.internal("assets/bluebird3.png"));
            blueBird1 = new BlueBird(world,blueBirdTexture1, 380, 350);
            blueBird2 = new BlueBird(world,blueBirdTexture2, 140, 165);
            blueBird3 = new BlueBird(world,blueBirdTexture3, 180, 165);
            Texture glassTexture2 = new Texture(Gdx.files.internal("assets/glass2.png"));
            Texture glassTexture3 = new Texture(Gdx.files.internal("assets/glass5.png"));
            Texture glassTexture4 = new Texture(Gdx.files.internal("assets/glass2.png"));
            Texture glassTexture5 = new Texture(Gdx.files.internal("assets/glass5.png"));
            glassBlocks.add(new Glass(world,glassTexture2, 1150, 175));
            glassBlocks.add(new Glass(world,glassTexture3, 1520, 500));
            glassBlocks.add(new Glass(world,glassTexture4, 1520, 475));
            glassBlocks.add(new Glass(world,glassTexture5, 1300, 325));
            birds.add(blueBird1);
            birds.add(blueBird2);
            birds.add(blueBird3);
            Texture pigTexture1 = new Texture(Gdx.files.internal("assets/pig1.png"));
            Texture pigTexture4 = new Texture(Gdx.files.internal("assets/pig4.png"));
            Texture pigTexture5 = new Texture(Gdx.files.internal("assets/pig5.png"));
            pigs.add(new SmallPig(world,pigTexture1, 1300, 325));
            pigs.add(new MediumPig(world,pigTexture4, 1100, 325));
            pigs.add(new LargePig(world,pigTexture5, 1500, 400));
        }
        Gdx.input.setInputProcessor(stage);
    }

    public GameScreen(Game game) {
        this.game = game;
        this.level = 1;
        slingPosition=new Vector2(373,515);
        camera = new OrthographicCamera();
        shapeRenderer=new ShapeRenderer();
        camera.setToOrtho(false, 900, 600);
        camera.update();
        viewport = new FitViewport(5000, 2000, camera);
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        World world = new World(new Vector2(0,-15f), true);
        PausedScreen pausedScreen = new PausedScreen(game, this);
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
    }

    public void setPaused(boolean paused) {
        this.isPaused = isPaused;
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
        createButton("Pause", Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 50, buttonStyle, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = true;
                game.setScreen(new PausedScreen(game, GameScreen.this)); // Pass GameScreen instance
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

    private void createButton(String text, float x, float y, TextButtonStyle style, ClickListener listener) {
        TextButton button = new TextButton(text, style);
        button.setPosition(x, y);
        if (listener != null) button.addListener(listener);
        stage.addActor(button);
    }
    @Override
    public void render(float delta) {
        if (pigs.isEmpty()) {
            game.setScreen(new WinScreen(game));
        } else if (birds.isEmpty() &&  (!isInframe(launchedBird) || launchedBird.body.getLinearVelocity().epsilonEquals(0,0))) {
            game.setScreen(new LoseScreen(game,level));

        }

        for (Pig pig : pigs) {
            if (!isInframe((pig))) {
                toRemovepigs.add(pig);
            }
        }


        if (isPaused) {
            return;
        }

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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        createGround();
        createSlingshot();

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


            if (level == 1) {
                batch.draw(slingshotRight, 400, 275, slingshotRight.getWidth() * 1.8f, slingshotRight.getHeight() * 1.6f);
                for(Bird bird: birds){
                    bird.draw(batch, 100, 100);
                }
                if(launchedBird != null){
                    launchedBird.draw(batch, 100, 100);
                }
                batch.draw(slingshotLeft, 365, 385, slingshotLeft.getWidth() * 1.4f, slingshotLeft.getHeight() * 1.5f);
                for (Wood block : woodBlocks) {
                    block.draw(batch);
                }

                for (Pig pig : pigs) {
                    pig.draw(batch, pig.width, pig.height);
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
                for (Glass glass : glassBlocks) {
                    glass.draw(batch);
                }
                float pigWidth = 115;
                float pigHeight = 100;
                for (Pig pig : pigs) {
                    pig.draw(batch, pigWidth, pigHeight);
                }
            }


        world.step(1 / 60f, 6, 2);
        world.step(1 / 60f, 6, 2);
        world.step(1 / 60f, 6, 2);

        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.545f,0.271f,0.075f,1);
        if (isPulling) {
            shapeRenderer.line(slingPosition,dragPosition);
            shapeRenderer.line(slingPosition.cpy().add(100,10),dragPosition);
        }
        shapeRenderer.end();
        handleInput();
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
    private void createGround() {
        BodyDef groundBodyDef = new BodyDef();
        if (level == 1) {
            groundBodyDef.position.set(50, 70);
        } else if (level ==2) {
            groundBodyDef.position.set(50, 60);
        } else {
            groundBodyDef.position.set(35, 20);
        }
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        Body groundBody =this.world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(850, 70);
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 1000f;
        groundFixtureDef.restitution = 0.01f;
        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
    }
    private void createSlingshot() {
        BodyDef bodyDef = new BodyDef();
        if (level == 1) {
            bodyDef.position.set(195,150);
        } else if (level == 2) {
            bodyDef.position.set(195,130);
        } else {
            bodyDef.position.set(195,80);
        }
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body groundBody =this.world.createBody(bodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(7, 75);
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;
        groundFixtureDef.restitution = 0f;
        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
    }
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            System.out.println(launchedBird);
            if (launchedBird instanceof YellowBird) {
                YellowBird yellowBird = (YellowBird)(launchedBird);
                yellowBird.body.setLinearVelocity(new Vector2(1000000000, 0));
            }

            if (launchedBird instanceof BlueBird) {
                BlueBird blueBird = (BlueBird)(launchedBird);
                blueBird.body.setLinearVelocity(new Vector2(0, -6));
            }
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.W)) {
            game.setScreen(new WinScreen(game));
            return;
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.L)) {
            game.setScreen(new LoseScreen(game, level));
            return;
        }

        if (birds.isEmpty()) return;
        Bird bird = birds.get(0);
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY());
            if (!isPulling) {
                if (bird.getBounds().contains(touchPos.x, touchPos.y)) {
                    isPulling = true;
                }
            }
            if (isPulling) {
                dragPosition.set(touchPos.x, touchPos.y);
                bird.body.setTransform(touchPos.x/2f, touchPos.y/2f, bird.body.getAngle());
            }
        } else {
            if (isPulling) {
                isPulling = false;
                Vector2 launchForce = slingPosition.cpy().sub(dragPosition).scl(900000000f);
                launchForce.y*=30;
                launchForce.x*=20;
                bird.body.applyLinearImpulse(launchForce, bird.body.getWorldCenter(), true);
                launchedBird = bird;
                System.out.println("hi"+launchedBird);
                birds.remove(0);
                if (!birds.isEmpty()){
                birds.get(0).body.setTransform(slingPosition.x/1.8f-5,slingPosition.y/1.8f+200,bird.body.getAngle());
                birds.get(0).x=slingPosition.x;
                birds.get(0).y=slingPosition.y;}
            }
        }

    }


    public void saveBirdPositions(){
        String fileName = "BirdPositions.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("Level: " + level + "\n");
                for (Bird bird : birds) {
                    if (bird != null) {
                        String type = bird.getClass().getSimpleName(); // Bird type
                        Vector2 velocity = bird.body.getLinearVelocity();
                        float x = bird.body.getPosition().x;
                        float y = bird.body.getPosition().y;
                        float velocityX = velocity.x;
                        float velocityY = velocity.y;

                        String line = String.format("%s, %.2f, %.2f, %.2f, %.2f", type, x, y, velocityX, velocityY);
                        writer.write(line + "\n");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error saving bird positions: " + e.getMessage());
            }

    }
    // Save pig positions
    public void savePigPositions() {
        String fileName = "PigPositions.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Level: " + level + "\n");
            for (Pig pig : pigs) {
                if (pig != null && pig.body != null) {
                    String type = pig.getClass().getSimpleName();
                    Vector2 velocity = pig.body.getLinearVelocity();
                    float x = pig.body.getPosition().x;
                    float y = pig.body.getPosition().y;
                    float velocityX = velocity.x;
                    float velocityY = velocity.y;
                    int health = pig.health;

                    String line = String.format("%s, %.2f, %.2f, %.2f, %.2f, %d", type, x, y, velocityX, velocityY, health);
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving pig positions: " + e.getMessage());
        }
    }

    public void saveBlockPositions() {
        String fileName = "BlockPositions.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Level: " + level + "\n");

            List<Block> allBlocks = new ArrayList<>();
            if (woodBlocks != null) allBlocks.addAll(woodBlocks);
            if (glassBlocks != null) allBlocks.addAll(glassBlocks);
            if (metalBlocks != null) allBlocks.addAll(metalBlocks);

            for (Block block : allBlocks) {
                if (block != null) {
                    String type = block.getClass().getSimpleName();
                    float x = block.body.getPosition().x;
                    float y = block.body.getPosition().y;
                    float height = block.height;
                    float width = block.width;
                    int health = block.health;

                    String line = String.format("%s, %.2f, %.2f, %.2f, %.2f, %d", type, x, y, height, width, health);
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving block positions: " + e.getMessage());
        }
    }
    public void saveGameState() {
        saveBirdPositions();
        savePigPositions();
        saveBlockPositions();
    }
    public void loadBirdPositions() {
        String fileName = "BirdPositions.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Level:")) {
                    String levelStr = line.substring("Level: ".length()).trim();
                    this.level = Integer.parseInt(levelStr);
                    continue;
                }

                String[] data = line.split(", ");
                if (data.length == 5) {
                    String type = data[0].trim();
                    float x = Float.parseFloat(data[1].trim());
                    float y = Float.parseFloat(data[2].trim());
                    float velocityX = Float.parseFloat(data[3].trim());
                    float velocityY = Float.parseFloat(data[4].trim());
                    System.out.println(type + " " + x + " " + y + " " + velocityX + " " + velocityY);
                    Bird bird = createBird(type, x, y, velocityX, velocityY);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading bird positions: " + e.getMessage());
        }
    }

    private Bird createBird(String type, float x, float y, float velocityX, float velocityY) {
        Bird bird = null;
        System.out.println(type);

        switch (type) {
            case "RedBird":
                String texture1 = "redbird1.png";
                bird = new RedBird(world, x, y, velocityX, velocityY, texture1);
                break;
            case "BlueBird":
                String texture2 = "bluebird1.png";
                bird = new BlueBird(world, x, y, velocityX, velocityY, texture2);
                break;
            case "YellowBird":
                String texture3 = "yellowbird1.png";
                bird = new YellowBird(world, x, y, velocityX, velocityY, texture3);
                break;
        }

        return bird;
    }
    public void loadPigPositions() {
        String fileName = "PigPositions.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Level:")) {
                    String levelStr = line.substring("Level: ".length()).trim();
                    continue;
                }

                String[] data = line.split(", ");
                if (data.length == 6) {
                    String type = data[0];
                    float x = Float.parseFloat(data[1]);
                    float y = Float.parseFloat(data[2]);
                    float velocityX = Float.parseFloat(data[3]);
                    float velocityY = Float.parseFloat(data[4]);
                    int health = Integer.parseInt(data[5]);

                    Pig pig = createPig(type, x, y, velocityX, velocityY, health);

                    if (pig != null) {
                        pigs.add(pig);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading pig positions: " + e.getMessage());
        }
    }

    private Pig createPig(String type, float x, float y, float velocityX, float velocityY, int health) {
        Pig pig = null;

        switch (type) {
            case "SmallPig":
                Texture texture1 = new Texture("assets/pig1.png");
                pig = new SmallPig(world, x, y, velocityX, velocityY, health, texture1);
                break;
            case "MediumPig":
                Texture texture2 = new Texture("assets/pig2.png");
                pig = new MediumPig(world, x, y, velocityX, velocityY, health, texture2);
                break;
            case "LargePig":
                Texture texture3 = new Texture("assets/pig3.png");
                pig = new LargePig(world, x, y, velocityX, velocityY, health, texture3);
                break;
        }

        return pig;
    }
    public void loadBlockPositions() {
        String fileName = "BlockPositions.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Level:")) {
                    String levelStr = line.substring("Level: ".length()).trim();
                    continue;
                }

                String[] data = line.split(", ");
                if (data.length == 6) {
                    String type = data[0];
                    float x = Float.parseFloat(data[1]);
                    float y = Float.parseFloat(data[2]);
                    float height = Float.parseFloat(data[3]);
                    float width = Float.parseFloat(data[4]);
                    int health = Integer.parseInt(data[5]);

                    Block block = createBlock(type, x, y, height, width, health);

                    if (type.equals("Glass")) {
                        Texture texture1 = new Texture("assets/glass1.png");
                        glassBlocks.add((Glass)block);
                    } else if (type.equals("Metal")) {
                        Texture texture2 = new Texture("assets/metal2.png");
                        metalBlocks.add((Metal)block);
                    } else if (type.equals("Wood")) {
                        Texture texture3 = new Texture("assets/wood2.png");
                        woodBlocks.add((Wood)block);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading block positions: " + e.getMessage());
        }
    }

    private Block createBlock(String type, float x, float y, float height, float width, int health) {
        Block block = null;

        switch (type) {
            case "Wood":
                Texture texture1 = new Texture("assets/wood1.png");
                block = new Wood(world,x, y, height, width, health, texture1);
                break;
            case "Glass":
                Texture texture2 = new Texture("assets/wood2.png");
                block = new Glass(world, x, y, height, width, health, texture2);
                break;
            case "Metal":
                Texture texture3 = new Texture("assets/wood3.png");
                block = new Metal(world, x, y, height, width, health, texture3);
                break;
        }
        return block;
    }
    public boolean isInframe(Pig pig) {
        if (pig.body.getPosition().x > 1920/1.8f || pig.body.getPosition().x <0 || pig.body.getPosition().y <0 || pig.body.getPosition().y > 1200/1.8f) {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean isInframe(Bird bird) {
        if (bird.body.getPosition().x > 1920/1.8f || bird.body.getPosition().x <0 || bird.body.getPosition().y <0 || bird.body.getPosition().y > 1200/1.8f) {
            return false;
        }
        else {
            return true;
        }
    }
}
