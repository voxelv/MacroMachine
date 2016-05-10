package com.derelictech.macromachine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Contains and/or gets the Assets needed for the game
 * @author Tim Slippy, voxelv
 */

public class Assets implements Disposable{

    public static Assets inst = new Assets();

    private TextureAtlas atlas;

    private Music music;
    private Music drill;
    private Music proximity_alert;
    public Sound move;
    public Sound radical_explosion;

    private Array<Sound> sounds = new Array<Sound>();
    private Array<Music> musics = new Array<Music>();

    public static Array<TextureRegion> cellCloseFrames;
    public static TextureRegion basicMaterial;
    public static TextureRegion basicMaterialIcon;
    public static TextureRegion metalicMaterial;
    public static TextureRegion metalicMaterialIcon;
    public static TextureRegion radicalMaterial;
    public static TextureRegion radicalMaterialIcon;
    public static TextureRegion control_unit;
    public static TextureRegion drillTexture;
    public static Array<TextureRegion> drill_extend;
    public static TextureRegion e_battery;
    public static TextureRegion generator;
    public static TextureRegion hull_upgrade;
    public static TextureRegion proximity_detector;
    public static TextureRegion proximity_dish;
    public static Array<TextureRegion> wire;
    public static TextureRegion cell_edge2_pad1;
    public static TextureRegion game_grid_edge5_pad3;
    public static TextureRegion hud_bg;



    /**
     * Able to be called to get the textures again
     */
    public void init() {
        Gdx.app.log("ASSETS", "Loading Assets");
        try {
            atlas = new TextureAtlas("packs/pack.atlas");
        }
        catch(Exception e) {
            System.err.println("Could not load packs/pack.atlas");
            atlas = null;
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("sfx/music.mp3"));
        music.setVolume(0.3f);
        music.setLooping(true);
        musics.add(music);
        drill = Gdx.audio.newMusic(Gdx.files.internal("sfx/drill.wav"));
        musics.add(drill);
        proximity_alert = Gdx.audio.newMusic(Gdx.files.internal("sfx/proximity_alert.wav"));
        musics.add(proximity_alert);
        move = Gdx.audio.newSound(Gdx.files.internal("sfx/move.wav"));
        sounds.add(move);
        radical_explosion = Gdx.audio.newSound(Gdx.files.internal("sfx/radical_explosion.wav"));
        sounds.add(radical_explosion);

        cellCloseFrames = new Array<TextureRegion>(atlas.findRegions("cell_anim/cell_edge2_pad1"));
        basicMaterial = new TextureRegion(atlas.findRegion("materials/basic"));
        basicMaterialIcon = new TextureRegion(atlas.findRegion("materials/basic_resource"));
        metalicMaterial = new TextureRegion(atlas.findRegion("materials/metalic"));
        metalicMaterialIcon = new TextureRegion(atlas.findRegion("materials/metalic_resource"));
        radicalMaterial = new TextureRegion(atlas.findRegion("materials/radical"));
        radicalMaterialIcon = new TextureRegion(atlas.findRegion("materials/radical_resource"));
        control_unit = new TextureRegion(atlas.findRegion("units/control_unit"));
        drillTexture = new TextureRegion(atlas.findRegion("units/drill"));
        drill_extend = new Array<TextureRegion>(atlas.findRegions("units/drill_extend"));
        e_battery = new TextureRegion(atlas.findRegion("units/e_battery"));
        generator = new TextureRegion(atlas.findRegion("units/generator"));
        hull_upgrade = new TextureRegion(atlas.findRegion("units/hull_upgrade"));
        proximity_detector = new TextureRegion(atlas.findRegion("units/proximity_detector"));
        proximity_dish = new TextureRegion(atlas.findRegion("units/proximity_dish"));
        wire = new Array<TextureRegion>(atlas.findRegions("units/wire"));

        cell_edge2_pad1 = new TextureRegion(atlas.findRegion("cell_edge2_pad1"));
        game_grid_edge5_pad3 = new TextureRegion(atlas.findRegion("game_grid_edge5_pad3"));
        hud_bg = new TextureRegion(atlas.findRegion("hud_bg"));
    }

    /**
     * Gets the region needed from the TextureAtlas
     * @param name The name of the texture to get
     * @return Returns the Texture region from the atlas specified by name
     */
    public TextureRegion getRegion(String name) {
        if(name.equals("materials/basic")) return basicMaterial;
        if(name.equals("materials/basic_resource")) return basicMaterialIcon;
        if(name.equals("materials/metalic")) return metalicMaterial;
        if(name.equals("materials/metalic_resource")) return metalicMaterialIcon;
        if(name.equals("materials/radical")) return radicalMaterial;
        if(name.equals("materials/radical_resource")) return radicalMaterialIcon;
        if(name.equals("units/control_unit")) return control_unit;
        if(name.equals("units/drill")) return drillTexture;
        if(name.equals("units/e_battery")) return e_battery;
        if(name.equals("units/generator")) return generator;
        if(name.equals("units/hull_upgrade")) return hull_upgrade;
        if(name.equals("units/proximity_detector")) return proximity_detector;

        if(name.equals("units/proximity/dish")) return proximity_dish;
        if(name.equals("cell_edge2_pad1")) return cell_edge2_pad1;
        if(name.equals("game_grid_edge5_pad3")) return game_grid_edge5_pad3;
        if(name.equals("hud_bg")) return hud_bg;


        return null;
    }

    /**
     * Sends the TextureRegion array of given name
     * @param frame_sequence_name The name of the frame sequence
     * @return Returns the {@link Array<TextureRegion>}
     */
    public Array<TextureRegion> getFrameSequence(String frame_sequence_name) {
        if(frame_sequence_name.equals("units/drill_extend")) return drill_extend;
        if(frame_sequence_name.equals("cell_anim/cell_edge2_pad1")) return cellCloseFrames;
        if(frame_sequence_name.equals("units/wire")) return wire;
        return null;
    }

    public void playDrillSound() {
        if(!drill.isPlaying()) drill.play();
    }

    public void playProximityAlert(boolean play) {
        if(play) proximity_alert.play();
        else proximity_alert.stop();
    }

    public void playMusic(boolean play) {
        if(play) music.play();
        else music.stop();
    }

    /**
     * Disposes stuff
     * If this is called during runtime, {@link Assets#init()} must be called again to get the textures from
     * the pack again.
     */
    @Override
    public void dispose() {
        if(atlas != null) atlas.dispose();
        for(Sound s : sounds) {
            s.dispose();
        }
        for(Music m : musics) {
            m.dispose();
        }
    }
}