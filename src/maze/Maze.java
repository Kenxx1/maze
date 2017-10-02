
package maze;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Maze extends JFrame implements Runnable {
    
    static final int WINDOW_WIDTH = 407;
    static final int WINDOW_HEIGHT = 465;
    
    final int TOP_BORDER = 40;
    final int YTITLE = 25;
    
    
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;
//Dimensions of the board
    final int numRows = 20;
    final int numColumns = 15;
//The types of blocks.
    final int PASS = 0;
    final int WALL = 1;
    final int SPAS= 3;
    final int NPC=4;
    final int OPEN=5;
    final int CLOS =6;
    final int PRT1 = 7;
    final int REC1 = 8;
    final int PRT2 = 9;
    final int REC2 = 10;
    final int BRD1 = 11;
    final int BRD2 = 12;
   
    
//    int board[][] = {   {WALL,PASS,PASS,PASS} ,
//                        {PASS,WALL,PASS,PASS} ,
//                        {PASS,PASS,PASS,PASS} ,
//                        {PASS,PASS,PASS,PASS}
//    };
    
    int boardInit[][] = new int[numRows][numColumns];
    int board[][] = {   {WALL,WALL,WALL,WALL,WALL,WALL,WALL,REC2,WALL,WALL,WALL,WALL,WALL,WALL,WALL} ,
                        {WALL,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,WALL,WALL,WALL,WALL} ,
                        {WALL,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL,WALL,PASS,WALL,WALL,WALL,WALL} ,
                        {WALL,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL,WALL,PASS,WALL,WALL,WALL,WALL} ,
                        {WALL,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL,WALL,PASS,PASS,PASS,PASS,WALL} ,
                        {WALL,PASS,PASS,PASS,WALL,WALL,WALL,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL} ,
                        {WALL,PASS,WALL,OPEN,SPAS,SPAS,SPAS,CLOS,WALL,WALL,PASS,PASS,PASS,PASS,WALL} ,
                        {WALL,PASS,WALL,PASS,WALL,WALL,WALL,PASS,PASS,PASS,PASS,PASS,PRT1,PASS,WALL} ,
                        {WALL,PASS,WALL,PASS,WALL,WALL,WALL,SPAS,WALL,WALL,PASS,PASS,PASS,PASS,WALL} ,
                        {WALL,PASS,PASS,PASS,PASS,WALL,WALL,SPAS,WALL,WALL,WALL,WALL,PASS,WALL,WALL} ,
                        {WALL,WALL,WALL,WALL,PASS,WALL,WALL,SPAS,WALL,WALL,WALL,WALL,PASS,WALL,WALL} ,
                        {WALL,WALL,WALL,WALL,PASS,WALL,WALL,SPAS,WALL,WALL,WALL,WALL,PASS,PASS,WALL} ,
                        {WALL,WALL,WALL,WALL,PASS,PASS,PASS,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL} ,
                        {WALL,PASS,PASS,PASS,WALL,WALL,WALL,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL} ,
                        {WALL,PASS,PASS,PASS,WALL,WALL,WALL,PASS,WALL,WALL,WALL,WALL,WALL,PASS,WALL} ,
                        {BRD1,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,PASS,BRD2} ,
                        {WALL,PASS,REC1,PASS,WALL,WALL,WALL,PASS,WALL,WALL,WALL,WALL,WALL,WALL,WALL} ,
                        {WALL,PASS,PASS,PASS,WALL,WALL,WALL,PASS,WALL,WALL,WALL,WALL,WALL,WALL,WALL} ,
                        {WALL,PASS,PASS,PASS,PASS,PASS,PASS,PASS,WALL,WALL,WALL,WALL,WALL,WALL,WALL} ,
                        {WALL,WALL,WALL,WALL,WALL,WALL,WALL,PRT2,WALL,WALL,WALL,WALL,WALL,WALL,WALL} ,
                        
    };

    int currentRow;
    int currentColumn;
    int REC1col;
    int REC1row;
    int PRT1col;
    int PRT1row;
    int BRD1col;
    int BRD2col;
    
    int NUM_NPC = 4;
    player npc[] = new player[NUM_NPC];
    
    int NUM_TREASURE = 4;
    treasure gold[] = new treasure[NUM_TREASURE];
    boolean treasureactive[] = new boolean[NUM_TREASURE];
    boolean SECRET;
    boolean gameOver;
    int score;
    boolean drawgold;
    
    int timecount;
 

    static Maze frame1;

    public static void main(String[] args) {
        frame1 = new Maze();
        frame1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }

    public Maze() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button
                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
               if (e.VK_RIGHT == e.getKeyCode())
                {
                    if(board[currentRow][currentColumn]== BRD2)
                     {
                         currentColumn=BRD1col;
                     }
                     if(board[currentRow][currentColumn+1]== REC1 && score>=1)
                     {
                        currentRow=PRT1row;
                         currentColumn=PRT1col;
                         score--;
                     }
                     if(board[currentRow][currentColumn+1]== PRT1 && score>=1)
                     {
                        currentRow=REC1row;
                         currentColumn=REC1col;
                         score--;
                     }
                     if(board[currentRow][currentColumn+1]== WALL && score>=1){
                         board[currentRow][currentColumn+1]=PASS;
                         score--;
                     }
                     if( !gameOver && board[currentRow][currentColumn+1]==PASS || 
                            board[currentRow][currentColumn+1]==OPEN ||
                            board[currentRow][currentColumn+1]==CLOS ||
                        //    board[currentRow][currentColumn+1]==PRT1 ||
                            board[currentRow][currentColumn+1]==BRD2 ||
                     (SECRET && board[currentRow][currentColumn+1]==SPAS))
                    currentColumn+=1;
                    
                }
                if (e.VK_LEFT == e.getKeyCode())
                {
                    
                    if(board[currentRow][currentColumn]== BRD1)
                     {
                         currentColumn=BRD2col;
                     }
                     if(board[currentRow][currentColumn-1]== REC1 && score>=1)
                     {
                        currentRow=PRT1row;
                         currentColumn=PRT1col;
                         score--;
                     }
                     if(board[currentRow][currentColumn-1]== PRT1 && score>=1)
                     {
                        currentRow=REC1row;
                         currentColumn=REC1col;
                         score--;
                     }
                     if(board[currentRow][currentColumn-1]== WALL && score>=1){
                         board[currentRow][currentColumn-1]=PASS;
                         score--;
                     }
                     
                    if( !gameOver && board[currentRow][currentColumn-1]==PASS ||
                            board[currentRow][currentColumn-1]==OPEN ||
                            board[currentRow][currentColumn-1]==CLOS ||
                           // board[currentRow][currentColumn-1]==REC1 ||
                            board[currentRow][currentColumn-1]==BRD1 ||
                        (SECRET && board[currentRow][currentColumn-1]==SPAS))
                    currentColumn-=1;
                }
                if (e.VK_UP == e.getKeyCode())
                {
                    if(board[currentRow-1][currentColumn]== REC1 && score>=1)
                     {
                        currentRow=PRT1row;
                         currentColumn=PRT1col;
                         score--;
                     }
                    if(board[currentRow-1][currentColumn]== PRT1 && score>=1)
                     {
                        currentRow=REC1row;
                         currentColumn=REC1col;
                         score--;
                     }
                    if(board[currentRow-1][currentColumn]== WALL && score>=1){
                         board[currentRow-1][currentColumn]=PASS;
                         score--;
                    }
                    if( !gameOver && board[currentRow-1][currentColumn]==PASS ||
                            board[currentRow-1][currentColumn]==OPEN ||
                            board[currentRow-1][currentColumn]==CLOS || 
                            board[currentRow-1][currentColumn]==REC2 || 
                    (SECRET && board[currentRow-1][currentColumn]==SPAS))
                    currentRow-=1;
                }
                if (e.VK_DOWN == e.getKeyCode())
                {
                    if(board[currentRow+1][currentColumn]== REC1 && score>=1)
                     {
                        currentRow=PRT1row;
                         currentColumn=PRT1col;
                         score--;
                     }
                    if(board[currentRow+1][currentColumn]== PRT1 && score>=1)
                     {
                        currentRow=REC1row;
                         currentColumn=REC1col;
                         score--;
                     }
                     if(board[currentRow+1][currentColumn]== WALL && score>=1){
                         board[currentRow+1][currentColumn]=PASS;
                         score--;
                     }                     
                     if( !gameOver && board[currentRow+1][currentColumn]==PASS ||
                            board[currentRow+1][currentColumn]==OPEN ||
                            board[currentRow+1][currentColumn]==CLOS ||
                            board[currentRow+1][currentColumn]==PRT2 ||
                    (SECRET && board[currentRow+1][currentColumn]==SPAS))                             
                    currentRow+=1;
                }

                repaint();
            }
        });
        init();
        start();
    }




    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

