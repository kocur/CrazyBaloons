package com.example.projtest;

import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

// TODO: Auto-generated Javadoc
/**
 * Klasa odpowiedzialna za rozgrywk�.
 */
public class Game extends Scene{

	
	/** ilo�� balonow. */
	public static int numberOfBaloons = 5;
	
	/**  Generator liczb losowych. */
	Random generator = new Random();
	
	/** �wiat fizyki. */
	private PhysicsWorld physicsWorld;
	
	/** hud. */
	private HUD hud;
	
	/** Kamera. */
	private Camera mCamera;
	
	/** Silnik gry. */
	private Engine mEngine;
	
	/** Teksty u�ywane w rozgrywce. */
	private Text fpsText, pointsText, timeText, winText, win2Text;
	
	/** przez ten obiekt nast�pi po��czenie zjawisk fizyki (elatycznosc, ciezkosc...) z obiektami balonow. */
	PhysicsConnector[] pc = new PhysicsConnector[numberOfBaloons];
	
	/** Tablica oniektow sprite balonow. */
	Sprite[] sBaloon = new Sprite[numberOfBaloons];
	
	/** Tablica obiektow odpowiedzialnych za operacje na obiektach balonow. */
	Cbaloon[] baloon = new Cbaloon[numberOfBaloons];
	
	/** zmienna zwi�zana ze zjawiskami fizycznymi. */
	final FixtureDef[] PLAYER_FIX = new  FixtureDef[numberOfBaloons];
	
	/** czcionki. */
	private Font font, pointsFont, winFont;
	
	/** Licznik klatek na sekund�. */
	final FPSCounter fpsCounter = new FPSCounter();
	
	/** Region tekstury balonow. */
	private ITextureRegion playerTexureRegion;
	
	/** Cia�o tekstury. */
	private Body body;
	
	/** Czas rozgrywki. */
	private int timee = 0;
	
	/** przycisk. */
	Sprite spriteButton;
	
	
	/**
	 * Instantiates a new game.
	 *
	 * @param physicsWorld - �wiat fizyki
	 * @param hud - hud
	 * @param mCamera - aktualna kamera
	 * @param mEngine - silnik gry
	 * @param font - ma�a czcionka do fpsow
	 * @param font30 - rozmiar 30 czcionki
	 * @param font100 - rozmiar 100 czcionki
	 * @param playerTexureRegion - Region tekstury balonow
	 * @param body - cia�o tekstury
	 */
	public Game(PhysicsWorld physicsWorld, HUD hud, Camera mCamera,
			Engine mEngine,  Font font, Font font30, Font font100,
			ITextureRegion playerTexureRegion, Body body) {
		this.physicsWorld = physicsWorld;
		this.hud = hud;
		this.mCamera = mCamera;
		this.mEngine = mEngine;
		this.font = font;
		this.playerTexureRegion = playerTexureRegion;
		this.body = body;
		this.pointsFont = font30;
		this.winFont = font100;
		init();
	}
	
	
    
