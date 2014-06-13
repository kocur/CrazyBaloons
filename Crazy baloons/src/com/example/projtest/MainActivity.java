package com.example.projtest;

import java.io.IOException;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import com.badlogic.gdx.physics.box2d.Body;




// TODO: Auto-generated Javadoc
/**
 * G³owna klasa aktywnosci, w ktorej ³adowane s¹ zasoby ca³ej aplikacji a nastêpnie przekazywane dalej.
 *
 * @author kocur_000
 */

public class MainActivity extends BaseGameActivity{
	
	/** obiekt klasy singleton - silnik	 */
	mainEngine me;

	/** sceny wykorzystywane w aplikacji */
	public static Scene gameScene,menuScene, optionsScene;
	
	/** Sta³a szerokosc ekranu */
	protected static final int CAMERA_WIDTH = 800;
	
	/** Sta³a wysokoœæ ekranu. */
	protected static final int CAMERA_HEIGHT = 480;
	
	/** Tekstury. */
	BitmapTextureAtlas baloon1Texture,baloon2Texture;
	
	/** regiony tekstur */
	ITextureRegion baloon1TexureRegion,baloon2TexureRegion;
	
	/** aktualna tekstura balona */
	ITextureRegion[] currentTexureRegion = new TextureRegion[2];
	
	/** indeks aktualnej tekstury balona. */
	public static int currentTextureRegionIndex=1;
	
	/** swiat fizyki. */
	PhysicsWorld physicsWorld;
	
	/** body. */
	Body body;
	
	/** hud. */
	HUD hud;
	
	/** Kamera. */
	static Camera mCamera;
	
	/** czcionki. */
	private Font font, font30,font100;
	
	/** Stala maksymalnej iloœci klatek na sekundê */
	public static final int FPS_LIMIT = 60;
	
	/** ekran ³adowania. */
	private Scene splashScene;
	
	/** obiekt ekranu ³adowania */
	private Sprite splash;
	
	/** tekstura obiektu ³adowania. */
	private BitmapTextureAtlas splashTextureAtlas;
    
    /** tekstura obiektu ³adowania. */
    private ITextureRegion splashTextureRegion;
    
    /** DŸwiêk naciœniêcia. */
    static public Sound musicPop;
    
    /** nazwa sceny. */
    static public String sceneName = null;
	
	/** Regiony tekstur. */
	private ITextureRegion playTexureRegion, optionsTexureRegion, exitTextureRegion, changeTextureRegion;
	
	/** Tekstury. */
	private BitmapTextureAtlas playTexture, optionsTexture, exitTexture, changeTexture;
	
	/** Aktualna scena. */
	public static String currentScene = null;
	
