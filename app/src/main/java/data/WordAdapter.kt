package data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tangocho_test.R
import com.example.tangocho_test.databinding.ItemWordBinding


class WordAdapter(
    private val onItemCheckedChange: (Word, Boolean) -> Unit // 選択状態の変更時に通知
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val wordList = mutableListOf<Word>()

    // 更新用関数
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newWords: List<Word>) {
        wordList.clear()
        wordList.addAll(newWords)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordList[position]
        holder.bind(word)
    }

    override fun getItemCount(): Int = wordList.size

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val wordTextView: TextView = itemView.findViewById(R.id.wordTextView)
        private val meaningTextView: TextView = itemView.findViewById(R.id.meaningTextView)

        fun bind(word: Word) {
            wordTextView.text = word.word
            meaningTextView.text = word.meaning

            // チェックボックスの状態変更をリスナーに通知
            checkBox.setOnCheckedChangeListener(null) // リスナーの再設定を防ぐ
            checkBox.isChecked = false
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onItemCheckedChange(word, isChecked)
            }
        }
    }
}


