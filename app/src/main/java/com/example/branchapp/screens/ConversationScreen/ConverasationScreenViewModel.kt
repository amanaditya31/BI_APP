package com.example.branchapp.screens.ConversationScreen

import androidx.lifecycle.ViewModel
import com.example.branchapp.repository.BranchAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConverasationScreenViewModel @Inject constructor(private val repository: BranchAppRepository)
    : ViewModel() {


}