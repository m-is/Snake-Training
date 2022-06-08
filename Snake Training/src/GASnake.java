public class GASnake extends snake{
    //with local sensors there are 243 states 3^5 (one option for empty square, one for food, one for wall)
    //the snake's own body is considered a wall
    //not all of the states are possible obviously, but should still be mapped
    int[] policy = new int[243];
}
