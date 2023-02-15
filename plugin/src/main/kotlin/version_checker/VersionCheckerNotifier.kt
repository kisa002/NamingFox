package version_checker
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.ProjectManager


object VersionCheckerNotifier {
    fun notifyNewestVersion(newestVersion: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Update Notification Group")
            .createNotification("A new version has been released: $newestVersion<br>You can update in 'Settings' - 'Plugins' - NamingFox", NotificationType.INFORMATION)
            .setTitle("NamingFox can update")
            .notify(ProjectManager.getInstance().defaultProject)
    }
}