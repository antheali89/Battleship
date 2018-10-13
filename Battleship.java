import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Battleship extends JPanel
{
   private JButton[][] board;
   private int[][] matrix;
   private int hits, torpedoes;
   private JLabel label;
   private JButton reset;
   private boolean gameOver;

   private int posR;
   private int posC;
   private int coin;
   
   public Battleship()
   {
      setLayout(new BorderLayout());
      hits = 0;
      torpedoes = 20;
      gameOver = false;
   
      JPanel north = new JPanel();
      north.setLayout(new FlowLayout());
      add(north, BorderLayout.NORTH);
      label = new JLabel("You have 20 torpedoes.");
      north.add(label);
   
      JPanel center = new JPanel();
      center.setLayout(new GridLayout(10,10));
      add(center, BorderLayout.CENTER);
      board = new JButton[10][10];
      matrix = new int[10][10];
      for(int r = 0; r < 10; r++)
         for(int c = 0; c < 10; c++)
         {
            matrix[r][c] = 0;
            board[r][c] = new JButton();
            board[r][c].setBackground(Color.blue);
            board[r][c].addActionListener( new Handler1(r, c) );
            center.add(board[r][c]);
         }
   
      reset = new JButton("Reset");
      reset.addActionListener( new Handler2() );
      reset.setEnabled(false);
      add(reset, BorderLayout.SOUTH);
   
      placeShip();
   }

   private void placeShip()
   {
      coin = (int)(Math.random() * 2 + 1);
      posR = (int)(Math.random() * 10);
      posC = (int)(Math.random() * 10);
      
      if(coin == 1) {
         if(posR < 4)
            posR += 4 - posR;
         int r = posR;
         for(int i = 0; i < 4; i++)
            matrix[r--][posC] = 1;
      }
      else if(coin == 2) {
         if(posC > 6)
            posC += 6 - posC;
         int c = posC;
         for(int i = 0; i < 4; i++)
            matrix[posR][c++] = 1;
      }
   }

   private class Handler1 implements ActionListener
   {
      private int myRow, myCol;
      public Handler1(int r, int c)
      {
         myRow = r;
         myCol = c;
      }
      public void actionPerformed(ActionEvent e)
      {
         // The following two statements are for debugging purpose
         System.out.println(myRow);
         System.out.println(myCol);
         
         if(matrix[myRow][myCol] == 3 || matrix[myRow][myCol] == 2 || gameOver == true)
            return;
         torpedoes--;
         board[myRow][myCol].setBackground(Color.red);
         label.setText("You have " + torpedoes + " torpedoes.");
         if(matrix[myRow][myCol] == 1) {
            hits++;
            matrix[myRow][myCol] = 3;
            label.setText("YOU HIT SOMETHING");
            board[myRow][myCol].setBackground(Color.green);
         }
         else if(matrix[myRow][myCol] == 0) {
            matrix[myRow][myCol] = 2;
         }
         if(hits >= 4) {
            label.setText("YOU WIN!");
            reset.setEnabled(true);
         }
         if(torpedoes <= 0 && hits < 4) {
            label.setText("YOU LOSE");
            reset.setEnabled(true);
            gameOver = true;
            if(coin == 1)
               for(int i = 0; i < 4; i++)
                  board[posR--][posC].setBackground(Color.green);
            if(coin == 2)
               for(int i = 0; i < 4; i++)
                  board[posR][posC++].setBackground(Color.green);
         }
      
          
      }   // actionPerformed of Handler
   }
      
   // Handling the Reset button
   private class Handler2 implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         for(int r = 0; r < 10; r++) {
            for(int c = 0; c < 10; c++) {
               matrix[r][c] = 0;
               board[r][c].setBackground(Color.blue);
            }
         }
         hits = 0;
         torpedoes = 20;
         label.setText("You have 20 torpedoes.");
         gameOver = false;
         placeShip();
      }  // actionPerformed of Handler2
   }
		
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Battleship!");
      frame.setSize(400, 400);
      frame.setLocation(200, 100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new Battleship());
      frame.setVisible(true);
   }
}