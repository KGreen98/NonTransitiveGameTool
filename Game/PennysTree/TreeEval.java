package PennysTree;
//JSience Rational import
import java.util.ArrayList;

public class TreeEval {
    //List of evaluations for calculation later
    //will be parsed through
    //1|000 if node == A
    //01|000 if loop, e 01
    ArrayList<String> lst = new ArrayList<>();

    public void totalEval(PTreeNode root){
        ArrayList<String[]> a = SplittedList();
        analyseList(a, root);
    }

    public void Eval(PTreeNode node, ArrayList<Item> items){
        if (node.isDeadend()){
            if (!(node.getTerminalMark().getNodeType() == 3)){
                lst.add(EvalTerminalNonLoop(node) + "|" + node.writeAsString());
                return;
                //mark node as equivalent to node
                //return Eval(node.getTerminalMark().getEquivalentNode());
            }
            lst.add(node.getTerminalMark().getEquivalentNode().writeAsStringWithRoot() + "|" + node.writeAsString());
            return;
        }
        //maybe add non terminal nodes
        System.out.println(node.writeAsString() + " evals to product of lower nodes");
        Eval(node.getChild(0), items);
        Eval(node.getChild(1), items);
    }

    public String EvalTerminalNonLoop(PTreeNode node){
        if (node.getTerminalMark().getNodeType() == 0){
            return "A";
        }
        return "B";
    }

    public void printList(){
        for(String s: lst){
            System.out.println(s);
        }
        System.out.println("Splitter");
        for(String s: lst){
            listEntryParse(s);
        }
    }

    public String[] listEntryParse(String s){
        String[] parts = s.split("\\|");
        return parts;
    }

    public ArrayList<String[]> SplittedList(){
        ArrayList<String[]> splist = new ArrayList<>();
        for (String s: lst){
            splist.add(listEntryParse(s));
        }
        return splist;
    }

    public void analyseList(ArrayList<String[]> splist, PTreeNode root){
        System.out.println("Analysing Real List");
        double r = 0;
        double A = 0;
        double B = 0;
        for(String[] s : splist){
            if (s[0].equals("A")){
                A += 1/(Math.pow(2,s[1].length()));
                System.out.println("+ " + 1/(Math.pow(2,s[1].length())));
            }
            if (s[0].equals("B")){
                System.out.println("// " + 1/(Math.pow(2,s[1].length())));
            }
            else {
                if (s[0].equals("root")) {
                    r += 1 / (Math.pow(2, s[1].length()));
                }
                else{
                    A += 1/(Math.pow(2,s[1].length()+1));
                }
//                if (s[0].equals("root")){
//                    s[0] = "";
//                }
//                PTreeManip m = new PTreeManip(10);
//                PTreeNode eqN = m.getNodeByName(root, s[0]);
// System.out.println("// " + 1/(Math.pow(2,s[1].length())) + "  : " + eqN.writeAsString().length());
            }
        }
        System.out.println("r: " + r);
        System.out.println("A: " + A);
        System.out.println("B: " + B);
        System.out.println("Eval: " + A/(1-r));
    }
}
