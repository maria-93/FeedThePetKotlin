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
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.MainActivity
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

       /* if (pet.isRunning) {
            timer.start()
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        with(viewModel) {
            allPetLive.observe(viewLifecycleOwner, Observer {
                adapter.petList = it

            })

            createNewPet.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_startFragment_to_addNewPetFragment)
                }
            })

            editButtonClicked.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_startFragment_to_addNewPetFragment)
                }
            })

            petFedButtonClicked.observe(viewLifecycleOwner, Observer {
            })
        }
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
        super.onPause()
        adapter.stopTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopTimers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.stopTimers()
    }



}
