package com.haeyum.namingfox

import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import kotlinx.coroutines.*
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

object PopupManager {
//    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    private var popup: JBPopup? = null

    fun showLoading(project: Project? = null) {
        val panel: JComponent = JPanel()
        val label = JLabel("Ask naming value to fox")

        panel.add(label)
        popup = JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(panel, label)
            .setCancelOnClickOutside(false)
            .setCancelKeyEnabled(false)
            .setBelongsToGlobalPopupStack(true)
//            .setBelongsToGlobalPopupStack()
//            .setCancelCallback {
//                println("setCancelCallback")
//                false
//            }
//            .setCancelOnMouseOutCallback {
//                println("setCancelOnMouseOutCallback")
//                false
//            }
            .createPopup()
            .apply {
                showInFocusCenter()
            }

//        popup.showCenteredInCurrentWindow(project)
//        popup.showInFocusCenter()

//        job?.cancel()
//        job = CoroutineScope(Dispatchers.IO).launch {
//            while (isActive) {
//                for (i in 0..3) {
//                    delay(700)
//                    label.text = "Ask naming value to fox" + ".".repeat(i)
//                    println(i)
//                }
//            }
//        }
    }

    suspend fun showLoading(coroutineScope: CoroutineScope) {
        val panel: JComponent = JPanel()
        val label = JLabel("Ask naming value to fox")

        panel.add(label)
        popup = JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(panel, label)
            .setCancelOnClickOutside(false)
            .setCancelKeyEnabled(false)
            .setBelongsToGlobalPopupStack(true)
//            .setBelongsToGlobalPopupStack()
//            .setCancelCallback {
//                println("setCancelCallback")
//                false
//            }
//            .setCancelOnMouseOutCallback {
//                println("setCancelOnMouseOutCallback")
//                false
//            }
            .createPopup()
            .apply {
                showInFocusCenter()
            }

//        popup.showCenteredInCurrentWindow(project)
//        popup.showInFocusCenter()

        job?.cancel()
//        job = coroutineScope.launch {
//            while (isActive) {
//                for (i in 0..3) {
//                    delay(700)
//                    label.text = "Ask naming value to fox" + ".".repeat(i)
//                    println(i)
//                }
//            }
//        }
    }


    fun hide() {
        job?.cancel()
        popup?.dispose()
    }
}