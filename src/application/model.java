package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;

public class model {

	/**
	 * Data set for handling data for keywordFrequency using Hashtable, keywords for
	 * movies using HashTable, store unique keywords using Hashset, to store all
	 * keywords using Arraylist, sort occurence of keywords using a Hashmap
	 */
	private Hashtable<String, Integer> keywordFrequency;
	private Hashtable<String, ArrayList<String>> movieKeywords;
	private ArrayList<String> allKeyWords;
	private HashSet<String> uniqueKeyWords;
	private Map<String, Integer> tableSortedByOccurence;

	/**
	 * @param selectedFile
	 * @return the parsed xml file
	 */
	public String parsingXmlContents(File selectedFile) {

		keywordFrequency = new Hashtable<String, Integer>();
		movieKeywords = new Hashtable<String, ArrayList<String>>();
		allKeyWords = new ArrayList<String>();
		uniqueKeyWords = new HashSet<String>();
		
		String parsedXml = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder docBuilder = null;
		Document doc = null;
		// parse content of xml file and represent the data for elements
		try {
			docBuilder = factory.newDocumentBuilder();
			doc = docBuilder.parse(selectedFile.getPath());

			doc.getDocumentElement().normalize();

			parsedXml = "";
			parsedXml += "Root element :" + doc.getDocumentElement().getNodeName() + "\n";
			NodeList nList = doc.getElementsByTagName("movie");
			parsedXml += "----------------------------------" + "\n";
			/**
			 * iterate through each element of the list using Node
			 */
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				parsedXml += "\nCurrent Element : " + nNode.getNodeName() + "\n"; // represent movies element

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					movieKeywords.put(eElement.getElementsByTagName("title").item(0).getTextContent(),
							new ArrayList<String>());
					/**
					 * parsed XML data with Title, Year of release, Rating on IMDB
					 */
					parsedXml += "Title : " + eElement.getElementsByTagName("title").item(0).getTextContent() + "\n";
					parsedXml += "Year : " + eElement.getElementsByTagName("year").item(0).getTextContent() + "\n";
					parsedXml += "Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent() + "\n";
					// iterating for Director
					NodeList directorsList = eElement.getElementsByTagName("director");
					for (int count = 0; count < directorsList.getLength(); count++) {

						Node nodeDirector = directorsList.item(count);
						if (nodeDirector.getNodeType() == nodeDirector.ELEMENT_NODE) {
							Element director = (Element) nodeDirector;

							parsedXml += "Director Name : "
									+ director.getElementsByTagName("name").item(0).getTextContent() + "\n";
						}
					}
					// iterating for genres
					NodeList genres = eElement.getElementsByTagName("item");
					for (int count = 0; count < genres.getLength(); count++) {

						Node nodeItem = genres.item(count);
						if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
							Element item = (Element) nodeItem;
							/*
							 * System.out.print( "genre : "); System.out.println(item.getTextContent());
							 */
							parsedXml += "Genre : " + item.getTextContent() + "\n";

						}
					}

					// iterating for keywords
					NodeList keyword = eElement.getElementsByTagName("kw");
					for (int count = 0; count < keyword.getLength(); count++) {
						Node nodeItem = keyword.item(count);
						if (nodeItem.getNodeType() == nodeItem.ELEMENT_NODE) {
							Element item = (Element) nodeItem;
							allKeyWords.add(item.getTextContent().trim());
							uniqueKeyWords.add(item.getTextContent().trim());
							movieKeywords.get(eElement.getElementsByTagName("title").item(0).getTextContent())
									.add(item.getTextContent().trim());
						}
					}
					// iterating for uniqueKeywords
					for (String stock : uniqueKeyWords) {
						int count = 0;
						for (int i = 0; i < allKeyWords.size(); i++) {
							if (allKeyWords.get(i).equals(stock))
								count++;
						}
						keywordFrequency.put(stock, count);
					}

				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return parsedXml;
	}

