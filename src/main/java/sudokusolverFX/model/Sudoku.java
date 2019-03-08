package sudokusolverFX.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Sudoku {

	private int[][] grid;
	private int size, sizec, sizer;
	private String filePath;

	public Sudoku(int[][] grid, int size, int sizec, int sizer) {
		grid = this.grid;
		size = this.size;
		sizec = this.sizec;
		sizer = this.sizer;
	}

	public Sudoku() {

	}

	public void count_line() {
		this.size = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {

			@SuppressWarnings("unused")
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				this.size++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initGridFile() {
		this.grid = new int[this.size][this.size];

		try {

			File file = new File(this.filePath);
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {

				for (int i = 0; i < this.size; i++) {
					for (int j = 0; j < this.size; j++) {
						this.grid[i][j] = sc.nextInt();

					}
				}

			}
			sc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initGrid() {

		this.grid = new int[this.size][this.size];

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.grid[i][j] = 0;

			}
		}
	}

	public void resetGrid() {
		if (grid != null) {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					this.grid[i][j] = 0;
				}

			}
		}
	}

	public void diplay_grid() {

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				System.out.println(this.grid[i][j]);

			}
		}

	}

	public boolean ValidGrid() {
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				if (this.grid[i][j] > getSize() || this.grid[i][j] < 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void setSizeCell() {
		if (this.size == 4) {
			this.sizec = 2;
			this.sizer = 2;
		} else if (this.size == 6) {
			this.sizec = 2;
			this.sizer = 3;
		} else if (this.size == 9) {
			this.sizec = 3;
			this.sizer = 3;
		} else if (this.size == 12) {
			this.sizec = 3;
			this.sizer = 4;
		} else if (this.size == 16) {
			this.sizec = 4;
			this.sizer = 4;
		}
	}

	public boolean notInColumn(int k, int j) {

		int i;
		for (i = 0; i < this.size; i++) {
			if (this.grid[i][j] == k)
				return false;
		}
		return true;
	}

	public boolean notInRow(int k, int i) {
		int j;
		for (j = 0; j < this.size; j++) {
			if (this.grid[i][j] == k)
				return false;
		}
		return true;
	}

	public boolean notInCell(int k, int i, int j) {

		int i2 = i - (i % this.sizec);
		int j2 = j - (j % this.sizer);
		for (i = i2; i < i2 + this.sizec; i++)
			for (j = j2; j < j2 + this.sizer; j++)
				if (this.grid[i][j] == k)
					return false;
		return true;
	}

	public boolean isValid(int position) {

		if (position == this.size * this.size)
			return true;

		int i = position / this.size;
		int j = position % this.size;

		if (this.grid[i][j] != 0) {
			return isValid(position + 1);
		}

		int k;
		for (k = 1; k <= this.size; k++) {

			if (notInColumn(k, j) == true && notInRow(k, i) == true && notInCell(k, i, j) == true) {

				this.grid[i][j] = k;

				if (isValid(position + 1))
					return true;
			}
		}
		this.grid[i][j] = 0;

		return false;
	}

	public int[][] getGrid() {
		return grid;
	}

	public boolean isEmpty() {
		int a = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				a = a + this.grid[i][j];
			}
		}
		if (a == 0) {
			return true;
		}
		return false;
	}

	public void setGrid(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				this.grid[i][j] = grid[i][j];
			}
		}
	}

	public int getSizec() {
		return sizec;
	}

	public void setSizec(int sizec) {
		this.sizec = sizec;
	}

	public int getSizer() {
		return sizer;
	}

	public void setSizer(int sizer) {
		this.sizer = sizer;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getSize() {
		return size;
	}

}
