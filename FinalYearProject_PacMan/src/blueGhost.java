import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class blueGhost extends Object{

    public blueGhost(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        initializeBlueGhost();
    }

    private void initializeBlueGhost(){
        //gets image and sets the borders of the image
        loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\Blue_ghost.png");
        getImageDimensions();
    }



    int x,y,dx,dy;
    short ch = 0, ch1 = 0;
    short boxg = 0;
    Maze corn;
    pacmanObject gpac;
    long start = 0;
    int leftborderx, rightborderx;
    int topbordery, bottombordery;
    boolean frightened,bhome;

    public void moveGhost(Maze m, pacmanObject pp,boolean fright,boolean home) {
        corn = m;
        this.gpac = pp;

        if (fright){
            //when frightened turns ghost white
            loadImage("C:\\Users\\tomzi\\OneDrive\\Desktop\\PAC_MAN_MINE\\FinalYearProject_PacMan\\src\\Images\\White_ghost.png");
            frightened = true;
        }else {
            initializeBlueGhost();
            frightened =false;

        }

        bhome =home;


        //find which block ghost is in
        //make sure ghost does not go out of bounds
        leftborderx = x / m.BLOCK_SIZE;
        rightborderx = (x + 20) / m.BLOCK_SIZE;
        topbordery = y / m.BLOCK_SIZE;
        bottombordery = (y + 20) / m.BLOCK_SIZE;


        gMove();
        glocomote();

        //make sure ghost is inside maze
        if (topbordery < 16 && leftborderx < 15) ch = m.screenData[topbordery][leftborderx];
        if (bottombordery < 16 && rightborderx < 15) ch1 = m.screenData[bottombordery][rightborderx];


        //if wall, no go
        if (((boxg & 1) != 0) && (dx == -1)
                || ((boxg & 4) != 0) && (dx == 1)
                || ((boxg & 2) != 0) && (dy == -1)
                || ((boxg & 8) != 0) && (dy == 1)) {
            dx = dy = 0;

        }

        if (bhome){
            //take frihghtened ghost home
            if ((x == 170)){
                if (!frightened) {
                    if ((boxg & 8) == 0) {
                        dy = 1;
                    }
                }else{
                    dx=dy=0;
                }
            }
        }


        teleport();
        x += dx;
        y += dy;

    }



    public ArrayList<Integer> canTurn() {
        //generates a list of all possible turns the ghost can make at current ghost position

        ArrayList<Integer> possi = new ArrayList<>();

        if ((boxg & 1) == 0) {
            possi.add(1);
        }

        if ((boxg & 4) == 0) {
            possi.add(4);
        }

        if ((boxg & 2) == 0) {
            possi.add(2);
        }

        if ((boxg & 8) == 0) {
            possi.add(8);
        }

        return possi;
    }

    public boolean isSafeToTurn() {
        return (ch == ch1);
    }

    public void gMove(){
        //check if ghost is exactly within cell borders
        if (ch == ch1) {
            boxg = ch;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Rectangle getBounds(){return new Rectangle(getX(),getY(),width/2,height/2);}

    public void glocomote() {

        //ghost AI
        int diffx = rightborderx - gpac.rightborderx;
        int diffy = bottombordery - gpac.bottombordery;
        Random rand = new Random();
        if (!frightened) {
            //starts of not frightened
            if (isSafeToTurn()) {

                long time = System.currentTimeMillis();

                if (time > start + 200) {

                    if (diffx > diffy) {

                        dx = dy = 0;

                        if (gpac.bottombordery >= bottombordery) {
                            if (gpac.bottombordery == bottombordery) {
                                if (gpac.rightborderx > rightborderx) {
                                    dx = 1;
                                } else if (gpac.rightborderx < rightborderx) {
                                    dx = -1;
                                }
                            }
                            else {
                                if ((boxg & 8) == 0) {
                                    dy = 1;
                                } else if ((boxg & 1) == 0) {
                                    dx = -1;
                                } else if ((boxg & 4) == 0) {
                                    dx = 1;
                                }
                            }
                        } else {
                            if (((boxg & 2) == 0)) {
                                dy = -1;
                            }
                        }
                    } else if (diffx <= diffy) {
                        dx = dy = 0;
                        if (gpac.rightborderx >= rightborderx) {
                            if (gpac.rightborderx == rightborderx) {
                                if (gpac.bottombordery > bottombordery) {
                                    dy = 1;
                                } else if (gpac.bottombordery < bottombordery) {
                                    dy = -1;
                                }
                            } else {
                                if ((boxg & 4) == 0) {
                                    dx = 1;
                                } else if ((boxg & 8) == 0) {
                                    dy = 1;
                                } else if ((boxg & 2) == 0) {
                                    dy = -1;
                                }
                            }
                        } else {
                            if ((boxg & 1) == 0) {
                                dx = -1;
                            }
                        }
                    }

                    start = time;
                }

            }
        } else {

            long time = System.currentTimeMillis();


            ArrayList<Integer> possi = canTurn();

            if (time > start + 2000) {

                if (isSafeToTurn()) {

                    dy = -1;
                    //random movement when ghost is away from pacman
                    if (Math.round(Math.abs(diffx)) > 3 || Math.round(Math.abs(diffy)) > 3) {

                        int dir = possi.get(rand.nextInt(possi.size()));
                        dx = dy = 0;

                        if (dir == 8) {
                            dy = 1;
                        }
                        if (dir == 2) {
                            dy = -1;
                        }
                        if (dir == 1) {
                            dx = -1;
                        }
                        if (dir == 4) {
                            dx = 1;
                        }

                    } else {
                        int best = 0;
                        double distance = 0;
                        //choosing best move that would increase the distance between pacman and ghost
                        for (int p : possi) {
                            int tempx, tempy;
                            tempx = tempy = 0;

                            if (p == 1) {
                                tempx = -1;
                            }
                            if (p == 2) {
                                tempy = -1;
                            }
                            if (p == 4) {
                                tempx = 1;
                            }
                            if (p == 8) {
                                tempy = 1;
                            }

                            int m1 = diffx + (24 * tempx);
                            int m2 = diffy + (24 * tempy);
                            double d2 = Math.sqrt((m1 * m1) + (m2 * m2));

                            if (d2 > distance) {
                                distance = d2;
                                best = p;
                            }
                        }

                        dx = dy = 0;

                        if (best == 1) {
                            dx = -1;
                        }
                        if (best == 2) {
                            dy = -1;
                        }
                        if (best == 4) {
                            dx = 1;
                        }
                        if (best == 8) {
                            dy = 1;
                        }
                    }
                    start = time;
                }
            }

        }
    }


    public void teleport(){
        //allow ghost to move between two ends of screen
        if(x<-3){
            x = 373;
        } else if(x>=373) {
            x = -3;
        }
    }



}

