import java.util.Arrays;

public class GAPop {
    public GASnake [] population;
    public int popScores[];

    double mutation_rate = 0.05;
    int popSize;
    int genMax = 50;
    int [] bestScores = new int [genMax];

    GAPop(gameGrid gridIn){

        popSize = 10;
        population = new GASnake [popSize];
        popScores = new int [popSize];
        for(int i = 0; i < popSize; ++i){
            population[i] = new GASnake();
            population[i].initialize();
        }
    }

    public void playSnake(){


        for(int gen = 0; gen < genMax; ++gen){
            int [] genScores = new int [popSize];   //Stores the scores of the population.

            for(int pop = 0; pop < popSize; ++pop){
                gameGrid grid = new gameGrid(); //Each pop needs a grid to play on.

                while(!population[pop].gameOver){
                    grid.grid[population[pop].getX()][population[pop].getY()] = population[pop].getAction(grid);
                    if(grid.empty()){
                        grid.addFood();
                    }
                }
                genScores[pop] = population[pop].score;
            }
            //Currently paused
             population = evolve(population, genScores);          
        }
    }
    //Lots of work to go
    public GASnake [] evolve(GASnake [] population, int [] popScores){
        GASnake [] newPop = new GASnake[popSize];
        int s1 =0, s2 =0, s3 =0, s4 =0; //find location for top 4 "parents" to create bebies with.
        

        return newPop;
    }
}
