public class qTable {
    /*
    q table has 3^5 states, and 5 actions
    the state can be calculated with this formula
    current = C = 1, north = N = 3, south = S = 9, east = E = 27, west = W = 81
    P = property of the square where: empty = 0, food = 1, wall/body = 2
    state = C*P + N*P + S*P + E*P + W*P
    only 3 actions are possible, move forward, turn left, turn right
    */
    static double eta = .2;
    static double gamma = .9;
    //this declaration has been commented out so the qTable can be changed to be more universal
    //the dimensions commented out are set for local sensors
    static double[][] table;// = new double[243][5];
    qTable(){
    }
    qTable(int x, int y){
        table = new double[x][y];
    }

    public static double lookUp(int state,int action) {
        return table[state][action];
    }

    public static void update(int state, int newState, int action, int reward) {
        double oldValue = qTable.lookUp(state,action);
        double value = oldValue + eta*(reward + gamma*(qTable.maxSucsr(newState)) - oldValue);
        table[state][action] = value;
    }

    private static double maxSucsr(int newState) {
        double max = qTable.lookUp(newState,0);
        for(int i=0;i<5;i++){
            if(qTable.lookUp(newState,i)>max)
                max = qTable.lookUp(newState,i);
        }
        return max;
    }
}
