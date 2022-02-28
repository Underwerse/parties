package fi.example.parties.data

import android.util.Log
import androidx.lifecycle.LiveData
import fi.example.parties.room.DB
import fi.example.parties.room.entities.PartyMember
import fi.example.parties.room.entities.PartyMemberDao

class MembersRepository(private val partyMemberDao: PartyMemberDao) {
    val allPartiesFromDb = partyMemberDao.getParties()
    
    suspend fun addMember(member: PartyMember) {
        partyMemberDao.addMember(member)
    }

    suspend fun deleteAll() {
        DB.getInstance()
            .partyMemberDao
            .deleteAll()
    }
    
    fun getPartyMembersByParty(selectedParty: String): LiveData<List<PartyMember>> {
        Log.d("LOG", "Repository: getPartyMemberByParty call")
        return partyMemberDao.getPartyMembersByParty(selectedParty)
    }
    
    fun getMemberByPersNumber(personNumber: Int): LiveData<PartyMember> {
        Log.d("LOG", "Repository: getMemberByPersNumber call")
        return partyMemberDao.getMemberByPersNumber(personNumber)
    }
}