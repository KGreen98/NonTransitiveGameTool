package PennysGame;

public class Pennys {
    private String p1;  //player 1's choice
    private String p2;  //player 2's choice
    private String list = "";
    private int index = 1;

    public Pennys(String c1, String c2, int length) throws InterruptedException {
        setP1(c1);
        setP2(c2);
        for(int i =0; i<length; i++){
            Integer bin = genRandom();
            list = list.concat(bin.toString());
        }
        run();
    }

    public void run() {

        while (!equals(getP1(), getList())){
            incrementList();
        }
    }

    public int genRandom() {
        int randomInt = (int)(2 * Math.random());
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

    public void incrementList() {
        setList(getList().substring(1));
        Integer bin = genRandom();

        setList(getList().concat(bin.toString()));
        System.out.println(index + " " + getList());
        index++;
    }

    public boolean equals(String a, String b) {
        if (a.equals(b)){
            return true;
        }
        return false;
    }
}
