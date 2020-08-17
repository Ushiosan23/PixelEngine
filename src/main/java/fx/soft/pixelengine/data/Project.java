package fx.soft.pixelengine.data;

import fx.soft.pixelengine.javafx.BaseApplication;
import fx.soft.pixelengine.javafx.dialogs.ProjectDialogLocation;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class for project data
 */
public final class Project {
	
	/**
	 * Project string location
	 */
	private final File location;
	
	/**
	 * Get string location
	 *
	 * @param location project location
	 */
	public Project(String location) {
		this.location = new File(location);
	}
	
	/**
	 * Check if project location exists
	 *
	 * @return {@link Boolean} with project status
	 */
	public boolean exists() {
		return location.exists();
	}
	
	/* ---------------------------------------------------------
	 *
	 * Static methods
	 *
	 * --------------------------------------------------------- */
	
	
	@Nullable
	public static Project requestForNewProject(ResourceBundle bundle) {
		ProjectDialogLocation chooser = new ProjectDialogLocation();
		
		chooser.setTitle(bundle.getString("dialog.create.title"));
		chooser.initOwner(BaseApplication.current.currentStage);
		Optional<Map<String, String>> resultChooser = chooser.showAndWait();
		
		System.out.println(resultChooser);
		return null;
	}
	
}
