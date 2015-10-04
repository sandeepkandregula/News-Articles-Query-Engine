/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * Class responsible for writing indexes to disk
 */
public class IndexWriter {
	String indexDir;
	static int docCounter=0;
	static int termCounter=0;
	static int categoryCounter=0;
	static int authorCounter=0;
	static int placeCounter=0;
	
	static List<HashMap> mapList ;
	public static HashMap<Integer,String> docDictionary = new HashMap<Integer,String>(); 
	public static HashMap<String,Integer> termDictionary = new HashMap<String,Integer>();
	public static HashMap<Integer,String> inverseTermDictionary = new HashMap<Integer,String>();
	public static HashMap<String,Integer> categoryDictionary = new HashMap<String,Integer>();
	public static HashMap<String,Integer> authorDictionary = new HashMap<String,Integer>();
	public static HashMap<String,Integer> placeDictionary = new HashMap<String,Integer>();
	public static HashMap<Integer,LinkedList<Integer>> termIndex = new HashMap<Integer,LinkedList<Integer>>();
	public static HashMap<Integer,LinkedList<Integer>> categoryIndex = new HashMap<Integer,LinkedList<Integer>>();
	public static HashMap<Integer,LinkedList<Integer>> authorIndex = new HashMap<Integer,LinkedList<Integer>>();
	public static HashMap<Integer,LinkedList<Integer>> placeIndex = new HashMap<Integer,LinkedList<Integer>>();
	public static HashMap<Integer,LinkedList<Integer>> docTermFreq = new HashMap<Integer,LinkedList<Integer>>();
	public static HashMap<LinkedList<Integer>,Integer> termTermFreq = new HashMap<LinkedList<Integer>,Integer>();
	
	
	static {
		mapList = new ArrayList();
		mapList.add(docDictionary); 
		mapList.add(termDictionary);
		mapList.add(inverseTermDictionary);
		mapList.add(categoryDictionary);
		mapList.add(authorDictionary);
		mapList.add(placeDictionary);
		mapList.add(termIndex);
		mapList.add(categoryIndex);
		mapList.add(authorIndex);
		mapList.add(placeIndex);
		mapList.add(docTermFreq);
		mapList.add(termTermFreq);
	}
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {
		//TODO : YOU MUST IMPLEMENT THIS
		this.indexDir = indexDir;
		//FileReader fileReader = new FileReader(indexDir);
	}

	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the filed values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 */
	public void addDocument(Document d) throws IndexerException {
		//TODO : YOU MUST IMPLEMENT THIS

		if(d!=null){

			docCounter++;
			//Document doc = new Document();
			Tokenizer tokenizer = new Tokenizer();
			String fileid;
			String category = "";
			String title = "";
			String author = "";
			String authorOrg = "";
			String place = "";
			String newsDate = "";
			String content = "";

			fileid = d.getField(FieldNames.FILEID)[0];
			if(d.getField(FieldNames.CATEGORY) != null && 
					d.getField(FieldNames.CATEGORY)[0] != null){
				category = d.getField(FieldNames.CATEGORY)[0];
			}
			if(d.getField(FieldNames.TITLE)!= null && 
					d.getField(FieldNames.TITLE)[0] != null){
				title = d.getField(FieldNames.TITLE)[0];
			}
			if(d.getField(FieldNames.AUTHOR) != null &&
					d.getField(FieldNames.AUTHOR)[0] != null ){
				author = d.getField(FieldNames.AUTHOR)[0];
			}
			if(d.getField(FieldNames.AUTHORORG)!= null &&
					d.getField(FieldNames.AUTHORORG)[0] != null){
				authorOrg = d.getField(FieldNames.AUTHORORG)[0];
			}
			if(d.getField(FieldNames.PLACE)!= null && 
					d.getField(FieldNames.PLACE)[0] != null){
				place = d.getField(FieldNames.PLACE)[0];
			}
			if(d.getField(FieldNames.NEWSDATE)!= null &&
					d.getField(FieldNames.NEWSDATE)[0]!= null){
				newsDate = d.getField(FieldNames.NEWSDATE)[0];
			}
			if(d.getField(FieldNames.CONTENT)!= null && 
					d.getField(FieldNames.CONTENT)[0]!= null){
				content = d.getField(FieldNames.CONTENT)[0];
			}
			docDictionary.put(docCounter,fileid);


			try {
				Tokenizer tokenizer1 = new Tokenizer();

				AnalyzerFactory analyzer;
				analyzer = AnalyzerFactory.getInstance();
				TokenStream stream = new TokenStream();
				if(content != null && content != ""){
					stream = tokenizer.consume(content);
				}
				if(title != null && title != ""){
					stream.append(tokenizer1.consume(title));
				}
				if(authorOrg != null && authorOrg != ""){
					stream.append(tokenizer1.consume(authorOrg));
				}
				if(newsDate != null && newsDate != ""){
					stream.append(tokenizer1.consume(newsDate));
				}
				if(stream != null){
					Analyzer anl = analyzer.getAnalyzerForField(FieldNames.CONTENT, stream);
					while(anl.increment()){

					}
					stream = anl.getStream();
					

					addToTermIndex(stream, docCounter);
				}
				if(category != null && category != ""){
					stream = tokenizer.consume(category);
				}
				if(stream != null){
					//Analyzer anl = analyzer.getAnalyzerForField(FieldNames.CATEGORY, stream);
					addToCategoryIndex(stream, docCounter);
				}
				if(author != null && author != ""){
					stream = tokenizer.consume(author);
				}
				if(stream != null){
					Analyzer anl = analyzer.getAnalyzerForField(FieldNames.AUTHOR, stream);
					while(anl.increment()){

					}
					stream = anl.getStream();
					addToAuthorIndex(stream, docCounter);
				}
				if(place != null && place != ""){
					place = place.replaceAll("[*{}]","");
					String[] parts = place.split(",");
					for(int i=0 ; i<parts.length ; i++){
						parts[i] = parts[i].trim();
						parts[i] = parts[i].toLowerCase();
						 String[] arr = parts[i].split(" ");
						    StringBuffer sb = new StringBuffer();

						    for (int j = 0; j < arr.length; j++) {
						        sb.append(Character.toUpperCase(arr[j].charAt(0)))
						            .append(arr[j].substring(1)).append(" ");
						    }
						    parts[i] = sb.toString().trim();
					}
					//stream = tokenizer.consume(place);
					addToPlaceIndex(parts, docCounter);
//					for(int i =0 ; i<parts.length ; i++){
//					System.out.println(parts[i]);
//					}
				}
//				if(stream != null){
//					Analyzer anl = analyzer.getAnalyzerForField(FieldNames.PLACE, stream);
//					while(anl.increment()){
//
//					}
//					stream = anl.getStream();
//					addToPlaceIndex(stream, docCounter);
//					//System.out.println(docCounter);
//				}

			} catch (TokenizerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void addToTermIndex(TokenStream stream, int counter){

		if(stream != null){
			int streamSize = stream.currentTokenList.size();
			for(int i=0; i<streamSize; i++){
				LinkedList<Integer> docFreq = new LinkedList<Integer>();
				LinkedList<Integer> termDocFreq = new LinkedList<Integer>();
				LinkedList<Integer> termDocKey = new LinkedList<Integer>();
				String currentToken = stream.currentTokenList.get(i);
				if(currentToken != null && currentToken != ""){
					if(!(termDictionary.containsKey(currentToken))){
						docFreq.clear();
						termDocFreq.clear();
						termDocKey.clear();
						termDictionary.put(currentToken, ++termCounter);
						inverseTermDictionary.put(termCounter, currentToken);
						docFreq.add(counter);
						termIndex.put(termDictionary.get(currentToken), docFreq);
						termDocFreq.add(1);
						termDocFreq.add(1);
						docTermFreq.put(termDictionary.get(currentToken), termDocFreq);
						termDocKey.add(termDictionary.get(currentToken));
						termDocKey.add(counter);
						termTermFreq.put(termDocKey, 1);
					}
					else{

						termDocFreq = docTermFreq.get(termDictionary.get(currentToken));
						int temp = termDocFreq.get(1);
						temp++;
						termDocFreq.set(1, temp);
						docTermFreq.remove(termDictionary.get(currentToken));
						docTermFreq.put(termDictionary.get(currentToken), termDocFreq);
						docFreq=termIndex.get(termDictionary.get(currentToken));
						if(docFreq.getLast()!= counter){
							docFreq.add(counter);
							termIndex.put(termDictionary.get(currentToken), docFreq);
							termDocFreq = docTermFreq.get(termDictionary.get(currentToken));
							int temp5 = termDocFreq.get(0);
							temp5++;
							termDocFreq.set(0, temp5);
							docTermFreq.put(termDictionary.get(currentToken), termDocFreq);
							termDocKey.add(termDictionary.get(currentToken));
							termDocKey.add(counter);
							termTermFreq.put(termDocKey, 1);

						}
						else{
							termDocKey.add(termDictionary.get(currentToken));
							termDocKey.add(counter);
							int temp2 = termTermFreq.get(termDocKey);
							temp2++;
							termTermFreq.put(termDocKey, temp2++);
						}
					}

				}
			}
		}
	}
	public void addToCategoryIndex(TokenStream stream, int counter){
		LinkedList<Integer> docFreq = new LinkedList<Integer>();
		if(stream != null){
			int streamSize = stream.currentTokenList.size();
			for(int i=0; i<streamSize; i++){
				String currentToken = stream.currentTokenList.get(i);
				if(currentToken != null && currentToken != ""){
					if(!(categoryDictionary.containsKey(currentToken))){
						categoryDictionary.put(currentToken, ++categoryCounter);
						docFreq.add(counter);
						categoryIndex.put(categoryDictionary.get(currentToken), docFreq);
					}
					else{
						docFreq=categoryIndex.get(categoryDictionary.get(currentToken));
						if(docFreq.getLast()!= counter){
							docFreq.add(counter);
							categoryIndex.put(categoryDictionary.get(currentToken), docFreq);
						}
					}

				}
			}
		}
	}

	public void addToAuthorIndex(TokenStream stream, int counter){
		LinkedList<Integer> docFreq = new LinkedList<Integer>();
		if(stream != null){
			int streamSize = stream.currentTokenList.size();
			for(int i=0; i<streamSize; i++){
				String currentToken = stream.currentTokenList.get(i);
				if(currentToken != null && currentToken != ""){
					if(!(authorDictionary.containsKey(currentToken))){
						authorDictionary.put(currentToken, ++authorCounter);
						docFreq.add(counter);
						authorIndex.put(authorDictionary.get(currentToken), docFreq);
					}
					else{
						docFreq=authorIndex.get(authorDictionary.get(currentToken));
						if(docFreq.getLast()!= counter){
							docFreq.add(counter);
							authorIndex.put(authorDictionary.get(currentToken), docFreq);
						}
					}

				}
			}
		}
	}


	public void addToPlaceIndex(String[] parts, int counter){
		LinkedList<Integer> docFreq = new LinkedList<Integer>();
		if(parts != null){
			int streamSize = parts.length;
			for(int i=0; i<streamSize; i++){
				String currentToken = parts[i];
				if(currentToken != null && currentToken != ""){
					if(!(placeDictionary.containsKey(currentToken))){
						placeDictionary.put(currentToken, ++placeCounter);
						docFreq.add(counter);
						placeIndex.put(placeDictionary.get(currentToken), docFreq);
					}
					else{
						docFreq=placeIndex.get(placeDictionary.get(currentToken));
						//System.out.println(docFreq);
						if(docFreq.getLast()!= counter){
							docFreq.add(counter);
							placeIndex.put(placeDictionary.get(currentToken), docFreq);
						}
					}

				}
			}
		}
	}
	public void close() throws IndexerException {
		//TODO
				//File file = new File("C:\\Users\\Ankit\\Desktop\\Dictionary Test\\filename.txt");
		int i =0;
		String[] s={"docDictionary.txt","termDictionary.txt","inverseTermDictionary.txt","categoryDictionary.txt","authorDictionary.txt","placeDictionary.txt","termIndex.txt","categoryIndex.txt","authorIndex.txt","placeIndex.txt","docTermFreq.txt","termTermFreq.txt"}; 
		File dir = new File (indexDir);
		for(HashMap map : mapList) {
			File actualFile = new File (dir.getAbsolutePath()+File.separator+s[i]);
			try {
				if (!actualFile.exists()) {
					actualFile.createNewFile();
				}
			FileOutputStream fos = new FileOutputStream(actualFile);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(map);
	        oos.flush();
	        oos.close();
	        fos.close();
	        i++;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

