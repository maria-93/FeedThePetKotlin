package ru.kesva.feedthepet.ui.startfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.databinding.FragmentStartBinding
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.di.modules.ClickHandlersProvideModule
import ru.kesva.feedthepet.di.subcomponents.StartComponent
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.getFormattedTime
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
    private lateinit var fab: FloatingActionButton


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
    ): View {
        binding = FragmentStartBinding.inflate(inflater)
        binding.recyclerView.adapter = adapter
        fab = binding.fabButton
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //скроллим вверх
                if (dy < 0 && fab.visibility == View.VISIBLE) {
                    fab.hide()
                }
                //если в recyclerview так мало элементов, что не выполняется скроллинг,
                //делаем кнопку видимой
                else if (fab.visibility != View.VISIBLE) {
                    fab.show()
                }
                //скроллим вниз
                else if (dy > 0 && fab.visibility != View.VISIBLE) {
                    fab.show()
                }
            }

        })
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

            createNewPet.observe(viewLifecycleOwner, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_startFragment_to_addNewPetFragment)
                    val timers = adapter.timerMap.values
                    timers.forEach {
                        Log.d("Tick", "subscribeToEvents: timer stopped")
                        it.stop()
                    }
                    timers.clear()
                }
            })

            deletePet.observe(viewLifecycleOwner, Observer { pet ->
                pet.getContentIfNotHandled()?.let {
                    adapter.petList.remove(it)
                    adapter.notifyItemRemoved(adapter.petList.indexOf(it))
                }
            })

            editButtonClicked.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_startFragment_to_addNewPetFragment)
                }
            })

            petFedButtonClicked.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { _ ->
                    Log.d("Test!", "subscribeToEvents: start Alarm Toast")
                    startAlarmShowToast()
                }
            })

            cancelAlarmButtonClicked.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { pet ->
                    Log.d("Test!", "subscribeToEvents: cancel Alarm Toast")
                    adapter.stopTimerForPet(pet)
                    cancelAlarmShowToast()
                }
            })

            onOkButtonClicked.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { pet ->
                    adapter.stopTimerForPet(pet)
                }
            })

            alertDialogInitiated.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { _ ->
                    showAlertDialog()
                }
            })
        }
    }

    private fun startAlarmShowToast() {
        Toast.makeText(context, getString(R.string.toast_alarm_launched), Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarmShowToast() {
        Toast.makeText(context, getString(R.string.toast_alarm_stopped), Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        Log.d("Tick", "onResume: ")

    }

    override fun onPause() {
        Log.d("Tick", "onPause: startFr")
        adapter.stopTimers()
        super.onPause()
    }

    override fun onStop() {
        Log.d("Tick", "onStop: startFr")
        adapter.stopTimers()
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("Tick", "onDestroy: startFr")
        adapter.stopTimers()
        super.onDestroy()

    }

    override fun onDestroyView() {
        Log.d("Tick", "onDestroyView: startFr")
        adapter.stopTimers()
        super.onDestroyView()
    }


}
