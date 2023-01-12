package application;

import javafx.application.Application;
import javafx.scene.Scene; 
import javafx.stage.Stage; 


public class main extends Application {
	/**
	 * stage class for container of windown
	 */
	public static Stage stage;
	private view view;
	private model model;
	private controller controller;
	/**
	 * main function to launch java application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * create Stage container for starting the application
	 */
	public void start(Stage stage) throws Exception {
		this.stage = stage; // set current stage
		view = new view(); // call view
		model = new model(); // call data model
		controller = new controller(view, model); // pass them to controller for handling

		Scene scene = new Scene(view.asParent(), 500, 500); // generate scene

		stage.setScene(scene); // set scene
		stage.setTitle("IMDB XML File Parsing System"); // set title for application
		stage.show();// present the stage

	}

}
