package version_checker

import Version
import api.VersionRepository
import com.intellij.ide.AppLifecycleListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.pow


class StartUpVersionCheckListener : AppLifecycleListener {
    override fun appStarted() {
        super.appStarted()

        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                VersionRepository.fetchVersion().version
            }.onSuccess { version ->
                if (version.toVersionInteger() > Version.client.toVersionInteger()) {
                    VersionCheckerNotifier.notifyNewestVersion(version)
                }
            }.onFailure {
                println("FAILURE: $it")
            }
        }
    }

    private fun String.toVersionInteger() = split(".").map(String::toInt).mapIndexed { index, n ->
        n * 10.0.pow(3 - index)
    }.sum().toInt()
}