//fill background
        g.setColor(Color.cyan);

        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        g.setColor(Color.red);
//horizontal lines
        for (int zi=1;zi<numRows;zi++)
        {
            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
            getX(getWidth2()) ,getY(0)+zi*getHeight2()/numRows );
        }
//vertical lines
        for (int zi=1;zi<numColumns;zi++)
        {
            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
            getX(0)+zi*getWidth2()/numColumns,getY(getHeight2())  );
        }
        
//Determine which type of block to draw on the board.
        for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                if (board[zrow][zcolumn] == WALL || 
                        (SECRET==false && board[zrow][zcolumn]== SPAS))  
                {
                    g.setColor(Color.red);
                    g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                    getY(0)+zrow*getHeight2()/numRows,
                    getWidth2()/numColumns,
                    getHeight2()/numRows);
                }
                if(SECRET)
                {
                    if (board[zrow][zcolumn] == SPAS)  
                    {
                        g.setColor(Color.yellow);
                        g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                        getY(0)+zrow*getHeight2()/numRows,
                        getWidth2()/numColumns,
                        getHeight2()/numRows);
                 }
                }
                if (board[zrow][zcolumn] == REC1)  
                    {
                        g.setColor(Color.green);
                        g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                        getY(0)+zrow*getHeight2()/numRows,
                        getWidth2()/numColumns,
                        getHeight2()/numRows);
                 }
                 if (board[zrow][zcolumn] == PRT1)  
                    {
                        g.setColor(Color.green);
                        g.fillRect(getX(0)+zcolumn*getWidth2()/numColumns,
                        getY(0)+zrow*getHeight2()/numRows,
                        getWidth2()/numColumns,
                        getHeight2()/numRows);
                 }
            }
        }

        //////////NPC
