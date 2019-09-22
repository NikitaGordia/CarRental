package main

import io.ktor.util.KtorExperimentalAPI

object ServerStarter {
    @KtorExperimentalAPI
    fun run() {
        RegularServerGenerator(Db.getInstance())
            .generateServer()
            .start(wait = true)
    }
}