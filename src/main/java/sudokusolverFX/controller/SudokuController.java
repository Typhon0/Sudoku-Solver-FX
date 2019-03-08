package sudokusolverFX.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sudokusolverFX.MainApp;
import sudokusolverFX.model.Sudoku;


public class SudokuController {

	@FXML
	private Button btnReset;
	@FXML
	private Button btnSolve;
	@FXML
	private BorderPane borderpan;
	private Sudoku sudoku = new Sudoku();

	public SudokuController() {

	}

	@FXML
	private void handleOpenAction(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
		fileChooser.setTitle("Open Sudoku File");
		File selectfile = fileChooser.showOpenDialog(MainApp.getPrimaryStage());

		if (selectfile != null) {

			String filePath = selectfile.getPath();
			sudoku.setFilePath(filePath);

			sudoku.count_line();

			try {
				sudoku.initGridFile();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error : Invalid file");
				alert.setContentText("Please select a other file or check file's syntax");
				alert.showAndWait();
			}

			sudoku.setSizeCell();

			// load fxml

			if (sudoku.getSize() == 4) {
				SwitchScene("/view/4x4.fxml");
			} else if (sudoku.getSize() == 6) {
				SwitchScene("/view/6x6.fxml");
			} else if (sudoku.getSize() == 9) {
				SwitchScene("/view/9x9.fxml");
			} else if (sudoku.getSize() == 12) {
				SwitchScene("/view/12x12.fxml");
			} else if (sudoku.getSize() == 16) {
				SwitchScene("/view/16x16.fxml");
				MainApp.getPrimaryStage().setHeight(900);
				MainApp.getPrimaryStage().setWidth(900);
			} else {
				System.out.println("Error");
			}

			Pane p = getPane();

			// fill grid
			int i = 0, j = 0;
			for (Node node3 : p.getChildren()) {
				if (node3 instanceof TextField) {
					if (sudoku.getGrid()[i][j] == 0) {
						((TextField) node3).setText("");
						j++;
					} else {
						((TextField) node3).setText(String.valueOf((sudoku.getGrid()[i][j])));
						// Mettre en rouge//((TextField)
						(node3).setStyle("-fx-text-inner-color: blue;");
						j++;
					}
				}
				if (j == sudoku.getGrid().length) {
					i++;
					j = 0;
				}
			}

		}
	}

