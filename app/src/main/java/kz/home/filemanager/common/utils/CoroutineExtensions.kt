package kz.home.filemanager.common.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchSafe(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    body: suspend CoroutineScope.() -> Unit,
    handleError: (Throwable) -> Unit,
    start: (() -> Unit)? = null,
    finish: (() -> Unit)? = null
): Job =
    launch(context = coroutineContext) {
        try {
            start?.invoke()
            body()
        } catch (e: Throwable) {
            handleError(e)
        } finally {
            finish?.invoke()
        }
    }