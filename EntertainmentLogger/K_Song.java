package EntertainmentLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class K_Song extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField singerField, titleField, yearField, ratingField, commentField;
    private JButton addBtn, editBtn, deleteBtn, refreshBtn;

    private static final String FILENAME = "Korean_song_logs.txt";

    public K_Song() {
        setTitle("Korean Song Logger");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        singerField = new JTextField();
        titleField = new JTextField();
        yearField = new JTextField();
        ratingField = new JTextField();
        commentField = new JTextField();

        inputPanel.add(new JLabel("Singer Name:"));
        inputPanel.add(singerField);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Rating:"));
        inputPanel.add(ratingField);
        inputPanel.add(new JLabel("Comment:"));
        inputPanel.add(commentField);

        model = new DefaultTableModel(new Object[]{"Singer Name", "Title", "Year", "Rating", "Comment"}, 0);
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
            String singer = singerField.getText();
            String title = titleField.getText();
            String year = yearField.getText();
            String rating = ratingField.getText();
            String comment = commentField.getText();
            if (!title.isEmpty()) {
                model.addRow(new Object[]{singer, title, year, rating, comment});
                clearFields();
                saveLogs(FILENAME);
            }
        });

        editBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                model.setValueAt(singerField.getText(), selected, 0);
                model.setValueAt(titleField.getText(), selected, 1);
                model.setValueAt(yearField.getText(), selected, 2);
                model.setValueAt(ratingField.getText(), selected, 3);
                model.setValueAt(commentField.getText(), selected, 4);
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
                singerField.setText((String) model.getValueAt(selected, 0));
                titleField.setText((String) model.getValueAt(selected, 1));
                yearField.setText((String) model.getValueAt(selected, 2));
                ratingField.setText((String) model.getValueAt(selected, 3));
                commentField.setText((String) model.getValueAt(selected, 4));
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadLogs(FILENAME);
        setVisible(true);
    }

    private void clearFields() {
        singerField.setText("");
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
                        model.getValueAt(i, 3).toString(),
                        model.getValueAt(i, 4).toString());
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
                if (tokens.length == 5) {
                    model.addRow(new Object[]{tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]});
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }
}