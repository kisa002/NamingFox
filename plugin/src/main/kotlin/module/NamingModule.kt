package module

import api.NamingRepository
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import dispatcher.AwtEventQueueDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.nio.channels.UnresolvedAddressException

object NamingModule {
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun namingVariable(event: AnActionEvent) {
        naming(event, "variable")
    }

    fun namingFunction(event: AnActionEvent) {
        naming(event, "function")
    }

    private fun naming(event: AnActionEvent, type: String) {
        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        val project = event.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document

        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        val text = document.getText(TextRange.create(start, end)).take(80)
        val language = document.toString().split(".").last().dropLast(1)

        PopupManager.showLoading(type)
        coroutineScope.launch {
            kotlin.runCatching {
                NamingRepository.getNaming(text, type, language)
            }.onSuccess { namingResponse ->
                withContext(AwtEventQueueDispatcher) {
                    when (namingResponse.code) {
                        0 -> if (namingResponse.result != null) {
                            WriteCommandAction.runWriteCommandAction(project) {
                                document.replaceString(start, end, namingResponse.result!!.naming)
                                PopupManager.hide()
                            }
                        } else {
                            PopupManager.showError("Something wrong... Please contact to admin.")
                        }
                        else -> PopupManager.showError(namingResponse.message)
                    }
                }
            }.onFailure {
                withContext(AwtEventQueueDispatcher) {
                    PopupManager.showError(when(it) {
                        is UnresolvedAddressException, is ConnectException -> "Server is down... Please contact to admin."
                        else -> "Failed naming, please try later."
                    })
                }
            }
        }
        primaryCaret.removeSelection()
    }
}