package com.gdx.platformer2D;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class Platformer2D extends ApplicationAdapter {

	ShapeRenderer shapeRenderer;
    OrthographicCamera cam;
    float viewportWidth;

    World world;

    @Override
	public void create () {

        // this object is instantiated for custom rendering
		shapeRenderer = new ShapeRenderer();

		// size of screen in world space (meters)
		viewportWidth = 20f;

        float aspect = Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera(viewportWidth, viewportWidth * aspect);

        // situate camera's bottom left corner at (0, 0)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

	}

	@Override
	public void render () {

        handleInput();

        // apply camera transform to renderer(s)
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);

        // sustain a black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw a red (2x2) square 2 meters from the left and bottom edges
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(.86f,.28f,.35f,1f);
        shapeRenderer.rect(2,2,2,2);
        shapeRenderer.end();

	}

    private void handleInput() {

        // move the camera about on keypress
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-.1f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(.1f, 0, 0);
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = viewportWidth;
        cam.viewportHeight = viewportWidth * (height/width);
        cam.update();
    }

	@Override
	public void dispose () {
        shapeRenderer.dispose();
	}
}
