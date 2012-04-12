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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		RollCall.name = name;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		RollCall.present = present;
	}
	public String getTimeArrived() {
		return timeArrived;
	}
	public void setTimeArrived(String timeArrived) {
		RollCall.timeArrived = timeArrived;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		RollCall.comment = comment;
	}
}