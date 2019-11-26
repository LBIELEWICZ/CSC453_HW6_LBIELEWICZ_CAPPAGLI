import java.util.*;
import java.io.*;

public class AdvancedJava {
	
	public void codeGen (String eval, String fileName) {
		EvalParser parse = new EvalParser();
		
		LinkedList<CodeGenTuple> codeGen = parse.getThreeAddr(eval);
		
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

			writer.write("int64_t");
			TreeMap<String,SymbolDef> global = parse.getGlobalSymTab();
			
			// Remove non integers from global
			Set<String> keys = new TreeSet<>();
			keys.addAll(global.keySet());
			for (String key : keys) {
				if (!global.get(key).getType().equals("INT"))
					global.remove(key);
			}
			String [] keyArr = global.keySet().toArray(new String[0]);
			
			for (int i = 0; i < keyArr.length; i++) {
				if (global.get(keyArr[i]).getType().equals("INT")) {
					writer.write(" " + keyArr[i] + " = " + global.get(keyArr[i]).getVal());
					if (i != keyArr.length-1)
						writer.write(",");
				}
			}
			writer.write(";\n");
	
			for (int i = 0; i < codeGen.size(); i++) {
				writer.write(codeGen.get(i).getName() + ":\n");
				writer.write("sp = sp - 2;\n"+
						"*(sp+2) = fp;\n"+
						"*(sp+1) = ra;\n"+
						"fp = sp;\n"+
						"sp = sp - " + codeGen.get(i).getStackSize() + ";\n");
				
				LinkedList<TACObject> tacs = codeGen.get(i).getList();
				for (int j = 0; j < tacs.size(); j++) {
					if (tacs.get(j).getType() == TACObject.OpType.CASSIGN) {
						writer.write("r1 = " + tacs.get(j).getSrc1() + ";\n");
						System.out.println(codeGen.get(i));
						writer.write("*(fp-" + codeGen.get(i).getSymTab().get(tacs.get(j).getDest()).getOffset() +
								") = r1;\n");
					}
				}
			}
	
			writer.write("exit:\n"+
					"return reserved;\n"+
					"}");
			writer.close();
		} catch (IOException ie) {
			System.out.println("ERROR: File error");
			System.exit(1);
		}
	}
}
