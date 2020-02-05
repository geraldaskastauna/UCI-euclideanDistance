package coursework2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {	
	/* Set variables that hold paths for reading files */
	private static final String FIRST_FILE = "src/resources/cw2DataSet1.csv";
	private static final String SECOND_FILE = "src/resources/cw2DataSet2.csv";
	
	/* Variables for array size */
	private static int TOTAL_ELEMENTS = 65;
	private static int TOTAL_ROWS = 2810;
	
	/* Arrays with data from files */
	private static int[][] firstFileData = new int[TOTAL_ROWS][TOTAL_ELEMENTS];
	private static int[][] secondFileData = new int[TOTAL_ROWS][TOTAL_ELEMENTS];
	
	/* File read method (returns array) */
	private static int[][] fileRead(String fileName, int[][] array) {
		Scanner scannerObject;
		String inputLine;
		int row = 0;
		
		try {
			scannerObject = new Scanner(new BufferedReader(new FileReader(fileName)));
			
			while(scannerObject.hasNextLine()) {
				inputLine = scannerObject.nextLine();
				
				// Split elements separeted by comma from the file
				String[] inputArray = inputLine.split(",");
				
				for(int arrayElement = 0; arrayElement < inputArray.length; arrayElement++) {
					array[row][arrayElement] = Integer.parseInt(inputArray[arrayElement]);
				}
				
				row++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File was not found" + e);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error reading file" + e);
		}
		return array;
	}

	/* Store both files data into arrays */
	public static void storeData() {
		fileRead(FIRST_FILE, firstFileData);
		fileRead(SECOND_FILE, secondFileData);
	}
	
	/* Count euclidean distance */
	public static int distance(int[][] firstArray, int[][] secondArray) {
		int distance;
		int[] closestArray;
		int closestDistance;
		int count = 0;
			
		// Loop through first data set
		for(int firstFileArrays = 0; firstFileArrays < TOTAL_ROWS; firstFileArrays++) {
			// Reset values
			closestArray = new int[0];
			closestDistance = 0;
			// Loop through second data set
			for(int secondFileArrays = 0; secondFileArrays < TOTAL_ROWS; secondFileArrays++) {
				// Reset value
				distance = 0;
				// Loop through array elements
				for(int arrayElements = 0; arrayElements < TOTAL_ELEMENTS - 1; arrayElements++) {
					// Euclidean distance formula
					distance += Math.pow((secondArray[secondFileArrays][arrayElements] - firstArray[firstFileArrays][arrayElements]), 2);
				}
				// Check for closest array
				if(closestDistance == 0 || distance < closestDistance) {
					closestDistance = distance;
					closestArray = secondArray[secondFileArrays];
				}
			}
			// Count total correct answers
			if(firstArray[firstFileArrays][64] == closestArray[64]) {
				count++;
			}
		}
		return count;
	}
	
	/* Round double values to one decimal point */
	public static double roundToOneDecimal(double value) {
									// val = 123.123
		value = value * 10; 		// val = 1231.23
		value = Math.round(value); 	// val = 1231
		value = value / 10; 		// val = 123.1
		
		return value;
	}
	
	/* Get accuracy percentage */
	public static double getAccuracy(int[][] firstArray, int[][] secondArray) {
		// Store accurate results from first to second dataset
		double firstTotal = distance(firstArray, secondArray);
		System.out.println("From FIRST dataset to SECOND: " + (int) firstTotal + "/" + TOTAL_ROWS);
		
		// Get FIRST to SECOND dataset accuracy percentage
		double firstPercentage = firstTotal / TOTAL_ROWS * 100;
		System.out.println("Percentage: " + roundToOneDecimal(firstPercentage));
		
		// Store accurate results from second to first dataset
		double secondTotal = distance(secondArray, firstArray);
		System.out.println("\nFrom SECOND dataset to FIRST: " + (int) secondTotal + "/" + TOTAL_ROWS);
		
		// Get SECOND to FIRST dataset accuracy percentage
		double secondPercentage = secondTotal / TOTAL_ROWS * 100;
		System.out.println("Percentage: " + roundToOneDecimal(secondPercentage));
				
		double accuracy;
		
		// Count percentage
		accuracy = (firstTotal + secondTotal) * 100 / (TOTAL_ROWS + TOTAL_ROWS);
		
		// Get one decimal point
		accuracy = roundToOneDecimal(accuracy);
		
		return accuracy;
	}
	
	/* Start program */
	public static void initProgram() {
		//Store data into arrays
		storeData();
		
		//Print accuracy percentage
		double accuracy = getAccuracy(firstFileData, secondFileData);
		System.out.println("\nTotal accuracy: " + accuracy);
	}
	
	/* Main method */
	public static void main(String[] args) {
		// Start program
		initProgram();
	}
}
