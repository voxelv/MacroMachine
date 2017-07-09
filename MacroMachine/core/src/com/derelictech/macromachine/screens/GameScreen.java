package com.derelictech.macromachine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.derelictech.macromachine.tiles.Tile;
import com.derelictech.macromachine.tiles.materials.BasicMaterial;
import com.derelictech.macromachine.tiles.materials.MetalicMaterial;
import com.derelictech.macromachine.tiles.materials.RadicalMaterial;
import com.derelictech.macromachine.tiles.units.*;
import com.derelictech.macromachine.util.*;

/**
 * Main Game Screen
 * @author Tim Slippy, voxelv
 * @author Nate Groggett, Ghost4dot2
 */
public class GameScreen extends AbstractGameScreen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Slot selectedSlot;

    private Image im;

    private Stage hud;
    private Skin skin;

    public Button build;
    public Button levelUp;
    public Button info;
    public Button repair;

    public int repair_cost = 2;
    public int repair_scale = 20;

    private Image material1;
    private Image material2;
    private Image material3;

    private Label mat1;
    private Label mat2;
    private Label mat3;
    private Label powerLvl;
    private Label powerName;
    private Label health;
    private Label healthName;

    private Label build_cost;

    private int base = 0;
    private int meta = 0;
    private int rad = 0;

    private Label message;

    private Label goal;
    private Label collected;
    private int collected_this_level = 0;

    final SelectBox selectBox;
    public static int index;
    final SelectBox inventory;
    public static int index2;
    public int currIndex;


    private Label buildTag;
    private Label inventoryTag;
    private Label inventoryAmount;
    public  Button addBack;



    private int num_drills = 1;
    private int num_battery = 0;
    private int num_generator = 0;
    private int num_hull = 0;
    private int num_detector = 0;
    private int num_wires = 3;
    private int numberOf = 0;


    public int amount1 = 0;
    public int amount2 = 0;
    public int amount3 = 0;
    public int power;

    public boolean tooExpensive = false;
    public boolean EmptyInventory = false;

    private Table resourceTable;

    private InputMultiplexer multiplexer;

    private Level level;
    private Tile invTile;

    /**
     * Constructor for {@link GameScreen}
     * @param inputGame The game to set. Used to switch screens.
     */
    public GameScreen(Game inputGame) {
        super(inputGame);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
//        viewport = new ScreenViewport(camera);
        viewport = new ExtendViewport(Const.VIEWPORT_W,Const.VIEWPORT_H, Const.VIEWPORT_W, Const.VIEWPORT_H, camera);
//        viewport= new FitViewport(Const.VIEWPORT_W, Const.VIEWPORT_H);
        stage = new Stage(viewport);
        camera.update();

        im = new Image(new Texture("hud_bg.png"));


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        float x = 5, y = -55;
        material1 = new Image(new Texture("materials/basic_resource.png"));
        material1.setPosition(x, y + 85);
        material2 = new Image(new Texture("materials/metalic_resource.png"));
        material2.setPosition(x + 100, y + 85);
        material3 = new Image(new Texture("materials/radical_resource.png"));
        material3.setPosition(x, y);

        powerLvl = new Label("initializing", skin);
        powerLvl.setFontScale(2);
        powerLvl.setPosition(x + 300, y + 5);
        powerName = new Label("Power",skin);
        powerName.setPosition(x+300, y + 30);


        mat1 = new Label(Integer.toString(amount1) ,skin);
        mat1.setFontScale(2);
        mat1.setPosition(x + 45, y + 90);
        mat2 = new Label(Integer.toString(amount2) ,skin);
        mat2.setFontScale(2);
        mat2.setPosition(x + 145, y + 90);
        mat3 = new Label(Integer.toString(amount3) ,skin);
        mat3.setFontScale(2);
        mat3.setPosition(x + 45, y + 5);

        message = new Label("Message box", skin);
//        message.setFontScale(2);
        message.setPosition(x + 450, y + 20);

        hud = new Stage(new ExtendViewport(Const.HUD_VIEWPORT_W, Const.HUD_VIEWPORT_H, Const.HUD_VIEWPORT_W, Const.HUD_VIEWPORT_H));

        healthName = new Label("Cell Durability", skin);
        healthName.setPosition(x+300, y +90);

        health = new Label(Integer.toString(power), skin);
        health.setFontScale(2);
        health.setPosition(x + 300, y + 60);

        collected = new Label("initializing", skin);
        collected.setFontScale(2);
        collected.setPosition(x + 150, y + 5);

        goal = new Label("Mines Collected", skin);
        goal.setPosition(x +100, y + 30);


        resourceTable = new Table();
        resourceTable.setWidth(hud.getWidth());
        resourceTable.align( Align.center|Align.top);
        resourceTable.setPosition(0,Const.HUD_VIEWPORT_H - 100);

        build = new TextButton("Build", skin, "toggle");
        build.setWidth(100);
        build.setHeight(50);
        build.setPosition(640, -550);
        build.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor)
            {
                currIndex = index;
                if(addBack.isChecked())
                    addBack.toggle();
            }
        });

        repair = new TextButton("repair", skin);
        repair.setWidth(100);
        repair.setHeight(50);
        repair.setPosition(x + 450, y + 60);

        repair.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

                if (level.getCell().getHP() != level.getCell().getMaxHP()) {
                    final int basic_cost = repair_cost % repair_scale;
                    final int metalic_cost = repair_cost / repair_scale;

                    new Dialog("Repair Window", skin, "dialog") {
                        protected void result(Object object) {
                            Gdx.app.log("GameScreen", "Chosen: " + object);
                            if (object.toString().equals("true")) {
                                if(amount1 < basic_cost || amount2 < metalic_cost) {
                                    message.setText("Not enough resources");
                                }
                                else {
                                    message.setText("Repairs Complete");
                                    Gdx.app.log("button", "repairing 20");
                                    level.getCell().repairDamage(20);
                                    repair_cost += 4;
                                    amount1 = amount1 - basic_cost;
                                    amount2 = amount2 - metalic_cost;
                                }
                            }
                        }
                    }.text("This repair will cost " + Integer.toString(basic_cost) + " basic resources and \n" +
                            Integer.toString(metalic_cost) + " metalic to repair 20 health").button("OK", true).button("Cancel", false).key(Input.Keys.ENTER, true)
                            .key(Input.Keys.ESCAPE, false).show(hud);

                }
                else
                    message.setText("Your At Full Health!");
            }
        });

        levelUp = new TextButton("Level up", skin);
        levelUp.setWidth(100);
        levelUp.setHeight(50);
        levelUp.setPosition(640, -685);
