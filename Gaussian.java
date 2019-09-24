package cs3010;
import java.util.*;
import java.io.*;


public class Gaussian {

	public static int arraySize;
	public static void main(String[] args) throws FileNotFoundException {
/*
		//Boolean flag to determine which method to use
		if (!args[0].equalsIgnoreCase("--spp")) {
			System.out.prdoubleln("Naive Gaussian Elimination");
		}
		else
			System.out.prdouble("Gaussian Elimination with Scaled Partial Pivoting");
*/
		//Static files for now, implement dynamic files next iteration
		String fileName = "sys1.lin";
		String solutionName = "sys1.sol";
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			
			//Read first line to determine matrix size
			arraySize = Integer.parseInt(bufferedReader.readLine());
			double[][] coeffMatrix = new double[arraySize][arraySize];
			double[] vectorB = new double[arraySize];
			
			String line = null;
			String[] row = null;
			
			
			//Read in linear systems for coefficent matrix
			for(int i = 0; i < arraySize; i++) {
				line = bufferedReader.readLine();
				row = line.split(" ");
				for(int j = 0; j < arraySize; j++) {
					coeffMatrix[i][j] = Double.parseDouble(row[j]);
				}
			}

			//Read last line for vector matrix
			line = bufferedReader.readLine();
			row = line.split(" ");
			for(int i = 0; i < arraySize; i++) {
				vectorB[i] = Double.parseDouble(row[i]);
			}
			
			FwdElimination(coeffMatrix,vectorB);
			
			bufferedReader.close();

		}//end try
		catch(FileNotFoundException e) {
		} 
		catch(IOException e) {
		}//end catch	
		//Writer 
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(solutionName));	
			/** TBD */
            bufferedWriter.close();	
		}
		catch(IOException e) {	
		}
		
	
	}//end main
	
	public static void FwdElimination(double[][] coeffMatrix, double[] vectorB) {

		double mult;
		for(int k = 0; k < (arraySize-1); k++) {
			for(int i = k+1; i < arraySize; i++) {
				mult = (coeffMatrix[i][k]) / (coeffMatrix[k][k]);
				for(int j = k+1; j < arraySize; j++) {
					coeffMatrix[i][j] = (coeffMatrix[i][j]  - (mult * coeffMatrix[k][j]));
				}//end for
				vectorB[i] = (vectorB[i] - (mult * vectorB[k]));
			}//end for
		}//end for
		System.out.println(Arrays.deepToString(coeffMatrix));
		System.out.println(Arrays.toString(vectorB));
		BackSubt(coeffMatrix,vectorB);
	}//end FwdElimination
	
	public static double[] BackSubt(double[][] coeffMatrix, double[] vectorB) {

		double[] solution = new double[arraySize];
		solution[arraySize-1] = vectorB[arraySize-1] / (coeffMatrix[arraySize-1][arraySize-1]);
		System.out.println(Arrays.toString(solution));
		
		for(int i = arraySize-2; i > -1; i--) {
			double sum = vectorB[i];
			for(int j = i+1; j < arraySize; j++) {
				sum = sum - (coeffMatrix[i][j] * solution[j]);
			}//end for
			solution[i] = (sum / coeffMatrix[i][i]);
		}
		System.out.println(Arrays.toString(solution));
		
		return solution;
	}

	public static String SPPFwdElimination( ) {
		return null;	
	}
	public static String SPPBackSubst( ) {
		return null;
	}
	
}
