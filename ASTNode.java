import java.util.*;

public class ASTNode {
	
	public enum NodeType {
		OP, NUM, ID, IF, WHILE, ASSG, SLIST, PROG, RELOP, COMP, FUNC, AND, OR, FLIST, PLIST, PARAM, RET, ALIST, ARG, CALL;
	}
	
	private ASTNode left = null;
	private ASTNode right = null;
	private ASTNode extra = null;
	private String val = null;
	private NodeType type;
	private ArrayList<ASTNode> params = null;
	private ArrayList<ASTNode> args = null;
	private int id;
	private int tID;
	private int fID;
	private int rID;
	private int cID;
	private boolean dec;

	public ASTNode(ASTNode.NodeType type) {
		this.type = type;
	}

	public ArrayList<ASTNode> getArgs() {
		return args;
	}

	public void addArg(ASTNode arg) {
		if (args == null)
			args = new ArrayList<>();
		args.add(arg);
	}

	public ArrayList<ASTNode> getParams() {
		return params;
	}

	public void addParam(ASTNode param) {
		if (params == null)
			params = new ArrayList<>();
		params.add(param);
	}

	public NodeType getType() {
		return this.type;
	}

	public ASTNode getLeft() {
		return left;
	}

	public ASTNode getRight() {
		return right;
	}

	public ASTNode getExtra() {
		return extra;
	}

	public String getVal() {
		return val;
	}

	public int getID() {
		return id;
	}
	
	public int getTID() {
		return tID;
	}

	public int getFID() {
		return fID;
	}
	
	public int getRID() {
		return rID;
	}
	
	public int CID() {
		return cID;
	}

	public boolean getDec() {
		return dec;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setLeft(ASTNode node) {
		left = node;
	}

	public void setRight(ASTNode node) {
		right = node;
	}

	public void setExtra(ASTNode node) {
		extra = node;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setTID(int tID) {
		this.tID = tID;
	}

	public void setFID(int fID) {
		this.fID = fID;
	}

	public void setRID(int rID) {
		this.rID = rID;
	}

	public void setCID(int cID) {
		this.cID = cID;
	}

	public void setDec(boolean dec) {
		this.dec = dec;
	}
}
