package dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import javax.swing.SwingUtilities
import kotlin.coroutines.CoroutineContext

object AwtEventQueueDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        SwingUtilities.invokeLater(block)
    }
}