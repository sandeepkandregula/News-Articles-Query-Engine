package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.WHITESPACE)
public class WhiteSpaceRule implements TokenizerRule {

	public WhiteSpaceRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {

		//Pattern p = Pattern.compile("[a-zA-Z0-9]{1,}\\s{1,}[a-zA-Z0-9]{1,}");
		String result[];
		String trimmed;
		// TODO Auto-generated method stub
		if (stream != null) {
			String token;

			while (stream.hasNext()) { 
				token = stream.next();
				if (token != null) {	
					trimmed = token.trim();
					result = trimmed.split("\\s{1,}");

					stream.previous();
					stream.set(result);
					stream.next();
				}				
			}
			
		}
		stream.reset();
	}
}