//        levelUp.addListener(new TextTooltip("This is how you complete the level", skin));


        levelUp.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if(level.upLevel()) {
                    Gdx.app.log("GameScreen", "GO UP ONE LEVEL");
                    collected_this_level = 0;
                }
                else{
                    new Dialog("Level Up Window", skin, "dialog") {
                        protected void result (Object object) {
                            Gdx.app.log("GameScreen", "Chosen: " + object);
                        }
                    }.text("You are not Ready to level up!\nYou still have bombs to disarm!").button("OK", true).button("Cancel", false).key(Input.Keys.ENTER, true)
                            .key(Input.Keys.ESCAPE, false).show(hud);
                }

            }
        });


        selectBox = new SelectBox(skin);
        selectBox.setWidth(150);
        selectBox.setHeight(50);
        selectBox.setPosition(640, -400);
        selectBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.log("GameScreen", selectBox.getSelected().toString());
                index = selectBox.getSelectedIndex();
//                message.setText(Integer.toString(index));
            }
        });
        selectBox.setItems("Drill", "E-battery", "Generator", "Hull Upgrade", "Proximity Detector", "Wire");
        selectBox.setSelected("Drill");

        inventory = new SelectBox(skin);
        inventory.setWidth(150);
        inventory.setHeight(50);
        inventory.setPosition(640, -190);
        inventory.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.log("GameScreen", inventory.getSelected().toString());
                index2 = inventory.getSelectedIndex();
