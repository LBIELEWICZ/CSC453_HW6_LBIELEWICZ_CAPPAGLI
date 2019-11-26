import java.util.*;
import java.io.*;

public class AdvancedJava {
	
	public void codeGen (String eval, String fileName) {
		EvalParser parse = new EvalParser();
		
		LinkedList<CodeGenTuple> codeGen = parse.getThreeAddr(eval);

		TreeMap<String,SymbolType> symTab;
		String str;
		CodeGenTuple cur;
		List<TACObject> threeAddr;
		TACObject tac;
		int temp;
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));	

			writer.write("#include <stdio.h>\n"+
					"#include <inttypes.h>\n"+
					"\n"+
					"int main(int argc, char **argv){\n"+
					"int64_t r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0;\n"+
					"int64_t stack[100];\n"+
					"int64_t *sp = &stack[99];\n"+
					"int64_t *fp = &stack[99];\n"+
					"int64_t *ra = &&exit;\n"+
					"goto mainEntry;\n");

			symTab = parse.getGlobalSymTab();
			if(symTab.size() > 0){
				str = "int64_t";
				temp = 0;
				for(Map.Entry<String,SymbolType> entry : symTab.entrySet()){
					String key = entry.getKey();
					SymbolType value = entry.getValue();
					str = str + " " + key + " = 0";
					temp++;
					if(temp < symTab.size()){
						str = str + ",";
					}
					else{
						str = str + ";\n";
					}
				}
				writer.write(str);
			}

			temp = 0;
			while(temp < codeGen.size()){
				cur = codeGen.get(temp);
				cur.setOffset();
				symTab = cur.getSymTab();
				str = cur.getName() + ":\n" +
						"sp = sp - 2;\n" +
						"*(sp+2) = fp;\n" +
						"*(sp+1) = ra;\n" +
						"fp = sp;\n" +
						"sp = sp - " + Integer.toString(symTab.size()) + ";\n";
				writer.write(str);

				threeAddr = cur.getList();
				String s;
				while(threeAddr.peek != null){
					tac = threeAddr.pop();
					if(tac.getOp == TACObject.OpType.ASSIGN){
						if(symTab.containsKey(tac.getScr1())){
							str = "r1 = *(fp-" + symTab.get(tac.getScr1()).getOffset() + ");\n";
						}
						else{
							str = "r1 = " + tac.getScr1() + ";\n";
						}
						if(symTab.containsKey(tac.getDest())){
							str = str + "(fp-" + symTab.get(tac.getDest()).getOffset() ") = r1;\n";
						}
						else{
							str = str + tac.getDest() + " = r1;\n";
						}
					}
					else if(tac.getOp == TACObject.OpType.PLUS || tac.getOp == TACObject.OpType.MINUS || tac.getOp == TACObject.OpType.MUL || tac.getOp == TACObject.OpType.DIV){
						if(symTab.containsKey(tac.getScr1())){
							str = "r1 = *(fp-" + symTab.get(tac.getScr1()).getOffset() + ");\n";
						}
						else{
							str = "r1 = " + tac.getScr1() + ";\n";
						}
						if(symTab.containsKey(tac.getScr2())){
							str = str + "r2 = *(fp-" + symTab.get(tac.getScr2()).getOffset() + ");\n";
						}
						else{
							str = str + "r2 = " + tac.getScr2() + ";\n";
						}
						str = str + "r3 = r1 ";
						if(tac.getOp == TACObject.OpType.PLUS){
							str = str + "+"
						}
						else if(tac.getOp == TACObject.OpType.MINUS){
							str = str + "-"
						}
						else if(tac.getOp == TACObject.OpType.MUL){
							str = str + "*"
						}
						else if(tac.getOp == TACObject.OpType.DIV){
							str = str + "/"
						}
						str = str + " r2;\n";
						if(symTab.containsKey(tac.getDest())){
							str = str + "(fp-" + symTab.get(tac.getDest()).getOffset() + ") = r3;\n";
						}
						else{
							str = str + tac.getDest() + " = r3;\n";
						}
					}
					else if(tac.getOp == TACObject.OpType.LABLE){
						str = tac.getScr1() + ":\n";
					}
					else if(tac.getOp == TACObject.OpType.GOTO){
						str = "goto " + tac.getDest() + ";\n"
					}
					else{ //Control Flow
						if(symTab.containsKey(tac.getScr1())){
							str = "r1 = *(fp-" + symTab.get(tac.getScr1()).getOffset() + ");\n";
						}
						else{
							str = "r1 = " + tac.getScr1() + ";\n";
						}
						if(symTab.containsKey(tac.getScr2())){
							str = str + "r2 = *(fp-" + symTab.get(tac.getScr2()).getOffset() + ");\n";
						}
						else{
							str = str + "r2 = " + tac.getScr2() + ";\n";
						}
						str = str + "if(r1 < r2) goto " + tac.getDest() + ";\n";
					}
					writer.write(str);
				}

				str = "sp = sp + " + Integer.toString(symTab.size()) + ";\n" +
						"fp = *(sp+2);\n" +
						"ra = *(sp+1);\n" +
						"sp = sp + 2;\n" +
						"goto *ra;\n";
				writer.write(str);

				cur = codeGen
			}
		
			writer.write("exit:\n"+
					"return reserved;\n"+
					"}");
		} catch (IOException ie) {
			System.out.println("ERROR: File error");
			System.exit(1);
		}
	}
}
