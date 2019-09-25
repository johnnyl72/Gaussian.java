package cs3010;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) {
		String apples;
		String[] delim;
	
		String[] argss = {"--sp","leroooy.jinkkins"};
		apples = argss[1];
		delim = apples.split("\\.");
		 for (String a: delim)
	            System.out.println(a);
		 
		 

	}
		
	
	
}
