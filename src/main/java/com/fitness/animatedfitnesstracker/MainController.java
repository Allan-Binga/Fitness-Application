package com.fitness.animatedfitnesstracker;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MainController {

	public MainController() {
		workouts = new LinkedList<Workout>();
	}

	private int goal=60;
	Timeline timeline = null;
	List<Workout> workouts;
	@FXML
	private ComboBox<String> comboWorkout;
	@FXML
	private ImageView imageView;
	@FXML
	private LineChart<String, Number> lineChart;

	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button setGoal;
	@FXML
	private Button startWorkout;
	@FXML
	private Button stopWorkout;
	@FXML
	private TextField goalInput;
	@FXML
	private Tab tabStats;
	@FXML
	private Label labelSelected;
	@FXML
	private Button resetSession;
	
	@FXML
	private ProgressIndicator progressOverall;
	
	@FXML
	public void initialize() {
		String[] workouts = { "1. Back bend ", "2. Back train ", "3. Leg Stretch", "4. Leg joints " };
		this.comboWorkout.getItems().addAll(workouts);
		this.comboWorkout.getSelectionModel().select(0);
		labelSelected.setText(this.comboWorkout.getSelectionModel().getSelectedItem());
		Duration duration = Duration.seconds(1);
		

        // Set the cycle count to indefinite for continuous updates
        
		this.comboWorkout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				try {
					if (comboWorkout.getSelectionModel().getSelectedIndex() == 1) {
						// load the image to the image view
						imageView.setImage(new Image(this.getClass().getResource("HAfM.gif").toExternalForm()));
					}
					else if (comboWorkout.getSelectionModel().getSelectedIndex() == 2) {
						// load the image to the image view
						imageView.setImage(new Image(this.getClass().getResource("JJgA.gif").toExternalForm()));
					}
					else if (comboWorkout.getSelectionModel().getSelectedIndex() == 3) {
						// load the image to the image view
						imageView.setImage(new Image(this.getClass().getResource("KmKW.gif").toExternalForm()));
					}
					labelSelected.setText(comboWorkout.getSelectionModel().getSelectedItem());
				} catch (Exception ex) {
					System.out.println("Error: " + ex.getMessage());
				}
			}
		});
		
		this.setGoal.setOnAction((e) -> {
			progressIndicator.setProgress(0);
			try {
				int g = Integer.parseInt(goalInput.getText().toString());
				// Start the Timeline
				goal = g;
				if(timeline == null)
				timeline = new Timeline(
		                new KeyFrame(duration, event -> {
		                    // Update the progress of the ProgressBar
		                	
		                	double p =progressIndicator.getProgress();
		                	p +=(1.0/goal);//tick
		                    progressIndicator.setProgress(p);
		                })
		        );
				timeline.setCycleCount(Timeline.INDEFINITE);
		        
			}catch(Exception ex)
			{
				System.out.println("Error: "+ex.getMessage());
			}
	        
		});
		this.startWorkout.setOnAction((e)->{
			try {
				if(timeline != null)
				{
					progressIndicator.setProgress(0.00);
					timeline.play();
					this.startWorkout.setDisable(true);
				}
			}catch(Exception ex)
			{
				System.out.println("Error:" + ex.getMessage());
			}
		});
		this.stopWorkout.setOnAction((e)->{
			try {
				if(timeline != null)
				{
					timeline.stop();
					Workout workout = new Workout(this.comboWorkout.getSelectionModel().getSelectedItem(), this.progressIndicator.getProgress());
					this.workouts.add(workout);
					this.startWorkout.setDisable(false);
					this.progressOverall.setProgress(getOverallScore());
				}
				
			}catch(Exception ex)
			{
				System.out.println("Error:" + ex.getMessage());
			}
		});
		
		this.tabStats.setOnSelectionChanged((e)->{
			if(this.tabStats.isSelected())
			{
				//load the graph
				setGraph();
			}
		});
		this.resetSession.setOnAction((e)->{
			this.workouts.clear();
			this.progressIndicator.setProgress(0);
			this.progressOverall.setProgress(0);
		});
	}
	
	public void setGraph()
	{
		lineChart.getData().clear();
		XYChart.Series<String,Number> series=new XYChart.Series<String,Number>();
		for(Workout w : workouts)
		{
			series.getData().add(new XYChart.Data<String, Number>(w.name,w.percentage));
		}
		series.setName("Workout Statistics");
		
		lineChart.getData().add(series);
	}
	public double getOverallScore()
	{
		double total = 0;
		for(Workout w: workouts)
		{
			total += w.percentage;
		}
		total = (total/workouts.size());
		total = (int)total;
		total = total/100;
		//System.out.println("Overall score: "+total);
		return total;
	}
}
