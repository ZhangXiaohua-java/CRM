package cloud.huel.crm;

import cloud.huel.crm.commons.enumeration.DictionaryType;
import cloud.huel.crm.commons.utils.UUIDUtils;
import org.junit.Test;
import org.springframework.cglib.core.Local;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author 张晓华
 * @version 1.0
 */
public class NewTest {

	@Test
	public void testEnumDicValue(){
		System.out.println(DictionaryType.CLUE_STATE.getName());

	}

	@Test
	public void getUUID(){
		System.out.println(UUIDUtils.getUUID());
	}


	@Test
	public void message() throws UnsupportedEncodingException {
		String str = "%E5%B8%82%E5%9C%BA%E5%BC%80%E6%8B%93";
		String str2 = "%E7%99%BE%E5%BA%A6&";
		String str3 = "%E7%99%BE%E5%BA%A6";
		String s = URLDecoder.decode(str3, "UTF-8");
		System.out.println(s);
	}

	@Test
	public void testBundle(){
		ResourceBundle resourceBundle = ResourceBundle.getBundle("src/main/resources/possibility", Locale.CHINA);
		System.out.println(resourceBundle.getString("需求分析"));
	}

	@Test
	public void testProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/main/resources/possibility.properties"));
		String o = (String) properties.get("a");
		System.out.println(o);
	}


}
