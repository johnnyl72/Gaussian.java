package cs3010;

import java.util.Arrays;

public class Test {
	public static int arraySize;
	public static void main(String[] args) {
		arraySize = 4;
		for(int i = 0; i < (arraySize-1); i++) {
			arraySize = 10;
		}
		
		System.out.print(arraySize);
	}
	
	

}
