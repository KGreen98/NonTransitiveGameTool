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
//        ArrayList<String[]> a = SplittedList();
//        analyseList(a, root);
    }
    public double PA = 0;
    public double prob = 0;

    public void FinalEval(){
        System.out.println("_______________________");
        System.out.println("PA: " + PA);
        System.out.println("Prob: " + prob);
        PA = 1 - PA;
        double total = prob / PA;
        System.out.println("Total: " + total);
    }

    public void NewEval(PTreeNode root, PTreeNode node, String A, String B){
        if (node.isDeadend()){
            if ((node.nodeValueNo() == 0)){
                System.out.println("Length from leaf: " + node.writeAsString() + ":" + node.findLengthFromLeaf());
                System.out.println(1/Math.pow(2, node.findLengthFromLeaf()));
                prob += 1/Math.pow(2, node.findLengthFromLeaf());
            }
            if ((node.nodeValueNo() == 3)){
                if (node.equivNode().equals(root)){
                    System.out.println("Length from leaf: " + node.writeAsString() + ":" + node.findLengthFromLeaf());
                    System.out.println(1/Math.pow(2, node.findLengthFromLeaf()));
                    PA += 1/Math.pow(2, node.findLengthFromLeaf());
                }
                //Eval loop nodes
                else{
                    //todo consider root loops
                    System.out.println("Loop time" + node.writeAsString());
                    PTreeManip tm = new PTreeManip(2);
                    double[] dA = tm.evalLoopPA(root, node.equivNode(), node.equivNode(), A, B);
                    System.out.println("DA[0] prob" + dA[0]);
                    System.out.println("DA[1] self" + dA[1]);
                    System.out.println("DA[2] rot" + dA[2]);
                    double tempprob = dA[0] / (1 - dA[1]);
                    double tempPA = dA[2] / (1 - dA[1]);

                    double multi = 1/Math.pow(2, node.findLengthFromLeaf());
                    double valP = tempprob * multi;
                    //System.out.println(valP);
                    prob += tempprob * multi;
                    double valPA = tempprob * multi;
                    //System.out.println(valPA);
                    PA += tempPA * multi;
                }
            }
            return;
        }
        //todo fix for any char set
        NewEval(root, node.getChild(0), A, B);
        NewEval(root, node.getChild(1), A, B);
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
                    System.out.println("root");
                    r += 1 / (Math.pow(2, s[1].length()));
                }
                else{
                    System.out.println(s[0]);
                    A += 1/(Math.pow(2,s[1].length()+1));
                }
            }
        }
        System.out.println("r: " + r);
        System.out.println("A: " + A);
        System.out.println("B: " + B);
        System.out.println("Eval: " + A/(1-r));
    }
}
