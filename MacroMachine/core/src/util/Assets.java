package util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Tim on 3/22/2016.
 */

public class Assets implements Disposable{

    public static Assets inst = new Assets();

    private TextureAtlas atlas;

    public void init() {
        try {
            atlas = new TextureAtlas("packs/pack.atlas");
        }
        catch(Exception e) {
            System.err.println("Could not load packs/pack.atlas");
            atlas = null;
        }
    }

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

    @Override
    public void dispose() {
        if(atlas != null) atlas.dispose();
    }
}