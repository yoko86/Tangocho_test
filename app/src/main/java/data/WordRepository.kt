package data

class WordRepository(private val wordDao: WordDao) {
    suspend fun insert(word: Word) = wordDao.insert(word)
    suspend fun getAllWords() = wordDao.getAllWords()
    suspend fun deleteWordById(id: Int) {
        wordDao.deleteById(id)
    }
}