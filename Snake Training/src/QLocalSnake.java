public class QLocalSnake extends snake{
    qTable table;
    static int totalReward = 0;

    QLocalSnake(){
        table = new qTable(3,243);
    }
    public void initialize(){
        super.initialize();
        totalReward = 0;
    }
    public boolean getAction(gameGrid grid){
        //returns a true if the snake moves over a piece of food
        //returns false if not
        int state = getState(grid);
        int action = chooseAction(state);
        int reward = 0;
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
        //updates the table with proper rewards
        reward = grid.checkFood(getX(),getY())?10:(gameOver?-5:0);
        qTable.update(state,getState(grid),action,reward);
        totalReward += reward;
        //this clause checks if the snake has run over food, if it has we need to add a segment
        //the segment gets added, but it won't actually be created on the grid until the snake makes another movement
        //this is intended, this is how it functions in the original Snake game
        return false;
    }

    protected int getState(gameGrid grid){
        int state = 0;
        //checks each square in the five square detection area
        //current square
        state += getProperty(grid,getX(),getY());
        //up square
        state += 3*getProperty(grid,getX(),getY()+1);
        //down square
        state += 9*getProperty(grid,getX(),getY()-1);
        //right square
        state += 27*getProperty(grid,getX()+1,getY());
        //left square
        state += 81*getProperty(grid,getX()-1,getY());
        return state;
    }
    protected int chooseAction(int state){
        if(Math.random()>(1-epsilon))
            return (int)(Math.random()*3);
        else
            return chooseBest(state);
    }

    private int chooseBest(int state) {
        int pick = (int)(Math.random()*3);
        for(int i=0;i<3;i++){
            if(qTable.lookUp(state,i)>qTable.lookUp(state,pick)){
                pick = i;
            }
        }
        return pick;
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
