package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        Music freebird = Gdx.audio.newMusic(Gdx.files.internal("mainSound.mp3"));
        freebird.setLooping(true);
        freebird.setVolume(0.1f);
        freebird.play();
        setScreen(new HomePage(this));
    }
}