//                message.setText(Integer.toString(index));
            }
        });
        inventory.setItems("Drill", "E-battery", "Generator", "Hull Upgrade", "Proximity Detector", "Wire");
        inventory.setSelected("Drill");

        buildTag = new Label("Build", skin);
        buildTag.setPosition(640, -350);

        inventoryTag = new Label("Inventory", skin);
        inventoryTag.setPosition(640, -135);

        inventoryAmount = new Label("You have " +Integer.toString(numberOf) + " number\n of this item in\n your inventory.", skin);
        inventoryAmount.setPosition(640, -260);

        build_cost = new Label("initialize", skin);
        build_cost.setPosition(640, -455);

        addBack = new TextButton("Place", skin, "toggle");
        addBack.setWidth(100);
        addBack.setHeight(50);
        addBack.setPosition(640, -315);
        addBack.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor)
            {
                currIndex = index2;
                if(build.isChecked())
                    build.toggle();
            }
        });


        resourceTable.addActor(addBack);
        resourceTable.addActor(buildTag);
        resourceTable.addActor(inventoryTag);
        resourceTable.addActor(inventoryAmount);
        resourceTable.addActor(inventory);
        resourceTable.addActor(selectBox);
        resourceTable.addActor(build_cost);
        resourceTable.addActor(message);
        resourceTable.addActor(goal);
        resourceTable.addActor(collected);
        resourceTable.addActor(health);
        resourceTable.addActor(healthName);
        resourceTable.addActor(repair);
        resourceTable.addActor(build);
        resourceTable.addActor(levelUp);
        resourceTable.padRight(20f);
        resourceTable.addActor(material1);
        resourceTable.addActor(mat1);
        resourceTable.addActor(material2);
        resourceTable.addActor(mat2);
        resourceTable.addActor(material3);
        resourceTable.addActor(mat3);

        resourceTable.addActor(powerLvl);
        resourceTable.addActor(powerName);



        hud.addActor(im);
        hud.addActor(resourceTable);

