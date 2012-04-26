package utilities.pdf;
//-----------------------------------------------------------------------------
/**
 *
 */
public class FieldAndVal {
	String field;
	String val;
//-----------------------------------------------------------------------------
	public FieldAndVal(String field, String val){
		this.field=field;
		this.val=val;
	}
//-----------------------------------------------------------------------------
	public String getField(){
		return field;
	}
//-----------------------------------------------------------------------------
	public String getVal(){
		return val;
	}
//-----------------------------------------------------------------------------
	@Override
	public String toString(){
		String thisAsString = "FieldAndVal {\nfield = " + this.field+"\n"
				+"val = " + this.val + "\n}";
		return thisAsString;
	}
//-----------------------------------------------------------------------------
}