for(int zi=0;zi<npc.length; zi++)
{    
        g.setColor(npc[zi].getColor());
        g.fillRect(getX(0)+npc[zi].getColumn()*getWidth2()/numColumns,
        getY(0)+npc[zi].getRow()*getHeight2()/numRows,
        getWidth2()/numColumns,
        getHeight2()/numRows);
        
            g.setColor(Color.black);
            g.setFont(new Font("Bell MT",Font.PLAIN,12) );
            g.drawString( npc[zi].getName(), getX(0)+npc[zi].getColumn()*getWidth2()/numColumns,
        getY(0)+npc[zi].getRow()*getHeight2()/numRows +14);
}
//////////////Treasure
for(int zi=0;zi<gold.length; zi++)
{ 
    if(treasureactive[zi]){
        g.setColor(gold[zi].getColor());
        g.fillRect(getX(0)+gold[zi].getColumn()*getWidth2()/numColumns,
        getY(0)+gold[zi].getRow()*getHeight2()/numRows,
        getWidth2()/numColumns,
        getHeight2()/numRows);
     
        g.setColor(Color.black);
        g.setFont(new Font("Bell MT",Font.PLAIN,12) );
        g.drawString( "" +gold[zi].getValue(), getX(0)+gold[zi].getColumn()*getWidth2()/numColumns+10,
        getY(0)+gold[zi].getRow()*getHeight2()/numRows +10);
    }
}        
       
         //////////PLAYER
        g.setColor(Color.cyan);
        g.fillRect(getX(0)+currentColumn*getWidth2()/numColumns,
        getY(0)+currentRow*getHeight2()/numRows,
        getWidth2()/numColumns,
        getHeight2()/numRows);
        
        g.setColor(Color.black);
         g.setFont(new Font("Bell MT",Font.PLAIN,12) );
         g.drawString( "" +score, getX(0)+currentColumn*getWidth2()/numColumns+10,
        getY(0)+currentRow*getHeight2()/numRows +10);
         if (gameOver)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced",Font.PLAIN,66) );
            g.drawString("Game Over",20,300);
        }
        
        gOld.drawImage(image, 0, 0, null);
    }


