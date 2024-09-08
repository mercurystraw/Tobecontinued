package src.tech.zxuuu.util;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 不易归类的工具函数
 * 
 * @author z0gSh1u
 */
public class OtherUtils {

	/**
	 * 根据学院号获取学院名称
	 */
	public static String getAcademyByNumber(String number) {
		Map<String, String> map = new HashMap<>();
		map.put("01", "建筑学院");
		map.put("02", "机械工程学院");
		map.put("03", "能源与环境学院");
		map.put("04", "信息科学与工程学院");
		map.put("05", "土木工程学院");
		map.put("06", "电子科学与工程学院");
		map.put("07", "数学学院");
		map.put("08", "自动化学院");
		map.put("09", "计算机科学与工程学院");
		map.put("10", "物理学院");
		map.put("11", "生物科学与医学工程学院");
		map.put("12", "材料科学与工程学院");
		map.put("13", "人文学院");
		map.put("14", "经济管理学院");
		map.put("15", "马克思主义学院");
		map.put("16", "电气工程学院");
		map.put("17", "外国语学院");
		map.put("19", "化学化工学院");
		map.put("21", "交通学院");
		map.put("22", "仪器科学与工程学院");
		map.put("24", "艺术学院");
		map.put("25", "法学院");
		map.put("41", "医学院1");
		map.put("42", "公共卫生学院");
		map.put("43", "医学院2");
		map.put("57", "网络空间安全学院");
		map.put("61", "吴健雄学院");
		map.put("71", "软件学院");
		return map.getOrDefault(number, "");
	}

	/**
	 * 获取调用者端的本地时间Date对象 注意，如果需要准确可靠的时间，必须要在服务器端调用，不可以信任客户端的时间。
	 */
	public static Date getLocalTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * 根据Unix时间戳获取星期几
	 * 
	 * @param unixTimeStamp
	 * @return 1~7
	 */
	public static Integer getDay(long unixTimeStamp) {
		// 1 ~ 7
		Date date = new Date(unixTimeStamp);
		return date.getDay() == 0 ? 7 : date.getDay();
	}

	/**
	 * MD5（32位）
	 */
	public static String getMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
