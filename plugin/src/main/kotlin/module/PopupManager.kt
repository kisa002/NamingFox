package module

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import dispatcher.AwtEventQueueDispatcher
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

        job?.cancel()
        job = coroutineScope.launch {
            delay(3000)
            withContext(AwtEventQueueDispatcher) {
                hide()
            }
        }
    }

    fun show(text: String) {
        val panel: JComponent = JPanel()
        val label = JLabel(text)

        panel.add(label)
        popup = Popup(
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
        )
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