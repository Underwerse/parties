package fi.example.parties.room.entities

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemberRatingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setRating(rating: MemberRating)
    
    @Query("select * from party_members_ratings_table")
    fun getAllRatings(): LiveData<List<MemberRating>>

    @Query("select rating from party_members_ratings_table where personNumber = :key")
    fun getRating(key: Int): LiveData<Int>
}