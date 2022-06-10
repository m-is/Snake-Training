public class GAPop {
    public GASnake [] population;
    public int popScores[];
    int popSize;
    GAPop(){
        popSize = 10;
        population = new GASnake [popSize];
        popScores = new int [popSize];
        for(int i = 0; i < popSize; ++i){
            population[i] = new GASnake();
        }
    }
}
