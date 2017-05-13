package cn.edu.nju.software.enums;

/**
 * 直播状态码
 * <p> 用于判断直播的状态，共分为三个状态（未开始，直播中，已结束）
 * @author fenggang
 *
 */
public enum LiveStatusCode {
	
	LIVE_STATS_NOT(0, "直播未开始"),
	LIVE_STATS_STOP(1, "直播已结束"),
	LIVE_STATS_START(2, "直播进行中");

	public int code;
	public String label;

	private LiveStatusCode(int code, String label) {
		this.code = code;
		this.label = label;
	}

	/**
	 * 获取状态码描述
	 * 
	 * @param code
	 *          状态码
	 * @return 状态码描述，如果没有返回空串
	 */
	public static String getLabel(int code) {
		String result = "";
		for (LiveStatusCode status : LiveStatusCode.values()) {
			if (status.code == code) {
				result = status.label;
			}
		}
		return result;
	}
}
