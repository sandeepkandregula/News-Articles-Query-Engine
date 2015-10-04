package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.PUNCTUATION)
public class PunctuationRule implements TokenizerRule {

	public PunctuationRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		String token =null;

		if(stream!=null){
			while (stream.hasNext())
			{
				token=stream.next();

				token = token.replaceAll("(\\s{1,})([,.!?]{1,})", "$1");
				token = token.replaceAll("(\\w{1,})([,.!?]{1,})(\\s{1,}|$)", "$1$3");

				stream.previous();
				stream.set(token);
				stream.next();
			}
			
		}
		stream.reset();
	}
}