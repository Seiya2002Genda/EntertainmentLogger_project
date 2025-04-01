package EntertainmentLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class JapaneseMovie extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField titleField, yearField, ratingField, commentField;
    private JButton addBtn, editBtn, deleteBtn, refreshBtn;

    private static final String FILENAME = "japanese_movie_logs.txt";

    public JapaneseMovie() {
        setTitle("Japanese Movie Logger");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        titleField = new JTextField();
        yearField = new JTextField();
        ratingField = new JTextField();
        commentField = new JTextField();

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Rating:"));
        inputPanel.add(ratingField);
        inputPanel.add(new JLabel("Comment:"));
        inputPanel.add(commentField);

        model = new DefaultTableModel(new Object[]{"Title", "Year", "Rating", "Comment"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("追加");
        editBtn = new JButton("編集");
        deleteBtn = new JButton("削除");
        refreshBtn = new JButton("リフレッシュ");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        addBtn.addActionListener(e -> {
            String title = titleField.getText();
            String year = yearField.getText();
            String rating = ratingField.getText();
            String comment = commentField.getText();
            if (!title.isEmpty()) {
                model.addRow(new Object[]{title, year, rating, comment});
                clearFields();
                saveLogs(FILENAME);
            }
        });

        editBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                model.setValueAt(titleField.getText(), selected, 0);
                model.setValueAt(yearField.getText(), selected, 1);
                model.setValueAt(ratingField.getText(), selected, 2);
                model.setValueAt(commentField.getText(), selected, 3);
                saveLogs(FILENAME);
            }
        });

        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                model.removeRow(selected);
                saveLogs(FILENAME);
            }
        });

        refreshBtn.addActionListener(e -> {
            table.clearSelection();
            clearFields();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                titleField.setText((String) model.getValueAt(selected, 0));
                yearField.setText((String) model.getValueAt(selected, 1));
                ratingField.setText((String) model.getValueAt(selected, 2));
                commentField.setText((String) model.getValueAt(selected, 3));
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadLogs(FILENAME);
        setVisible(true);
    }

    private void clearFields() {
        titleField.setText("");
        yearField.setText("");
        ratingField.setText("");
        commentField.setText("");
    }

    private void saveLogs(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String line = String.join("||",
                        model.getValueAt(i, 0).toString(),
                        model.getValueAt(i, 1).toString(),
                        model.getValueAt(i, 2).toString(),
                        model.getValueAt(i, 3).toString());
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLogs(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|\\|");
                if (tokens.length == 4) {
                    model.addRow(new Object[]{tokens[0], tokens[1], tokens[2], tokens[3]});
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }
}