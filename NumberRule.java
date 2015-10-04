package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.NUMBERS)
public class NumberRule implements TokenizerRule {


	public void apply(TokenStream stream) throws TokenizerException {

		Pattern p1 = Pattern.compile("[0-9]{1,}\\%");
		Pattern p2 = Pattern.compile("[0-9]{1,}/[0-9]{1,}");
		Pattern p3 = Pattern.compile("[0-9]{1,}");

		// TODO Auto-generated method stub

		if (stream != null) 
		{
			String token;
			while (stream.hasNext()) 
			{ 
				token=stream.next();
				if (token != null)
				{
					if(token.contains(","))
						token = token.replace(",","");
					if(token.contains("."))
						token = token.replace(".","");

					Matcher m1 = p1.matcher(token);
					Matcher m2 = p2.matcher(token);
					Matcher m3 = p3.matcher(token);

					if(m1.find()){	
						token = m1.replaceAll("%");
						stream.previous();
						stream.set(token);
						stream.next();
					}

					else if(m2.find()){	
						token = m2.replaceAll("/");
						stream.previous();
						stream.set(token);
						stream.next();
					}

					else if(m3.find()){	
						token = m3.replaceAll("");
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
