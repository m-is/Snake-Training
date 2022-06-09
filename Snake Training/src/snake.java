import java.sql.SQLOutput;

public class snake {
    static snake_segment body = null;
    //orientation defines which way the snake is facing
    //0 = Up/North, 1 = Right/East, 2 = Down/South, 3 = Left/West
    int orientation = 2;
    double epsilon = .1;
    //this is a variable that checks if the snake has gameOver'd
    //depending on the model, this might just end the simulation, or it may return a negative reward
    //it does nothing in this class because of this variability
    boolean gameOver = false;
    snake(){
        initialize();
    }

    public int getX() {
        if(body!=null)
            return body.x;
        else
            return -1;
    }
    public int getY() {
        if(body!=null)
            return body.y;
        else
            return -1;
    }
    public void addSegment(){
        body.add_segment();
    }

    void initialize(){
        //the snake is initialized in the same location, facing downward, and with a length of 3
        //this is intended, this is how the snake functions in the actual game
        int x = 4;
        int y = 8;
        orientation = 2;
        body = new snake_segment(x,y);
        body.add_segment();
        move_forward();
        body.add_segment();
        move_forward();
    }
    boolean getAction(gameGrid grid){
    //returns a true if the snake moves over a piece of food
        //returns false if not
        int state = getState(grid);
        int action = chooseAction(state,grid);
        switch(action){
            //movement actions, will function as intended unless the snake runs into a wall or its own body
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
        //this clause checks if the snake has run over food, if it has we need to add a segment
        //the segment gets added, but it won't actually be created on the grid until the snake makes another movement
        //this is intended, this is how it functions in the original Snake game
        return grid.grid[body.x][body.y];
    }
    public boolean checkCollision(int x, int y){
        return body.checkCollision(x,y);
    }
    private boolean move_forward(){
        switch(orientation){
            case 0:
                return move_up();
            case 1:
                return move_right();
            case 2:
                return move_down();
            case 3:
                return move_left();
            default:
                System.out.println("Orientation has been compromised");
                return false;
        }
    }
    private boolean turn_left(){
        switch(orientation){
            case 0:
                orientation = 3;
                return move_left();
            case 1:
                orientation = 0;
                return move_up();
            case 2:
                orientation = 1;
                return move_right();
            case 3:
                orientation = 2;
                return move_down();
            default:
                System.out.println("Orientation has been compromised");
                return false;
        }
    }
    private boolean turn_right(){
        switch(orientation){
            case 0:
                orientation = 1;
                return move_right();
            case 1:
                orientation = 2;
                return move_down();
            case 2:
                orientation = 3;
                return move_left();
            case 3:
                orientation = 0;
                return move_up();
            default:
                System.out.println("Orientation has been compromised");
                return false;
        }
    }
    private boolean move_up(){
        if(body.y == 9){
            return false;
        }
        else{
            body.move(body.x,body.y+1);
            if(body.tail.checkCollision(body.x,body.y))
                return false;
        }
        return true;
    }
    private boolean move_right(){
        if(body.x == 9){
            return false;
        }
        else{
            body.move(body.x+1,body.y);
            if(body.tail.checkCollision(body.x,body.y))
                return false;
        }
        return true;
    }
    private boolean move_down(){
        if(body.y == 0){
            return false;
        }
        else{
            body.move(body.x,body.y-1);
            if(body.tail.checkCollision(body.x,body.y))
                return false;
        }
        return true;
    }
    private boolean move_left(){
        if(body.x == 0){
            return false;
        }
        else{
            body.move(body.x-1,body.y);
            if(body.tail.checkCollision(body.x,body.y))
                return false;
        }
        return true;
    }
    private int getState(gameGrid grid){
        //this should return the state determined by what model you are using
        return 0;
    }
    public void display(gameGrid grid){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                System.out.print("[");
                if(checkCollision(i,j))
                    System.out.print("s");
                else if(gameGrid.checkFood(i,j))
                    System.out.println("f");
                else
                    System.out.println(" ");
                System.out.println("]");
            }
        }
    }
    public static int chooseAction(int state, gameGrid grid) {
        //this should take a state and return an action
        return 0;
    }
    private static int chooseBest(int state, gameGrid grid){
        //this should take a state, return a best action if possible in the model
        return 0;
    }
    public void decrementEpsilon() {
        if(epsilon!=0)
            epsilon -= .0025;
    }
    public void resetEpsilon(){
        epsilon = .1;
    }
}
