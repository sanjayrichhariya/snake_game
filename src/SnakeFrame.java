import javax.swing.*;

public class SnakeFrame extends JFrame {
    SnakeFrame(){
        this.add(new SnakePanel());
        this.setTitle("Snake");
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }
}
