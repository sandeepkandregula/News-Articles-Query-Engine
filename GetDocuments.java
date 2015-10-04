package edu.buffalo.cse.irf14.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDocuments {
	String s;
	static int docCounter=0;
	static int termCounter=0;
	static int categoryCounter=0;
	static int authorCounter=0;
	static int placeCounter=0;
	static HashMap<String,Integer> termDictionary = new HashMap<String,Integer>();
	static HashMap<Integer,String> docDictionary = new HashMap<Integer,String>(); 
	static HashMap<Integer,String> inverseTermDictionary = new HashMap<Integer,String>();
	static HashMap<String,Integer> categoryDictionary = new HashMap<String,Integer>();
	static HashMap<String,Integer> authorDictionary = new HashMap<String,Integer>();
	static HashMap<String,Integer> placeDictionary = new HashMap<String,Integer>();
	static HashMap<Integer,LinkedList<Integer>> termIndex = new HashMap<Integer,LinkedList<Integer>>();
	static HashMap<Integer,LinkedList<Integer>> categoryIndex = new HashMap<Integer,LinkedList<Integer>>();
	static HashMap<Integer,LinkedList<Integer>> authorIndex = new HashMap<Integer,LinkedList<Integer>>();
	static HashMap<Integer,LinkedList<Integer>> placeIndex = new HashMap<Integer,LinkedList<Integer>>();
	static HashMap<Integer,LinkedList<Integer>> docTermFreq = new HashMap<Integer,LinkedList<Integer>>();
	static HashMap<LinkedList<Integer>,Integer> termTermFreq = new HashMap<LinkedList<Integer>,Integer>();
	public GetDocuments(String stringRep, String indexDir) {
		// TODO Auto-generated constructor stub
		
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
		s = stringRep;
		recursion(s);
	}
	public static void recursion(String s){
		List<Integer> positionClose = new ArrayList<Integer>();
		char [] sa = new char[s.length()];
		for(int i=0 ; i<s.length(); i++){
			sa[i] = s.charAt(i);
		}
		for(int i=0; i<sa.length; i++){
			if((sa[i]) == ']' ){
				positionClose.add(i);
			}
		}
		if(positionClose.isEmpty()){
			docFreq(s);
			s="";
		}
		else{
			if(sa[sa.length-1] == ']'){
				s = ForParentheis(s);

			}
			else{
				String sub = s.substring(positionClose.get(positionClose.size()-1)+1);
				s = s.substring(0, (positionClose.get(positionClose.size()-1))+1);
				sub = sub.trim();
				docFreq(sub);
			}
		}
		if(s!="" && s!=null){
			recursion(s);
		}
	}




	public static String ForParentheis(String s){
		//System.out.println(s);
		char [] sa = new char[s.length()];
		for(int i=0 ; i<s.length(); i++){
			sa[i] = s.charAt(i);
		}
		int count = 0;
		List<Integer> positionOpen = new ArrayList<Integer>();
		List<Integer> positionClose = new ArrayList<Integer>();
		for(int i=0; i<sa.length; i++){
			if((sa[i]) == '[' ){
				count++;
				positionOpen.add(i);
			}
			if((sa[i]) == ']' ){
				positionClose.add(i);
			}
		}
		//System.out.println(positionOpen);
		//System.out.println(positionClose);
		for(int i=(positionClose.size()-1); i>=0; i--){
			if(count>=1){
				if(i==0){
					String sub;
					if(count>1){
						sub = s.substring(positionOpen.get(positionOpen.size()-1), positionClose.get(i)+1);
						s = s.substring(0, (positionOpen.get(positionOpen.size()-1)))+ s.substring(positionClose.get(i)+1);	
					}
					else{
						sub = s.substring(positionOpen.get(positionOpen.size()-1), positionClose.get(0)+1);
						s = s.substring(0, (positionOpen.get(positionOpen.size()-1)));
					}
					sub = sub.replace("]", "");
					sub = sub.replace("[", "");
					sub = sub.trim();
					docFreq(sub);
					return s;
				}
				else{
					if(positionOpen.get(positionOpen.size()-1) > positionClose.get(i-1)){
						String sub = s.substring(positionOpen.get(positionOpen.size()-1), ((positionClose.get(i))+1));
						if(i==0){
						s = s.substring(0, (positionOpen.get(i)));
						}
						else{
							s = s.substring(0, (positionOpen.get(positionOpen.size()-1)))+ s.substring(positionClose.get(i)+1);	
						}
						sub = sub.replace("]", "");
						sub = sub.replace("[", "");
						sub = sub.trim();
						docFreq(sub);
						return s;

					}
				}
			}
		}
		return s;
		
	}

	public static void docFreq(String sub){
		//System.out.println(sub);
		StringTokenizer st = new StringTokenizer(sub);
		List<String> list = new LinkedList<String>();
		while(st.hasMoreTokens()){
			list.add(st.nextToken());
		}
		int count = list.size();
		for(int i=(list.size()-1); i>=0; i--){
			//System.out.println(list.get(i));
			String operatorFlag = null;
			Pattern pattern1 = Pattern.compile("<(.*)>");
			Matcher matcher1 = pattern1.matcher(list.get(i));
			if(matcher1.matches()){
				operatorFlag = "not";
				list.add(i, matcher1.group(1));
				list.remove(i+1);
			}
			if(list.get(i).equalsIgnoreCase("OR")){

			}
			if(list.get(i).equalsIgnoreCase("AND")){

			}
			Pattern pattern2 = Pattern.compile("(.*):(.*)");
			Matcher matcher2 = pattern2.matcher(list.get(i));
			if(matcher2.matches()){
				String[] parts = list.get(i).split(":");
				if(parts[0].equalsIgnoreCase("term")){
					GetTermDocFreq(parts[1]);
				}
				if(parts[0].equalsIgnoreCase("author")){
					GetAuthorDocFreq(parts[1]);
				}
				if(parts[0].equalsIgnoreCase("category")){
					GetCategoryDocFreq(parts[1]);
				}
				if(parts[0].equalsIgnoreCase("place")){
					GetPlaceDocFreq(parts[1]);
				}
			}
		}
	}
	private static void GetAuthorDocFreq(String string) {
		// TODO Auto-generated method stub
		
	}



	private static void GetCategoryDocFreq(String string) {
		// TODO Auto-generated method stub
		
	}



	private static void GetPlaceDocFreq(String string) {
		// TODO Auto-generated method stub
		if(string!="" && string!=null){
			string = string.toLowerCase();
			 String[] arr = string.split(" ");
			    StringBuffer sb = new StringBuffer();

			    for (int j = 0; j < arr.length; j++) {
			        sb.append(Character.toUpperCase(arr[j].charAt(0)))
			            .append(arr[j].substring(1)).append(" ");
			    }
			    string = sb.toString().trim();
			if(placeDictionary.containsKey(string)){
				
			}
		}
	}



	private static void GetTermDocFreq(String string) {
		// TODO Auto-generated method stub

	}
}
