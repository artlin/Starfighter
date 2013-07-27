package pl.swietlik.starfighter;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SFGameRenderer implements GLSurfaceView.Renderer {

    private SFBackground background = new SFBackground();
    private SFBackground background2 = new SFBackground();
    private SFGoodGuy player1 = new SFGoodGuy();
    private int goodGuyBankFrames = 0;

    private SFEnemy[] enemies = new SFEnemy[SFEngine.TOTAL_INTERCEPTORS
            + SFEngine.TOTAL_SCOUTS + SFEngine.TOTAL_WARSHIPS];
    private SFTextures textureLoader;
    private int[] spriteSheets = new int[1];

    private long loopStart = 0;
    private long loopEnd = 0;
    private long loopRunTime = 0;

    private float bgScroll1;
    private float bgScroll2;

    private void initializeInterceptors() {
        for(int x = 0; x <= SFEngine.TOTAL_INTERCEPTORS - 1; x++) {
            SFEnemy interceptor = new SFEnemy(SFEngine.TYPE_INTERCEPTOR,
                    SFEngine.ATTACK_RANDOM);
            enemies[x] = interceptor;
        }
    }

    private void initializeScouts() {
        for(int x = SFEngine.TOTAL_INTERCEPTORS; x <= SFEngine.TOTAL_INTERCEPTORS
                + SFEngine.TOTAL_SCOUTS - 1; x++) {
            SFEnemy scout;
            if(x >= (SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS) / 2) {
                scout = new SFEnemy(SFEngine.TYPE_SCOUT,
                        SFEngine.ATTACK_RIGHT);
            } else {
                scout = new SFEnemy(SFEngine.TYPE_SCOUT,
                        SFEngine.ATTACK_LEFT)
            }
            enemies[x] = scout;
        }
    }

    private void initializeWarships() {
        for(int x = SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS;
                x <= SFEngine.TOTAL_INTERCEPTORS + SFEngine.TOTAL_SCOUTS
                + SFEngine.TOTAL_WARSHIPS - 1; x++) {
            SFEnemy warship = new SFEnemy(SFEngine.TYPE_WARSHIP,
                    SFEngine.ATTACK_RANDOM);
            enemies[x] = warship;
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        loopStart = System.currentTimeMillis();
        try {
            if(loopRunTime < SFEngine.GAME_THREAD_FPS_SLEEP) {
                Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP - loopRunTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        scrollBackground1(gl);
        scrollBackground2(gl);
		movePlayer1(gl);
		
        // rest of printing methods will be call from here

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
        loopEnd = System.currentTimeMillis();
        loopRunTime = (loopEnd - loopStart);
    }

    private void scrollBackground1(GL10 gl) {
        if(bgScroll1 == Float.MAX_VALUE) {
            bgScroll1 = 0f;
        }

        // this code resets scale and movement model matrix mode
        // do not move this matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, bgScroll1, 0.0f); //moving texture

        background.draw(gl);
        gl.glPopMatrix();
        bgScroll1 += SFEngine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }

    private void scrollBackground2(GL10 gl) {
        if(bgScroll2 == Float.MAX_VALUE) {
            bgScroll2 = 0f;
        }
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.5f, 1f, 1f);
        gl.glTranslatef(1.5f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, bgScroll2, 0.0f);

        background2.draw(gl);
        gl.glPopMatrix();
        bgScroll2 += SFEngine.SCROLL_BACKGROUND_2;
        gl.glLoadIdentity();
    }

    private void movePlayer1(GL10 gl){
        switch (SFEngine.playerFlightAction) {
            case SFEngine.PLAYER_BANK_LEFT_1:
                gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glPushMatrix();
				gl.glScalef(.25f, .25f, 1f);
				if(goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI &&
						SFEngine.playerBankPosX > 0) {
					SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED;
					gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.75f, 0.0f, 0.0f);
					goodGuyBankFrames += 1;
				} else if(goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI
							&& SFEngine.playerBankPosX > 0 ) {
					SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED;
					gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0.25f, 0.0f); 
				} else {
					gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0.0f, 0.0f);
					goodGuyBankFrames = 0;
				}	
				player1.draw(gl);
				gl.glPopMatrix();
				gl.glLoadIdentity();
				break;
            case SFEngine.PLAYER_BANK_RIGHT_1:
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glPushMatrix();
				gl.glScalef(.25f, .25f, 1f);
				if(goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI &&
						SFEngine.playerBankPosX < 3 ) {
					SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED;
					gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.25f, 0.0f, 0.0f);
					goodGuyBankFrames += 1;
				} else if(goodGuyBankFrames >=
						SFEngine.PLAYER_FRAMES_BETWEEN_ANI
						&& SFEngine.playerBankPosX < 3 ) {
					SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED;
					gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.50f, 0.0f, 0.0f); 
				} else {
					gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
					gl.glMatrixMode(GL10.GL_TEXTURE);
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0.0f, 0.0f);
					goodGuyBankFrames = 0;
				}	
				player1.draw(gl);
				gl.glPopMatrix();
				gl.glLoadIdentity();
				
                break;
            case SFEngine.PLAYER_RELASE:
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f);
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, 0.0f);
                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
				goodGuyBankFrames +=1;
				
                break;
            default:
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.25f, .25f, 1f);
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f);
                gl.glMatrixMode(GL10.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, 0.0f);
                player1.draw(gl);
                gl.glPopMatrix();
                gl.glLoadIdentity();
                break;
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        initializeInterceptors();
        initializeScouts();
        initializeWarships();
        textureLoader = new SFTextures(gl);
        spriteSheets =  textureLoader.loadTexture(gl, SFEngine.CHARACTERS_SHEET,
                SFEngine.context, 1);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        background.loadTexture(gl, SFEngine.BACKGROUND_LAYER_ONE,
                SFEngine.context);
        background2.loadTexture(gl, SFEngine.BACKGROUND_LAYER_TWO,
                SFEngine.context);
        player1.loadTexture(gl, SFEngine.PLAYER_SHIP, SFEngine.context);

    }
}
