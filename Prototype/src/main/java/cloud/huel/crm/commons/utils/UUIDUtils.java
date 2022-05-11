package cloud.huel.crm.commons.utils;

import java.util.UUID;

/**
 * @author 张晓华
 * @version 1.0
 */
public final class UUIDUtils {

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

}
