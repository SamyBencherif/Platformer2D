package com.gdx.platformer2D;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Platformer2D extends ApplicationAdapter {

	ShapeRenderer shapeRenderer;
    OrthographicCamera cam;
    float viewportWidth;

    World world;
    Box2DDebugRenderer debugRenderer;

    @Override
	public void create () {

        /** Initialize Rendering */

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

        /** Initialize Physics */

        world = new World(new Vector2(0, -10), true);

        // temporary
        debugRenderer = new Box2DDebugRenderer();

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(10, 20);

        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

        // Create a circle shape and set its radius
        CircleShape circle = new CircleShape();
        circle.setRadius(2f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();


        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(10, 1));

        // Create a body from the definition and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(8, 1.0f);

        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();

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
//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(.86f,.28f,.35f,1f);
//        shapeRenderer.rect(2,2,2,2);
//        shapeRenderer.end();

        // debug renderer
        debugRenderer.render(world, cam.combined);

        // simulate physics
        world.step(1/60f, 6, 2);
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
        float oldHeight = cam.viewportHeight;
        cam.viewportHeight = viewportWidth * height/width;

        // keep the bottom left corner fixed
        cam.position.add(0,(cam.viewportHeight-oldHeight)/2,0);

        cam.update();
    }

	@Override
	public void dispose () {
        shapeRenderer.dispose();
	}
}
