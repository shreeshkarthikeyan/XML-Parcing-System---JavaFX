package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class view {

	/**
	 * javafx components for creating entire application
	 */
	private GridPane defaultView, defaultView1, defaultView2;
	private Button btnSource, btnDOMParser, proceedToDataBasePage;
	private Label lblSource;
	private TextArea xmlTxtR;
	private HBox hboxSource, hboxParsing;

	/**
	 * Stage1 components
	 */
	Stage stage1 = new Stage();
	private Button btnDatabase, proceedToVisualization, backToSourcePage;
	private TextArea keywordTxt, resultTxt;
	private HBox hboxDatabase, hboxDatabaseButtons;

	/**
	 * Stage2 components
	 */
	Stage stage2 = new Stage();
	private ToggleGroup buttonGroup; // toggle for representing the radio buttons
	private RadioButton t3, t5, t8, t10; // Radio Buttons for keyword frequency
	private Button btnPie; // pie chart button
	private Button btnGraph; // graph button
	private Button backToDatabasePage;
	private HBox hBoxKey, visBox; // HBox for key word and data visualization

	/**
	 * 
	 * @return defaultView as ParentView on startup
	 */
	public Parent asParent() {
		return defaultView;
	}

	/**
	 * Generate View
	 */
	public view() {
		mainPane();
		sourcePage();
		dataBasePage();
		visualization();
	}

	/**
	 * create visualization view
	 */
	private void visualization() {

		defaultView2 = new GridPane();
		defaultView2.setPadding(new Insets(10, 10, 10, 10));
		defaultView2.setHgap(10);
		defaultView2.setVgap(10);

		buttonGroup = new ToggleGroup();

		t3 = new RadioButton("Top 3");
		t3.setToggleGroup(buttonGroup);
		t3.setSelected(true);

		t5 = new RadioButton("Top 5");
		t5.setToggleGroup(buttonGroup);

		t8 = new RadioButton("Top 8");
		t8.setToggleGroup(buttonGroup);

		t10 = new RadioButton("Top 10");
		t10.setToggleGroup(buttonGroup);

		btnPie = new Button("Show Pie");
		btnGraph = new Button("Show Bar Graph");

		hBoxKey = new HBox(t3, t5, t8, t10);
		hBoxKey.setSpacing(10);

		visBox = new HBox(btnPie, btnGraph);
		visBox.setSpacing(10);

		backToDatabasePage = new Button("Back");
		defaultView2.addRow(4, hBoxKey);
		defaultView2.addRow(5, visBox);
		defaultView2.addRow(6, backToDatabasePage);
		
		Scene sc1 = new Scene(defaultView2, 500, 500);

		stage2.setScene(sc1);
		stage2.setTitle("Visualization Page");
		stage2.show();
	}

	/**
	 * create Database view or Keyword Search view
	 */
	private void dataBasePage() {

		defaultView1 = new GridPane();
		defaultView1.setPadding(new Insets(10, 10, 10, 10));
		defaultView1.setHgap(10);
		defaultView1.setVgap(10);
		btnDatabase = new Button("Search Movies");

		keywordTxt = new TextArea();
		keywordTxt.setMaxSize(270, 5);
		keywordTxt.setPromptText("Separate keywords with spaces");
		resultTxt = new TextArea();
		proceedToVisualization = new Button("Proceed");
		backToSourcePage = new Button("Back");
		
		hboxDatabase = new HBox(keywordTxt, btnDatabase);
		hboxDatabase.setSpacing(10);
		
		hboxDatabaseButtons = new HBox(proceedToVisualization, backToSourcePage);
		hboxDatabaseButtons.setSpacing(10);
		defaultView1.addRow(0, hboxDatabase);
		defaultView1.addRow(1, resultTxt);
		defaultView1.addRow(2, hboxDatabaseButtons);
		
		Scene sc = new Scene(defaultView1, 500, 500);

		stage1.setScene(sc);
		stage1.setTitle("Database Page");
		stage1.show();
	}

	/**
	 * generate main pane
	 */
	private void mainPane() {

		defaultView = new GridPane();
		defaultView.setPadding(new Insets(10, 10, 10, 10));
		defaultView.setHgap(10);
		defaultView.setVgap(10);
	}

	/**
	 * generate source page or home page of application
	 */
	private void sourcePage() {
		lblSource = new Label("");
		xmlTxtR = new TextArea();
		xmlTxtR.setEditable(false);
		btnSource = new Button("Choose source");
		btnDOMParser = new Button("DOM Parser");
		proceedToDataBasePage = new Button("Proceed");
		proceedToDataBasePage.setAlignment(Pos.CENTER);
		;
		hboxSource = new HBox(btnSource, lblSource);

		hboxSource.setSpacing(10);

		hboxParsing = new HBox(btnDOMParser);

		hboxParsing.setSpacing(10);

		defaultView.addRow(0, hboxSource);
		defaultView.addRow(1, hboxParsing);
		defaultView.addRow(3, xmlTxtR);
		defaultView.addRow(4, proceedToDataBasePage);
	}

	/**
	 * set label name for source
	 * 
	 * @param labelName
	 */
	public void setlblSource(String labelName) {
		lblSource.setText(labelName);
	}

	/**
	 * set label name for textarea for 'xmlTxtR'
	 * 
	 * @param labelName
	 */
	public void settxtR(String labelName) {
		xmlTxtR.setText(labelName);
	}

	/**
	 * get text inside the textarea for 'txtR'
	 * 
	 * @return String
	 */
	public String gettxtR() {
		return xmlTxtR.getText();
	}

	/**
	 * listener for choose a file
	 * 
	 * @param listener
	 */
	public void chooseSourceListener(EventHandler<ActionEvent> listener) {
		btnSource.setOnAction(listener);
	}

	/**
	 * listener for DOM parser
	 * 
	 * @param listener
	 */
	public void domParserListener(EventHandler<ActionEvent> listener) {
		btnDOMParser.setOnAction(listener);
	}

	/**
	 * proceed to data base or keyword search window Listener
	 * 
	 * @param listener
	 */
	public void proceedToDBListener(EventHandler<ActionEvent> listener) {
		proceedToDataBasePage.setOnAction(listener);
	}
	
	/**
	 * proceed back to source page window Listener
	 * 
	 * @param listener
	 */
	public void proceedBackToSourceListener(EventHandler<ActionEvent> listener) {
		backToSourcePage.setOnAction(listener);
	}
	
	/**
	 * proceed back to database page window Listener
	 * 
	 * @param listener
	 */
	public void proceedBackToDataBaseListener(EventHandler<ActionEvent> listener) {
		backToDatabasePage.setOnAction(listener);
	}

	/**
	 * hide content of database & virtualisation page
	 */
	public void setOtherContentsInvisible() {
		stage1.hide();
		stage2.hide();
	}

	/**
	 * hide content of database & shows the source page
	 */
	public void setSourceContentsVisible() {
		stage1.hide();
		main.stage.show();
		
	}
	
	/**
	 * make DataBase Page content visible
	 */
	public void setDatabaseContentsVisible() {
		main.stage.hide();
		stage1.show();
	}

	/**
	 * make DataBase Page content visible and hides virtualization page contents
	 */
	public void setDBContentsVisible() {
		stage2.hide();
		setDatabaseContentsVisible();
		
	}
	/**
	 * get data from textarea fot 'keywordTxtArea'
	 * 
	 * @return String
	 */
	public String getkeywordTxtArea() {
		return keywordTxt.getText();
	}

	/**
	 * set label name 
	 * 
	 * @param labelName
	 */
	public void setkeywordTxtArea(String labelName) {
		keywordTxt.setText(labelName);
	}
	
	/**
	 * set label name
	 * 
	 * @param labelName
	 */
	public void setresultTxt(String labelName) {
		resultTxt.setText(labelName);
	}
	
	/**
	 * get data from textarea
	 * 
	 * @return String
	 */
	public String getresultTxt() {
		return resultTxt.getText();
	}

	/**
	 * search keyword listener
	 * 
	 * @param listener
	 */
	public void searchKeyWordListener(EventHandler<ActionEvent> listener) {
		btnDatabase.setOnAction(listener);
	}

	/**
	 * proceed to visualization page
	 * 
	 * @param listener
	 */
	public void proceedToVisualizationListener(EventHandler<ActionEvent> listener) {
		proceedToVisualization.setOnAction(listener);
	}

	/**
	 * make Visualization Page content visible
	 */
	public void setVisualContentsVisible() {
		stage2.show();
	}

	/**
	 * check which radio button selected for frequency of keywords
	 * 
	 * @return keywordCount
	 */
	public int isRadioButtonSelected() {
		int keywordCount = 0;

		if (t3.isSelected())
			keywordCount = 3;
		else if (t5.isSelected())
			keywordCount = 5;
		else if (t8.isSelected())
			keywordCount = 8;
		else if (t10.isSelected())
			keywordCount = 10;

		return keywordCount;

	}

	/**
	 * listener for presenting bar chart
	 * 
	 * @param listener
	 */
	public void barChartVisualListener(EventHandler<ActionEvent> listener) {
		btnGraph.setOnAction(listener);
	}

	/**
	 * listener for presenting pie chart
	 * 
	 * @param listener
	 */
	public void pieChartVisualListener(EventHandler<ActionEvent> listener) {
		btnPie.setOnAction(listener);
	}
}
