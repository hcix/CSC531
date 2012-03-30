package shiftCdrTab;

import java.util.ArrayList;

public class RollCall {
	private ArrayList<String> names;
	private ArrayList<Boolean> present;
	private ArrayList<String> times;
	private ArrayList<String> comments;
	
	public RollCall(ArrayList<String> names, ArrayList<Boolean> present, ArrayList<String> times, ArrayList<String> comments) {
		this.names = names;
		this.present = present;
		this.times = times;
		this.comments = comments;
	}
}
