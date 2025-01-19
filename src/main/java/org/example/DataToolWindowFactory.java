package org.example;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataToolWindowFactory implements ToolWindowFactory, DumbAware {

    //private final List<Table> tables = new ArrayList<>();
    TableService tableService = ApplicationManager.getApplication().getService(TableService.class);

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Define UI Components
        JButton addTableButton = new JButton("Add Table");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addTableButton);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        mainPanel.add(innerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addTableButton.addActionListener(e -> {
            JTextField tableNameField = new JTextField();
            int result = JOptionPane.showConfirmDialog(null,
                    new Object[]{"Table Name:", tableNameField},
                    "Add Table", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                tableService.addTable(new Table(tableNameField.getText()));
            }
            updateTableModel(innerPanel);
        });

        // Add Content to Tool Window
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private void updateTableModel(JPanel panel) {

        clearScrollPanes(panel);

        for (Table t : tableService.getTables()) {
            JTable table = new JTable();

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createTitledBorder(t.getName()));
            Dimension tableSize = table.getPreferredSize();
            scrollPane.setPreferredSize(new Dimension(tableSize.width, tableSize.height));

            JButton addColumnButton = new JButton("Add Column");
            JButton addRowButton = new JButton("Add Row");

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(addColumnButton);
            buttonPanel.add(addRowButton);

            panel.add(scrollPane, BorderLayout.NORTH);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            List<String> columnNames = new ArrayList<>();
            for (Column column: t.getColumns()){
                columnNames.add(column.getName());
            }

            List<Object[]> rows = t.getRows();

            String[] columnArray = columnNames.toArray(new String[0]);
            Object[][] rowData = rows.toArray(new Object[0][]);

            table.setModel(new javax.swing.table.DefaultTableModel(rowData, columnArray));


            addColumnButton.addActionListener(e -> {
                JTextField columnNameField = new JTextField();
                JComboBox<String> columnTypeField = new JComboBox<>(new String[]{"String", "Integer", "Double", "Boolean"});

                int result = JOptionPane.showConfirmDialog(null,
                        new Object[]{"Column Name:", columnNameField, "Data Type:", columnTypeField},
                        "Add Column", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    System.out.println(columnNameField.getText());
                    t.addColumn(columnNameField.getText(), (String) columnTypeField.getSelectedItem());
                }
                updateTableModel(panel);
            });

            addRowButton.addActionListener(e -> {
                if (columnNames.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please add columns first!");
                    return;
                }
                rows.add(new Object[columnNames.size()]);
                updateTableModel(panel);
            });


        }
    }

    private void clearScrollPanes(JPanel panel){
        for (Component component: panel.getComponents()) {
            System.out.println(component);
            panel.remove(component);
        }
        panel.revalidate();
        panel.repaint();

    }
}
