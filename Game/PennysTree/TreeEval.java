package PennysTree;

import java.util.ArrayList;

public class TreeEval {
    //List of evaluations for calculation later
    //will be parsed through
    //1|000 if node == A
    //01|000 if loop, e 01
    ArrayList<String> lst = new ArrayList<>();

    public void totalEval(){
        ArrayList<String[]> a = SplittedList();
        analyseList(a);
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
        Eval(node.getLeftChild(), items);
        Eval(node.getRightChild(), items);
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

    public void analyseList(ArrayList<String[]> splist){
        for(String[] s : splist){
            if (s[0].equals("A")){
                return ;
            }

        }
    }
}
