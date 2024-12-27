package com.example.prac22

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Collections.shuffle

class EmojiViewModel : ViewModel() {
    private val emojis: MutableLiveData<MutableList<AndroidModel>> by lazy {
        MutableLiveData<MutableList<AndroidModel>>()
    }

    fun getEmojis(): LiveData<MutableList<AndroidModel>> {
        return emojis
    }

    fun loadEmojis() {
        emojis.value = mutableListOf(
            AndroidModel("😍"),
            AndroidModel("🥰"),
            AndroidModel("😘"),
            AndroidModel("😭"),
            AndroidModel("😢"),
            AndroidModel("😂"),
            AndroidModel("😍"),
            AndroidModel("🥰"),
            AndroidModel("😘"),
            AndroidModel("😭"),
            AndroidModel("😢"),
            AndroidModel("😂"),
        ).apply { shuffle() }
    }

    fun updateShowVisibleCard(id: String) {
        val selects: List<AndroidModel>? = emojis.value?.filter { it -> it.isSelect }
        val selectCount: Int = selects?.size ?: 0
        var charFind: String = "";
        if (selectCount >= 2) {
            val hasSameChar: Boolean = selects!!.get(0).char == selects.get(1).char
            if (hasSameChar) {
                charFind = selects.get(0).char
            }
        }

        val list: MutableList<AndroidModel>? = emojis.value?.map { it ->
            if (selectCount >= 2) {
                it.isSelect = false
            }

            if (it.char == charFind) {
                it.isVisible = false
            }

            if (it.id == id) {
                it.isSelect = true
            }

            it
        } as MutableList<AndroidModel>?

        val visibleCount: Int = list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            loadEmojis()
            return
        }

        emojis.value?.removeAll { true }
        emojis.value = list
    }

}