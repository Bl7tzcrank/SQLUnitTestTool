package org.example;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class DefineDataAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showMessageDialog("This is where the data definition tool will appear!", "Define Data", Messages.getInformationIcon());
    }
}
