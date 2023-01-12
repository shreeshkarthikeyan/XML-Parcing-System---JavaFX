package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

public class controller {

	private view view;
	private model model;
	private File selectedFile = null;

	/**
	 * @param view
	 * @param model
	 */
	public controller(view view, model model) {
		this.view = view;
		this.model = model;

		/**
		 * implement chooseSourceListner for selecting xml file for input
		 */
		this.view.chooseSourceListener(e -> {

			view.setOtherContentsInvisible();
			FileChooser file = new FileChooser();
			file.setTitle("Open File");
			selectedFile = file.showOpenDialog(main.stage);
			if(selectedFile == null) // Alerts the user to choose an xml file
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please choose a source");
				alert.show();
			}
			else
				this.view.setlblSource(selectedFile.getName());

			if (!this.view.gettxtR().isEmpty())
				this.view.settxtR("");
		});

		/**
		 * implement domParserListener
		 */
		this.view.domParserListener(e -> {

			if(!(selectedFile == null))
			{
				if (selectedFile.getName().contains(".xml")) {
					String xml = this.model.parsingXmlContents(selectedFile);
					this.view.settxtR(xml);
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Parsing file is not in xml type!!");
					alert.show();
				}
			}
			else
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION); 
				alert.setContentText("Please choose a source!!");
				alert.show();
			}
		});

		/**
		 * listener for proceeding to db page or keyword search page
		 */
		this.view.proceedToDBListener(e -> {
			
			if (this.view.gettxtR().isEmpty() && selectedFile == null)
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please choose a source and parse the source!!");
				alert.show();
			}
			else if (this.view.gettxtR().isEmpty() && selectedFile != null)
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please parse the source!!");
				alert.show();
			}
			else
			{
				view.setDatabaseContentsVisible();
				if (!this.view.getkeywordTxtArea().isEmpty())
					this.view.setkeywordTxtArea("");
				
				if (!this.view.getresultTxt().isEmpty())
					this.view.setresultTxt("");
			}
		});

		/**
		 * listener for search keyword
		 */
		this.view.searchKeyWordListener(e -> {
			
			ArrayList<String> dummyKeywords = new ArrayList<String>(
					Arrays.asList("the", "and", "a", "an", "asdf", "am", "an",
							"so" , "by", "to", "had", "has", "we", "who",
							"why", "for", "did", "do", "was", "its")); // Dummy Keywords
			
			if(this.view.getkeywordTxtArea().trim().equals("")) // Alerts when the user does not any keyword
			{
				this.view.setresultTxt("");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please provide a keyword to search!!");
				alert.show();
			}
			else if(dummyKeywords.contains(this.view.getkeywordTxtArea().trim().toLowerCase()) ||
					this.view.getkeywordTxtArea().trim().length() <=1 ) // Alerts when the user when provides any dummy keyword
			{
				this.view.setresultTxt("");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please provide a valid keyword to search!!");
				alert.show();
			}
			else
			{
				this.view.setresultTxt("");
				String movie = this.model.searchMovie(this.view.getkeywordTxtArea().trim());
				this.view.setresultTxt(movie);
			}
		});

		/**
		 * listener for proceeding to visualization window
		 */
		this.view.proceedToVisualizationListener(e -> {
			if(this.view.getresultTxt().trim().equals("No movies associated with the keyword"))
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please provide a valid keyword!!");
				alert.show();
			}
			else if(this.view.getresultTxt().trim().equals("") && 
					this.view.getkeywordTxtArea().trim().equals("") )
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please provide a keyword!!");
				alert.show();
			}
			else if(this.view.getkeywordTxtArea().trim().length() > 1 && 
					this.view.getresultTxt().trim().equals(""))
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please click 'Search Keyword' to view its correlated keyword!!");
				alert.show();
			}
			else	
				view.setVisualContentsVisible();

		});
		
		/**
		 * listener for proceeding back to source window
		 */
		this.view.proceedBackToSourceListener(e -> {
			view.setSourceContentsVisible();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("Do you want to choose a different source file");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) 
			{
				selectedFile = null;
				this.view.setlblSource("");
				this.view.settxtR("");
			}
		});
		
		/**
		 * listener for proceeding back to database window
		 */
		this.view.proceedBackToDataBaseListener(e -> {
			view.setDBContentsVisible();
			
		});

		/**
		 * listener for generating the bar chart
		 */
		this.view.barChartVisualListener(e -> {
			this.model.barChartVisualization(this.view.isRadioButtonSelected());

		});

		/**
		 * listener for generating the pie chart
		 */
		this.view.pieChartVisualListener(e -> {
			this.model.pieChartVisualization(this.view.isRadioButtonSelected());

		});
	}
}
