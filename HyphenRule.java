package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.HYPHEN)
public class HyphenRule implements TokenizerRule {

	public HyphenRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		if (stream != null) {
			String token;
			while (stream.hasNext()) { 
				token = stream.next();
				if(token!=null){		

					if(token.matches("[a-zA-Z]+\\-[a-zA-Z]+")){
						token = token.replace("-", " ");
						stream.previous();
						stream.set(token);
						stream.next();
					}

					else if(token.matches("[a-zA-Z]+\\-{1,}") || token.matches("\\-{1,}[a-zA-Z]+")){
						token = token.replace("-","");
						stream.previous();
						stream.set(token);
						stream.next();
					}	

					else if(token.matches("\\s{0,}\\-{1,}\\s{0,}")){
						token = token.replace("-","");
						stream.previous();
						stream.remove();
						stream.next();
					}	
				}				
			}
		//	
		}
		stream.reset();	
	}
}
