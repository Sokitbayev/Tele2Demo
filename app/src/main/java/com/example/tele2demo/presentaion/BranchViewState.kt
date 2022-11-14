package com.example.tele2demo.presentaion

import com.example.tele2demo.domain.model.Branch
import com.example.tele2demo.domain.model.City

sealed class BranchViewState {
    data class Cities(val cities: List<City>) : BranchViewState()
    data class Branches(val branches: List<Branch>): BranchViewState()
    data class OnDataReady(val deviceId: String?, val branchId: String) : BranchViewState()
}