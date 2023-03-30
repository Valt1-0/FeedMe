package com.example.feedme.ui.components.favorite

sealed class EventTrigger {
    object SearchEvent : EventTrigger()

    object NextPageEvent : EventTrigger()

    data class GetRecipeEvent(
        val id: Int,
    ) : EventTrigger()

}