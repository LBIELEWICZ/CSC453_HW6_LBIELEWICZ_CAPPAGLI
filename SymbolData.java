import java.util.*;

public class SymbolData {

	private String offset;
	private EvalParser.SymbolType type;

	public SymbolData(EvalParser.SymbolType t) {
		type = t;
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
