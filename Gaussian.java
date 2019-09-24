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
			
			//System.out.print(Arrays.toString(NaiveGaussian(coeffMatrix,vectorB)));
			//SPPFwdElimination(coeffMatrix,vectorB);
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
	public static void SPPFwdElimination(double[][] coeffMatrix, double[] vectorB, double[] ind) {
		double[] scaling = new double[arraySize];
		double smax = 0;
		// Initialize index and scaling vectors
		for(int i = 0; i < arraySize; i++) {
			for(int j = 0; j < arraySize; j++) {
				smax = Math.abs(Math.max(smax, coeffMatrix[i][j]));
			}//end force
			scaling[i] = smax;
		}//end for
		
		for(int k = 0; k < arraySize -1; k++) {
			double rmax = 0;
			double maxInd = k;
			double r;
			for(int i = k; i < arraySize; i++) {
				//ratio of coefficient to scaling factor
				r = Math.abs(coeffMatrix[(int) ind[i]][k] / scaling[(int) ind[i]]);
				if( r> rmax) {
					rmax = r;
					maxInd = i;
				}//end if		
			}//end for
			//swap(ind[maxInd, ind[k]
			double temp = ind[(int) maxInd];
			ind[(int)maxInd] = ind[k];
			ind[k] = temp;
			
			for(int i = k+1; i < arraySize; i++) {
				double mult = coeffMatrix[(int)ind[i]][k] / coeffMatrix[(int)ind[k]][k];
				for(int j = k+1; j < arraySize; j++) {
					coeffMatrix[(int)ind[i]][j] = coeffMatrix[(int)ind[i]][j] - (mult * coeffMatrix[(int)ind[k]][j]);
				}//end for
				
				vectorB[(int)ind[i]] = vectorB[(int)ind[i]] - mult * vectorB[(int)ind[k]];
			}//end for
		}//end for
			System.out.println(Arrays.deepToString(coeffMatrix));
			System.out.println(Arrays.toString(vectorB));
	}//end function
	public static void SPPBackSubst(double[][] coeffMatrix, double[] vectorB, double[] sol, double[] ind ) {
		sol[arraySize] = vectorB[(int)ind[arraySize]] / coeffMatrix[(int)ind[arraySize]][arraySize];
		double sum;
		for( int i = (arraySize-2); i < arraySize; i++) {
			sum = vectorB[(int)ind[i]];
			for(int j = i+1; j < arraySize; j++) {
				sum = sum - (coeffMatrix[(int)ind[i]][j] * sol[j]);
			}//end for
			sol[i] = sum /coeffMatrix[(int)ind[i]][i];
		}//end for
	}//end function
	public static void SPPGaussian(double[][] coeffMatrix, double[] vectorB) {
		double[] sol = new double[arraySize];
		double[] ind = new double[arraySize];
		
		for(int i = 0; i < arraySize; i++) {
			ind[i] = i;
		}//end for
		
		SPPFwdElimination(coeffMatrix, vectorB, ind);
		SPPBackSubst(coeffMatrix, vectorB, sol, ind);
	}
}
