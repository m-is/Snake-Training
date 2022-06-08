public class snake_segment {
    static int x, y;
    static snake_segment tail = null;
    snake_segment(){};
    snake_segment(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void move(int x, int y){
        if(tail!=null){
            tail.move(this.x,this.y);
        }
        this.x = x;
        this.y = y;
    }
    public void add_segment(){
        if(tail!=null){
            tail.add_segment();
        }
        else{
            tail = new snake_segment();
        }
    }

    public boolean checkCollision(int x, int y) {
    /*
        checks for collisions, this is used to check if the snake has collided with itself(game over)
        or when you are placing a new piece of food, you can check to make sure you aren't placing it within the snake's body
        */
        if((x == this.x)&&( y == this.y))
            return true;
        else if(tail==null)
            return false;
        else
            return tail.checkCollision(x,y);
    }
}
