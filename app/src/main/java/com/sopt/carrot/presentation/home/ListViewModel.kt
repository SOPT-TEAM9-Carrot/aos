package com.sopt.carrot.presentation.home

import androidx.lifecycle.ViewModel
import com.sopt.carrot.R

class ListViewModel : ViewModel() {
    val mockListLists = listOf(
        Card(
            id = 1,
            image = R.drawable.img_test_1,
            title = "당근 마켓",
            salary = "시급 10,000원"
        ),
        Card(
            id = 2,
            image = R.drawable.img_test_2,
            title = "당근 알바",
            salary = "시급 20,000원"

        ),
        Card(
            id = 3,
            image = R.drawable.img_test_1,
            title = "당근 마켓",
            salary = "시급 10,000원"
        )
    )

}