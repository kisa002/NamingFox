package com.haeyum.namingfox

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.ToolWindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class FirstToolAction : AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        println("WOW")
        p0.getData(PlatformDataKeys.PROJECT)?.let {
            ToolWindowManager.getInstance(it).getToolWindow("NamingFoxWindow")?.activate(null)
        }

        p0.getData(PlatformDataKeys.PROJECT)?.let {
            PopupManager.showLoading(it)
        } ?: println("Something wrong... Please restart IDE.")

//        val textField = JTextField("Ask naming value to fox...", 20)
//        val panel: JComponent = JPanel()
//        val label = JLabel("Ask naming value to fox------")
//
//        panel.add(label)
////        panel.add(textField)
//        val popup = JBPopupFactory
//            .getInstance()
//            .createComponentPopupBuilder(panel, textField)
//            .setCancelOnClickOutside(false)
//            .setCancelKeyEnabled(false)
////            .setBelongsToGlobalPopupStack()
////            .setCancelCallback {
////                println("setCancelCallback")
////                false
////            }
////            .setCancelOnMouseOutCallback {
////                println("setCancelOnMouseOutCallback")
////                false
////            }
//            .createPopup()
//
//        popup.showCenteredInCurrentWindow(p0.getData(PlatformDataKeys.PROJECT)!!)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            while (true) {
//                for (i in 0..3) {
//                    delay(700)
//                    label.text = "Ask naming value to fox" + ".".repeat(i)
//                    println(i)
//                }
//            }
//        }
    }
}