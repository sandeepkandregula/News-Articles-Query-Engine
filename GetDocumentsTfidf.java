package edu.buffalo.cse.irf14.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDocumentsTfidf {

	public GetDocumentsTfidf(String stringRep) {
		// TODO Auto-generated constructor stub
		String indexDir = null;
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
		File ipDirectory = new File(indexDir);
		String[] files = ipDirectory.list();
		HashMap<String,Integer> map = new HashMap<String,Integer>(); 
		for(int j=0; j<files.length; j++){
			map.put(files[j], j);
		}
		stringRep = "Category:War AND Author:Dutt AND Place:Baghdad AND [ Term:prisoners OR Term:detainees OR Term:rebels ]"; 
		StringTokenizer st = new StringTokenizer(stringRep);
		ArrayList<String> queryList = new ArrayList<String>();
		while(st.hasMoreTokens()){
			queryList.add(st.nextToken());
		}
		try {
			for(int i=0; i<queryList.size(); i++){
			Pattern pattern = Pattern.compile("(.*):(.*)");
			Matcher matcher = pattern.matcher(queryList.get(i));
			if(matcher.matches()){
				if(matcher.group(1).equalsIgnoreCase("Category")){
					FileInputStream fis = new FileInputStream(ipDirectory.getAbsolutePath() + File.separator +"categoryDictionary.txt");
					ObjectInputStream ois = new ObjectInputStream(fis);
					docDictionary = (HashMap<Integer, String>) ois.readObject();
					
				}
			}
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
					
	    			
	    			
	    			
	    		
	    	
		
		System.out.println(queryList);
	}

}
