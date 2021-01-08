package com.example.englishword3

import androidx.lifecycle.ViewModel
import java.util.*

class AddWord:ViewModel() {
    var Japaneseword: String = ""
    var Englishword: String = ""
    var wordId: String = "${System.currentTimeMillis()}"
    var createdAt: Date = Date()
}