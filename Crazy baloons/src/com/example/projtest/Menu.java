package com.example.projtest;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;



// TODO: Auto-generated Javadoc
/**
 * Klasa menu od ktorej zaczyna siê Tworzenie obiektow innych scen.
 */
public class Menu extends MenuScene implements IOnMenuItemClickListener{


	/** silnik gry. */
	private Engine engine = mainEngine.getInstance().engine;

	/** œwiat fizyki. */
	PhysicsWorld physicsWorld;
	
	/** silnik gry. */
	Engine mEngine;
	
	/** hud. */
	HUD hud;
	
	/** aktualna kamera. */
	Camera mCamera;
	
	/** czcionki. */
	Font font, font30, font100;
	
	/** Tekstury balonow. */
	ITextureRegion[] playerTextureRegion;
	
	/** cia³o tekstur. */
	Body body;
	
	/**
	 * Instantiates a new menu.
	 *
	 * @param font30 - czcionka rozmiar 30
	 * @param font100 - czcionka rozmiar 100
	 * @param camera - aktualna kamera
	 * @param e - silnik gry
	 * @param playButton - przycisk play
	 * @param optionsButton - przycisk opcji
	 * @param exitButton - przycisk wyjœcia
	 * @param physicsWorld - œwiat fizyki
	 * @param hud - hud
	 * @param font - czcionka rozmiar ma³y na fpsy
	 * @param playerTexureRegion - tekstura balona
	 * @param body - cia³o tekstury
	 */
	public Menu(Font font30,Font font100, Camera camera, Engine e, ITextureRegion playButton, ITextureRegion optionsButton, ITextureRegion exitButton, PhysicsWorld physicsWorld, HUD hud, Font font, ITextureRegion[] playerTexureRegion, Body body) {
		super(camera);
		//menuScene = new MenuScene(camera);
	    
		this.physicsWorld = physicsWorld;
		this.mEngine =  e;
		this.hud = hud;
		this.mCamera = camera;
		this.font = font;
		this.playerTextureRegion = playerTexureRegion;
		this.body = body;
		this.font30 = font30;
		this.font100 = font100;
		
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(0, playButton, e.getVertexBufferObjectManager()), 1.1f, 1);
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(1, optionsButton, e.getVertexBufferObjectManager()), 1.1f, 1);
	    final IMenuItem exitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(2, exitButton, e.getVertexBufferObjectManager()), 1.1f, 1);
	    
	    this.addMenuItem(optionsMenuItem);
	    this.addMenuItem(playMenuItem);
	    this.addMenuItem(exitMenuItem);
	    this.buildAnimations();
	    this.setBackground(new Background(0, 125, 58));
	    
	    this.setOnMenuItemClickListener(this);
	    
	    
	    
	}
	


	/* (non-Javadoc)
	 * @see org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener#onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene, org.andengine.entity.scene.menu.item.IMenuItem, float, float)
	 */
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		//handleButtonActions();
	    
	    switch(pMenuItem.getID())
	    {
	        case 0:
	            MainActivity.musicPop.play();
	            MainActivity.gameScene = new Game(physicsWorld, hud, mCamera, mEngine, font, font30,font100, playerTextureRegion[MainActivity.currentTextureRegionIndex], body);
	            engine.setScene(MainActivity.gameScene);
	            MainActivity.currentScene = "game";
	            return true;
	        case 1:
	            //action
	        	MainActivity.musicPop.play();
	        	engine.setScene(MainActivity.optionsScene);
	        	MainActivity.currentScene = "options";
	            return true;
	        case 2:
	        	MainActivity.musicPop.play();
	        	System.exit(0);
	        	return true;
	        default:
	            return false;
	    }

	}


	/**
	 * £adowanie sceny na ktorej ryzgrywa siê gra.
	 *
	 * @param physicsWorld - œwiat fizyki
	 * @param mEngine - silnik gry
	 * @param hud - hud
	 * @param mCamera - aktualna kamera
	 * @param font - ma³a czcionka na fpsy
	 * @param playerTextureRegion - tekstura balona
	 * @param body - cia³o tekstury
	 */
	public void loadGameScene(PhysicsWorld physicsWorld,Engine mEngine, HUD hud, Camera mCamera, Font font,
			TextureRegion playerTextureRegion, Body body)
	{
		MainActivity.gameScene = new Game(physicsWorld, hud, mCamera, mEngine, font, font30,font100, playerTextureRegion, body);
	}

}
