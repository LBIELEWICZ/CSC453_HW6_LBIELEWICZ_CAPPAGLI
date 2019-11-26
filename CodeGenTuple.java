import java.util.*;

public class CodeGenTuple {

	private List<TACObject> threeAddrList;
	private TreeMap<String, SymbolData> symbolTable;
	String name;
	ASTNode funcRoot;

	public CodeGenTuple(String name) {
		this.name = name;
	}

	public void setSymTab(TreeMap<String, SymbolData> symTab) {
		this.symbolTable = symTab;
	}

	public void setList(List<TACObject> list) {
		this.threeAddrList = list;
	}

	public void setRoot(ASTNode root) {
		funcRoot = root;
	}

	public void setOffset() {
		int off = 0;
		for(Map.Entry<String,SymbolData> entry : symbolTable.entrySet()){
			String key = entry.getKey();
			SymbolData value = entry.getValue();
			value.setOffset(off);
			off++;
		}
	}

	public String getName() {
		return name;
	}

	public TreeMap<String, SymbolData> getSymTab() {
		return symbolTable;
	}

	public List<TACObject> getList(){
		return threeAddrList;
	}

	public ASTNode getRoot() {
		return funcRoot;
	}

	public String toString() {
		String str = "";
		str += "FUNCTION NAME: " + name + "\n\n";
		for (int i = 0; i < threeAddrList.size(); i++) {
			str += threeAddrList.get(i);
		}
		str += "\n";
		
		str += "SYMBOL TABLE: " + symbolTable;
		return str;
	}
}
