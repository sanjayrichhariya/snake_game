import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Random;
import java.util.Arrays;
public class SnakePanel extends JPanel implements ActionListener {
    //Fixed Size of the Canvas and Unit
    static final int height=600;
    static final int width=1200;
    static final int unit_size=50;

    //Random variable for random food spawns
    Random random;

    //Coordinates of the food
    int foodX;
    int foodY;

    boolean game_flag=false;
    char dir='R';
    int body=3;
    int foodeaten;

    static final int size=(width*height)/(unit_size*unit_size);

    final int x_snake[]=new int[size];
    final int y_snake[]=new int[size];

    Timer timer;
    static final int DELAY=160;

    SnakePanel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.addKeyListener(new MyKey());
        this.setFocusable(true);
        random =new Random();
        Game_Start();
    }

    public void Game_Start(){
        spawnfood();
        game_flag=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }

    public void spawnfood(){
        //determining the random x and y coordinate of the food
        foodX=random.nextInt((int)(width/unit_size))*unit_size;
        foodY=random.nextInt((int)(height/unit_size))*unit_size;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent((graphic));
        draw(graphic);
    }

    public void draw(Graphics graphics){
        if(game_flag){
            //setting the graphics for the food block
            graphics.setColor(Color.red);
            graphics.fillOval(foodX,foodY,unit_size,unit_size);

            //setting the graphics for the snake body
            for(int i=0;i<body;i++){
                if(i==0){
                    graphics.setColor(Color.orange);
                    graphics.fillOval(x_snake[i],y_snake[i],unit_size,unit_size);
                }
                else{
                    graphics.setColor(Color.green);
                    graphics.fillOval(x_snake[i],y_snake[i],unit_size,unit_size);
                }
            }
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics font_me=getFontMetrics(graphics.getFont());
            graphics.drawString("Score: "+foodeaten,(width-font_me.stringWidth("Score: "+foodeaten))/2,graphics.getFont().getSize());
        }
        else{
            gameover(graphics);
        }
    }

    public void gameover(Graphics graphics){
        //to display the score
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics font_me=getFontMetrics(graphics.getFont());
        graphics.drawString("score: "+foodeaten,(width-font_me.stringWidth("score: "+foodeaten))/2,graphics.getFont().getSize());

        //display the game over text
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Comic Sans",Font.BOLD,80));
        FontMetrics font_me1=getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER",(width-font_me1.stringWidth("GAME OVER"))/2,height/2);

        //prompt to replay
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics font_me2=getFontMetrics(graphics.getFont());
        graphics.drawString("Press R to Replay",(width-font_me2.stringWidth("Press R to Replay"))/2,height/2-150);
    }

    public void move(){

        //updating the whole apart from its head
        for(int i=body;i>0;i--) {
            x_snake[i] = x_snake[i - 1];
            y_snake[i] = y_snake[i - 1];
        }
            switch(dir){
                case 'U':
                    y_snake[0]=y_snake[0]-unit_size;
                    break;
                case 'L':
                    x_snake[0]=x_snake[0]-unit_size;
                    break;
                case 'D':
                    y_snake[0]=y_snake[0]+unit_size;
                    break;
                case 'R':
                    x_snake[0]=x_snake[0]+unit_size;
                    break;
            }
        }

    public void checkhit(){
        //to check if snake hit itself or the wall

        for(int i=body;i>0;i--){
            if(x_snake[0]==x_snake[i]&&y_snake[0]==y_snake[i])
                game_flag=false;
        }
        if(x_snake[0]<0)
            game_flag=false;
        else if(x_snake[0]>width)
            game_flag=false;
        else if(y_snake[0]<0)
            game_flag=false;
        else if(y_snake[0]>height)
            game_flag=false;

        if(game_flag==false){
            timer.stop();
        }
    }

    public void eat (){
        if(x_snake[0]==foodX && y_snake[0]==foodY){
            body++;
            foodeaten++;
            spawnfood();
        }
    }
    public class MyKey extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(dir!='D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U') {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir!='R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L') {
                        dir = 'R';
                    }
                    break;
                    case KeyEvent.VK_R:
                        if(!game_flag){
                            foodeaten=0;
                            body=4;
                            dir='R';
                            Arrays.fill(x_snake,0);
                            Arrays.fill(y_snake,0);
                            Game_Start();
                        }
                        break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(game_flag){
            move();
            eat();
            checkhit();
        }
        repaint();
    }
}
