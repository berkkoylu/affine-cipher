
/*
 * 
 * 
 * 
 * The process of recovering plaintext from ciphertext without knowledge both of the
 * encryption method and the key is known as cryptanalysis or breaking codes.
 * 
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class Test implements  FileWriting {

	public static void main(String[] args)  throws IOException {

		ArrayList<String> plaintext = new ArrayList<String>();
		ArrayList<String> ciphertext = new ArrayList<String>();
		ArrayList<String> dictionary = new ArrayList<String>();

		
		TernarySearchTree treedic = new TernarySearchTree();

		dictionary = readFile(dictionary, "HW4_dictionary.txt");
		plaintext = readFile(plaintext, "input10.txt");

		int numberA = findBijection((int) (Math.random() * 25), 26);
		int numberB = (int) (Math.random() * 25);

		for (String d : dictionary) {

			treedic.insert(d.toLowerCase().toString());

		}
		
		
	
		for (String o : plaintext) {

			ciphertext.add(encrypt(o, numberA, numberB));

		}

		FileWriting.writingEncryptedFile(ciphertext);
		
	    ArrayList<String> ciphertext2 = new ArrayList<String>();
		
		ciphertext2 = readFile(ciphertext2, "encrypted7.txt");
		
		long starttime = System.nanoTime();

		bruteForce(treedic, ciphertext);	
		//bruteForceWithTextSearch(ciphertext,dictionary);

		long endtime = System.nanoTime();

		long durationInNano = (endtime - starttime);

		long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);

		//System.out.println("    "+durationInNano);
		//System.out.println(durationInMillis);

	}
	
	//Brute force searching from ternary search tree
	public static void bruteForce(TernarySearchTree tree, ArrayList<String> list) throws IOException {

		int[] indexA = { 1, 3, 21, 15, 9, 19, 7, 23, 11, 5, 17, 25 };

		int[] indexB = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };

		double commonWords = 0;
		double wordNumber = 0;
		double per = 0;
		int i,	j;

		for ( i = 0; i < indexA.length; i++) {

			for ( j = 0; j < indexB.length; j++) {

				for (String u : list) {

					String decryptString = decrypt(u.toString(), indexA[i], indexB[j]);

					wordNumber++;

					if (tree.search(decryptString.toLowerCase()) == true) {
                      
						commonWords = commonWords + 1;

					}

				}

				per = ((commonWords * 100) / wordNumber);
				if (per > 70) {
					System.out.println("Total words  "+wordNumber + "  Common words " + commonWords);
					
					System.out.println("Percentage % "+ (int) (per) + " a = " +indexA[i]+ "  b = " + indexB[j]);
				}

				per = 0;
				commonWords = 0;
				wordNumber = 0;
			}

		}

	}

	
//Brute force with searching dictionary file
	public static void bruteForceWithTextSearch(ArrayList<String> list,ArrayList<String> listdictionary) throws IOException {

		int[] indexA = { 1, 3, 21, 15, 9, 19, 7, 23, 11, 5, 17, 25 };

		int[] indexB = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };

		double commonWords = 0;
		double wordNumber = 0;
		double per = 0;

		for (int i = 0; i < indexA.length; i++) {

			for (int j = 0; j < indexB.length; j++) {

				for (String u : list) {

					String decryptString = decrypt(u.toString(), indexA[i], indexB[j]);

					wordNumber++;

			      	System.out.println("..");
			      	if (listdictionary.contains(decryptString.toLowerCase())) {

						commonWords++;
						
					}
			      	
			      	
			      	/*
					for(int a = 0; a < listdictionary.size();a++) {
						
					  
						if (decryptString.toLowerCase().compareTo(listdictionary.get(a).toLowerCase().toString()) == 0) {

							commonWords++;
							
						}
						
					}
					
					*/
					

				}

				if (commonWords >100) {
					System.out.println(wordNumber + "  " + commonWords);
					per = ((commonWords * 100) / wordNumber);
					System.out.println(per);
				}

				per = 0;
				commonWords = 0;
				wordNumber = 0;
			}

		}

	}

	//Open and read file and check if there is any similarity.
	public static boolean readFile2(String name, String filename) throws IOException {

		File file = new File(filename);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		String word;

		while ((st = br.readLine()) != null) {

			Scanner read = new Scanner(st);
			try {

				while ((word = read.next()) != null) {

					if (word.toLowerCase().compareTo(name) == 0) {

						return true;
					}

				}

				read.close();
			} catch (Exception e) {

			}

		}

		br.close();

		return false;

	}

	
	//read dictionary from txt file and return array list.
	public static ArrayList<String> readFile(ArrayList<String> list, String filename) throws IOException {

		File file = new File(filename);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		String word;

		while ((st = br.readLine()) != null) {

			Scanner read = new Scanner(st);
			try {

				while ((word = read.next()) != null) {
					list.add(word);
				}

				read.close();
			} catch (Exception e) {

			}

		}

		br.close();

		return list;

	}

	//Decrypt the given word.
	public static String decrypt(String word, int keyA, int keyB) {
		StringBuilder builder = new StringBuilder();

		BigInteger inverseOfKeyA = BigInteger.valueOf(keyA).modInverse(BigInteger.valueOf(26));

		for (int in = 0; in < word.length(); in++) {
			char character = word.charAt(in);
			if (Character.isLetter(character) && (Character.isLowerCase(character) == true)) {
				int decoded = inverseOfKeyA.intValue() * (character - 'a' - keyB + 26);
				character = (char) (decoded % 26 + 'a');
			} else if (Character.isLetter(character) && (Character.isUpperCase(character) == true)) {
				int decoded = inverseOfKeyA.intValue() * (character - 'A' - keyB + 26);
				character = (char) (decoded % 26 + 'A');
			}
			builder.append(character);
		}
		return builder.toString();
	}

	//finding bijection
	private static int findBijection(int number1, int number2) {

		if (findGCD(number1, number2) == 1) {
			return number1;
		} else {

			return findBijection((int) (Math.random() * 25), number2);

		}
	}

	//find greatest commmon divisor
	private static int findGCD(int number1, int number2) {

		if (number2 == 0) {
			return number1;
		} else {

			return findGCD(number2, number1 % number2);

		}
	}

	//encrypt the given word.
	public static String encrypt(String word, int a, int b) {

		String str = "";
		for (int i = 0; i < word.length(); i++) {
			// && str.charAt(i) != '�'
			if ((Character.isLetter(word.charAt(i)) == true) && (Character.isUpperCase(word.charAt(i)) == true)) {

				str = str + (char) ((((a * (word.charAt(i) - 'A')) + b) % 26) + 'A');
			} else if ((Character.isLetter(word.charAt(i)) == true) && (Character.isLowerCase(word.charAt(i)) == true)) {

				str = str + (char) ((((a * (word.charAt(i) - 'a')) + b) % 26) + 'a');
			} else if (word.charAt(i) == '�') {

				str += '`';
			} else if ((word.charAt(i) != '.') && (word.charAt(i) != '!') && (word.charAt(i) != '?')
					&& (word.charAt(i) != ',') && (word.charAt(i) != '"')) {
				
				str += word.charAt(i);

			}

		}
		return str;
	}

}
