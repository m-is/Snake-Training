import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        gameGrid grid = new gameGrid();
        /*
        Some variables for episodes/epochs/steps/reward tracking
        Feel free to comment them back in to use them for testing
        int episodes = 5000;
        int steps = 200;
        int[] TrainingReward = new int[50];
        int[] TrainedReward = new int[episodes];
         */
        //once you create a snake, initialize it, then pass it into the initialize function for the grid ie: grid.initialize(agent#);
        /*
        QLocalSnake agent1 = new QLocalSnake();
        GASnake agent2 = new GASnake();
        QGlobalSnake agent3 = new QGlobalSnake();
        */
        GASnake agent2 = new GASnake();  
        boolean huh = agent2.getAction(grid);
        System.out.println(huh);
        localReinforcement(grid);
    }
    public static void localReinforcement(gameGrid grid){
        QLocalSnake snk = new QLocalSnake();
        int episodes = 100000;
        int training = 2000;
        int[] data = new int[50];
        for(int i=0;i<episodes;i++){
            snk.initialize();
            while(!snk.gameOver){
                grid.grid[snk.getX()][snk.getY()] = snk.getAction(grid);
                if(grid.empty()){
                    grid.addFood();
                }
            }
            if(i%2000==0){
                data[i/2000] = snk.totalReward;
            }
        }
        exportData(data,"qlocalRewards");
    }
    //these are some utility functions that will be useful later, but you don't need to worry about them
    private static double calculateStdDev(double avg, int[] trainedReward) {
        double sum = 0;
        for(int i=0;i< trainedReward.length;i++){
            sum += (trainedReward[i] - avg)*(trainedReward[i]-avg);
        }
        sum = sum/5000;
        return(Math.sqrt(sum));
    }

    private static double calculateAverage(int[] trainedReward) {
        double sum = 0;
        for(int i=0;i< trainedReward.length;i++){
            sum += trainedReward[i];
        }
        return(sum/5000);
    }
    private static void exportData(int[] rewardPlot, String filename){
        File file = new File(filename+".txt");
        file.delete();
        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(filename+".txt");
            StringBuilder string = new StringBuilder();
            for(int i=0;i<rewardPlot.length;i++){
                string.append(rewardPlot[i]);
                string.append("\n");
                out.write(string.toString());
                string.setLength(0);
            }
            out.close();
        }
        catch(Exception e) {
            System.out.println("File output failure");
            return;
        }
    }

    public static void initialize(boolean[][] grid){
        for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                if(Math.random()>.5)
                    grid[i][j] = true;
                else
                    grid[i][j] = false;
            }
        }
    }
}