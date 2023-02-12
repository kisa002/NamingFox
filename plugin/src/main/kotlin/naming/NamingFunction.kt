package naming;

import module.NamingModule
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NamingFunction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        NamingModule.namingFunction(event)
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
    }
}
