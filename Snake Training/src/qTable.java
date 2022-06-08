public class qTable {
    /*
    q table has 3^5 states, and 5 actions
    the state can be calculated with this formula
    current = C = 1, north = N = 3, south = S = 9, east = E = 27, west = W = 81
    P = property of the square where: empty = 0, can = 1, wall = 2
    state = C*P + N*P + S*P + E*P + W*P
    actions: 0 = pick up can, 1 = move north, 2 = move south, 3 = move east, 4 = move west
    */
    static double eta = .2;
    static double gamma = .9;
    //this declaration has been commented out so the qTable can be changed to be more universal
    //the dimensions commented out are set for local sensors
    static double[][] table;// = new double[243][5];
    qTable(){
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
