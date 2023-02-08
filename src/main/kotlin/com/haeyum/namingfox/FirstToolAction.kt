package com.haeyum.namingfox

import com.haeyum.namingfox.module.PopupManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.wm.ToolWindowManager


class FirstToolAction : AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        println("WOW")
        p0.getData(PlatformDataKeys.PROJECT)?.let {
            ToolWindowManager.getInstance(it).getToolWindow("NamingFoxWindow")?.activate(null)
        }

        p0.getData(PlatformDataKeys.PROJECT)?.let {

        } ?: println("Something wrong... Please restart IDE.")
    }
}