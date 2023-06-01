import java.awt.*;
import java.awt.event.KeyEvent;

public class pacmanObject extends Object {

    public pacmanObject(int x, int y){
        super(x,y);
        this.x=x;
        this.y=y;
        initializePacmanObject();
    }

    private void initializePacmanObject(){
        loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacright.png");
        getImageDimensions();
    }

    int x,y,dx,dy,key;
    short ch = 0, ch1 = 0;
    short box = 0;
    Maze corn;
    int leftborderx;
    int rightborderx;
    int topbordery, bottombordery,score,noofdots;
    boolean eaten;
    int animdelay = 5;


    public void movePac(Maze m) {
        corn = m;


        //find which block pac is in
        //make sure pac does not go out of bounds
        leftborderx = x / m.BLOCK_SIZE;
        rightborderx = (x + 20) / m.BLOCK_SIZE;
        topbordery = y / m.BLOCK_SIZE;
        bottombordery = (y + 20) / m.BLOCK_SIZE;


        locomote();

        eatPellet(m, topbordery, leftborderx);
        eatpowerpellet(m, topbordery, leftborderx);
        animatePac();


        //make sure pacman is inside maze
        if (topbordery < 16 && leftborderx < 15) ch = m.screenData[topbordery][leftborderx];
        if (bottombordery < 16 && rightborderx < 15) ch1 = m.screenData[bottombordery][rightborderx];

        //if there is a wall, no go

        if (((box & 1) != 0) && (dx == -1)
                || ((box & 4) != 0) && (dx == 1)
                || ((box & 2) != 0) && (dy == -1)
                || ((box & 8) != 0) && (dy == 1)) {
            dx = dy = 0;
        }
        teleport();
        x += dx;
        y += dy;

    }

    public void eatPellet(Maze m,int topbordery,int leftborderx){
        if (ch == ch1) {
            box = ch;
            if ((ch & 16) != 0) { //eat pellet if present
                m.screenData[topbordery][leftborderx] =(short) (ch & 15);
                m.score++;
                m.noofpellets--;
                score = m.score;
                noofdots=m.noofpellets;
            }
        }
    }

    public void eatpowerpellet(Maze m,int topbordery,int leftborderx){
        if (ch == ch1) {
            if((ch & 32)!=0){//power pellet
                m.screenData[topbordery][leftborderx] = (short) (ch & 31);
                m.score++;
                m.noofpellets--;
                score=m.score;
                noofdots=m.noofpellets;
                eaten=true;
            }
        }
    }

    public boolean canTurn(int key) {
        boolean temp =false;

        if (key == KeyEvent.VK_UP){
            if((box & 2) == 0){
                temp =true;
            }
        }
        else if (key == KeyEvent.VK_DOWN){
           if ((box & 8) == 0){
               temp = true;
           }
        }
        else if (key == KeyEvent.VK_LEFT){
            if((box & 1) == 0){
                temp = true;
            }
        }
        else if (key == KeyEvent.VK_RIGHT){
            if((box & 4) == 0){
                temp = true;
            }
        }
        return temp;
    }

    public boolean isSafeToTurn() {
        return (ch == ch1) && canTurn(key);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Rectangle getBounds(){return new Rectangle(getX(),getY(),width/2,height/2);}

    public void locomote() {

        if (isSafeToTurn()) {
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_SPACE) {
                dx = dy = 0;//Resets every time it takes a key pressed input to prevent diagonal movement.
            }

            if (key == KeyEvent.VK_LEFT) {
                drawPacLeft();
                if ((box & 1) == 0) {
                    dx = -1;
                }
            }

            if (key == KeyEvent.VK_RIGHT) {
                drawPacRight();
                if ((box & 4) == 0) {
                    dx = 1;
                }
            }

            if (key == KeyEvent.VK_UP) {
                drawPacUP();
                if ((box & 2) == 0) {
                    dy = -1;
                }
            }

            if (key == KeyEvent.VK_DOWN) {
                drawPacDown();
                if ((box & 8) == 0) {
                    dy = 1;
                }
            }
        }
    }

    public void teleport(){
        //allow pacman move between two ends of screen
        if(x<-3){
            x = 373;
        } else if(x>=373) {
            x = -3;
        }
    }

    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e){}

    public int getScore(){
        return score;
    }

    public int getDots(){
        return noofdots;
    }

    public void animatePac(){
        animdelay--;
        if (animdelay <= 0){
            animdelay=5;
        }
    }


    //pacman image draw
    public void drawPacUP(){
        if (animdelay < 3) {
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacup.png");
        }
        else{
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacup11.png");
        }

    }

    public void drawPacLeft(){
        if (animdelay < 3) {
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacleft.png");
        }
        else{
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacleft11.png");
        }

    }

    public void drawPacRight(){
        if (animdelay < 3) {
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacright.png");
        }
        else{
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacright11.png");
        }
    }

    public void drawPacDown(){
        if (animdelay < 3) {
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacdown.png");
        }
        else{
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\pacdown11.png");
        }
    }

}
