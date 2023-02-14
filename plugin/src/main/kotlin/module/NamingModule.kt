package module

import api.NamingRepository
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object NamingModule {
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
        runBlocking {
            NamingRepository.getNaming(text, type, language)

            kotlin.runCatching {
                NamingRepository.getNaming(text, type, language)
            }.onSuccess { namingData ->
                if (namingData != null) {
                    WriteCommandAction.runWriteCommandAction(project) {
                        document.replaceString(start, end, namingData.naming)
                        PopupManager.hide()
                    }
                } else {
                    PopupManager.showError("Something wrong...")
                }
            }.onFailure {
                PopupManager.showError("Failed naming, please try later.")
            }
        }
        primaryCaret.removeSelection()
    }
}