package com.haeyum.namingfox.module

import com.haeyum.namingfox.api.NamingRepository
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                NamingRepository.getNaming(text, language, type)!!
            }.onSuccess {
                WriteCommandAction.runWriteCommandAction(project) {
                    document.replaceString(start, end, it)
                    PopupManager.hide()
                }
            }.onFailure {
                println(it)
                // TODO - announce
            }
        }
        primaryCaret.removeSelection()
    }
}