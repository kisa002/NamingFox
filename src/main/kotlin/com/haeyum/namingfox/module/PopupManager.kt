package com.haeyum.namingfox.module

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import kotlinx.coroutines.*
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

object PopupManager {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    private var popup: JBPopup? = null

    fun showLoading(type: String) {
        val panel: JComponent = JPanel()
        val label = JLabel("Ask naming $type to fox...")

        panel.add(label)
        popup = JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(panel, label)
            .setCancelOnClickOutside(false)
            .setCancelKeyEnabled(false)
            .setBelongsToGlobalPopupStack(true)
            .createPopup()
            .apply {
                showInFocusCenter()
            }

        job = coroutineScope.launch {
            while(isActive) {
                repeat(4) {
                    label.text = "Ask naming $type to fox" + ".".repeat(it)
                    delay(500)
                }
            }
        }
    }

    fun hide() {
        job?.cancel()
        popup?.dispose()
    }
}