public class GASnake extends snake{
    int score = -1; //init check can be done on this.
    double mutation_rate = 0.05;
    public double [][] i1_h1 = new double [7][5];     //Weights for Input -> Hidden Layer1
    public double [][] h1_o1 = new double [5][3];     //Weights for Hidden Layer1  to output
       
    GASnake(){
        //For some reason these loops need to occur here not in init.
        initialize();
        for(int i = 0; i < 7; ++i){
            for(int j = 0; j < 5; ++j){
                i1_h1[i][j] = Math.random()-0.5;
            }
        }
        for(int l = 0; l < 5; ++l){
            for(int k = 0; k < 3; ++k){
                h1_o1[l][k] = Math.random()-0.5;
            }
        }    
    }
    public void initialize(){
        super.initialize();
        score = 0;
    }

    public boolean getAction(gameGrid grid){
        //God help me this is the Matrix multiplication bit.
        int [] inputs = getGAState(grid);
        double [] hidden = new double [5];
        double [] out = new double [3];
        for(int i = 0; i< 7; ++i){
            for(int j = 0; j < 5; ++j){
                hidden[j] += inputs[i]*i1_h1[i][j];
            }
        }
        for(int l = 0; l < 5; ++l){
            for(int k = 0; k < 3; ++k){
                out[k] += hidden[l]*h1_o1[l][k];
            }
        }

        for(int test = 0; test < 3; ++test){
            System.out.println(out[test]);
        }

        return false;
    }

    protected int [] getGAState(gameGrid grid){
        int [] catcher = grid.locateFood();
        int [] input = new int [7];
        input[0] = catcher[0];  // Food X
        input[1] = catcher[1];  // Food Y
        input[2] = this.getX(); // Body X
        input[3] = this.getY(); // Body Y
        int [] sensors = GAcheats(input[2], input[3], orientation, grid); // Could be placed better.
        input[4] = sensors[0]; //What is the collision for in front 
        input[5] = sensors[1]; //What is the collision to the left 
        input[6] = sensors[2]; //What is the collision to the right

        
        return input;
    }

    public int [] GAcheats(int x, int y, int orientation, gameGrid grid){
        // x / y is snake body location.
        int [] toReturn = new int [3];  //0 = front, 1 = left, 2 = right
        switch(orientation){
            case 0: // Front == north
                toReturn[0] = getProperty(grid, x, y+1); 
                toReturn[1] = getProperty(grid, x-1, y);
                toReturn[2] = getProperty(grid, x+1, y);
            case 1: // Front == east
                toReturn[0] = getProperty(grid, x+1, y); 
                toReturn[1] = getProperty(grid, x, y+1);
                toReturn[2] = getProperty(grid, x, y-1);
            case 2: // Front == south
                toReturn[0] = getProperty(grid, x, y-1); 
                toReturn[1] = getProperty(grid, x+1, y);
                toReturn[2] = getProperty(grid, x-1, y);
            case 3: // Front == west
                toReturn[0] = getProperty(grid, x-1, y); 
                toReturn[1] = getProperty(grid, x, y-1);
                toReturn[2] = getProperty(grid, x, y+1);
           default:
                System.out.println("Something is wrong in GA Cheats");
        return toReturn;
        }
        
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
    //Used to create a 1d array out of the weights for a snake.
    public double [] getWeights(GASnake snek){
        double [] toReturn = new double [50]; // 7x5 + 5x3 matricies
        int count = 0;
        for(int i = 0; i < 7; ++i){
            for(int j = 0; j < 5; ++j){
                toReturn[count] = snek.i1_h1[i][j];
                count += 1;
            }
        }
        for(int l = 0; l < 5; ++l){
            for(int k = 0; k < 3; ++k){
                toReturn[count] = snek.h1_o1[l][k];
                count += 1;
            }
        }
        return toReturn;
    }
    //Sanity checks. Catch the getweights and toss in here
    public void showWeights(double [] weights){
        int l = weights.length;
        for(int i = 0; i < l; ++i){{
            System.out.println(weights[i]);
        }}
    }
  

}
