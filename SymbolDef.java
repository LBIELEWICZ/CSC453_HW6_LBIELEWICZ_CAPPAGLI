public class SymbolDef {
	
	private int val;
	private int offset;
	private String type;
	
	public SymbolDef(String type) {
		this.type = type;
		this.val = 0;
	}

	public String getType() {
		return type;
	}

	public int getVal() {
		return val;
	}

	public int getOffset() {
		return offset;
	}

	public void setVal(int  val) {
		this.val = val;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
