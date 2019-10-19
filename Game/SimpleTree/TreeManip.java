package SimpleTree;

import SimpleTree.MyTreeNode;

public class TreeManip {

    public int bestProgress(String A, MyTreeNode root){
        //Returns distance from match, if returns 0, it matches.
        //If returns Length of A, 0 match.
        //If returns Length of A-1, 1 match.
        //Get total string from tree node up to root

        String a = writeSingleString(root);
        //Most recent n in tree as String
        String subString = a.substring(a.length() - A.length());
        System.out.println("Tree:" + subString + " :vs Input:" + A);
        int maxCount = 0;
        while (A.length()>=0) {
            //System.out.println("    Tree:" + subString + " :vs Input:" + A);
            if (A.equals(subString)) {
                System.out.println(maxCount);
                return A.length() - maxCount;
            }
            subString = subString.substring(1);
            A = A.substring(0, A.length() - 1);
            maxCount++;
        }
        System.out.println("Error");
        return 100000;
    }

    public String writeSingleString(MyTreeNode node){
        String str = "" + node.getValue();
        int n = node.findlength();
        for (int i=0; i<(n-1); i++) {
            MyTreeNode a = node.getParent();
            str = str + a.getValue();
            node = node.getParent();
        }
        return str;
    }

    public double probabilitySearch(MyTreeNode root, String A, String B, int n){
        double x = (probabilitySearch2(root, A, B, n, 0) + probabilitySearch2(root, A, B, n, 1))/2;
        return x;
    }

    public double probabilitySearch2(MyTreeNode root, String A, String B, int n, int initial){
        return 0.5;
    }
}
