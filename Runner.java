/**
 * 
 */
package edu.buffalo.cse.irf14;

import java.io.File;
import java.util.ArrayList;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

//import src.edu.buffalo.cse.irf14.document.Document;
//import src.edu.buffalo.cse.irf14.document.Parser;
//import src.edu.buffalo.cse.irf14.document.ParserException;
//import src.edu.buffalo.cse.irf14.index.IndexWriter;
//import src.edu.buffalo.cse.irf14.index.IndexerException;

/**
 * @author nikhillo
 *
 */
public class Runner {



	/**
	 * 
	 */
	public Runner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		String ipDir = args[0];
		String indexDir = args[1];
		//more? idk!

	

		File ipDirectory = new File(ipDir);
		String[] catDirectories = ipDirectory.list();
		
		String[] files;
		File dir;

		Document d = null;
		IndexWriter writer = new IndexWriter(indexDir);

		try {
			for (String cat : catDirectories) {
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				if (files == null)
					continue;

				for (String f : files) {
					try {
						d = Parser.parse(dir.getAbsolutePath() + File.separator +f);
						if(d==null){
							throw new ParserException();
						}
						//System.out.println(d.getField(FieldNames.FILEID));
						writer.addDocument(d);
					} 
					catch (ParserException e) {
						System.out.println("Invalid File");
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

				}

			}
			System.out.println("Indexing done wait for file to be written.");
			writer.close();
			long endTime = System.nanoTime();
			System.out.println("Took "+(endTime - startTime) + " ns"); 
			IndexReader reader = new IndexReader(indexDir, IndexType.TERM);
		} 
		catch (IndexerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}

}
