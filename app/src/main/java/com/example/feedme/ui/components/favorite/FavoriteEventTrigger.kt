package com.example.feedme.ui.components.favorite

sealed class FavoriteEventTrigger {
    object  SearchEvent : FavoriteEventTrigger()

    object NextPageEvent : FavoriteEventTrigger()

}