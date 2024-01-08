package com.example.wz_tracker.models

data class LifetimeStats(
    val br_all: BrStats
)

data class BrStats(
    val deaths: Int,
    val downs: Int,
    val kills: Int,
    val wins: Int
)