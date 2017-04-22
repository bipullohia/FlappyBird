package com.bipullohia.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    //ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture bg, topTube, bottomTube, gameover;
    BitmapFont bitmapFont;
    Texture[] birds;
    int flapstate = 0;
    int gameState = 0;
    float birdY = 0;
    float velocity = 0;
    float gravity = 2;
    float tubeGap = 500;
    int score = 0;
    int scoringTube;
    float tubeOffsetMax;

    Random r;
    Circle birdCircle = new Circle();
    Rectangle[] bottomTubeRectangle;
    Rectangle[] topTubeRectangle;

    float tubeVelocity = 4;

    int nooftubes = 4;
    float[] tubeX = new float[nooftubes];
    float[] tubeOffset = new float[nooftubes];


    float distancebetweentubes;


    @Override
    public void create() {
        batch = new SpriteBatch();
        bg = new Texture("bg.png");

        birdCircle = new Circle();
        // shapeRenderer = new ShapeRenderer();

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(10);

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        gameover = new Texture("gameover.png");

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");


        tubeOffsetMax = Gdx.graphics.getHeight() - tubeGap / 2 - 100;
        r = new Random();

        topTubeRectangle = new Rectangle[nooftubes];
        bottomTubeRectangle = new Rectangle[nooftubes];

        distancebetweentubes = Gdx.graphics.getWidth() * 3 / 4;

        startNewGame();

    }

    public void startNewGame() {

        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

        for (int i = 0; i < nooftubes; i++) {

            tubeOffset[i] = (r.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - tubeGap - 200);
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distancebetweentubes;

            topTubeRectangle[i] = new Rectangle();
            bottomTubeRectangle[i] = new Rectangle();
        }


    }

    @Override
    public void render() {

        batch.begin();
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {

                score++;
                if (scoringTube < nooftubes - 1) {
                    scoringTube++;
                } else {
                    scoringTube = 0;
                }


            }


            if (Gdx.input.justTouched()) {

                velocity = -35;

            }

            for (int i = 0; i < nooftubes; i++) {

                if (tubeX[i] < -topTube.getWidth()) {

                    tubeX[i] += nooftubes * distancebetweentubes;
                    tubeOffset[i] = (r.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - tubeGap - 200);

                } else {

                    tubeX[i] = tubeX[i] - tubeVelocity;
                }

                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + tubeGap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - tubeGap / 2 - bottomTube.getHeight() + tubeOffset[i]);

                topTubeRectangle[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + tubeGap / 2 + tubeOffset[i],
                        topTube.getWidth(), topTube.getHeight());

                bottomTubeRectangle[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - tubeGap / 2 - bottomTube.getHeight() + tubeOffset[i],
                        bottomTube.getWidth(), bottomTube.getHeight());
            }

            if (birdY > 0) {

                velocity = velocity + gravity;
                birdY -= velocity;

            } else {

                gameState = 2;
            }

        } else if (gameState == 0) {

            if (Gdx.input.justTouched()) {

                gameState = 1;

            }


        } else if (gameState == 2) {

            batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);

            if (Gdx.input.justTouched()) {

                gameState = 1;
                startNewGame();
                score = 0;
                scoringTube = 0;
                velocity = 0;
            }

        }

        if (flapstate == 0) {
            flapstate = 1;
        } else {
            flapstate = 0;
        }

        batch.draw(birds[flapstate], Gdx.graphics.getWidth() / 2 - birds[flapstate].getWidth() / 2, birdY);

        bitmapFont.draw(batch, String.valueOf(score), Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 100);

        batch.end();

        birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapstate].getHeight() / 2, birds[flapstate].getWidth() / 2);

        // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // shapeRenderer.setColor(Color.CYAN);
        // shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for (int i = 0; i < nooftubes; i++) {

            // shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + tubeGap / 2 + tubeOffset[i],
            // topTube.getWidth(), topTube.getHeight());
            // shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - tubeGap / 2 - bottomTube.getHeight() + tubeOffset[i],
            //   bottomTube.getWidth(), bottomTube.getHeight());

            if (Intersector.overlaps(birdCircle, topTubeRectangle[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangle[i])) {

                gameState = 2;

            }

        }
        // shapeRenderer.end();


    }
}