//




        //buttonMulti.addListener(new TextTooltip("this is a tooltip test!", skin));







        stage.addListener(new MacroMachineListener(){
            @Override
            public boolean cellDeath(MacroMachineEvent event) {
                Gdx.app.log("GameScreen", "CELL HAS DIED XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                Timer.Task gameOver = new Timer.Task() {
                    @Override
                    public void run() {
                        stage.clear();
                        Timer.instance().clear();
                        game.setScreen(new GameOverScreen(game, level.getPowerLevel()));
                    }
                };
                Timer.schedule(gameOver, 2);
                return true;
            }

            @Override
            public boolean drilledMaterial(MacroMachineEvent event, Tile tile) {
                if(tile instanceof BasicMaterial)
                    amount1++;
                if(tile instanceof MetalicMaterial)
                    amount2++;
                if(tile instanceof RadicalMaterial) {
                    amount3++;
                    collected_this_level++;
                }

                return super.drilledMaterial(event, tile);
            }
        });

        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                float gutterX = viewport.getWorldWidth();
                float gutterY = viewport.getWorldHeight();
                int width = viewport.getScreenWidth();
                int height = viewport.getScreenHeight();
                Vector3 prevWorldMouse = new Vector3(camera.unproject(mouseRaw));
                Gdx.app.debug("GameScreen", "TouchLoc: " + prevWorldMouse.toString());
                Gdx.app.debug("GameScreen", "\nmouseRaw: " + mouseRaw.toString());
                Gdx.app.debug("GameScreen", "\nGutter: " + Float.toString(gutterX) + " " + Float.toString(gutterY));
                Gdx.app.debug("GameScreen", "\nScreen Width/height: " + Integer.toString(width) + " " + Integer.toString(height));
                if(event.getRelatedActor() != null){ }
                else Gdx.app.debug("--", "\n");
                if(addBack.isChecked() && !EmptyInventory)
                {
                    RemoveItem(index2);
                    addBack.toggle();
                }
                else
                    message.setText("You don't have any of that\n item in your inventory");

                if(build.isChecked() && !tooExpensive) {
                    message.setText("Unit was built");
                    amount1 -= base;
                    amount2 -= meta;
                    if( rad > 0)
                        amount3 -= rad;
                }
                else {
                    if(tooExpensive)
                        message.setText("Need more resources!");
                    else
                        if(button == Input.Keys.RIGHT)
                                message.setText("Item added to inventory");
                        else
                             message.setText("Press build if you wish to build\n item, then click destination");
                }
                if(build.isChecked())
                    build.toggle();

                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                Vector3 mouseRaw = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                Vector3 prevWorldMouse = new Vector3(viewport.unproject(mouseRaw)); // Get mouse location before zoom

                if(amount > 0) { // Scrolling Out
                    viewport.setWorldSize(viewport.getWorldWidth() + Math.abs(amount), viewport.getWorldHeight() + Math.abs(amount)); // Change viewport

                    if(viewport.getWorldWidth() > Const.VIEWPORT_W || viewport.getWorldHeight() > Const.VIEWPORT_H) {
                        viewport.setWorldSize(Const.VIEWPORT_W, Const.VIEWPORT_H); // CLAMP
                    }
                }
                else if(amount < 0) { // Scrolling In
                    viewport.setWorldSize(viewport.getWorldWidth() - Math.abs(amount), viewport.getWorldHeight() - Math.abs(amount)); // Change viewport
                    if(viewport.getWorldWidth() < 1 || viewport.getWorldHeight() < 1) {
                        viewport.setWorldSize(1, 1); // CLAMP
                    }
                }
                viewport.apply(true); // Update viewport, don't center camera.

                mouseRaw.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                Vector3 newWorldMouse = new Vector3(viewport.unproject(mouseRaw)); // Get new mouse location
                prevWorldMouse.sub(newWorldMouse); // Math

                camera.translate(prevWorldMouse); // Move camera to correct location so that mouse is over the exact spot
                // it was over before zooming (unless clamping disallows as per below)

                if(camera.position.x - camera.viewportWidth/2 < 0) {
                    camera.position.x = camera.viewportWidth/2;
                }
                if(camera.position.y - camera.viewportHeight/2 < 0) {
                    camera.position.y = camera.viewportHeight/2;
                }
                if(camera.position.x + camera.viewportWidth/2 > Const.VIEWPORT_W) {
                    camera.position.x = Const.VIEWPORT_W - camera.viewportWidth/2;
                }
                if(camera.position.y + camera.viewportHeight/2 > Const.VIEWPORT_H) {
                    camera.position.y = Const.VIEWPORT_H - camera.viewportHeight/2;
                }


                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode) {
                    case Input.Keys.RIGHT:
                        Gdx.app.debug("GameScreen", "Move cell returns: " + level.moveCell(GridDirection.RIGHT));
                        break;
                    case Input.Keys.UP:
                        Gdx.app.debug("GameScreen", "Move cell returns: " + level.moveCell(GridDirection.UP));
                        break;
                    case Input.Keys.LEFT:
                        Gdx.app.debug("GameScreen", "Move cell returns: " + level.moveCell(GridDirection.LEFT));
                        break;
                    case Input.Keys.DOWN:
                        Gdx.app.debug("GameScreen", "Move cell returns: " + level.moveCell(GridDirection.DOWN));
                        break;
                    case Input.Keys.U:
                        Gdx.app.debug("GameScreen", "GO UP ONE LEVEL");
                        level.upLevel();
                        break;
                    case Input.Keys.SPACE:
                        level.getCell().doMining();
                        break;
                    case Input.Keys.R:
                        if(selectedSlot != null && selectedSlot.getTile() != null) selectedSlot.getTile().rotate90();
                        break;
                    case Input.Keys.X:
                        level.getCell().takeDamage(100);
                        break;
                    case Input.Keys.P:
                        level.purgeGrid();
                        collected_this_level = level.getRadicalNum();
                        break;
                    case Input.Keys.B:
                        if(addBack.isChecked())
                            addBack.toggle();
                        build.toggle();
                        break;
                    case Input.Keys.A:
                        amount1 += 10;
                        amount2 += 10;
                        amount3 += 10;
                        break;
                    case Input.Keys.O:
                        game.setScreen(new GameOverScreen(game, level.getPowerLevel()));
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(event.getRelatedActor() instanceof Slot) {
                    selectedSlot = ((Slot) event.getRelatedActor());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                selectedSlot = null;
            }
        });


        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        level = new Level(this, 0);
        stage.addActor(level);
    }

    /**
     * Sets the background color and renders the stage
     * @param delta Amount of time between calls
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Builder(index);
        Space(index2);
        mat1.setText(Integer.toString(amount1));
        mat2.setText(Integer.toString(amount2));
        mat3.setText(Integer.toString(amount3));

        collected.setText(Integer.toString(collected_this_level) + "/" + Integer.toString(level.getRadicalNum()));

        health.setText(Long.toString(level.getCell().getHP()) + "/" + Long.toString(level.getCell().getMaxHP()));
        powerLvl.setText(Long.toString(level.getCell().getControlUnit().amountStored()) + "/" + Long.toString(level.getCell().getControlUnit().getCapacity()));


        stage.act(delta);
        hud.act(delta);

        stage.draw();
        hud.draw();
    }

    /**
     * Called when the window is resized. Updates the viewport with the new information. Updates the camera.
     * @param width New width of the window
     * @param height New height of the window
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        camera.update();



        hud.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {
        stage.dispose();
        hud.dispose();
    }

    public void Space(int choice) {

        if(invTile != null) {

            Gdx.app.error("GAMESCREEN", "It's a: " + invTile.TAG());
            if (invTile instanceof Drill)
                num_drills++;
            if (invTile instanceof EBattery)
                num_battery++;
            if (invTile instanceof Generator)
                num_generator++;
            if (invTile instanceof HullUpgrade)
                num_hull++;
            if (invTile instanceof ProximityDetector)
                num_detector++;
            if (invTile instanceof Wire)
                num_wires++;

            invTile = null;
        }
        switch(choice) {
            case 0:
                numberOf = num_drills;
                break;
            case 1:
                numberOf = num_battery;
                break;
            case 2:
                numberOf = num_generator;
                break;
            case 3:
                numberOf = num_hull;
                break;
            case 4:
                numberOf = num_detector;
                break;
            case 5:
                numberOf = num_wires;
                break;
            default:
                numberOf = 0;
        }

        if(numberOf == 0)
            EmptyInventory = true;
        else
            EmptyInventory = false;

        inventoryAmount.setText("You have " +Integer.toString(numberOf) + " number\n of this item in\n your inventory.");
    }

    public void RemoveItem(int choice) {
        switch(choice) {
            case 0:
                num_drills--;
                break;
            case 1:
                num_battery--;
                break;
            case 2:
                num_generator--;
                break;
            case 3:
                num_hull--;
                break;
            case 4:
                num_detector--;
                break;
            case 5:
                num_wires--;
                break;
        }
    }

    public void Builder(int choice){
        switch(choice) {
            case 0:
                base = 8;
                meta = 2;
                rad = 0;
                break;
            case 1:
                base = 15;
                meta = 6;
                rad = 0;
                break;
            case 2:
                base = 5;
                meta = 5;
                rad = 0;
                break;
            case 3:
                base = 25;
                meta = 10;
                rad = 0;
                break;
            case 4:
                base = 20;
                meta = 10;
                rad = 0;
                break;
            case 5:
                base = 0;
                meta = 1;
                rad = 0;
                break;
            default:
                rad = -2;
        }
        if(rad < 0)
            build_cost.setText("Left Clicking an item \nwill remove it with \nno refund of \nresources");
        else
            build_cost.setText("Resource Cost\nBasic: " + Integer.toString(base) +"\nMetalic: " + Integer.toString(meta) + "\nRadical: " + Integer.toString(rad));
        if(base > amount1 || meta > amount2 || rad > amount3)
            set_tooExpensive(true);
        else
            set_tooExpensive(false);

    }

    void set_tooExpensive(boolean indicator) {
        tooExpensive = indicator;
    }

    public void takeTheTile(Tile tile) {
        this.invTile = tile;
    }
}
