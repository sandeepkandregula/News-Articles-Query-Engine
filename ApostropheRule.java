
package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.APOSTROPHE)
public class ApostropheRule implements TokenizerRule {

	public void apply(TokenStream stream) throws TokenizerException {
		//String[] toStream;
		// TODO Auto-generated method stub
		if (stream != null) {

			String token;
			while (stream.hasNext()) {

				token = stream.next();
				if (token != null) {

					if(token.contains("let's")) token = token.replace("let's", "let us");
					if(token.contains("won't")) token = token.replace("won't", "will not");
					if(token.contains("shan't")) token = token.replace("shan't", "shall not");
					if(token.contains("isn't")) token = token.replace("isn't", "is not");	
					if(token.contains("n't")) token = token.replace("n't", " not");	
					if(token.contains("i'm")) token = token.replace("i'm", "i am");
					if(token.contains("I'm")) token = token.replace("I'm", "I am"); 
					if (token.contains("'d")) token = token.replace("'d", " would");
					if(token.contains("'ll")) token = token.replace("'ll", " will");
					if(token.contains("'re")) token = token.replace("'re", " are");
					if(token.contains("'em")) token = token.replace("'em", " them");
					if(token.contains("'ve")) token = token.replace("'ve", " have");
					if(token.contains("'n'")) token = token.replace("'n'", "and");
					if(token.contains("'s"))  token = token.replace("'s", "");
					if(token.contains("'am")) token = token.replace("ma'am", "madam");	
					if(token.contains("'"))	token = token.replace("'","");
					
					String temp[] = token.split("\\s{1,}");
					stream.previous();
					stream.set(temp);
					stream.next();		
				}				
			}
			
		}
		stream.reset();
	}
	
}