/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author nikhillo
 * Class that emulates reading data back from a written index
 */
public class IndexReader {
	IndexType type;
	IndexWriter writer;
	/**
	 * Default constructor
	 * @param indexDir : The root directory from which the index is to be read.
	 * This will be exactly the same directory as passed on IndexWriter. In case 
	 * you make subdirectories etc., you will have to handle it accordingly.
	 * @param type The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		//TODO
		this.type = type;
		int docCounter=0;
		int termCounter=0;
		int categoryCounter=0;
		int authorCounter=0;
		int placeCounter=0;
		HashMap<String,Integer> termDictionary = new HashMap<String,Integer>();
		HashMap<Integer,String> docDictionary = new HashMap<Integer,String>(); 
		HashMap<Integer,String> inverseTermDictionary = new HashMap<Integer,String>();
		HashMap<String,Integer> categoryDictionary = new HashMap<String,Integer>();
		HashMap<String,Integer> authorDictionary = new HashMap<String,Integer>();
		HashMap<String,Integer> placeDictionary = new HashMap<String,Integer>();
		HashMap<Integer,LinkedList<Integer>> termIndex = new HashMap<Integer,LinkedList<Integer>>();
		HashMap<Integer,LinkedList<Integer>> categoryIndex = new HashMap<Integer,LinkedList<Integer>>();
		HashMap<Integer,LinkedList<Integer>> authorIndex = new HashMap<Integer,LinkedList<Integer>>();
		HashMap<Integer,LinkedList<Integer>> placeIndex = new HashMap<Integer,LinkedList<Integer>>();
		HashMap<Integer,LinkedList<Integer>> docTermFreq = new HashMap<Integer,LinkedList<Integer>>();
		HashMap<LinkedList<Integer>,Integer> termTermFreq = new HashMap<LinkedList<Integer>,Integer>();
		try {
			File ipDirectory = new File(indexDir);
			String[] files = ipDirectory.list();
			for (int i =0; i<12; i++){
				if(files[i].equals("docDictionary.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					docDictionary = (HashMap<Integer, String>) ois.readObject();
					
				}
				if(files[i].equals("termDictionary.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					termDictionary =(HashMap<String, Integer>) ois.readObject();
					
				}
				if(files[i].equals("inverseTermDictionary.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					inverseTermDictionary = (HashMap<Integer, String>) ois.readObject();
				}
				if(files[i].equals("categoryDictionary.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					categoryDictionary = (HashMap<String, Integer>) ois.readObject();
				}
				if(files[i].equals("authorDictionary.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					authorDictionary = (HashMap<String, Integer>) ois.readObject();
				}
				if(files[i].equals("placeDictionary.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					placeDictionary = (HashMap<String, Integer>) ois.readObject();
				}
				if(files[i].equals("termIndex.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					termIndex = (HashMap<Integer, LinkedList<Integer>>) ois.readObject();
				}
				if(files[i].equals("categoryIndex.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					categoryIndex = (HashMap<Integer, LinkedList<Integer>>) ois.readObject();
				}
				if(files[i].equals("authorIndex.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					authorIndex = (HashMap<Integer, LinkedList<Integer>>) ois.readObject();
				}
				if(files[i].equals("placeIndex.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					placeIndex = (HashMap<Integer, LinkedList<Integer>>) ois.readObject();
				}
				if(files[i].equals("docTermFreq.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					docTermFreq = (HashMap<Integer, LinkedList<Integer>>) ois.readObject();
				}
				if(files[i].equals("termTermFreq.txt")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +files[i]);
					ObjectInputStream ois = new ObjectInputStream(fis);
					termTermFreq = (HashMap<LinkedList<Integer>, Integer>) ois.readObject();
				}
				docCounter = docDictionary.size();
				termCounter = termDictionary.size();
				categoryCounter = categoryDictionary.size();
				authorCounter = authorDictionary.size();
				placeCounter = placeDictionary.size();
				
				
			}
			

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get total number of terms from the "key" dictionary associated with this 
	 * index. A postings list is always created against the "key" dictionary
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {
		//TODO : YOU MUST IMPLEMENT THIS
		if(type==IndexType.TERM){
			return writer.termCounter;
		}
		if(type==IndexType.CATEGORY){
			return writer.categoryCounter;
		}
		if(type==IndexType.AUTHOR){
			return writer.authorCounter;
		}
		if(type==IndexType.PLACE){
			return writer.placeCounter;
		}
		return -1;
	}
	
	/**
	 * Get total number of terms from the "value" dictionary associated with this 
	 * index. A postings list is always created with the "value" dictionary
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		//TODO: YOU MUST IMPLEMENT THIS
		return writer.docCounter;
	}
	
	/**
	 * Method to get the postings for a given term. You can assume that
	 * the raw string that is used to query would be passed through the same
	 * Analyzer as the original field would have been.
	 * @param term : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the 
	 * number of occurrences as values if the given term was found, null otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		//TODO:YOU MUST IMPLEMENT THIS
		if(term != null && term !=""){
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for(int i=1 ; i<=writer.docCounter ; i++){
				String temp = writer.docDictionary.get(i);
				if(writer.termIndex.containsKey(writer.termDictionary.get(term))){
					LinkedList<Integer> list = new LinkedList<Integer>();
					list = writer.termIndex.get(writer.termDictionary.get(term));
					if(list.contains(i)){
						LinkedList<Integer> list1 = new LinkedList<Integer>();
						int termID = writer.termDictionary.get(term);
						list1.add(termID);
						list1.add(i);
						int freq = writer.termTermFreq.get(list1);
						map.put(temp, freq);
					}
					
				}
				else{
					return null;
				}
			}
			
			return map;
		}
		return null;
	}
	
	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * @param k : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values
	 * null for invalid k values
	 */
	public List<String> getTopK(int k) {
		//TODO YOU MUST IMPLEMENT THIS
		if(k>0){
		List<String> list = new LinkedList<String>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		LinkedList<Integer> docfreq = new LinkedList<Integer>();
		int[][] multi = new int[writer.termCounter][2];
		for(int i=1 ; i<=writer.termCounter; i++){
			
			docfreq = writer.docTermFreq.get(i);
			map.put(writer.inverseTermDictionary.get(i),docfreq.get(1));
			multi[i-1][0] = i;
			multi[i-1][1] = docfreq.get(1);
					
		}
			SortedSet<Integer> values = new TreeSet<Integer>(map.values());
			int a = values.size();
			Object[] b = values.toArray();
			while(list.size()<k){
				for(int i=0; i< multi.length; i++){
					
					if(multi[i][1] == (Integer)b[a-1]){
						list.add(writer.inverseTermDictionary.get(i+1));
					}
				}
				a--;
			}
			return list;
		}
		else{
			return null;
		}
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * @param terms The ordered set of terms to AND, similar to getPostings()
	 * the terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key 
	 * and number of occurrences as the value, the number of occurrences 
	 * would be the sum of occurrences for each participating term. return null
	 * if the given term list returns no results
	 * BONUS ONLY
	 */
	public Map<String, Integer> query(String...terms) {
		//TODO : BONUS ONLY
		return null;
	}
}
