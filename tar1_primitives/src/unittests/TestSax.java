package unittests;

import junit.framework.TestCase;
import parser.SceneXmlParser;

public class TestSax extends TestCase {
	SceneXmlParser a;
	public void HandlerTest() {
		String tString = a.getHandler().getTmp().get_sceneAttributes().toString();
		System.out.println(tString);
	}

}

