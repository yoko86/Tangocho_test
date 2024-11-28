package data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    fun insert(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    suspend fun getAllWords(): List<Word> {
        return repository.getAllWords()
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.deleteWordById(word.id)
        }
    }

    fun addWord(word: Word) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(word)
        }
    }
}