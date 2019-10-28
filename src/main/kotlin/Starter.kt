import eva.Eva
import io.ktor.util.KtorExperimentalAPI
import main.Main

object Starter {
    @KtorExperimentalAPI
    fun run() {
        Eva.create().start()
        Main.create().start()
    }
}