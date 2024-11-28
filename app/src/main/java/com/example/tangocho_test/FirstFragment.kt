package com.example.tangocho_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tangocho_test.databinding.FragmentFirstBinding
import data.AppDatabase
import data.Word
import data.WordRepository
import data.WordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSplitText.setOnClickListener {
            val inputText = binding.editTextLongText.text.toString()
            if (inputText.isNotEmpty()) {
                // 長文を単語に分解
                val words = inputText.split("\\s+".toRegex()).map { it.trim() }

                // ViewModel を使用してデータベースに単語を追加
                val database = AppDatabase.getDatabase(requireContext())
                val repository = WordRepository(database.wordDao())
                val viewModel = WordViewModel(repository)

                CoroutineScope(Dispatchers.IO).launch {
                    words.forEach { word ->
                        if (word.isNotEmpty()) {
                            val newWord = Word(word = word, meaning = "") // 意味は空にしておく
                            viewModel.addWord(newWord)
                        }
                    }
                }

                // SecondFragment に遷移
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = "分解画面"
    }
}
