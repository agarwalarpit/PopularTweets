import java.util.ArrayList;
import java.util.Stack;

public class InfixToPrefix {
	String inputString; 
	ArrayList<String> operatorString;  
	Stack<String> operationStack; 
	
	public InfixToPrefix() {
		this.operatorString.add("+");
		this.operatorString.add("-");
		this.operatorString.add("*");
		this.operatorString.add("/");
		operationStack = new Stack<>(); 
	}
	
	public boolean isOperator(String s) {
		return this.operatorString.contains(s); 
	}
	
	public boolean isBraces(String s) {
		return (s.equals(")") || s.equals("(")); 
	}
	
	public boolean isOperand(String s) {
		return !(isOperator(s) || isBraces(s)); 
	}
	
	public String Prefixer() {
		String outputString = ""; 
		
		String stringArray[] = inputString.split(" "); 
		
		for (String string : stringArray) {
			 this.operationStack.add(string); 
		}
		
		
		
		return outputString; 
	}
	
	public static void main(String[] args) {
		
	}
}
