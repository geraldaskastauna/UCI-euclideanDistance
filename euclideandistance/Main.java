package euclideandistance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
	/* Input files path constants*/
	private static final String FIRST_FILE = "src/resources/cw2DataSet1.csv";
	private static final String SECOND_FILE = "src/resources/cw2DataSet2.csv";
	
	/* Constants */
	private static final int TOTAL_ELEMENTS = 65;
	private static final int TOTAL_ROWS = 2810;
	
	/* Arrays for data from files */
	private static int[][] firstFileData = new int[TOTAL_ROWS][TOTAL_ELEMENTS];
	private static int[][] secondFileData = new int[TOTAL_ROWS][TOTAL_ELEMENTS];
	
	/* Method that reads in file data from CSV (comma separated values) files.
	 * Requires 2 parameters:
	 * 1st parameter is String type named fileName: 
	 * Example: "example.csv" or use constants "FIRST_FILE".
	 * 2nd parameter is int type 2D array named fileDataArray:
	 * An array that holds the data from the file.
	 * Method returns type int 2D array.
	 */
	private static int[][] fileRead(String fileName, int[][] fileDataArray) {
		Scanner scannerObject;
		String inputLine;
		int row = 0;
		
		try {
			scannerObject = new Scanner(new BufferedReader(new FileReader(fileName)));
			
			while(scannerObject.hasNextLine()) {
				inputLine = scannerObject.nextLine();
				String[] delimiter = inputLine.split(",");
				
				// Split elements separated by comma from the file
				String[] inputArray = delimiter;
				
				for(int arrayElement = 0; arrayElement < inputArray.length; arrayElement++) {
					fileDataArray[row][arrayElement] = Integer.parseInt(inputArray[arrayElement]);
				}
				row++;
			}
		} catch (FileNotFoundException errorName) {
			System.out.println("File was not found, ERROR: " + errorName);
		} catch (IndexOutOfBoundsException errorName) {
			System.out.println("Error reading file, ERROR: " + errorName);
		}
		return fileDataArray;
	}

	/* Void type method that stores data from files into into arrays */
	public static void storeData() {
		fileRead(FIRST_FILE, firstFileData);
		fileRead(SECOND_FILE, secondFileData);
	}
	
	/* Method that calculates the euclidean distance in any given dimensions.
	 * Requires 2 parameters:
	 * 1st parameter is int type 2D array that holds data from first file 
	 * 2nd paramter is int type 2D array that holds data from second file
	 * Method returns int type value of total number of correct predictions from first parameter to second
	 */
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
				// Reset distance value
				distance = 0;
				// Loop through array elements
				for(int arrayElements = 0; arrayElements < TOTAL_ELEMENTS - 1; arrayElements++) {
					// Euclidean distance formula
					distance += Math.pow((secondArray[secondFileArrays][arrayElements] - firstArray[firstFileArrays][arrayElements]), 2);
				}
				// Find closest array
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
	
    /* Method that rounds double values to one decimal point.
     * Requires 1 parameter:
     * 1st parameter is any double type value.
     * Working example is commented in the method.
     * Method returns double type value
     */
	public static double roundToOneDecimal(double value) {
									// value = 123.123
		value = value * 10; 		// value = 1231.23
		value = Math.round(value); 	// value = 1231
		value = value / 10; 		// value = 123.1
		
		return value;
	}
	
	/* Method that calculates the accuracy of euclidean distance and prints it out
     * Requires 2 parameters:
	 * 1st parameter is int type 2D array that holds data from first file 
	 * 2nd paramter is int type 2D array that holds data from second file
     * Method returns the classification accuracy percentage
     */
	public static double getAccuracy(int[][] firstArray, int[][] secondArray) {
		double accuracy;
		
		// Store accurate results from first to second dataset
		double firstTotal = distance(firstArray, secondArray);
		System.out.println("From FIRST dataset to SECOND: " + (int) firstTotal + "/" + TOTAL_ROWS);
		
		// Get FIRST to SECOND dataset accuracy percentage
		double firstPercentage = firstTotal / TOTAL_ROWS * 100;
		System.out.println("Percentage: " + roundToOneDecimal(firstPercentage) + "%");
		
		// Store accurate results from second to first dataset
		double secondTotal = distance(secondArray, firstArray);
		System.out.println("\nFrom SECOND dataset to FIRST: " + (int) secondTotal + "/" + TOTAL_ROWS);
		
		// Get SECOND to FIRST dataset accuracy percentage
		double secondPercentage = secondTotal / TOTAL_ROWS * 100;
		System.out.println("Percentage: " + roundToOneDecimal(secondPercentage) + "%");
		
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
		System.out.println("\nTotal accuracy: " + accuracy + "%");
	}
	
	/* Main method */
	public static void main(String[] args) {
		// Start program
		initProgram();
	}
}
