package com.example.mynotesapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase(){

        abstract fun notesDao() : notesDao

        //now adding a companion object that will help us add functions to the employee database class

        companion object{

            //this will keep a reference to any database returned by our get instance
            @Volatile
            private var INSTANCE: NotesDatabase? = null

            fun getInstance(context: Context): NotesDatabase{

                synchronized(this){
                    var instance = INSTANCE

                    if(instance == null ){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            NotesDatabase::class.java,
                            "notes_db"
                        ).fallbackToDestructiveMigration()
                            .build()

                        INSTANCE = instance

                    }
                    return instance
                }

            }

        }
    }
