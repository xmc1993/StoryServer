package cn.edu.nju.software.enums;

public enum ForumOrderEnums {

	order(10000,"活跃度在前，时间在后，降序"),
	order_time_desc(10001,""),
	order_time_asc(10002,""),
	order_hot_desc(10003,""),
	order_hot_asc(10004,"");
	
	public int code;
	public String label;

	private ForumOrderEnums(int code, String label) {
		this.code = code;
		this.label = label;
	}

}
