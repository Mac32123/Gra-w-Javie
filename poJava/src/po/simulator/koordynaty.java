package po.simulator;

public class koordynaty {
    public int x;
    public int y;

    public koordynaty(int y, int x){
        this.y = y;
        this.x = x;
    }
    public koordynaty(koordynaty kord){
        this.y = kord.y;
        this.x = kord.x;
    }
}
