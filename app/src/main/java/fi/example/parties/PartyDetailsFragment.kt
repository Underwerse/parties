package fi.example.parties

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import fi.example.parties.databinding.FragmentPartyDetailsBinding
import fi.example.parties.viewmodels.ParliamentMemberViewModel
import fi.example.parties.viewmodels.ParliamentMemberViewModelFactory
import fi.example.parties.viewmodels.PartyMemberViewModelScope

class PartyDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPartyDetailsBinding
    private lateinit var viewModel: ParliamentMemberViewModel
    private lateinit var viewModelFactory: ParliamentMemberViewModelFactory
    private lateinit var viewModelPartyMemberScope: PartyMemberViewModelScope
    private lateinit var selectedParty: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_party_details,container,false)

        Log.i("ParliamentMemberViewModel", "Called ViewModelProvider.get()")
        selectedParty = arguments?.getString("selectedParty") ?: ""

        binding.tvPartyTitle.text = "Selected party: " + selectedParty

        viewModelFactory = ParliamentMemberViewModelFactory(selectedParty)
        viewModel = ViewModelProvider(this,viewModelFactory)
            .get(ParliamentMemberViewModel::class.java)
        viewModelPartyMemberScope = ViewModelProvider(this).get(PartyMemberViewModelScope::class.java)

        viewModel.newMember().observe(viewLifecycleOwner) {
            val imgResName = "@drawable/" + selectedParty
            val imageID = resources.getIdentifier(imgResName, "drawable", activity?.getPackageName())
            binding.imgParty.setImageResource(imageID)
            binding.tvMemberName.text = it.last + ", " + it.first
            binding.tvMemberBirthYear.text = "Borned in " + it.bornYear.toString()
            binding.tvDistrict.text = "District: " + it.constituency
            binding.tvMemberTwitter.text = "Twitter: " +
                    if (it.twitter != "") it.twitter else "none"
        }

        binding.btnRandom.setOnClickListener { view : View ->
            viewModel.setMember()
        }

        binding.btnToBack.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_partyDetailsFragment_to_selectPartyFragment)
        }

        binding.btnToMain.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_partyDetailsFragment_to_titleFragment)
        }

        return binding.root
    }
}