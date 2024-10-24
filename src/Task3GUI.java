import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MatrixSizeException extends ArithmeticException {
    public MatrixSizeException(String message) {
        super(message);
    }
}

public class Task3GUI extends JFrame {
    private JTextField sizeField;
    private JButton loadButton, shiftButton;
    private JTable matrixTable;
    private int[][] matrix;

    public Task3GUI() {
        setTitle("Matrix Shift GUI");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel sizeLabel = new JLabel("Введіть розмір матриці:");
        sizeField = new JTextField(5);
        loadButton = new JButton("Завантажити матрицю");

        topPanel.add(sizeLabel);
        topPanel.add(sizeField);
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);

        matrixTable = new JTable();
        add(new JScrollPane(matrixTable), BorderLayout.CENTER);

        shiftButton = new JButton("Циклічний зсув");
        shiftButton.setEnabled(false); 
        add(shiftButton, BorderLayout.SOUTH);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadMatrixFromFile();
                    shiftButton.setEnabled(true);
                } catch (FileNotFoundException ex) {
                    showError("Файл не знайдено.");
                } catch (MatrixSizeException ex) {
                    showError(ex.getMessage());
                } catch (NumberFormatException ex) {
                    showError("Невірний формат вхідних даних.");
                }
            }
        });

        shiftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cyclicShift();
                updateTable();
            }
        });
    }

    private void loadMatrixFromFile() throws FileNotFoundException {
        int n = Integer.parseInt(sizeField.getText());
        if (n > 15 || n <= 0) {
            throw new MatrixSizeException("Некоректний розмір матриці. Розмір має бути від 1 до 15.");
        }

        File file = new File("matrix.txt"); 
        Scanner scanner = new Scanner(file);
        matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (scanner.hasNextInt()) {
                    matrix[i][j] = scanner.nextInt();
                } else {
                    throw new NumberFormatException();
                }
            }
        }
        scanner.close();
        updateTable();
    }

    private void cyclicShift() {
        int n = matrix.length;

        int maxRow = 0;
        int maxElement = matrix[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > maxElement) {
                    maxElement = matrix[i][j];
                    maxRow = i;
                }
            }
        }

        int[] tempRow = matrix[maxRow];
        for (int i = maxRow; i > 0; i--) {
            matrix[i] = matrix[i - 1];
        }
        matrix[0] = tempRow;
    }

    private void updateTable() {
        String[] columnNames = new String[matrix.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = "Col " + (i + 1);
        }

        Object[][] tableData = new Object[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                tableData[i][j] = matrix[i][j];
            }
        }

        matrixTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Помилка", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Task3GUI().setVisible(true);
            }
        });
    }
}