	/**
	 * Inits the.
	 */
	private void init()
	{
		
		
		
		Cbaloon.baloonInstances = 0;
		this.setBackground(new Background(0, 125, 58));
		physicsWorld = new PhysicsWorld(new Vector2(10,
				-10), false);
		this.registerUpdateHandler(physicsWorld);
		hud = new HUD();
		
	
			mCamera.setHUD(hud);
			
		
		fpsText = new Text(MainActivity.CAMERA_WIDTH-45, MainActivity.CAMERA_HEIGHT-15, this.font, "FPS:", "FPS: XX".length(),mEngine.getVertexBufferObjectManager());
		pointsText = new Text(120,15,this.pointsFont,"balony: ","balony: XXX".length(),mEngine.getVertexBufferObjectManager());
		timeText = new Text(400,15,this.pointsFont,"czas: 0","w czasie XXX sekund".length(),mEngine.getVertexBufferObjectManager());
		winText = new Text(400,240,this.winFont,"BRAWOOO!","BRAWOOO!".length(),mEngine.getVertexBufferObjectManager());
		win2Text = new Text(400,120,this.pointsFont,"zbiles "+numberOfBaloons+" balonow!","zbiles XX balonow!".length(),mEngine.getVertexBufferObjectManager());
		winText.setVisible(false);
		win2Text.setVisible(false);
		
		createWalls();
		addBaloons(numberOfBaloons);
		
			
		this.mEngine.registerUpdateHandler(fpsCounter);
		hud.attachChild(fpsText);
		hud.attachChild(pointsText);
		hud.attachChild(timeText);
		hud.attachChild(winText);
		hud.attachChild(win2Text);
		
		
		//gameScene.setOnSceneTouchListener(this);
		//------------------------------------------------
				//--------------GAME LOOP-------------------------
				//------------------------------------------------
				this.registerUpdateHandler(new IUpdateHandler() {                    
		            public void reset() {        
		            }             
		            public void onUpdate(float pSecondsElapsed) {
		            	//SETTING RANDOM GRAVITY
		            	physicsWorld.setGravity(new Vector2((generator.nextInt(40)-20),
		        				generator.nextInt(40)-20));
		            	//DISPLAYING FPS
		            	fpsText.setText("fps: "+(int)fpsCounter.getFPS());
		            	//DISPLAYING POINTS
		            	pointsText.setText("balony: "+Cbaloon.baloonInstances);
		            	
		            	if(Cbaloon.baloonInstances == 0){
		            		hud.detachChild(fpsText);
		            		hud.detachChild(pointsText);
		            		timeText.setText("w czasie "+timee+" sekund!");
		            		winText.setVisible(true);
		            		win2Text.setVisible(true);
	
		            		
		            		
		            	}
		            		
		            	
		            }
		        });
				//------------------------------------------------
				//--------------END OF GAME LOOP------------------
				//------------------------------------------------
				
			this.registerUpdateHandler(new TimerHandler(1.0f, true, new ITimerCallback() {
				
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					if(!winText.isVisible()){
						timee++;
						timeText.setText("czas: "+timee);
						
					}
					
				}
			}));
			
	}

	

	
	/**
	 * Tworzy ramk� dla balonow (granice) �eby balony nie ucieka�y poza kamer�.
	 */
	private void createWalls() {
		// TODO Auto-generated method stub
		FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		Rectangle ground = new Rectangle(400, MainActivity.CAMERA_HEIGHT, MainActivity.CAMERA_WIDTH,
				1, this.mEngine.getVertexBufferObjectManager());
		Rectangle ceil = new Rectangle(400, 0, MainActivity.CAMERA_WIDTH,
				1, this.mEngine.getVertexBufferObjectManager());
		Rectangle left = new Rectangle(0, 240, 1,
				MainActivity.CAMERA_HEIGHT, this.mEngine.getVertexBufferObjectManager());
		Rectangle right = new Rectangle(MainActivity.CAMERA_WIDTH, 240, 1,
				MainActivity.CAMERA_HEIGHT, this.mEngine.getVertexBufferObjectManager());
		
		ground.setColor(new Color(15, 50, 0));
		ceil.setColor(new Color(15, 50, 0));
		left.setColor(new Color(15, 50, 0));
		right.setColor(new Color(15, 50, 0));
		
		PhysicsFactory.createBoxBody(physicsWorld, ground, BodyType.StaticBody,
				WALL_FIX);
		PhysicsFactory.createBoxBody(physicsWorld, ceil, BodyType.StaticBody,
				WALL_FIX);
		PhysicsFactory.createBoxBody(physicsWorld, left, BodyType.StaticBody,
				WALL_FIX);
		PhysicsFactory.createBoxBody(physicsWorld, right, BodyType.StaticBody,
				WALL_FIX);
		
		this.attachChild(ground);
		this.attachChild(ceil);
		this.attachChild(left);
		this.attachChild(right);
	}
	
	
	
	/**
	 * dodaje odpowiedni� ilo�� balonow do sceny.
	 *
	 * @param numberOfBaloons - ilo�� balonow
	 */
	private void addBaloons(int numberOfBaloons){
		for(int i = 0; i < numberOfBaloons; i++)
		{
			PLAYER_FIX[i] = PhysicsFactory.createFixtureDef(0.0f,
					0.94f, 0.0f);
							
			baloon[i] = new Cbaloon(sBaloon[i], playerTexureRegion, mEngine);
			sBaloon[i] = baloon[i].getSprite();
			//sPlayer.setRotation(45.0f);	
			body = PhysicsFactory.createCircleBody(physicsWorld, sBaloon[i],
					BodyType.DynamicBody, PLAYER_FIX[i]);
			this.registerTouchArea(sBaloon[i]);
			this.attachChild(sBaloon[i]);
			pc[i] = new PhysicsConnector(sBaloon[i],	body, true, false);
			physicsWorld.registerPhysicsConnector(pc[i]);
								
	}
	}
	

	

	
}
