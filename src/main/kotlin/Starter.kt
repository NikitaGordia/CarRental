import adam.Adam
import eva.Eva
import io.ktor.util.KtorExperimentalAPI
import main.Main

object Starter {
    @KtorExperimentalAPI
    fun run() {
        Adam.create().start()
        Eva.create().start()
        Main.create().start()
    }
}