package fx.soft.pixelengine.javafx.dialogs;

import fx.soft.pixelengine.R;
import fx.soft.pixelengine.controllers.base.BaseController;
import fx.soft.pixelengine.javafx.utils.ScreenUtils;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to show select location dialog
 */
public final class ProjectDialogLocation extends Dialog<Map<String, String>> {
	
	/**
	 * Save current stage window
	 */
	private final Stage currentStage;
	
	/**
	 * Primary constructor
	 */
	public ProjectDialogLocation() {
		super();
		currentStage = (Stage) getDialogPane().getScene().getWindow();
		currentStage.setOnCloseRequest(this::onCloseDialogRequest);
		currentStage.setOnShown(this::onDialogShown);
		
		try {
			FXMLLoader loader = new FXMLLoader(R.fxml_dialogs_projectdialoglocation.getURL());
			loader.setController(new ProjectDialogLocationController(this));
			// Create and load scene
			Parent parent = loader.load();
			Scene scene = new Scene(parent, 400, 200);
			// Set scene stylesheets
			scene.getStylesheets().add(R.css_base.location);
			// Set scene
			currentStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close dialog if cross window is clicked
	 *
	 * @param event window event information
	 */
	private void onCloseDialogRequest(WindowEvent event) {
		resultProperty().setValue(null);
		currentStage.close();
		event.consume();
	}
	
	/**
	 * Called after window shown
	 *
	 * @param event window information event
	 */
	private void onDialogShown(WindowEvent event) {
		Rectangle2D bounds = ScreenUtils.getPrimaryVisualBounds();
		Scene scene = currentStage.getScene();
		
		currentStage.setX((bounds.getWidth() - scene.getWidth()) / 2f);
		currentStage.setY((bounds.getHeight() - scene.getHeight()) / 2f);
	}
	
	/**
	 * Class to manage all scene dialog
	 */
	private static class ProjectDialogLocationController extends BaseController {
		
		/**
		 * Current dialog object
		 */
		ProjectDialogLocation currentDialog;
		
		/**
		 * Project message label
		 */
		@FXML
		public Label errorMessage;
		
		/**
		 * Project name field
		 */
		@FXML
		public TextField prjName;
		
		/**
		 * Project location field
		 */
		@FXML
		public TextField prjLocation;
		
		/**
		 * Project location button selection
		 */
		@FXML
		public Button prjSelectLocation;
		
		/**
		 * Project action cancel
		 */
		@FXML
		public Button prjActionCancel;
		
		/**
		 * Project action accept
		 */
		@FXML
		public Button prjActionAccept;
		
		/**
		 * Default constructor
		 *
		 * @param dialog pass current dialog
		 */
		public ProjectDialogLocationController(ProjectDialogLocation dialog) {
			currentDialog = dialog;
		}
		
		/**
		 * Initialize controller method
		 */
		@Override
		protected void lateInit() {
			prjActionAccept.setDisable(true);
			prjLocation.setEditable(false);
			
			// Set events
			prjName.textProperty().addListener(this::onInputNameTextEvent);
			prjSelectLocation.setOnMouseClicked(this::onSelectFolderLocation);
			
			prjActionCancel.setOnMouseClicked(this::onCancelEvent);
			prjActionAccept.setOnMouseClicked(this::onAcceptEvent);
		}
		
		/**
		 * Called every time when input name text change
		 *
		 * @param observable object to bind event
		 * @param oldVal     value before change
		 * @param newVal     value after change
		 */
		private void onInputNameTextEvent(ObservableValue<? extends String> observable, String oldVal, String newVal) {
			StringProperty property = (StringProperty) observable;
			TextField field = (TextField) property.getBean();
			
			prjActionAccept.setDisable(field.getText().length() == 0 || prjLocation.getText().length() == 0);
		}
		
		/**
		 * Called when user click in cancel button and set null value
		 *
		 * @param event mouse event info
		 */
		private void onCancelEvent(MouseEvent event) {
			currentDialog.resultProperty().setValue(null);
			currentDialog.currentStage.close();
		}
		
		/**
		 * Called when user click in accept button
		 *
		 * @param event mouse event info
		 */
		private void onAcceptEvent(MouseEvent event) {
			Map<String, String> result = new HashMap<>();
			
			result.put("name", prjName.getText());
			result.put("location", prjLocation.getText());
			
			currentDialog.resultProperty().setValue(result);
			currentDialog.currentStage.close();
		}
		
		/**
		 * Called when user click in folder icon button
		 *
		 * @param event mouse event info
		 */
		private void onSelectFolderLocation(MouseEvent event) {
			DirectoryChooser chooser = new DirectoryChooser();
			// configure chooser
			chooser.setTitle("Select project location");
			// Show chooser and get file
			File selected = chooser.showDialog(currentDialog.currentStage);
			prjName.requestFocus();
			if (selected != null) {
				prjLocation.setText(selected.getPath());
				checkFolderPermissions(selected);
			}
		}
		
		/**
		 * Check if folder has write permissions
		 *
		 * @param location target folder to check permissions
		 */
		private void checkFolderPermissions(File location) {
			prjActionAccept.setDisable(!location.canWrite() || prjName.getText().length() == 0);
			
			if (!location.canWrite()) {
				errorMessage.setText("Cannot has permission to write in this location");
			} else {
				errorMessage.setText("");
			}
		}
		
	}
	
}
