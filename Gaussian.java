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
			
			//System.out.println(Arrays.toString(NaiveGaussian(coeffMatrix,vectorB)));
			SPPGaussian(coeffMatrix,vectorB);
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
		
	}//end FwdElimination
	public static void BackSubt(double[][] coeffMatrix, double[] consta,double[] solution) {

		solution[arraySize-1] = consta[arraySize-1] / coeffMatrix[arraySize-1][arraySize-1]; 
		for(int i = arraySize-2; i > -1; i--) {
			double sum = consta[i];
			for(int j = i+1; j < arraySize; j++) {
				sum = sum - (coeffMatrix[i][j] * solution[j]);
			}//end for
			solution[i] = (sum / coeffMatrix[i][i]);
		}
	}
	public static double[] NaiveGaussian(double[][] coeffMatrix, double[] vectorB) {

		double[] sol = new double[arraySize];
		FwdElimination(coeffMatrix,vectorB);
		BackSubt(coeffMatrix,vectorB,sol);
		return sol;
		
	}
	
	public static void SPPFwdElimination(double[][] coeffMatrix, double[] vectorB, int[] ind) {
		double[] scaling = new double[arraySize];
		double smax;
		// Initialize index and scaling vectors
		for(int i = 0; i < arraySize; i++) {
			smax = 0;
			for(int j = 0; j < arraySize; j++) {
				smax = Math.max(Math.abs(smax), Math.abs(coeffMatrix[i][j]));
			}//end force
			scaling[i] = smax;
		}//end for
		
		
		
		int maxInd;	
		double r;
		for(int k = 0; k < (arraySize-1); k++) {
			double rmax = 0;
			maxInd = k; // Index vector
			
			for(int i = k; i < arraySize; i++) {
				//ratio of coefficient to scaling factor
				r = Math.abs(coeffMatrix[ind[i]][k] / scaling[ind[i]]);
				if( r > rmax) {
					rmax = r;
					maxInd = i;
				}//end if		
			}//end for

			//swap(ind[maxInd], ind[k])
			int temp = ind[maxInd];
			ind[maxInd] = ind[k];
			ind[k] = temp;
			
			for(int i = k+1; i < arraySize; i++) {
				double mult = (coeffMatrix[ind[i]][k]) / (coeffMatrix[ind[k]][k]);
				for(int j = k+1; j < arraySize; j++) {
					coeffMatrix[ind[i]][j] = coeffMatrix[ind[i]][j] - (mult * coeffMatrix[ind[k]][j]);
				}//end for
				
				vectorB[ind[i]] = vectorB[ind[i]] - (mult * vectorB[ind[k]]);
			}//end for
		}//end for
		
		//System.out.print(Arrays.deepToString(coeffMatrix));
			
	}//end function
	public static void SPPBackSubst(double[][] coeffMatrix, double[] vectorB, double[] sol, int[] ind ) {
		sol[arraySize-1] = vectorB[ind[arraySize-1]] / coeffMatrix[ind[arraySize-1]][arraySize-1];
		double sum;
		for( int i = (arraySize-2); i > -1; i--) {
			sum = vectorB[ind[i]];
			for(int j = i+1; j < arraySize; j++) {
				sum = sum - (coeffMatrix[ind[i]][j] * sol[j]);
			}//end for
			sol[i] = sum / coeffMatrix[ind[i]][i];
		}//end for
		System.out.println(Arrays.toString(sol));
	}//end function
	public static void SPPGaussian(double[][] coeffMatrix, double[] vectorB) {
		double[] sol = new double[arraySize];
		int[] ind = new int[arraySize]; //Index array
		
		for(int i = 0; i < arraySize; i++) {
			ind[i] = i;
		}//end for
		
		SPPFwdElimination(coeffMatrix, vectorB, ind);
		SPPBackSubst(coeffMatrix, vectorB, sol, ind);
	}
}
