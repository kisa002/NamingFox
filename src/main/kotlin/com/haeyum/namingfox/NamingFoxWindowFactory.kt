package com.haeyum.namingfox

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class NamingFoxWindowFactory: ToolWindowFactory  {
    override fun createToolWindowContent(p0: Project, p1: ToolWindow) {
        val window = NamingFoxWindow.getInstance(p0)
    }
}