public class gameGrid {
    boolean[][] grid = new boolean[10][10];
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
        int x,y;
        while(!placement){
            x = (int)(Math.random()*10);
            y = (int)(Math.random()*10);
            if(!snake.body.checkCollision(x,y)){
                placement = true;
            }
        }
    }
}
