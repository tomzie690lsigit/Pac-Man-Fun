import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Game extends JPanel implements ActionListener {
    private pacmanObject pac;
    private final Maze maze = new Maze();
    private redGhost rgg;
    private blueGhost bgg;
    private greenGhost ggg;
    private pinkGhost pgg;
    public int pac_lives = 3;
    int initpac_X = 170;
    int initpac_Y = 290;
    int initred_x = 2;
    int initred_y = 2;
    int initblue_x = 338;
    int iniitblue_y = 2;
    int initgreen_x = 2;
    int initgreen_y = 362;
    int initpinkx = 338;
    int initpinky = 362;
    int homex = 170;
    int homey = 266;
    int homeiix =194;
    int homejojox = 146;
    int homerenx = 218;
    int score,noofdots;
    boolean gamelost, gamestarted,ingame,frightened,gamewon,leveldone;
    boolean ghome,rhome,bhome,phome;
    boolean done = true;
    boolean gettttt = true;
    boolean highscore = false;
    long frighetenedtimer = 0;

    public Game() {
        initializeView();
    }

    private void initializeView() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        gamelost = false;
        gamestarted = false;
        gamewon = false;
        ingame = true;
        pac = new pacmanObject(initpac_X, initpac_Y);
        rgg = new redGhost(initred_x, initred_y);
        bgg = new blueGhost(initblue_x, iniitblue_y);
        ggg = new greenGhost(initgreen_x, initgreen_y);
        pgg = new pinkGhost(initpinkx,initpinky);

        int DELAY = 10;
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    private void updatePacMan() {

        if (gamestarted) {
            if (pac.isVisible()) {
                score=pac.getScore();
                noofdots=pac.getDots();
                pac.movePac(maze);
            }
        }

    }

    private void updateGhost() {

        if (pac.eaten && !frightened){
            frighetenedtimer = System.currentTimeMillis();
            frightened = true;
        }
        if (gamestarted) {
            if (frightened && (frighetenedtimer + 6000 <= System.currentTimeMillis())){
                frightened=false;
                pac.eaten=false;
                ghome=rhome =bhome=phome = false;
            }
            if (rgg.isVisible()) {
               rgg.moveGhost(maze,pac,frightened,rhome);
            }
            if (bgg.isVisible()) {
               bgg.moveGhost(maze, pac,frightened,bhome);
            }
            if (ggg.isVisible()) {
                ggg.moveGhost(maze, pac,frightened,ghome);
            }
            if (pgg.isVisible()){
               pgg.moveGhost(maze,pac,frightened,phome);
            }
        }

    }

    private void highscores() {

        try {
            Integer[] scores = getHighScores(); //Populate array from file

            for (int i = 0; i < 5; i++) {

                Arrays.sort(scores); //sort array in ascending order so that lowest high-score is replaced first

                if (score > scores[i]) {
                    highscore = true;
                    scores[i] = score;
                    break;
                }
            }

            Arrays.sort(scores, Collections.reverseOrder()); // rearrange scores by highest-first

            FileWriter out = new FileWriter("highscores.txt");
            for (int highsc : scores) {
                out.write("\n" + highsc);
            }
            out.close();

        } catch (Exception n) {
            File highScores = new File("highscores.txt");

            try {

                FileWriter writer = new FileWriter(highScores);

                writer.write("\n" + score);

                writer.close();

            } catch (IOException ioException) {

                ioException.printStackTrace();
            }
        }

    }

    private Integer[] getHighScores() throws FileNotFoundException {

        File myObj = new File("highscores.txt");
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        Integer[] data = new Integer[5];
        Arrays.fill(data, 0);

        for (int i = 0; myReader.hasNext(); i++) {
            data[i] = Integer.parseInt(myReader.next());
        }

        myReader.close();

        return data;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (ingame){
            try {
                menuScreen(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(gamestarted){
            drawObjects(g);
        }
        else if (gamelost){
            try {
                menuScreen(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void drawObjects(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        maze.drawMaze(g2d);

        if (pac.isVisible()) {
            g.drawImage(pac.getImage(), pac.getX(), pac.getY(), this);
        }

        if (rgg.isVisible()) {
            g.drawImage(rgg.getImage(), rgg.getX(), rgg.getY(), this);
        }

        if (bgg.isVisible()) {
            g.drawImage(bgg.getImage(), bgg.getX(), bgg.getY(), this);
        }

        if (ggg.isVisible()){
            g.drawImage(ggg.getImage(),ggg.getX(),ggg.getY(),this);
        }

        if (pgg.isVisible()){
            g.drawImage(pgg.getImage(),pgg.getX(),pgg.getY(),this);
        }

        if (pac.getDots() == 0){
            ingame =false;
            leveldone = true;
        }
        g2d.setColor(Color.WHITE);
        g2d.drawString("LIVES : " + pac_lives,300,400 );

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updatePacMan();
        updateGhost();
        checkCollision();
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            pac.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                ingame = false;
                gamestarted = true;
            }
            if (gamestarted) {
                pac.keyPressed(e);
            }
        }

    }

    public void checkCollision(){

        Rectangle pacboundaries = pac.getBounds();
        Rectangle redghostboundaries = rgg.getBounds();
        Rectangle blueghostboundaries = bgg.getBounds();
        Rectangle greenghostboundaries =  ggg.getBounds();
        Rectangle pinkghostboundaries = pgg.getBounds();

        if (pacboundaries.intersects(pinkghostboundaries)||pacboundaries.intersects(redghostboundaries) || pacboundaries.intersects(blueghostboundaries) || pacboundaries.intersects(greenghostboundaries)) {
            if (!frightened) {
                if (pac_lives > 0) {
                    pac.setVisible(true);
                    rgg.setVisible(false);
                    bgg.setVisible(false);
                    ggg.setVisible(false);
                    rgg = new redGhost(initred_x, initred_y);
                    bgg = new blueGhost(initblue_x, iniitblue_y);
                    ggg = new greenGhost(initgreen_x, initgreen_y);
                    pgg = new pinkGhost(initpinkx, initpinky);
                    pac = new pacmanObject(initpac_X, initpac_Y);
                    pac_lives--;
                } else {
                    gamelost = true;
                    gamestarted = false;
                    pac.setVisible(false);
                    bgg.setVisible(false);
                    rgg.setVisible(false);
                    ggg.setVisible(false);
                    pgg.setVisible(false);
                }
            } else {
                if (pacboundaries.intersects(blueghostboundaries)) {
                    bgg.setVisible(false);
                    maze.score += 10;
                    bhome =true;
                    bgg = new blueGhost(homex, homey);
                } else if (pacboundaries.intersects(redghostboundaries)) {
                    rgg.setVisible(false);
                    maze.score += 10;
                    rhome =true;
                    rgg = new redGhost(homeiix, homey);
                } else if (pacboundaries.intersects(greenghostboundaries)) {
                    ggg.setVisible(false);
                    maze.score += 10;
                    ghome = true;
                    ggg = new greenGhost(homejojox, homey);

                } else if (pacboundaries.intersects(pinkghostboundaries)) {
                    pgg.setVisible(false);
                    maze.score += 10;
                    phome =true;
                    pgg = new pinkGhost(homerenx, homey);

                }
            }
        }

    }

    public void menuScreen(Graphics g) throws IOException {

        if ((gamewon)){

            int b_WIDTH = 375;
            int b_HEIGHT = 350;
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics fm = getFontMetrics(small);
            g.setColor(Color.MAGENTA);
            g.drawString("Congratulations, You've won", 140, 20);
            g.drawString("Your Score: " + pac.getScore(),140,40);
            String msg3 = "HIGH SCORES";

            g.drawString(msg3, (b_WIDTH - fm.stringWidth(msg3)) / 2, (b_HEIGHT / 10) + 100);

            if (done) {
                highscores();
                done = false;
            }

            Integer[] high = getHighScores();

            for (int i = 0; i < high.length; i++) { //display list of high-scores
                String msg4 = Integer.toString(high[i]);

                g.drawString(msg4, (b_WIDTH - fm.stringWidth(msg4)) / 2, ((b_HEIGHT / 10) + 150) + 20 * i);

            }
        }

        if (gamelost) {

            int b_WIDTH = 375;
            int b_HEIGHT = 350;
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics fm = getFontMetrics(small);
            g.setColor(Color.MAGENTA);
            g.drawString("Game Over", 140, 20);
            g.drawString("Your Score: " + maze.score,140,40);
            String msg3 = "HIGH SCORES";

            g.drawString(msg3, (b_WIDTH - fm.stringWidth(msg3)) / 2, (b_HEIGHT / 10) + 100);

            if (done) {
                highscores();
                done = false;
            }

            Integer[] high = getHighScores();

            for (int i = 0; i < high.length; i++) { //display list of high-scores
                String msg4 = Integer.toString(high[i]);

                g.drawString(msg4, (b_WIDTH - fm.stringWidth(msg4)) / 2, ((b_HEIGHT / 10) + 150) + 20 * i);

            }
        }

        if (ingame){
            g.setColor(Color.YELLOW);
            g.drawString("Press Enter to Begin", 130, 180);
        }

    }
}

