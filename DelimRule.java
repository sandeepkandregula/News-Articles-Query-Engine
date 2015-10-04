package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.DELIM)
public class DelimRule implements TokenizerRule {


	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
	/*	if (stream != null) {
			String token;

			while (stream.hasNext()) { 
				token = stream.next();
				if (token != null) {
					String pattern="[!.?:;|@#$%^&*><~]";
					Pattern p=Pattern.compile(pattern);
					Matcher m=p.matcher(token);
					String [] s = token.split(pattern);
				}
				else
				{
					String result=token;
				}	
			}	
		}
		stream.reset();*/
	}

}

