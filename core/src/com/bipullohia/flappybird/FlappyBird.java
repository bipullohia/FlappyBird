package com.bipullohia.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    SpriteBatch batch;
    Texture bg, topTube, bottomTube;

    Texture[] birds;
    int flapstate = 0;
    int gameState = 0;
    float birdY = 0;
    float velocity = 0;
    float gravity = 2;
    float tubeGap = 500;

    float tubeOffsetMax;

    Random r;

    float tubeVelocity = 4;


    int nooftubes = 4;
    float[] tubeX = new float[nooftubes];
    float[] tubeOffset = new float[nooftubes];


    float distancebetweentubes;


    @Override
    public void create() {
        batch = new SpriteBatch();
        bg = new Texture("bg.png");

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

        tubeOffsetMax = Gdx.graphics.getHeight() - tubeGap / 2 - 100;
        r = new Random();

        distancebetweentubes = Gdx.graphics.getWidth() * 3 / 4;

        for (int i = 0; i < nooftubes; i++) {

            tubeOffset[i] = (r.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - tubeGap - 200);
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distancebetweentubes;

        }
    }

    @Override
    public void render() {

        batch.begin();
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState != 0) {

            if (Gdx.input.justTouched()) {

                velocity = -35;


            }

            for (int i = 0; i < nooftubes; i++) {

                if (tubeX[i] < -topTube.getWidth()) {

                    tubeX[i] = nooftubes * distancebetweentubes;

                } else {

                    tubeX[i] = tubeX[i] - tubeVelocity;
                }

                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + tubeGap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - tubeGap / 2 - bottomTube.getHeight() + tubeOffset[i]);

            }

            if (birdY > 0 || velocity < 0) {

                velocity = velocity + gravity;
                birdY -= velocity;
            }

        } else {

            if (Gdx.input.justTouched()) {

                Gdx.app.log("touched", "yes");
                gameState = 1;
            }


        }

        if (flapstate == 0) {
            flapstate = 1;
        } else {
            flapstate = 0;
        }

        batch.draw(birds[flapstate], Gdx.graphics.getWidth() / 2 - birds[flapstate].getWidth() / 2, birdY);
        batch.end();

    }
}
