package com.haeyum.namingfox

import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.panel

class NamingFoxWindow {
    companion object {
        fun getInstance(project: Project): NamingFoxWindow = project.getComponent(NamingFoxWindow::class.java)
    }

    init {
        panel {
            row("enter") {
                textField()
            }
        }
    }
}