	/* (non-Javadoc)
	 * @see org.andengine.ui.IGameInterface#onCreateEngineOptions()
	 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		

		 mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
		options.getAudioOptions().setNeedsSound(true);
		
		return options;
	}


	
	/* (non-Javadoc)
	 * @see org.andengine.ui.IGameInterface#onCreateResources(org.andengine.ui.IGameInterface.OnCreateResourcesCallback)
	 */
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback) {
		// TODO Auto-generated method stub
		 
		 
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	        splashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
	        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, this, "splash.jpg", 0, 0);
	        splashTextureAtlas.load();
	        
	        mainEngine.prepare(mEngine, this, mCamera, getVertexBufferObjectManager());
	        me = mainEngine.getInstance();
		// resource
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	
	/* (non-Javadoc)
	 * @see org.andengine.ui.IGameInterface#onCreateScene(org.andengine.ui.IGameInterface.OnCreateSceneCallback)
	 */
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		initSplashScene();

		pOnCreateSceneCallback.onCreateSceneFinished(this.splashScene);

	}

	

	
	/* (non-Javadoc)
	 * @see org.andengine.ui.IGameInterface#onPopulateScene(org.andengine.entity.scene.Scene, org.andengine.ui.IGameInterface.OnPopulateSceneCallback)
	 */
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) {
		// TODO Auto-generated method stub
			
		mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
             
                loadGfx();
       		 	loadFonts();
       		 	loadSounds();
                loadScenes();
                
                splash.detachSelf();
                mEngine.setScene(menuScene);
                
            }
		}));
  
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	/**
	 * £adowanie dŸwiêkow.
	 */
	public void loadSounds()
	{
		SoundFactory.setAssetBasePath("mfx/");
		try {
				musicPop = SoundFactory.createSoundFromAsset(getSoundManager(), this, "WHATTHE.WAV");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * £adowanie czcionek.
	 */
	public void loadFonts()
	{
		font = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, this.getAssets(),
			    "font/DigitalDream.ttf", 15, true, android.graphics.Color.BLACK);
			  font.load();
			  
		font30 = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 512, 512, this.getAssets(),
					    "font/DigitalDream.ttf", 30, true, android.graphics.Color.BLACK);
			  font30.load();
			  
		font100 = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 512, 512, this.getAssets(),
					    "font/DigitalDream.ttf", 100, true, android.graphics.Color.RED);
			  font100.load();		  
	}
	
	
	/**
	 * £adowanie grafik/tekstur.
	 */
	private void loadGfx() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// width and height power of 2^x
		baloon1Texture = new BitmapTextureAtlas(getTextureManager(), 39, 64);
		baloon1TexureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(baloon1Texture, this, "baloon.png", 0, 0);
		baloon1Texture.load();
		
		baloon2Texture = new BitmapTextureAtlas(getTextureManager(), 80, 80);
		baloon2TexureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(baloon2Texture, this, "baloon2.png", 0, 0);
		baloon2Texture.load();
		
		currentTexureRegion[0] = baloon1TexureRegion;
		currentTexureRegion[1] = baloon2TexureRegion;
		
		playTexture = new BitmapTextureAtlas(getTextureManager(), 220, 60);
		playTexureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(playTexture, this, "play.png", 0, 0);
		playTexture.load();
		
		optionsTexture = new BitmapTextureAtlas(getTextureManager(), 220, 60);
		optionsTexureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(optionsTexture, this, "opcje.png", 0, 0);
		optionsTexture.load();
		
		exitTexture = new BitmapTextureAtlas(getTextureManager(), 220, 60);
		exitTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(exitTexture, this, "koniec.png", 0, 0);
		exitTexture.load();
		
		changeTexture = new BitmapTextureAtlas(getTextureManager(), 150, 40);
		changeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(changeTexture, this, "change.png", 0, 0);
		changeTexture.load();
	}
    
	/**
	 * Inicjowanie ekranu ³adowania aplikacji.
	 */
	long a = 10000;
	private void initSplashScene()
	{
    	splashScene = new Scene();
    	splash = new Sprite(0, 0, splashTextureRegion, mEngine.getVertexBufferObjectManager())
    	{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
                
            }
    		
    	};
    	
    	splash.setScale(1.5f);
    	//splash.setPosition((CAMERA_WIDTH - splash.getWidth()) * 0.5f, (CAMERA_HEIGHT - splash.getHeight()) * 0.5f);
    	splash.setPosition(CAMERA_WIDTH/2, CAMERA_HEIGHT/2);
    	splashScene.attachChild(splash);

	}
	
	/**
	 * £adowanie scen.
	 */
	private void loadScenes()
	{
		
		this.menuScene = new Menu(font30,font100, mCamera, mEngine, playTexureRegion, optionsTexureRegion, exitTextureRegion,
				physicsWorld, hud, font, currentTexureRegion, body);
		//
	
		//this.gameScene = new Game(physicsWorld, hud, mCamera, mEngine, font, playerTexureRegion, body);
		this.optionsScene = new Options(mCamera, mEngine, changeTextureRegion, currentTexureRegion, font30);
	}

	

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	
	        mEngine.setScene(menuScene);
	        
	        if(mCamera.getHUD() != null) mCamera.getHUD().setVisible(false);
	        

	    }
	    return false; 
	}
	
	/**
	 * Ustawia aktualn¹ scenê.
	 *
	 * @param nameScene the new scene
	 */
	public void setScene(String nameScene)
	{
		if(sceneName == "menu") 
			mEngine.setScene(menuScene);
		if(sceneName == "game")
			mEngine.setScene(gameScene);
	}



	
}