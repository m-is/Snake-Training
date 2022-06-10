public class gameGrid {
    int food_x; //Used for GA
    int food_y; //Used for GA
    static boolean[][] grid = new boolean[10][10];
    gameGrid(){
        //sets all the grid squares to false on initialization
        for(int i = 0;i<10;i++){
            for(int j=0;j<10;j++){
                grid[i][j] = false;
            }
        }
    }
    void initialize(snake snk){
        addFood(snk);
    }
    void addFood(snake snk){
        //adds a piece of food to an unoccupied square
        boolean placement = false;
        int x = 0,y = 0;
        while(!placement){
            x = (int)(Math.random()*10);
            y = (int)(Math.random()*10);
            if(!snake.body.checkCollision(x,y)){
                placement = true;
            }
        }
        food_x = x;
        food_y = y;
        grid[x][y] = true;
    }
    boolean checkFood(int x, int y){
        return grid[x][y];
    }
    boolean empty(){
        boolean empty = true;
        for(int i=0;i < 10;i++){
            for(int j=0;j < 10;j++){
                if(grid[i][j])
                    empty = false;
            }
        }
        return empty;
    }

    int [] locateFood(){
        int [] location = new int [2];
        location[0] = food_x;
        location[1] = food_y;;
        return location;
    }
}
