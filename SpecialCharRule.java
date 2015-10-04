package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.SPECIALCHARS)
public class SpecialCharRule implements TokenizerRule {

	public SpecialCharRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if(stream!=null)
		{	// TODO Auto-generated method stub

			String str=null;
			String token;
			while(stream.hasNext())	
			{
				token = stream.next();
				if(token!=null){
					str = token.replaceAll("(\\w+)(?![-.,'!?])\\p{Punct}(\\w+)", "$1 $2");
					str = str.replaceAll("(?![.,'!?-])\\p{Punct}","");
					str = str.trim();
					str= str.replaceAll(" ( )", " ");

					stream.previous();
					stream.set(str);
					stream.next();

				}			
			}
		//	
		}
		stream.reset();
	}
}