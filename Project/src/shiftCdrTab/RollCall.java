package shiftCdrTab;

public class RollCall {
	private static String name;
	private static String present;
	private static String timeArrived;
	private static String comment;
	
	public RollCall() {
		name = "unknown";
		present = "unknown";
		timeArrived = "unknown";
		comment = "unknown";
	}
	
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		RollCall.name = name;
	}
	public static String getPresent() {
		return present;
	}
	public static void setPresent(String present) {
		RollCall.present = present;
	}
	public static String getTimeArrived() {
		return timeArrived;
	}
	public static void setTimeArrived(String timeArrived) {
		RollCall.timeArrived = timeArrived;
	}
	public static String getComment() {
		return comment;
	}
	public static void setComment(String comment) {
		RollCall.comment = comment;
	}

}
