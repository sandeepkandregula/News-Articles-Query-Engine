/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author nikhillo
 * Class that converts a given string into a {@link TokenStream} instance
 */
public class Tokenizer {
	/**
	 * Default constructor. Assumes tokens are whitespace delimited
	 */
	
	public Tokenizer() {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
	}
	
	/**
	 * Overloaded constructor. Creates the tokenizer with the given delimiter
	 * @param delim : The delimiter to be used
	 */
	public Tokenizer(String delim) {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
	}
	
	/**
	 * Method to convert the given string into a TokenStream instance.
	 * This must only break it into tokens and initialize the stream.
	 * No other processing must be performed. Also the number of tokens
	 * would be determined by the string and the delimiter.
	 * So if the string were "hello world" with a whitespace delimited
	 * tokenizer, you would get two tokens in the stream. But for the same
	 * text used with lets say "~" as a delimiter would return just one
	 * token in the stream.
	 * @param str : The string to be consumed
	 * @return : The converted TokenStream as defined above
	 * @throws TokenizerException : In case any exception occurs during
	 * tokenization
	 */
	public static TokenStream consume(String str) throws TokenizerException {
		//TODO : YOU MUST IMPLEMENT THIS METHOD
		
		
		
		ArrayList<String> tokenList = new ArrayList<String>();
		StringTokenizer str1 = new StringTokenizer (str, " ,.\";");
		while(str1.hasMoreTokens()){
			tokenList.add(str1.nextToken());
			
		}
		int count = 0;
		while(tokenList != null){
			System.out.println(tokenList.get(count));
			count++;
		}
		
		return null;
	}
}
