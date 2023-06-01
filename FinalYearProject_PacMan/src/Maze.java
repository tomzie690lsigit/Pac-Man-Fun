import java.awt.*;

public class Maze {
    public final int t = 2;
    public final int b = 8;
    public final int l= 1;
    public final int r = 4;
    public final int p = 16;
    public final int pp = 32;
    public int score = 0;

    //numbers chosen as they do not add up to themselves and represent bits

    public final int BLOCK_SIZE = 24;
    public final int BLOCKS_HIGH = 16;
    public final int BLOCKS_WIDE = 15;
    public int noofpellets = 3;


    public final int HEIGHT = BLOCK_SIZE*BLOCKS_HIGH;
    public final int WIDTH = BLOCK_SIZE*BLOCKS_WIDE;

    public short[][] screenData = {
            {t+l+p,t+b+p,t+b+p,t+b+p,t+p,t+b+p,t+r+p,0,t+l+p,t+b+p,t+p,t+b+p,t+b+p,t+b+p,t+r+p},
            {l+r+pp,0,0,0,l+r+p,0,l+r+p,0,l+r+p,0,l+r+p,0,0,0,r+l+pp},
            {l+r+p,0,0,0,l+r+p,0,l+r+p,0,l+r+p,0,l+r+p,0,0,0,r+l+p},
            {l+p,t+b+p,t+b+p,t+b+p,p,t+b+p,p,t+b+p,p,t+b+p,p,t+b+p,t+b+p,t+b+p,r+p},
            {l+r+p,0,0,0,l+r+p,0,l+r+p,0,l+r+p,0,l+r+p,0,0,0,r+l+p},
            {l+b+p,t+b+p,t+b+p,t+b+p,r+p,0,l+b+p,t+p,r+b+p,0,l+p,t+b+p,t+b+p,t+b+p,r+b+p},
            {0,0,0,0,l+r+p,0,0,l+r+p,0,0,l+r+p,0,0,0,0},
            {t+b,t+b,t+b,t+b,r+p,0,t+l,b,t+r,0,l+p,t+b,t+b,t+b,t+b},
            {0,0,0,0,l+r+p,0,l+r,0,r+l,0,l+r+p,0,0,0,0},
            {t+l+p,t+b+p,t+b+p,t+b+p,p,t+b,b,t+b,b,t+b,p,t+b+p,t+b+p,t+b+p,r+t+p},
            {l+r+p,0,0,0,l+r+p,0,0,0,0,0,l+r+p,0,0,0,r+l+p},
            {l+b+p,t+r+p,0,0,l+r+p,0,0,0,0,0,l+r+p,0,0,t+l+p,r+b+p},
            {0,l+p,t+b+p,t+b+p,p,t+b,t+b,t,t+b,t+b,p,t+b+p,t+b+p,r+p,0},
            {l+t+p,r+b+p,0,0,l+r+p,0,0,l+r+p,0,0,l+r+p,0,0,l+b+p,r+t+p},
            {l+r+pp,0,0,0,l+r+p,0,0,l+r+p,0,0,l+r+p,0,0,0,r+l+pp},
            {b+l+p,b+t+p,b+t+p,b+t+p,b+p,b+t+p,b+t+p,b+p,b+t+p,b+t+p,b+p,b+t+p,b+t+p,b+t+p,b+r+p}
    };



    public void drawMaze(Graphics2D g2d){

        Font small = new Font("Helvetica", Font.BOLD, 14);
        g2d.setFont(small);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score : " + score,0,400);


        int i = 0;
        int j =0;
        int x,y;

        for(y=0;y<HEIGHT; y+= BLOCK_SIZE){
            for (x=0;x<WIDTH;x+=BLOCK_SIZE){
                g2d.setColor(Color.blue);
                g2d.setStroke(new BasicStroke(2));

                if((this.screenData[i][j] & 1) != 0){
                    g2d.drawLine(x,y,x,y+BLOCK_SIZE-1);

                }
                if((this.screenData[i][j] & 2) != 0){
                    g2d.drawLine(x,y,x+BLOCK_SIZE-1,y);
                }
                if((this.screenData[i][j] & 4) != 0){
                    g2d.drawLine(x + BLOCK_SIZE-1 ,y,x + BLOCK_SIZE-1 ,y+BLOCK_SIZE-1 );

                }
                if((this.screenData[i][j] & 8) != 0){
                    g2d.drawLine(x,y + BLOCK_SIZE-1 ,x + BLOCK_SIZE-1,y+BLOCK_SIZE-1);

                }
                if((this.screenData[i][j] & 16) != 0){
                    g2d.setColor(Color.orange);
                    g2d.fillRect(x+11,y+11,2,2);

                }
                if((this.screenData[i][j] & 32) != 0){
                    g2d.setColor(Color.white);
                    g2d.fillRect(x+8,y+8,6,6);

                }
                j++;
            }
            j=0;
            i++;


        }



    }


}
