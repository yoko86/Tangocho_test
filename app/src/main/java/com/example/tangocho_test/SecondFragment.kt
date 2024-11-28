package com.example.tangocho_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tangocho_test.databinding.FragmentSecondBinding
import data.AppDatabase
import data.Word
import data.WordAdapter
import data.WordRepository
import data.WordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var adapter: WordAdapter
    private lateinit var viewModel: WordViewModel
    private val selectedWords = mutableSetOf<Word>() // 選択された単語のセット

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView の設定
        adapter = WordAdapter { word, isChecked ->
            if (isChecked) {
                selectedWords.add(word) // チェックされた単語をセットに追加
            } else {
                selectedWords.remove(word) // チェックが外れた単語をセットから削除
            }
        }
        binding.recyclerViewWords.adapter = adapter
        binding.recyclerViewWords.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel のセットアップ
        val database = AppDatabase.getDatabase(requireContext())
        val repository = WordRepository(database.wordDao())
        viewModel = WordViewModel(repository)

        // データの取得と表示
        // データの取得と表示
        updateWordList()

        // 削除ボタンの設定
        binding.deleteButton.setOnClickListener {
            deleteSelectedWords()
        }

        // アクションバーのタイトル設定
        (activity as? AppCompatActivity)?.supportActionBar?.title = "単語一覧"
    }

    private fun updateWordList() {
        CoroutineScope(Dispatchers.Main).launch {
            val words = viewModel.getAllWords()
            adapter.updateData(words)
        }
    }

    private fun deleteSelectedWords() {
        CoroutineScope(Dispatchers.IO).launch {
            selectedWords.forEach { word ->
                viewModel.deleteWord(word) // 削除処理を実行
            }
            selectedWords.clear() // 選択リストをクリア
            updateWordList() // 再取得してRecyclerViewを更新
        }
    }
}

