import java.util.*;

public class SymbolData {

	private String offset;
	private EvalParser.SymbolType type;
	private int argNum;

	public SymbolData(EvalParser.SymbolType t) {
		type = t;
		argNum = 0;
	}

	public int getArgNum(){
		return argNum;
	}
	
	public void incArgNum(){
		argNum = argNum + 1;
	}

	public void setOffset(int o) {
		offset = Integer.toString(o);
	}

	public EvalParser.SymbolType getType() {
		return type;
	}

	public String getOffset() {
		return offset;
	}
}
