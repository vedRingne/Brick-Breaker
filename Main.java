import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Main extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 90;
    private static final int PADDLE_HEIGHT = 15;
    private static final int BALL_SIZE = 15;
    private static final int BRICK_COLS = 7;
    private static final int BRICK_WIDTH = WIDTH / BRICK_COLS;
    private static final int BRICK_HEIGHT = 30;

    private int paddleX;
    private int ballX, ballY;
    private int ballDX, ballDY;
    private boolean[][] bricks;
    private Color[][] brickColors;
    private int score;
    private Random random;
    private int maxRows;
    private int difficulty;

    public Main(int difficulty) {
        this.difficulty = difficulty;
        initializeGame();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) {
                    paddleX -= 15;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < WIDTH - PADDLE_WIDTH) {
                    paddleX += 15;
                }
            }
        });

        Timer timer = new Timer(10, this);
        timer.start();
    }

    private void initializeGame() {
        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = WIDTH / 2;
        ballY = HEIGHT - 30;
        // Initialize both arrays with the same maxRows
        bricks = new boolean[15][BRICK_COLS];
        brickColors = new Color[7][BRICK_COLS];
        random = new Random();
        score = 0;

        switch (difficulty) {
            case 1: // Easy
                maxRows = 6; // Fewer rows
                ballDX = 2;
                ballDY = -2;
                createPattern(new int[][] {
                        {1, 1, 1, 0, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 1, 1, 1}
                });
                break;
            case 2: // Medium
                maxRows = 7; // Standard rows
                ballDX = 3;
                ballDY = -3;
                createPattern(new int[][] {
                        {1, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 1, 1, 1}
                });
                break;
            case 3: // Hard
                maxRows = 9; // More rows
                ballDX = 4;
                ballDY = -4;
                createPattern(new int[][] {
                        {1, 0, 1, 0, 1, 0, 1},
                        {0, 1, 0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1, 0, 1},
                        {0, 1, 0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1, 1, 1},
                        {0, 1, 0, 1, 0, 1, 0},
                        {1, 1, 1, 1, 1, 1, 1}
                });
                break;
            default:
                maxRows = 5;
                ballDX = 3;
                ballDY = -3;
                break;
        }
    }

    private void updateBrick(boolean[][] bricks, boolean val, int x, int y) {
        if (x >= 0 && y >= 0 && x < bricks.length && y < bricks[0].length) {
            bricks[x][y] = val;
        }
    }

    private void updateBrickColors(Color[][] arr, int x, int y) {
        if (x >= 0 && y >= 0 && x < arr.length && y < arr[0].length) {
            arr[x][y] = getRandomColor();
        }
    }

    private void createPattern(int[][] pattern) {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (i < pattern.length && j < pattern[i].length && pattern[i][j] == 1) {
                    this.updateBrick(bricks, true, i, j);
                    this.updateBrickColors(brickColors, i, j);
                } else {
                    this.updateBrick(bricks, false, i, j);
                }
            }
        }
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBricks(g);
        drawPaddle(g);
        drawBall(g);
        drawScore(g);
    }

    private void drawBricks(Graphics g) {
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (i < brickColors.length && bricks[i][j]) { // Ensure i is within bounds
                    g.setColor(brickColors[i][j]);
                    g.fillRect(j * BRICK_WIDTH, i * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }
    }

    private void drawPaddle(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBall();
        checkCollisions();
        repaint();
    }

    private void moveBall() {
        ballX += ballDX;
        ballY += ballDY;

        if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
            ballDX = -ballDX;
        }
        if (ballY <= 0) {
            ballDY = -ballDY;
        }
    }

    private void checkCollisions() {
        if (ballY >= HEIGHT - PADDLE_HEIGHT - BALL_SIZE && ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
            ballDY = -ballDY;
            score++;
        }

        boolean allBricksCleared = true;

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < BRICK_COLS; j++) {
                if (bricks[i][j]) {
                    int brickX = j * BRICK_WIDTH;
                    int brickY = i * BRICK_HEIGHT;

                    if (ballX + BALL_SIZE >= brickX && ballX <= brickX + BRICK_WIDTH &&
                            ballY + BALL_SIZE >= brickY && ballY <= brickY + BRICK_HEIGHT) {
                        bricks[i][j] = false;
                        ballDY = -ballDY;
                        score += 10;
                    }
                } else {
                    allBricksCleared = false;
                }
            }
        }

        if (allBricksCleared) {
            levelUp();
        }

        if (ballY >= HEIGHT) {
            gameOver();
        }
    }

    private void levelUp() {
        if (maxRows < 7) {
            maxRows++;
        }
        initializeGame();
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        System.exit(0);
    }

    public static void main(String[] args) {
        String[] options = {"Easy", "Medium", "Hard"};
        int difficulty = JOptionPane.showOptionDialog(null, "Select Difficulty Level", "Brick Breaker",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (difficulty == JOptionPane.CLOSED_OPTION || difficulty == -1) {
            System.exit(0); // Exit if no option is selected
        }

        JFrame frame = new JFrame("Brick Breaker");
        Main game = new Main(difficulty + 1); // Adjust difficulty for internal use
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
