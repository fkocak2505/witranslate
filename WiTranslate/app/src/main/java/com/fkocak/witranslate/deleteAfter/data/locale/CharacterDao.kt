package com.fkocak.witranslate.deleteAfter.data.locale

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fkocak.witranslate.deleteAfter.data.entites.CharacterData

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<CharacterData>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): LiveData<CharacterData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterData: List<CharacterData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterData: CharacterData)


}