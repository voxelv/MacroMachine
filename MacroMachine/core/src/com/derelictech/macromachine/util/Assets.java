package com.derelictech.macromachine.util;

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
    private Array<TextureRegion> wireTextures = new Array<TextureRegion>();

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
     * Sends the TextureRegion array of wire connection configurations
     * @return Returns the {@link Array<TextureRegion>} of the wire configurations
     */
    public Array<TextureRegion> getWireTextures() {
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions("units/wire");
        for(TextureAtlas.AtlasRegion ar : regions) {
            wireTextures.add(ar);
        }
        return wireTextures;
    }

    /**
     * Disposes of the atlas and clears {@link Assets#wireTextures}.
     * If this is called during runtime, {@link Assets#init()} must be called again to get the textures from
     * the pack again.
     */
    @Override
    public void dispose() {
        if(atlas != null) atlas.dispose();
        wireTextures.clear();
    }
}