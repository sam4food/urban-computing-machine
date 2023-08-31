package edu.ics211.hw12;

public class WordFrequency implements Comparable<WordFrequency> {

	String word;
	long frequency;

	public WordFrequency(String w, long f) {
		word = w;
		frequency = f;
	}

	public int compareTo(WordFrequency other) {
		if (frequency - other.frequency < 0) {
			return -1;
		}
		else if (frequency - other.frequency > 0) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public String toString() {
		return word + ": " + frequency;
	}

}
