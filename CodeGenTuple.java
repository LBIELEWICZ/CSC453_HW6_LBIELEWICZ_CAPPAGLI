import java.util.*;

public class CodeGenTuple {

	private LinkedList<TACObject> threeAddrList;
	private TreeMap<String, SymbolDef> symbolTable;
	private String name;
	private ASTNode funcRoot;
	private int stackSize;

	public CodeGenTuple(String name) {
		this.name = name;
		stackSize = 0;
	}

	public void setStackSize(int size) {
		stackSize = size;
	}

	public void setSymTab(TreeMap<String, SymbolDef> symTab) {
		this.symbolTable = symTab;
		// Set offsets
		int i = 0;
		for (String key : symTab.keySet()) {
			symTab.get(key).setOffset(i);
			i++;
		}
	}

	public void setList(LinkedList<TACObject> list) {
		this.threeAddrList = list;
	}

	public void setRoot(ASTNode root) {
		funcRoot = root;
	}
	
	public LinkedList<TACObject> getList() {
		return threeAddrList;
	}

	public ASTNode getRoot() {
		return funcRoot;
	}
	
	public TreeMap<String, SymbolDef> getSymTab() {
		return symbolTable;
	}

	public String getName() {
		return name;
	}

	public int getStackSize() {
		return stackSize;
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
