package com.example.projtest;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;

// TODO: Auto-generated Javadoc
/**
 * Klasa opcji, dzieki ktorej mo�emy wybra� tekstur� oraz ilo�� balonow.
 */
public class Options extends Scene{

	
	/** ilo�� balonow. */
	int countBaloons = Game.numberOfBaloons;
	
	/** przyciski zmiany warto�ci. */
	Sprite upButton, downButton, upChooseBaloonButton, downChooseBaloonButton;
	
	/** Tekst ktory pokazuje aktualn� ilo�� balonow. */
	Text countText;
	
	/**
	 * Konstruktor.
	 * 
	 * @param mCamera
	 *            - aktualna kamera
	 * @param mEngine
	 *            - silnik gry
	 * @param changeButton
	 *            - przycisk zmiany
	 * @param currentBaloon
	 *            - aktualna tekstura balonow
	 * @param font
	 *            - czcionka
	 */
	public Options(Camera mCamera, Engine mEngine, ITextureRegion changeButton, ITextureRegion[] currentBaloon, Font font)
	{
		final Sprite[] choosenBaloon = new Sprite[currentBaloon.length];
		this.setBackground(new Background(0, 125, 58));
		//left = new Rectangle(0, 0, 80, 80, mEngine.getVertexBufferObjectManager());
		countText = new Text(550, 240, font, "Ilosc balonow: "+String.valueOf(countBaloons), "Ilosc balonow: XXX".length(),mEngine.getVertexBufferObjectManager());
		this.attachChild(countText);
		
		upButton = new Sprite(550, 285, changeButton, mEngine.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
		        if (pSceneTouchEvent.isActionUp())
		        {
		        	if(countBaloons < 50)
		        	{
			        	MainActivity.musicPop.play();
			        	countBaloons++;
			        	Game.numberOfBaloons = countBaloons;
			        	countText.setText("Ilosc balonow: "+String.valueOf(countBaloons));
		        	}
		        	
		            
		        }
		        return true;
		    };
		};
		this.registerTouchArea(upButton);
		
		downButton = new Sprite(550, 195, changeButton, mEngine.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
		        if (pSceneTouchEvent.isActionUp())
		        {
		        	if(countBaloons > 1)
		        	{
			        	MainActivity.musicPop.play();
			        	countBaloons--;
			        	Game.numberOfBaloons = countBaloons;
			        	countText.setText("Ilosc balonow: "+String.valueOf(countBaloons));
		        	}
		            
		        }
		        return true;
		    };
		};
		this.registerTouchArea(downButton);
		downButton.setRotation(180);
		
		downChooseBaloonButton = new Sprite(180, 185, changeButton, mEngine.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
		        if (pSceneTouchEvent.isActionUp())
		        {         	
		         	if(MainActivity.currentTextureRegionIndex > 0)
		         	{
		         		MainActivity.musicPop.play();
			         	choosenBaloon[MainActivity.currentTextureRegionIndex].setVisible(false);
			         	choosenBaloon[MainActivity.currentTextureRegionIndex-1].setVisible(true);	        
			         	MainActivity.currentTextureRegionIndex--;

		         	}		         	
		        }
		        return true;
		    };
		};
		this.registerTouchArea(downChooseBaloonButton);
		downChooseBaloonButton.setRotation(180);
		

		
		upChooseBaloonButton = new Sprite(180, 305, changeButton, mEngine.getVertexBufferObjectManager()){
			
			/* (non-Javadoc)
			 * @see org.andengine.entity.Entity#onAreaTouched(org.andengine.input.touch.TouchEvent, float, float)
			 */
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
		    {
				 if (pSceneTouchEvent.isActionUp())
			     {
		         	
		         	if(MainActivity.currentTextureRegionIndex < choosenBaloon.length-1)
		         	{
		         		MainActivity.musicPop.play();
			         	choosenBaloon[MainActivity.currentTextureRegionIndex].setVisible(false);
			         	choosenBaloon[MainActivity.currentTextureRegionIndex+1].setVisible(true);	        
			         	MainActivity.currentTextureRegionIndex++;		         	

		         	}
		         	
		         	
			     }

		        return true;
		    };
		};
		this.registerTouchArea(upChooseBaloonButton);
		
		for(int i = 0; i < choosenBaloon.length; i++){
			choosenBaloon[i] = new Sprite(180, 240, currentBaloon[i], mEngine.getVertexBufferObjectManager());
			this.attachChild(choosenBaloon[i]);
			choosenBaloon[i].setVisible(false);
		}
		choosenBaloon[1].setVisible(true);
		
		this.attachChild(upButton);
		this.attachChild(downButton);
		this.attachChild(downChooseBaloonButton);
		this.attachChild(upChooseBaloonButton);
		
		//left.setColor(0.5f, 0.5f, 0.5f);
		//upButton.setPosition(50, 50);
		
		
	}
	
	
	
	
}
