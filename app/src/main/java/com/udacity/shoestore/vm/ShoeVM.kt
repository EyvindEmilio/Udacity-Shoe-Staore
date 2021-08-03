package com.udacity.shoestore.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.shoestore.models.Shoe

class ShoeVM(application: Application) : AndroidViewModel(application) {

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList


    init {
        val shoesList = mutableListOf<Shoe>()
//        for (index in 0..50) {
//            shoesList.add(
//                Shoe("Name $index", 43.543, "Company $index", "Description $index", listOf())
//            )
//        }
        _shoeList.value = shoesList
    }

    fun addShoe(shoe: Shoe) {
        _shoeList.value?.add(shoe)
    }
}