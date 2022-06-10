import java.util.Arrays;

public class GAPop {
    public GASnake [] population;
    public int popScores[];

    double mutation_rate = 0.05;
    int popSize;
    int genMax = 50;
    int [] bestScores = new int [genMax];

    GAPop(){
        popSize = 1000; //Must be an even number. 
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
                int sanityCount = 0;
                while(!population[pop].gameOver){
                    if(sanityCount > 2000){
                        break;
                    }
                    //System.out.println("Test");
                    grid.grid[population[pop].getX()][population[pop].getY()] = population[pop].getAction(grid);
                    if(grid.empty()){
                        //System.out.println("Test2 L 33");
                        grid.addFood();
                    }
                    sanityCount += 1;
                }

                genScores[pop] = population[pop].score;
            }
             population = evolve(population, genScores, gen);          
        }
        displayScores();
    }

    public void displayScores(){
        for(int i =0; i<genMax; ++i){
            System.out.println(bestScores[i]);
        }
    }

    public GASnake [] evolve(GASnake [] population, int [] popScores, int generation){
        GASnake [] newPop = new GASnake[popSize];
        //GASnake [] parents = new GASnake[4];
        double [][] pWeights = new double [4][50];
        int [] parentsVal = new int [4];
        int[] parentsLoc = new int [4]; //find location for top 4 "parents" to create bebies with.
        int loc = 0;
        int val = -1; //This is compared to score.
        //Selecting the best 4 snakes for reproduction
        for(int i =0; i<4; ++i){
            val =0;
            loc = 0;
            for(int j = 0; j < popSize; ++j){
                if(population[j].score > val){
                    loc = j;
                    val = population[j].score;
                }
            }
            parentsLoc[i] = loc;                    
            parentsVal[i] = population[loc].score;  //Need to save this for later
            //Below is resetting for next round.
            population[loc].score =0;//Otherwise the parent list would be the same snek
            loc =0;
            val = -1;
            pWeights[i] = population[loc].getWeights(population[loc]);

            int maxScore = 0;
            for(int j = 0; j < 4; ++j){
                if(parentsVal[j] > maxScore){
                    maxScore = parentsVal[j];
                }
            }
            bestScores[generation] = maxScore;
        }


        double [][] newGenWeights = getOffSpring(pWeights);
        for(int i = 0; i < popSize; ++i){
            newPop[i] = new GASnake(newGenWeights[i]);
            //System.out.println("New Gen Weights L 92");
        }
        return newPop;
    }

    private double[][] getOffSpring(double[][] pWeights) {
        double [] p1 = pWeights[0];
        double [] p2 = pWeights[1];
        double [] p3 = pWeights[2];
        double [] p4 = pWeights[3];

        double [][] newPopWeights = new double[popSize][50];
        for(int i = 0; i < popSize; i +=2){ //If popSize is not even, this will break
            newPopWeights[i] = crossover(p1, p2);
            newPopWeights[i+1] = crossover(p3, p4);
        }
        return newPopWeights;
    }
    private double [] crossover(double [] p1, double [] p2){
        //Just returns 1 kid.
        int xPoint = (int)Math.random() * 48 + 1;
        double [] temp = new double [50];
        for(int i = xPoint; i < 50; ++i){
            temp[i] = p1[i];
            p1[i] = p2[i];
        }
        if(Math.random() < mutation_rate){
            return mutate(temp);
        }

        return temp;
    }

    private double[] mutate(double[] temp) {
        int loc = (int)Math.random()*50 + 1;
        temp[loc] = Math.random()-0.5;
        return temp;
    }
}
