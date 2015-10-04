/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author nikhillo
 * Class that represents a stream of Tokens. All {@link Analyzer} and
 * {@link TokenFilter} instances operate on this to implement their
 * behavior
 */
public class TokenStream implements Iterator<Token>{
	//public List<String> tokenList = new LinkedList<String>();
	public List<String> currentTokenList = new LinkedList<String>();
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	Token tok = new Token();
	int position =0;
	int streamSize;
	StringTokenizer stringTokens;
	//Token tok;
	
	public TokenStream(StringTokenizer stringTokens)
	{ 
		if(stringTokens !=null){
			this.stringTokens =stringTokens;
			streamSize = stringTokens.countTokens();
			position = 0;
			for(int i = 0; i< streamSize; i++){
				if(stringTokens.hasMoreElements()){
					currentTokenList.add(stringTokens.nextToken());
				}
			}
		}
	}

	public TokenStream() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method that checks if there is any Token left in the stream
	 * with regards to the current pointer.
	 * DOES NOT ADVANCE THE POINTER
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {
		// TODO YOU MUST IMPLEMENT THIS
		if(currentTokenList != null){
			if(position >= 0 && position < streamSize && currentTokenList.get(position)!=null && currentTokenList.get(position)!= ""){
				
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to return the next Token in the stream. If a previous
	 * hasNext() call returned true, this method must return a non-null
	 * Token.
	 * If for any reason, it is called at the end of the stream, when all
	 * tokens have already been iterated, return null
	 */
	@Override
	public Token next() {
		// TODO YOU MUST IMPLEMENT THIS
		if (hasNext() == true){
			position++;
			String s = currentTokenList.get(position-1);
			
			tok.setTermText(s);
			
			return tok;
		}
		position++;
		return null;		
	}
	
	public void replace(String s){
		currentTokenList.set(position-1, s);
	}
	
	/**
	 * Method to remove the current Token from the stream.
	 * Note that "current" token refers to the Token just returned
	 * by the next method. 
	 * Must thus be NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		// TODO YOU MUST IMPLEMENT THIS
		if (currentTokenList != null){
			if(position >0 && position < (streamSize+1)){
				currentTokenList.remove(position-1);
				position--;
				streamSize--;
			}
		}
		
	}
	
	/**
	 * Method to reset the stream to bring the iterator back to the beginning
	 * of the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		//TODO : YOU MUST IMPLEMENT THIS
		if(currentTokenList != null){
			position = 0;
		}
	}
	
	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the iterator
	 * currently stands. After appending, the iterator position must be unchanged
	 * Of course this means if the iterator was at the end of the stream and a 
	 * new stream was appended, the iterator hasn't moved but that is no longer
	 * the end of the stream.
	 * @param stream : The stream to be appended
	 */
	public void append(TokenStream stream) {
		//TODO : YOU MUST IMPLEMENT THIS
		if(stream != null && !stream.currentTokenList.isEmpty()){
			int size = stream.currentTokenList.size();
			for(int i=0 ; i<size; i++){
			currentTokenList.add(stream.currentTokenList.get(i));
			}
			streamSize = currentTokenList.size();
		}
		
		
	}
	
	/**
	 * Method to get the current Token from the stream without iteration.
	 * The only difference between this method and {@link TokenStream#next()} is that
	 * the latter moves the stream forward, this one does not.
	 * Calling this method multiple times would not alter the return value of {@link TokenStream#hasNext()}
	 * @return The current {@link Token} if one exists, null if end of stream
	 * has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		//TODO: YOU MUST IMPLEMENT THIS
		if (position>0 && position < 5){
			String s = currentTokenList.get(position-1);
			
			//Token tok = new Token();
			if(s == tok.getTermText()){
				return tok;
			}
//			else{
//				tok.setTermText(s);
//				tok.setTermBuffer(s.toCharArray());
//				return tok;
//			}
			
		}
		return null;
	}
}
