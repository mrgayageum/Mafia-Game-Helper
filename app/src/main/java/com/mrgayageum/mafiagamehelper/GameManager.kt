package com.mrgayageum.mafiagamehelper

object GameManager {

    class Player(val number: Int, val role: Int) {
        var alive = true
        var warns = 0
        var isAddedForVoting = false
    }

    const val actions = 6
    const val maxPlayers = 12

    private val mafiaNumber = mapOf(6 to 1, 7 to 1, 8 to 1, 9 to 2, 10 to 2, 11 to 3, 12 to 3)
    val roleRes = intArrayOf(R.drawable.role_civilian, R.drawable.role_sheriff,
        R.drawable.role_mafia, R.drawable.role_don)
    val warnRes = intArrayOf(R.drawable.warns_1, R.drawable.warns_2, R.drawable.warns_3)

    var playersNumber = 10
    var ghost = false

    var positions = Array(0) {""}
    var roles = IntArray(0)
    var players = Array(0) {Player(0, 0)}
    var voting = ArrayList<Int>()

    var started = false

    fun getRoles() {
        positions = Array(playersNumber) { i -> (i + 1).toString() }
        roles = IntArray(playersNumber)

        ghost = (playersNumber % 2 == 1)
        if (playersNumber > 6) {
            roles[playersNumber - 1] = 3
            roles[playersNumber - 2] = 1
        }
        for (i in 1..mafiaNumber[playersNumber]!!) {
            roles[i] = 2
        }

        positions.shuffle()
        roles.shuffle()
    }

    fun startGame() {
        players = Array(playersNumber) { i -> Player(i, roles[i])}
        started = true
    }

    fun endGame() {
        voting.clear()
        started = false
    }

    // В блоке ниже сделать добавление в логи

    fun kill(id: Int) {
        players[id].alive = false
    }

    fun checkSheriff(id: Int): Boolean {
        return players[id].role > 1
    }

    fun checkDon(id: Int): Boolean {
        return players[id].role == 1
    }

    fun add(id: Int) {
        if (!voting.contains(id)) {
            voting.add(id)
        }
    }
}