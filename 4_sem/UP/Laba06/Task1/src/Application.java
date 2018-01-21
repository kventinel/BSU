import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Rak Alexey on 4/4/17.
 */

public class Application extends JFrame{
    private static final int PART_SIZE=100;
    private static final int X_SIZE=4;
    private static final int Y_SIZE=4;
    private static final int EMPTY=X_SIZE*Y_SIZE-1;
    private static final String IMAGE_NAME="img/image"+X_SIZE+"x"+Y_SIZE+".png";

    private final Image[] arr= new Image[X_SIZE*Y_SIZE];
    private final int[][] data=new int[X_SIZE][];
    public Application(){
        super("Task1");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage img= getScaledImage(new ImageIcon(IMAGE_NAME).getImage(),PART_SIZE*X_SIZE,PART_SIZE*Y_SIZE);
        for (int i=0;i<X_SIZE;i++) {
            data[i] = new int[Y_SIZE];
        }
        for(int i=0;i<X_SIZE;i++)
            for(int j=0;j<Y_SIZE;j++){
                data[i][j]=i*X_SIZE+j;
                arr[data[i][j]]=img.getSubimage(i*PART_SIZE,j*PART_SIZE,PART_SIZE,PART_SIZE);
            }
        MyPanel panel=new MyPanel();
        JButton button=new JButton("mix");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.mix();
            }
        });
        panel.mix();
        add(panel);
        add(button,BorderLayout.SOUTH);

        setSize(200+PART_SIZE*X_SIZE+20,200+PART_SIZE*Y_SIZE+70);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void checkComplete(){
        for(int i=0;i<X_SIZE;i++)
            for(int j=0;j<Y_SIZE;j++)
                if(data[i][j]!=i*X_SIZE+j) return;
        JOptionPane.showMessageDialog(this,"Finish");
    }
    private class MyPanel extends JPanel{
        MyPanel(){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x=e.getX();
                    int y=e.getY();
                    x/=PART_SIZE;
                    y/=PART_SIZE;
                    System.out.println("x: "+x+", y: "+y);
                    if(!isDot(x,0,X_SIZE-1) || !isDot(y,0,Y_SIZE-1)) return;
                    if(tryGo(x,y)){
                        MyPanel.this.repaint();
                        checkComplete();
                    }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    mouseClicked(e);
                }
            });
        }
        private boolean  tryGo(int x,int y){
            return
                    tryGoTo(x-1,y,x,y) ||
                            tryGoTo(x,y-1,x,y) ||
                            tryGoTo(x+1,y,x,y) ||
                            tryGoTo(x,y+1,x,y);
        }
        private boolean tryGoTo(int x, int y, int a, int b){
            if(!isDot(x,0,X_SIZE-1) || !isDot(y,0,Y_SIZE-1)) return false;
            if(data[x][y]!=EMPTY) return false;
            data[x][y]=data[a][b];
            data[a][b]=EMPTY;
            return true;
        }
        public void mix(){
            Random rnd=new Random();
            for(int i=0;i<1000;i++)
                tryGo(rnd.nextInt(X_SIZE),rnd.nextInt(Y_SIZE));
            MyPanel.this.repaint();
        }
        @Override
        public void paint(Graphics g){
            int xSize=this.getWidth();
            int ySize=this.getHeight();
            g.clearRect(0,0,xSize,ySize);
            for(int i=0;i<X_SIZE;i++)
                for(int j=0;j<Y_SIZE;j++)
                    g.drawImage(arr[data[i][j]],i*PART_SIZE,j*PART_SIZE,null);
        }
    }
    static private BufferedImage getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    static private BufferedImage cropImage(Image img, Rectangle rect) {
        BufferedImage src=makeBuffered(img);
        return src.getSubimage(0, 0, rect.width, rect.height);
    }
    static private BufferedImage makeBuffered(Image img){
        return getScaledImage(img,img.getHeight(null),img.getWidth(null));
    }
    static private boolean isDot(int x,int from,int to){
        return from<=x && x<=to;
    }
}