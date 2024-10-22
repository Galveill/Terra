package commons;

import messages.SimpleMessageWrapper;

public class Main {
	public static void main(String[] args) throws Exception {
		SimpleMessageWrapper smw = new SimpleMessageWrapper("A", "G", new String[]{"a", "b"});
		System.out.println(smw.getJSON());
	}
}
