package cloud.huel.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author 张晓华
 * @version 1.0
 */
public final class HSSFUtils {

	@Deprecated
	public static String getCellValue(HSSFCell cell){
		switch (cell.getCellType()){
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case HSSFCell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case HSSFCell.CELL_TYPE_FORMULA:
				return String.valueOf(cell.getCellFormula());
			default:
				return "";
		}
	}

}