////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.03;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        
        boolean keeplooping=true;
        while(keeplooping){
            
        currentRow=(int)(Math.random()*numRows);
        currentColumn=(int)(Math.random()*numColumns);
            if(board[currentRow][currentColumn] == PASS){
                 keeplooping=false;
            }
        }
   for(int zi=0;zi<npc.length; zi++)
   {
       npc[zi]= new player();
        boolean keeplooping2=true;
        while(keeplooping2){
            
        npc[zi].setRow((int)(Math.random()*numRows));
        npc[zi].setColumn((int)(Math.random()*numColumns));
            if(board[npc[zi].getRow()][npc[zi].getColumn()] == PASS){
                 keeplooping2=false;
            }
        }
   }
   /////Treasure
   for(int zi=0;zi<gold.length; zi++)
   {
       treasureactive[zi]=true;
       gold[zi]= new treasure();
        boolean keeplooping2=true;
        while(keeplooping2){
            
        gold[zi].setRow((int)(Math.random()*numRows));
        gold[zi].setColumn((int)(Math.random()*numColumns));
            if(board[gold[zi].getRow()][gold[zi].getColumn()] == PASS){
                 keeplooping2=false;
            }
        }
   }
        SECRET=false;
        gameOver=false;

        npc[0].setName("boby");
        npc[0].setSpeed(5);
        npc[1].setName("Timo");
        npc[1].setSpeed(10);
        npc[2].setName("Gazy");
        npc[2].setSpeed(20);
        npc[3].setName("Plop");
        npc[3].setSpeed(35);
        score=0;
        
        for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                if(board[zrow][zcolumn]==REC1){
                    REC1col=zcolumn;
                    REC1row=zrow;
                }
                if(board[zrow][zcolumn]==PRT1){
                    PRT1col=zcolumn;
                    PRT1row=zrow;
                }
                if(board[zrow][zcolumn]==BRD1){
                    BRD1col=zcolumn;
                }
                if(board[zrow][zcolumn]==BRD2){
                    BRD2col=zcolumn;
                }
            }
        }
       for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                boardInit[zrow][zcolumn]=board[zrow][zcolumn]; 
            }           
        }     
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }

            reset();
        }
if(gameOver){
    return;
}
        for(int zi=0;zi<npc.length; zi++)
        {
            if(timecount % npc[zi].getSpeed() == npc[zi].getSpeed()-1)
            {

                int dir = (int)(Math.random()*4);
                if(dir==0){//move up
                    if(board[npc[zi].getRow()-1][npc[zi].getColumn()]==PASS)
                                npc[zi].setRow(npc[zi].getRow()-1);
                }
                 if(dir==1){//move down
                    if(board[npc[zi].getRow()+1][npc[zi].getColumn()]==PASS)
                                npc[zi].setRow(npc[zi].getRow()+1);
                }
                 if(dir==2){//move right
                    if(board[npc[zi].getRow()][npc[zi].getColumn()+1]==PASS)
                                npc[zi].setColumn(npc[zi].getColumn()+1);
                }
                if(dir==3){//move left
                    if(board[npc[zi].getRow()][npc[zi].getColumn()-1]==PASS)
                                npc[zi].setColumn(npc[zi].getColumn()-1);
                }
                /////////SPECEAL WALL NPC
                if(board[npc[1].getRow()][npc[1].getColumn()]==PASS){
                    board[npc[1].getRow()][npc[1].getColumn()]= WALL;
                    
                }                   
            }
        }
        ///UNDO WALL

    
