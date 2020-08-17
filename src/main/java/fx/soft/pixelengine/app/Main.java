package fx.soft.pixelengine.app;

import fx.soft.pixelengine.database.DatabaseAccess;
import javafx.application.Application;

/**
 * Main entry class
 */
public class Main {
	
	/**
	 * Entry main point. <br/>
	 * In this method initialize all resources and application thread.
	 *
	 * @param args application arguments
	 */
	public static void main(String[] args) throws Exception {
		DatabaseAccess.initializeDatabase();
		Application.launch(PixelEngineApp.class, args);
	}
	
}
