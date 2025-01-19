package org.example;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.SelectionModel;


import java.awt.*;
        import java.awt.datatransfer.StringSelection;

public class ExecuteAgainstData extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        TableService tableService = ApplicationManager.getApplication().getService(TableService.class);

        String tableString = "Tables: ";
        for (Table t : tableService.getTables()) {
            tableString = tableString + " " + t.getName();
        }

        String markedCode = "";
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            SelectionModel selectionModel = editor.getSelectionModel();
            String selectedText = selectionModel.getSelectedText();
            if (selectedText != null && !selectedText.isEmpty()) {
                markedCode = selectedText;
            }
        }

        // Show a confirmation dialog*/
        Messages.showInfoMessage("Executing: " + markedCode + " for:" + tableString, "Success");
    }

    @Override
    public void update(AnActionEvent event) {
        // Optionally, control when the action is enabled/disabled
        event.getPresentation().setEnabled(true);
    }
}
