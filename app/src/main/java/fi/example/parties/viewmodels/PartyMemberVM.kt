package fi.example.parties.viewmodels

import android.app.Application
import androidx.lifecycle.*
import fi.example.parties.room.DB
import fi.example.parties.room.entities.PartyMember
import fi.example.parties.room.repositories.PartyMemberRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PartyMemberVM(application: Application) : AndroidViewModel(application) {
    
    private val getAllMembers: LiveData<List<PartyMember>>
    private val repository: PartyMemberRepository
    
    init {
        val partyMemberDao = DB.getInstance(application).partyMemberDao
        repository = PartyMemberRepository(partyMemberDao)
        getAllMembers = repository.getAllMembers
    }
    
    fun addMember(member: PartyMember) {
        GlobalScope.launch {
            repository.addMember(member)
        }
    }
    
    fun deleteAll() {
        GlobalScope.launch {
            repository.deleteAll()
        }
    }
}