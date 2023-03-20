package com.example.feedme.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE recipes_favorite (id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER NOT NULL)"
        )
    }
}