package edu.buffalo.cse.irf14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.query.GetDocuments;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;
import edu.buffalo.cse.irf14.query.Snippet;

/**
 * Main class to run the searcher.
 * As before implement all TODO methods unless marked for bonus
 * @author nikhillo
 *
 */
public class SearchRunner {
	public enum ScoringModel {TFIDF, OKAPI};
	String indexDir;
	String corpusDir;
	PrintStream stream;
	/**
	 * Default (and only public) constuctor
	 * @param indexDir : The directory where the index resides
	 * @param corpusDir : Directory where the (flattened) corpus resides
	 * @param mode : Mode, one of Q or E
	 * @param stream: Stream to write output to
	 */
	public SearchRunner(String indexDir, String corpusDir, 
			char mode, PrintStream stream) {
		//TODO: IMPLEMENT THIS METHOD
		this.indexDir = indexDir;
		this.corpusDir = corpusDir;
		this.stream = stream;
	}

	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {
		//TODO: IMPLEMENT THIS METHOD
		long startTime = System.nanoTime();
		LinkedList<Double> score = new LinkedList<Double>();
		LinkedList<String> name = new LinkedList<String>();
		String defaultOperator = "OR";
		QueryParser queryParser = new QueryParser();
		stream.print("input = "+ userQuery);
		//stream.print(s);
		Query queryObject;
		queryObject = queryParser.parse(userQuery, defaultOperator);
		String stringRep = queryObject.toString();
		GetDocuments getDoc = new GetDocuments(indexDir);
		getDoc.Rank(stringRep, model);
		score = getDoc.returnScore();
		name  = getDoc.returnName();
		if(!(score.isEmpty()) && !(name.isEmpty())){
			Snippet snippet = new Snippet(corpusDir);
			int length = 5;
			for(int i=0; i<name.size(); i++){
				snippet.snip(name.get(i), userQuery, length);
				String title = snippet.getTitle();
				String snip = snippet.getsnip();
				System.out.println(name.get(i));
				System.out.println(score.get(i));
				System.out.println(title);
				System.out.println(snip);
			}
		}
		else{
			System.out.println("None of the Document matches");
		}
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime)/1000000 + " ms"); 
	}

	/**
	 * Method to exe-cute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 */
	public void query(File queryFile) {
		//TODO: IMPLEMENT THIS METHOD
		int numQueries = 0;
		String query;
		String defaultOperator = "OR";
		ArrayList<String> queryID = new ArrayList<String>();
		QueryParser queryParser = new QueryParser();
		Query queryObject;
		LinkedList<Double> score = new LinkedList<Double>();
		LinkedList<String> name = new LinkedList<String>();
		ScoringModel model = ScoringModel.TFIDF;
		GetDocuments getDoc = new GetDocuments(indexDir);
		if(queryFile!=null){
			try {
				StringBuilder contentSB = new StringBuilder();
				FileReader inputFile = new FileReader(queryFile);
				BufferedReader br = new BufferedReader(inputFile);
				String path = queryFile.getParent();
				File fout = new File("output.txt");
				FileOutputStream fos = new FileOutputStream(fout);

				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				String line = null;
				int lineno = 1;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (!line.isEmpty()) {

						Pattern pattern = Pattern.compile("(.*)\\s*=\\s*(\\d+)");
						Matcher matcher = pattern.matcher(line);
						if(matcher.matches()&& lineno==1){
							lineno++;
							numQueries = Integer.parseInt(matcher.group(2));
							bw.write("numQueries="+numQueries);
							bw.newLine();
						}
						else{
							if(lineno>1){
								Pattern pattern1 = Pattern.compile("Q_(.*):\\s*\\{\\s*(.*)\\s*}");
								Matcher matcher1 = pattern1.matcher(line);
								if(matcher1.matches()){
									//queryID.add(matcher1.group(1)); 
									query = matcher1.group(2);
									System.out.println(query);
									queryObject = queryParser.parse(query, defaultOperator);
									String stringRep = queryObject.toString();
									//System.out.println(stringRep);

									getDoc.Rank(stringRep, model);
									score = getDoc.returnScore();
									name  = getDoc.returnName();

									System.out.println(score);
									System.out.println(name);
									if(!(score.isEmpty()) && !(name.isEmpty())){
										bw.write("Q_"+matcher1.group(1)+": { ");
										for (int j = 0; j < name.size(); j++) {
											bw.write(name.get(j)+"#"+score.get(j));
											if(j<name.size()-1){
												bw.write(", ");
											}
										}
										bw.write("}");
										bw.newLine();
									}
									
									
									
								}

							}
						}
					}
				}	
				
				bw.close();
				PrintStream(fout);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * General cleanup method
	 */
	public void close() {
		//TODO : IMPLEMENT THIS METHOD
	}

	/**
	 * Method to indicate if wildcard queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
	}

	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * @return A Map containing the original query term as key and list of
	 * possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		//TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		return null;

	}

	/**
	 * Method to indicate if speel correct queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return false;
	}

	/**
	 * Method to get ordered "full query" substitutions for a given misspelt query
	 * @return : Ordered list of full corrections (null if none present) for the given query
	 */
	public List<String> getCorrections() {
		//TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}

	public void PrintStream(File Stream){
		//String fout=stream.toString();
		File stream=new File("result.txt");
		return;
		
		
		
	}

}
