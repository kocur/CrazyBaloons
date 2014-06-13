package com.example.projtest;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

// TODO: Auto-generated Javadoc
/**
 * Klasa we wzorcu projektowym singleton, ktora obs³uguje silnik ca³ej gry (jeden).
 */
public class mainEngine {
	
	/** Sta³a instancja klasy. */
	private static final mainEngine INSTANCE = new mainEngine();
    
    /** silnik gry. */
    public Engine engine;
    
    /** g³owna aktywnoœæ (inicjuj¹ca aplikacjê). */
    public MainActivity activity;
    
    /** kamera. */
    public Camera camera;
    
    /** vbom. */
    public VertexBufferObjectManager vbom;
	
	/**
	 * Przygotowanie silnika gry.
	 * 
	 * @param engine
	 *            - silnik gry
	 * @param activity
	 *            - g³owna aktywnosc
	 * @param camera
	 *            - aktualna kamera
	 * @param vbom
	 *            - vbom
	 */
	public static void prepare(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {		
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;        
    }
	
	 /**
	 * Gets the single instance of mainEngine.
	 * 
	 * @return single instance of mainEngine
	 */
 	public static mainEngine getInstance()
     {
         return INSTANCE;
     }
}
