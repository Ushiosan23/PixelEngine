package fx.soft.pixelengine.controllers;

import fx.soft.pixelengine.R;
import fx.soft.pixelengine.controllers.base.BaseController;
import fx.soft.pixelengine.data.Project;
import fx.soft.pixelengine.javafx.controls.FXButtonResource;
import fx.soft.pixelengine.javafx.utils.EventUtils;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller to Home file view
 */
public final class HomeScreenController extends BaseController {
	
	/**
	 * TextField from view
	 */
	public TextField txtSearch;
	
	/**
	 * Main container
	 */
	@FXML
	public BorderPane sceneContainer;
	
	/**
	 * Sidebar container
	 */
	@FXML
	public VBox sidebarContainer;
	
	/**
	 * Button to create new project
	 */
	public Button btnNew;
	
	/**
	 * Button to open existing project
	 */
	public Button btnOpen;
	
	/**
	 * Called after initialize object
	 */
	@Override
	protected void lateInit() {
		configureSearchTextField();
		configureSidebarOptions();
		configureCenterContainer();
		
		btnNew.setOnMouseClicked(this::onClickNewProject);
	}
	
	/**
	 * Configure textField search
	 */
	private void configureSearchTextField() {
		txtSearch.setOnContextMenuRequested(Event::consume);
	}
	
	/**
	 * Configure center content
	 */
	private void configureCenterContainer() {
		for (Node node : sidebarContainer.getChildren()) {
			if (node instanceof FXButtonResource) {
				MouseEvent event = new MouseEvent(MouseEvent.MOUSE_CLICKED,
					0,
					0,
					0,
					0,
					MouseButton.PRIMARY,
					1,
					false,
					false,
					false,
					false,
					true,
					false,
					false,
					true,
					false,
					false,
					null);
				Event.fireEvent(node, event);
				break;
			}
		}
	}
	
	/**
	 * Configure sidebar actions
	 */
	private void configureSidebarOptions() {
		for (Node node : sidebarContainer.getChildren()) {
			if (node instanceof FXButtonResource)
				node.setOnMouseClicked(this::onClickSidebarOption);
		}
	}
	
	/**
	 * Change button state (style view)
	 *
	 * @param button target button to change
	 * @param others target other nodes to change
	 */
	private void changeButtonStyle(FXButtonResource button, ObservableList<Node> others) {
		if (!button.getStyleClass().contains("active"))
			button.getStyleClass().add("active");
		
		for (Node node : others) {
			if (node != button)
				node.getStyleClass().remove("active");
		}
	}
	
	/**
	 * Called when any sidebar button is clicked
	 *
	 * @param event mouse event information
	 */
	private void onClickSidebarOption(MouseEvent event) {
		FXButtonResource source = EventUtils.getSource(event);
		assert source != null;
		
		// Check if resource is empty
		if (source.getResource().length() == 0)
			return;
		
		try {
			R.Resource location = new R.Resource(source.getResource());
			Parent parent = FXMLLoader.load(location.getURL(), bundle);
			
			changeButtonStyle(source, sidebarContainer.getChildren());
			sceneContainer.setCenter(parent);
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
	
	/**
	 * Called when new project button is clicked
	 *
	 * @param event mouse event information
	 */
	private void onClickNewProject(MouseEvent event) {
		Project.requestForNewProject(bundle);
	}
	
}
