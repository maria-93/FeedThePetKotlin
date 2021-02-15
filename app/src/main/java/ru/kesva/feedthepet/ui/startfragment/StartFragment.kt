package ru.kesva.feedthepet.ui.startfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.databinding.FragmentStartBinding
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.di.modules.ClickHandlersProvideModule
import ru.kesva.feedthepet.di.subcomponents.StartComponent
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.MainActivity
import ru.kesva.feedthepet.ui.dialogfragment.DeletePetDialogFragment
import ru.kesva.feedthepet.ui.viewmodel.PetViewModel
import javax.inject.Inject

/**
 *
 */
class StartFragment : Fragment() {
    private lateinit var component: StartComponent
    private lateinit var navController: NavController
    private lateinit var binding: FragmentStartBinding


    @Inject
    lateinit var adapter: PetAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var viewModel: PetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        subscribeToEvents()
    }


    private fun subscribeToEvents() {
        with(viewModel) {
            allPetLiveData.observe(viewLifecycleOwner, Observer {
                adapter.petList = it as MutableList<Pet>

            })

            createNewPet.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_startFragment_to_addNewPetFragment)
                }
            })

            deletePet.observe(viewLifecycleOwner, Observer { pet ->
                pet.getContentIfNotHandled()?.let {
                    adapter.petList.remove(it)
                    adapter.notifyDataSetChanged()
                }
            })

            editButtonClicked.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_startFragment_to_addNewPetFragment)
                }
            })

            petFedButtonClicked.observe(viewLifecycleOwner, Observer {
            })

            alertDialogInitiated.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { _ ->
                    showAlertDialog()
                }
            })
        }
    }

    private fun showAlertDialog() {
        val dialogFragment = DeletePetDialogFragment()
        dialogFragment.show(childFragmentManager, "Show")
    }


    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as FeedThePetApplication).appComponent.startComponent()
                .create(
                    ClickHandlersProvideModule(requireActivity() as MainActivity)
                )
        component.provideDependenciesFor(this)
        viewModel = getViewModel(factory, requireActivity())
    }

    override fun onPause() {
        adapter.stopTimers()
        super.onPause()
    }

    override fun onDestroy() {
        adapter.stopTimers()
        super.onDestroy()

    }

    override fun onDestroyView() {
        adapter.stopTimers()
        super.onDestroyView()
    }


}
