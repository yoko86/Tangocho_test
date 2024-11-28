package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table") // テーブル名を指定
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 自動生成されるID
    val word: String,       // 単語
    val meaning: String     // 意味
)