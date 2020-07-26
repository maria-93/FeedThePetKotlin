package ru.kesva.feedthepet.ui.startfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.di.modules.ClickHandlersProvideModule
import ru.kesva.feedthepet.di.subcomponents.StartComponent
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.viewmodel.PetDataViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class StartFragment : Fragment() {
    private lateinit var component: StartComponent
    private lateinit var navController: NavController
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var adapter: PetDataAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var viewModel: PetDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        navController = NavHostFragment.findNavController(this)
        fab = view.findViewById(R.id.fab_button)
        recyclerView = view.findViewById(R.id.rv_pet_data)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        val listener: RecyclerView.OnChildAttachStateChangeListener =
            object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {
                    val petData = view.getTag(R.id.petdata_key) as PetData
                    viewModel.startTimer(petData)
                }

                override fun onChildViewDetachedFromWindow(view: View) {
                    viewModel.stopTimer()
                }


            }
        recyclerView.addOnChildAttachStateChangeListener(listener)
        subscribeToEvents()
        fab.setOnClickListener {
            viewModel.createPet()
        }

    }

    private fun subscribeToEvents() {
        with(viewModel) {
            allPetLiveData.observe(viewLifecycleOwner, Observer {
                adapter.petDataList = it
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
                val textView: TextView = requireView().findViewById(R.id.calendarTime)
                textView.visibility = View.VISIBLE
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopCountDownTimer()
    }

    private fun injectDependencies() {
        component =
            (requireContext().applicationContext as FeedThePetApplication).appComponent.startComponent()
                .create(
                    ClickHandlersProvideModule(this)
                )
        component.provideDependenciesFor(this)

        viewModel = getViewModel(factory)
    }

}
