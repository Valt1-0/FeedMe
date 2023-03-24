package com.example.feedme.ui.components.favorite

sealed class EventTrigger {
    object  SearchEvent : EventTrigger()

    object NextPageEvent : EventTrigger()

}