	private void SwitchScene(String fxml) throws IOException {
		AnchorPane anchorpane = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource(fxml));
		anchorpane = (AnchorPane) loader.load();
		borderpan.setCenter(anchorpane);
		MainApp.getPrimaryStage().setHeight(717);
		MainApp.getPrimaryStage().setWidth(768);
	}

	@FXML
	private void handleSizeAction(ActionEvent event) throws IOException {

		MenuItem mItem = (MenuItem) event.getSource();
		String label = mItem.getText();
		if (label.equalsIgnoreCase("4x4")) {
			SwitchScene("/view/4x4.fxml");
			sudoku.setSize(4);
		} else if (label.equalsIgnoreCase("6x6")) {
			SwitchScene("/view/6x6.fxml");
			sudoku.setSize(6);
		} else if (label.equalsIgnoreCase("9x9")) {
			SwitchScene("/view/9x9.fxml");
			sudoku.setSize(9);
		} else if (label.equalsIgnoreCase("12x12")) {
			SwitchScene("/view/12x12.fxml");
			sudoku.setSize(12);
		} else if (label.equalsIgnoreCase("16x16")) {
			SwitchScene("/view/16x16.fxml");
			MainApp.getPrimaryStage().setHeight(900);
			MainApp.getPrimaryStage().setWidth(900);
			sudoku.setSize(16);
		} else {
			System.out.println("Error");
		}
	}

	private Pane getPane() {
		AnchorPane anchorpane = null;
		for (Node node : borderpan.getChildren()) {
			if (node instanceof AnchorPane) {
				anchorpane = ((AnchorPane) node);
			}
		}

		// get Pane from AnchorPane
		Pane p = null;
		for (Node node2 : anchorpane.getChildren()) {
			if (node2 instanceof Pane) {
				p = ((Pane) node2);
			}

		}
		return p;
	}

	@FXML
	private void handleResetAction(ActionEvent event) {

		sudoku.resetGrid();
		Pane p = getPane();

		for (Node node3 : p.getChildren()) {
			if (node3 instanceof TextField) {
				// clear
				((TextField) node3).setText("");
			}
		}
	}

	@FXML
	private void handleSolveAction(ActionEvent event) {
		AnchorPane anchorpane = null;
		for (Node node : borderpan.getChildren()) {
			if (node instanceof AnchorPane) {
				anchorpane = ((AnchorPane) node);
			}
		}
		if (anchorpane != null) {
			sudoku.initGrid();
			sudoku.setSizeCell();
			Pane p = getPane();
			int[][] tmp = new int[sudoku.getSize()][sudoku.getSize()];
			int i = 0, j = 0;
			boolean charDetect = false;
			for (Node node3 : p.getChildren()) {
				if (node3 instanceof TextField) {
					String value = ((TextField) node3).getText();
					if (value.isEmpty()) {
						tmp[i][j] = 0;
						j++;
					} else {
						try {
							tmp[i][j] = Integer.parseInt(value);
						} catch (NumberFormatException e) {
							charDetect = true;
						}
						j++;
					}
				}
				if (j == tmp.length) {
					i++;
					j = 0;
				}
			}
			sudoku.setGrid(tmp);
			if (charDetect == true) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error : Character detected");
				alert.setContentText("You can't insert character");
				alert.showAndWait();
			} else if (sudoku.isEmpty() == true || sudoku.ValidGrid() == true) {

				if (sudoku.isValid(0)) {
					i = 0;
					j = 0;
					for (Node node4 : p.getChildren()) {
						if (node4 instanceof TextField) {
							if (sudoku.getGrid()[i][j] == 0) {
								((TextField) node4).setText("");
								j++;
							} else {
								((TextField) node4).setText(String.valueOf((sudoku.getGrid()[i][j])));

								j++;
							}
						}
						if (j == sudoku.getGrid().length) {
							i++;
							j = 0;
						}
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error : Sudoku isn't solvable");
					alert.setContentText("Please check your grid");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error : Number too high or too low");
				alert.setContentText("One value in the grid isn't between 1 and " + sudoku.getSize());
				alert.showAndWait();
			}

		} else {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error : Can't solve the grid");
			alert.setContentText("Please select size or open one file to solve a grid");
			alert.showAndWait();

		}

	}

	@FXML
	private void handleSaveAction() throws IOException {

		AnchorPane anchorpane = null;
		for (Node node : borderpan.getChildren()) {
			if (node instanceof AnchorPane) {
				anchorpane = ((AnchorPane) node);
			}
		}
		if (anchorpane != null) {
			FileChooser fileChooser = new FileChooser();
			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			// Show save file dialog
			File file = fileChooser.showSaveDialog(MainApp.getPrimaryStage());

			if (file != null) {
				String path = file.getPath();

				Pane p = getPane();

				sudoku.initGrid();
				sudoku.setSizeCell();

				int[][] savegrid = new int[sudoku.getSize()][sudoku.getSize()];
				int i = 0, j = 0;
				for (Node node3 : p.getChildren()) {
					if (node3 instanceof TextField) {
						String value = ((TextField) node3).getText();
						if (value.isEmpty()) {
							savegrid[i][j] = 0;
							j++;
						} else {
							savegrid[i][j] = Integer.parseInt(value);
							j++;
						}
					}
					if (j == savegrid.length) {
						i++;
						j = 0;
					}
				}
				// WRITE TO FILE
				BufferedWriter outputWriter = null;
				outputWriter = new BufferedWriter(new FileWriter(path));
				for (i = 0; i < savegrid.length; i++) {
					for (j = 0; j < savegrid.length; j++) {
						outputWriter.write(savegrid[i][j] + "");
						outputWriter.write(" ");
					}
					outputWriter.newLine();

				}
				outputWriter.flush();
				outputWriter.close();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select size or open one file to save a grid");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleAboutAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/About.fxml"));
		BorderPane page = (BorderPane) loader.load();
		Stage dialogStage = new Stage();
		dialogStage.setTitle("About Sudoku Solver FX");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(MainApp.getPrimaryStage());
		dialogStage.setResizable(false);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();

	}

}
