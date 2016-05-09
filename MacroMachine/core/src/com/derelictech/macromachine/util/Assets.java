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

    /**
     * Able to be called to get the textures again
     */
    public void init() {
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
    }

    /**
     * Gets the region needed from the TextureAtlas
     * @param name The name of the texture to get
     * @return Returns the Texture region from the atlas specified by name
     */
    public TextureRegion getRegion(String name) {
        if(atlas != null) {
            for (TextureAtlas.AtlasRegion r : atlas.getRegions()) {
                if (r.name.equals(name)) {
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * Sends the TextureRegion array of given name
     * @param frame_sequence_name The name of the frame sequence
     * @return Returns the {@link Array<TextureRegion>}
     */
    public Array<TextureRegion> getFrameSequence(String frame_sequence_name) {
        Array<TextureAtlas.AtlasRegion> atlasRegions = atlas.findRegions(frame_sequence_name);
        return new Array<TextureRegion>(atlasRegions);
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