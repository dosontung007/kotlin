// WITH_RUNTIME
// WITH_COROUTINES
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

class X {
    var res = ""
    suspend fun execute() {
        a()
        b()
    }

    private suspend fun a() {
        res += suspendThere("O")
        res += suspendThere("K")
    }

    private suspend fun b() {
        res += suspendThere("5")
        res += suspendThere("6")
    }
}

suspend fun suspendThere(v: String): String = suspendCoroutineOrReturn { x ->
    x.resume(v)
    SUSPENDED_MARKER
}

fun builder(c: suspend X.() -> Unit) {
    c.startCoroutine(X(), EmptyContinuation)
}

fun box(): String {
    var result = ""
    builder {
        execute()
        result = res
    }

    if (result != "OK56") return "fail 1: $result"

    return "OK"
}
