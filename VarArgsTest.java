package test;

public class VarArgsTest {

	
	public static void test(String... args) { 
		if(args.length == 1 && args[0] == null) {
			System.out.println("Done");
		} else
		System.out.println(args);
	}
	
	public static void main(String[] args) {
		String value = null;
		test("Hello");
		test(value);
		test(new String[] {"1", "2"});

	}

}