//////////PLAYER MOVE ABILITY
//        if(board[currentRow][currentColumn]== OPEN)
//            SECRET=true;
//         if(board[currentRow][currentColumn]== CLOS)
//            SECRET=false;
//         if(board[currentRow][currentColumn]== PRT1)
//              currentColumn-=13;
//         if(board[currentRow][currentColumn]== REC1)
//         {
//              currentColumn+=13;
//         }
//         if(board[currentRow][currentColumn]== PRT2)
//              currentRow-=18;
//         if(board[currentRow][currentColumn]== REC2)
//              currentRow+=18;
//////////SecretPass
         if(score>=3)
             SECRET=true;
         if(score > 7)
             SECRET=false;
//      for(int zi=0;zi<npc.length; zi++)
//        {
//         if(npc[zi].getRow() == currentRow && npc[zi].getColumn() == currentColumn)
//                 gameOver=true;
//        }
      for(int zi=0;zi<gold.length; zi++)
        {
            if(treasureactive[zi]&&gold[zi].getRow() == currentRow && gold[zi].getColumn() == currentColumn){
                 score+= gold[zi].getValue();
                treasureactive[zi]=false;
         }
        }
      if(timecount % 60 == 59 && treasureactive[1]){
          gold[1].setValue((int)(Math.random()*5+1));
      }
      
         timecount++;
        
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x);
    }
    public int getY(int y) {
        return (y + TOP_BORDER + YTITLE);
    }
    public int getWidth2() {
        return (xsize - getX(0));
    }
    public int getHeight2() {
        return (ysize - getY(0));
    }
}


class player {
 //position  
    private int row;
    private int column;
    private int speed;
    
    private Color color;
    private String name;
    
    player()
    {
        color= Color.blue;
    }        
    
    void setRow(int _row)
    {
        row = _row;
    }
    int getRow()
    {
        return(row);
                
    }
    void setColumn(int _column)
    {
        column = _column;
    }
    int getColumn()
    {
        return(column);
    }
    void setColor(Color _color)
    {
        color = _color;
    }
    Color getColor()
    {
        return(color);
    }
   void setName(String _name)
   {
       name = _name;
   }  
   String getName()
   {
       return(name);
   }   
   void setSpeed(int _speed)
   {
       speed = _speed;
   }
   int getSpeed()
   {
       return(speed);
   }
}
class treasure {
    private int value;
    private Color color;
    private int row;
    private int column;
    Color brightest = new Color(211,201,14);
    Color brighter = new Color(238,201,51);
    Color bright = new Color(245,227,99);
    Color dim = new Color(249,236,149);
    Color dimer = new Color(252,246,201);
    /////////////////////////////////
    treasure()
    {
        value= (int)(Math.random()*5+1);
        if(value==5)
        color = brightest;
        if(value==4)
        color = brighter;
        if(value==3)
        color = bright;
        if(value==2)
        color = dim;
        if(value==1)
        color = dimer;
        
    }
     void setValue(int _value)
    {
        value = _value;
         if(value==5)
        color = brightest;
        if(value==4)
        color = brighter;
        if(value==3)
        color = bright;
        if(value==2)
        color = dim;
        if(value==1)
        color = dimer;
    }
    int getValue()
    {
        return(value);
                
    }
     void setRow(int _row)
    {
        row = _row;
    }
    int getRow()
    {
        return(row);
                
    }
    void setColumn(int _column)
    {
        column = _column;
    }
    int getColumn()
    {
        return(column);
    }
    void setColor(Color _color)
    {
        color = _color;
    }
    Color getColor()
    {
        return(color);
    }
   
 
}
