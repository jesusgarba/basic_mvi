package com.schugarba.basicmvi.model

data class Todo(
    val userId: String = "",
    val title: String = "",
    val completed: Boolean = false
)

/*
* userId: 1,
id: 2,
title: "quis ut nam facilis et officia qui",
completed: false
*
* */
