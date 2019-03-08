package sudokusolverFX;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import sudokusolverFX.model.Sudoku;

public class MainApp extends Application {

	private static Stage primaryStage;
	private static BorderPane rootLayout;
	private Sudoku sudoku = new Sudoku();
	



	@Override
	public void start(Stage primaryStage) {
		MainApp.primaryStage = primaryStage;
		MainApp.primaryStage.setTitle("Sudoku Solver");
		MainApp.primaryStage.getIcons().add(new Image("img/logo.png"));
		initRootLayout();
		
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	
	public Sudoku getSudoku() {
		return sudoku;
	}
	

	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
	launch(args);
	}
}
