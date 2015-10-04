package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RuleClass(className = RULENAMES.ACCENTS)
public class AccentRule implements TokenizerRule {

	public AccentRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		
		if (stream != null) 
		{
			String token;
			while (stream.hasNext()) {
				token = stream.next();
			
				if (token != null)
				{
						token = Normalizer.normalize(token, Normalizer.Form.NFD);
						Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
						Matcher matcher = pattern.matcher(token);
						if(matcher.find()){
						token = matcher.replaceAll("");
						stream.previous();
						stream.set(token);
						stream.next();
						}
					}
				}
			
			}
		stream.reset();
		}
	}

