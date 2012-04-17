package shiftCdrTab;

public class RollCall {
	private  String name;
	private  String present;
	private  String timeArrived;
	private  String comment;
	
	public RollCall() {
		name = "unknown";
		present = "unknown";
		timeArrived = "unknown";
		comment = "unknown";
	}
	public RollCall(String name, String present, String timeArrived, String comment) {
		this.name = name;
		this.present = present;
		this.timeArrived = timeArrived;
		this.comment = comment;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
	public String getTimeArrived() {
		return timeArrived;
	}
	public void setTimeArrived(String timeArrived) {
		this.timeArrived = timeArrived;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}