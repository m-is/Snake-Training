import java.util.HashMap;
import java.util.Hashtable;

public class QGlobalSnake extends snake{
    static Hashtable<String,Double> table = new Hashtable<String,Double>();
    static int totalReward = 0;
    static double eta = .2;
    static double gamma = .9;
    QGlobalSnake(){
        super.initialize();
        totalReward = 0;
    }
    void initialize(){
        super.initialize();
        totalReward = 0;
    }
    boolean getAction(gameGrid grid){
        String state = getGlobalState(grid);
        int action = getAction(state);
        int reward;
        //int initDist = calcDist(grid,getX(),getY());
        switch(action){
            case 0:
                if(!move_forward()){
                    gameOver = true;
                }
                break;
            case 1:
                if(!turn_left()){
                    gameOver = true;
                }
                break;
            case 2:
                if(!turn_right()){
                    gameOver = true;
                }
                break;
            default:
                System.out.println("Action failure");
        }
        reward = grid.checkFood(getX(),getY())?10:(gameOver?-5:0);
        //reward += (initDist>calcDist(grid,getX(),getY())?1:-1);
        if(grid.checkFood(getX(),getY())) {
            body.add_segment();
            totalReward ++;
        }
        update(state,getGlobalState(grid),action,reward);
        return false;
    }

    private void update(String state, String newState, int action, int reward) {
        double oldValue = lookUp(state,action);
        double newValue = oldValue + eta*(reward + gamma*(maxSucsr(newState)) - oldValue);
        state += action;
        table.put(state,newValue);
    }

    private double maxSucsr(String newState) {
        return lookUp(newState,chooseBest(newState));
    }

    private int getAction(String state) {
        if(Math.random()<epsilon)
            return (int)(Math.random()*3);
        else
            return chooseBest(state);
    }

    private int chooseBest(String state) {
        int pick = (int)(Math.random()*3);
        for(int i=0;i<3;i++){
            if(lookUp(state,i)>lookUp(state,i)){
                pick = i;
            }
        }
        return pick;
    }

    private double lookUp(String state, int i) {
        state += i;
        double value = 0;
        if(table.get(state)!=null)
            value = table.get(state);
        return value;
    }
    private int calcDist(gameGrid grid, int x, int y){
        int foodX = x;
        int foodY = y;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(grid.grid[i][j]){
                    foodX = i;
                    foodY = j;
                }
            }
        }
        int dist = Math.abs(x-foodX) + Math.abs(y-foodY);
        return dist;
    }
    String getGlobalState(gameGrid grid){
        StringBuilder string = new StringBuilder();
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(i==getX()&&j==getY())
                    string.append('h');
                else
                    switch(getProperty(grid,i,j)){
                        case 0 -> string.append("e");
                        case 1 -> string.append("f");
                        case 2 -> string.append("s");
                    }
            }
        }
        return string.toString();
    }
    protected int getProperty(gameGrid grid, int x, int y){
        if(checkCollision(x,y))
            return 2;
        else if (x == -1||x == 10||y == -1||y == 10)
            return 2;
        else if (grid.grid[x][y])
            return 1;
        else
            return 0;
    }
    public void display(gameGrid grid){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                System.out.print("[");
                switch (getProperty(grid, i, j)) {
                    case 0 -> System.out.print(" ");
                    case 1 -> System.out.print("f");
                    case 2 -> System.out.print("s");
                }
                System.out.print("]");
            }
            System.out.println();
        }
        System.out.println();
    }
}
