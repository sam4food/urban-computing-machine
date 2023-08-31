package edu.ics211.hw12;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.IOException;

public class ReadDoc {

	public static void main(String[] args) {
//		if (args.length != 1) {
//			System.out.println("Make sure you include the input file as the argument");
//			System.exit(1);
//		}
		File f = new File("C:/Users/samue/Downloads/constitution.txt");
		//File f = new File("C:/Users/samue/Downloads/the-full-bee-movie-script.txt");
		//File f = new File("C:/Users/samue/Downloads/declaration_indepenence.txt");
		Scanner infile = null;
		try {
			infile = new Scanner(f);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		int count = 0;
		ArrayList<String> wordList = new ArrayList<String>();
		while (infile.hasNext()) {
			String pword = infile.next();
			//System.out.println(pword);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < pword.length(); i++) {
				char ch = pword.charAt(i);
				if (Character.isJavaIdentifierStart(ch)) {
					sb.append(ch);
				}
			}
			String fixStr = sb.toString();
			if (fixStr.length() > 0) {
				wordList.add(fixStr);
				if (fixStr.equals("the")) {
					count++;
				}
			}
		}
		analyze(wordList);
	}
	public static void analyze(ArrayList<String> wordList) {
		long start = System.currentTimeMillis();
		BinarySearchTree<String, Long> bst = new BinarySearchTree<String, Long>(wordList);
		long end = System.currentTimeMillis();
		System.out.println("adding nodes took: " + (end-start) + "ms");
		System.out.println("the tree has " + bst.countNodes() + " unique strings");
		System.out.println("the has depth: " + bst.height());
		WordFrequency[] wordFrequencyArray = bst.toArray();
		java.util.Arrays.sort(wordFrequencyArray, Collections.reverseOrder());
		System.out.println("the 10 highest frequency words:");
		for(int i = 0; i<10 && i < wordFrequencyArray.length; i++) {
			System.out.println(wordFrequencyArray[i]);
		}
	}
}