	/**
	 * @param titleKeyword
	 * @return movie with corresponding title
	 */
	public String searchMovie(String titleKeyword) {

		tableSortedByOccurence = new LinkedHashMap<String, Integer>();
		String movie = "";
		Hashtable<String, Integer> resultMovieKeywords = new Hashtable<String, Integer>(); // store founded movie titles
		// iterate the titles
		for (String titles : movieKeywords.keySet()) {
			if (titles.toLowerCase().contains(titleKeyword.toLowerCase()))
				movie += titles + "\n";
		}
		if(!movie.equals(""))
		{
			movie = movie.trim();
			// Multiple movies associated with the title keyword
			if (movie.contains("\n")) {
				String[] movies = movie.split("\n");
				for (int i = 0; i < movies.length; i++) {
					for (int j = 0; j < movieKeywords.get(movies[i].trim()).size(); j++) {
						// Initializes the frequency of the co-related keyword
						// based on the searched movie title
						int frequency = keywordFrequency.get(movieKeywords.get(movies[i].trim()).get(j));
	
						// Appends the keywords and the keyword to the "resultMovieKeywords" hash table
						// based on the searched movie title
						resultMovieKeywords.put(movieKeywords.get(movies[i].trim()).get(j), frequency);
					}
				}
			}
			// Single movie associated with the title keyword
			else {
				for (int i = 0; i < movieKeywords.get(movie).size(); i++) {
					int frequency = keywordFrequency.get(movieKeywords.get(movie).get(i));
					resultMovieKeywords.put(movieKeywords.get(movie).get(i), frequency);
				}
			}
	
			// Calculating the top occurency with the co-related keywords
	
			// get all the entries from the hashtable and put it in a List
			List<Map.Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(resultMovieKeywords.entrySet());
	
			// sort the entries based on the value by custom Comparator
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	
				public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
					return entry2.getValue().compareTo(entry1.getValue());
				}
	
			});
	
			// put all sorted entries in LinkedHashMap
			for (Map.Entry<String, Integer> entry : list) {
				tableSortedByOccurence.put(entry.getKey(), entry.getValue());
			}
			movie += "\n\n\n Associated keywords \n" + tableSortedByOccurence;
		}
		else
		{
			movie = "No movies associated with the keyword";
		}
		return movie; // return movie

	}

	/**
	 * the barChartVisualization to present the barchart with keywords occurence
	 * using bar chart
	 * 
	 * @param keywordCount
	 */
	public void barChartVisualization(int keywordCount) {
		try {
			CategoryAxis xAxis = new CategoryAxis(); // xAsis represent keywords
			NumberAxis yAxis = new NumberAxis(); // yAxis represent frequency
			xAxis.setLabel("Keywords");
			yAxis.setLabel("Frequency");

			BarChart<String, Integer> bar = new BarChart(xAxis, yAxis); // create bar chart
			bar.setTitle("Keywords Frequency Bar Chart");

			XYChart.Series<String, Integer> series = new XYChart.Series<>();
			int count = 0; // set initial count to 0
			/**
			 * visualize bar chart
			 */
			for (String i : tableSortedByOccurence.keySet()) {
				count++;
				series.getData().add(new XYChart.Data(i, tableSortedByOccurence.get(i)));
				if (count == keywordCount)
					break;
			}

			series.setName("Keywords Frequency");

			bar.getData().add(series);
			Group root = new Group(bar);
			Scene sc2 = new Scene(root, 500, 400);
			Stage stage4 = new Stage();
			stage4.setTitle("Bar Chart");
			stage4.setScene(sc2);
			stage4.show();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * the pieChartVisualization to present the barchart with keywords occurence
	 * using pie chart
	 * 
	 * @param keywordCount
	 */
	public void pieChartVisualization(int keywordCount) {
		try {
			ObservableList<Data> list = FXCollections.observableArrayList(); // create list for data

			int count = 0;
			/**
			 * iterate the occurence of keywords
			 */
			for (String i : tableSortedByOccurence.keySet()) {
				count++;
				list.add(new PieChart.Data(i, tableSortedByOccurence.get(i)));
				if (count == keywordCount)
					break;
			}

			PieChart pieChart = new PieChart();// create piechart
			pieChart.setData(list);// assign data
			pieChart.setLegendSide(Side.LEFT);
			pieChart.setTitle("Keywords Frequency Pie Chart");
			pieChart.setClockwise(false);

			Group root = new Group();
			root.getChildren().add(pieChart);
			Scene sc3 = new Scene(root, 500, 400);
			Stage stage5 = new Stage();
			stage5.setScene(sc3);
			stage5.setTitle("Pie Chart");
			stage5.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
