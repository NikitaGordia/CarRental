package main

import main.Db
import main.RegularServerGenerator

class ServerStarter {
    companion object {
        fun run() {
            val db = Db.getInstance()
            RegularServerGenerator(db)
                .generateServer()
                .start(wait = true)
        }
    }
}