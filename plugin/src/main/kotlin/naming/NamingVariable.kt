package naming;

import module.NamingModule
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NamingVariable: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        NamingModule.namingVariable(event)
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
    }
}
