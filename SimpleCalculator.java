import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleCalculator extends JFrame {
    private JTextField display;
    private String current = "";
    private String operator = "";
    private double num1 = 0;

    public SimpleCalculator() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());

        JPanel calculatorBody = new RoundedPanel(30, new Color(30, 30, 30));
        calculatorBody.setLayout(new BorderLayout(20, 20));
        calculatorBody.setPreferredSize(new Dimension(360, 500));
        calculatorBody.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        display = new JTextField();
        display.setFont(new Font("Digital-7 Mono", Font.PLAIN, 36));
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        display.setPreferredSize(new Dimension(360, 60));
        calculatorBody.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 15, 15));
        buttonPanel.setOpaque(false);

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            CircleButton btn = new CircleButton(text);

            if ("/*-+=C".contains(text)) {
                btn.setBackgroundColor(new Color(255, 87, 34));
                btn.setTextColor(Color.WHITE);
            } else {
                btn.setBackgroundColor(Color.WHITE);
                btn.setTextColor(Color.BLACK);
            }

            btn.addActionListener(e -> handleInput(text));
            buttonPanel.add(btn);
        }

        calculatorBody.add(buttonPanel, BorderLayout.CENTER);
        add(calculatorBody);
        setVisible(true);
    }

    private void handleInput(String input) {
        if (input.matches("\\d")) {
            current += input;
            display.setText(current);
        } else if ("/*-+".contains(input)) {
            if (!current.isEmpty()) {
                num1 = Double.parseDouble(current);
                operator = input;
                current = "";
            }
        } else if (input.equals("=")) {
            if (!current.isEmpty() && !operator.isEmpty()) {
                double num2 = Double.parseDouble(current);
                double result = switch (operator) {
                    case "+" -> num1 + num2;
                    case "-" -> num1 - num2;
                    case "*" -> num1 * num2;
                    case "/" -> num2 != 0 ? num1 / num2 : 0;
                    default -> 0;
                };
                display.setText(formatResult(result));
                current = "";
                operator = "";
            }
        } else if (input.equals("C")) {
            current = "";
            operator = "";
            num1 = 0;
            display.setText("");
        }
    }

    private String formatResult(double result) {
        if (result == (long) result)
            return String.valueOf((long) result);
        else
            return String.valueOf(result);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(SimpleCalculator::new);
    }
}

class CircleButton extends JButton {
    private Color hoverColor = new Color(244, 67, 54);
    private Color baseColor = Color.WHITE;
    private Color textColor = Color.BLACK;

    public CircleButton(String label) {
        super(label);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.BOLD, 18));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                baseColor = hoverColor;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                baseColor = getBackground();
                repaint();
            }
        });
    }

    public void setHoverColor(Color hover) {
        this.hoverColor = hover;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setBackgroundColor(Color color) {
        this.baseColor = color;
        setBackground(color);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int size = Math.min(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(baseColor);
        g2.fillOval((getWidth() - size) / 2, (getHeight() - size) / 2, size, size);

        g2.setColor(textColor);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 4);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(70, 70);
    }
}

class RoundedPanel extends JPanel {
    private int cornerRadius;
    private Color backgroundColor;

    public RoundedPanel(int radius, Color bgColor) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height);
        g2.dispose();
    }
}
