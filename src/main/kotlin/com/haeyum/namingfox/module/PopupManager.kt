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

    private var popup: Popup? = null

    fun showLoading(type: String) {
        hide()
        show("Ask naming $type to fox...")

        job = coroutineScope.launch {
            while (isActive) {
                repeat(4) {
                    popup?.setText("Ask naming $type to fox" + ".".repeat(it))
                    delay(600)
                }
            }
        }
    }

    fun showError(text: String) {
        hide()
        show(text)

        job = coroutineScope.launch {
            delay(1000)
            hide()
        }
    }

    fun show(text: String) = run {
        val panel: JComponent = JPanel()
        val label = JLabel(text)

        panel.add(label)
        Popup(
            popup = JBPopupFactory
                .getInstance()
                .createComponentPopupBuilder(panel, label)
                .setCancelOnClickOutside(false)
                .setCancelKeyEnabled(false)
                .setBelongsToGlobalPopupStack(true)
                .createPopup()
                .apply {
                    showInFocusCenter()
                },
            label = label
        ).also { popup = it }
    }

    fun hide() {
        job?.cancel()
        popup?.dispose()
    }
}

data class Popup(val popup: JBPopup, val label: JLabel) {
    fun dispose() = popup.dispose()
    fun setText(text: String) {
        label.text = text
    }
}