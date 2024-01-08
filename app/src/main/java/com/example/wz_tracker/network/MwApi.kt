package com.example.wz_tracker.network

import com.example.wz_tracker.models.LifetimeStats
import com.example.wz_tracker.models.MatchesStats
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MwApi {
    @Headers(
        "x-rapidapi-key: 4707b6485bmsh025db3a12baefbfp1e9fdfjsn53ad0ce245b4",
        "x-rapidapi-host: call-of-duty-modern-warfare.p.rapidapi.com"
    )
    @GET("warzone/{user}/acti")
    suspend fun fetchWzLifetimeStats(
        @Path("user", encoded = true)
        user: String
    ): LifetimeStats

    @Headers(
        "x-rapidapi-key: 4707b6485bmsh025db3a12baefbfp1e9fdfjsn53ad0ce245b4",
        "x-rapidapi-host: call-of-duty-modern-warfare.p.rapidapi.com"
    )
    @GET("warzone-matches/{user}/acti")
    suspend fun fetchWzMatches(
        @Path("user", encoded = true)
        user: String,
    ): MatchesStats
}