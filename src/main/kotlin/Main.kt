import io.ktor.server.engine.ApplicationEngine

fun main() {
    val serverGenerator: ServerGenerator = RegularServerGenerator(Db.getIntence())

    val server: ApplicationEngine = serverGenerator.generateServer()
    server.start(wait = true)
}