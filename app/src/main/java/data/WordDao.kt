package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface WordDao {
    @Insert
    suspend fun insert(word: Word) // データを挿入

    @Query("SELECT * FROM word_table")
    suspend fun getAllWords(): List<Word> // すべてのデータを取得



    @Query("DELETE FROM word_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}