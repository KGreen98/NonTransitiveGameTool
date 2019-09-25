import java.sql.SQLOutput;
import java.util.Random;

public class Pennys {
    private String p1;  //player 1's choice
    private String p2;  //player 2's choice
    private String list = "";
    public int stringLen = 6;
    Random random = new Random();

    public Pennys(String c1, String c2){
        setP1(c1);
        setP2(c2);
        for(int i =0; i<stringLen; i++){
            Integer bin = genRandom();
            list = list.concat(bin.toString());
        }
        System.out.println("Initial List: " + list);
        incrementList(list);
    }

    public void run() {
        while (!list.equals(getP1())){
            incrementList(list);
        }
    }

    public int genRandom() {
        int randomInt = (int)(2.0 * Math.random());
        return randomInt;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String incrementList(String list) {
        System.out.println("List increment");
        System.out.println(list);
        list = list.substring(1);
        System.out.println(list);
        Integer bin = genRandom();
        list = list.concat(bin.toString());
        System.out.println(list);
        return list;
    }

    public boolean equals(String a, String b) {
        System.out.println(a);
        System.out.println(b);

        if (a.equals(b)){
            return true;
        }
        return false;
    }
}
