package ru.kesva.feedthepet.ui.addnewpetfragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.kesva.feedthepet.*
import ru.kesva.feedthepet.data.model.Buffer
import ru.kesva.feedthepet.databinding.FragmentAddNewPetBinding
import ru.kesva.feedthepet.di.modules.ClickHandlersProvideModule
import ru.kesva.feedthepet.di.subcomponents.AddNewPetComponent
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.ui.MainActivity
import javax.inject.Inject


/**
 *
 *
 */
const val PICK_IMAGE = 1

class AddNewPetFragment : Fragment() {
    private lateinit var binding: FragmentAddNewPetBinding
    private lateinit var component: AddNewPetComponent
    private lateinit var petName: EditText
    private lateinit var petImage: ImageView
    private lateinit var okButton: Button

    @Inject
    lateinit var clickHandler: PetCreationClickHandler
    private lateinit var pickerForMinutes: NumberPicker
    private lateinit var pickerForDays: NumberPicker
    private lateinit var pickerForHours: NumberPicker
    private lateinit var buffer: Buffer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewPetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        binding.clickHandler = clickHandler
        buffer = clickHandler.getBuffer()
        binding.pet = buffer.pet


        petName.setOnKeyListener(View.OnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                hideKeyBoard(requireActivity())
                petName.clearFocus()
            }
            return@OnKeyListener false
        })

        okButton.setOnClickListener {
            val name = petName.text.toString().trim()
            if (name.isEmpty()) {
                showToastPetNameEmpty()
            } else {
                val daysInPicker = pickerForDays.value.toLong()
                val hoursInPicker = pickerForHours.value.toLong()
                val minutesInPicker = pickerForMinutes.value.toLong()

                val daysInMs = daysToMs(daysInPicker)
                val hoursInMs = hoursToMs(hoursInPicker)
                val minutesInMs = minutesToMs(minutesInPicker)

                if (daysInPicker == 0L && hoursInPicker == 0L && minutesInPicker == 0L) {
                    showToastTimeMustBeAboveZero()
                } else {
                    buffer.pet.petName = name
                    val time = getTotalTime(daysInMs, hoursInMs, minutesInMs)

                    buffer.pet.timeInterval = time
                    clickHandler.onOkButtonClicked()
                    val navController =
                        NavHostFragment.findNavController(this)
                    navController.popBackStack()
                }


            }
        }

        petImage.setOnClickListener {
            openGallery()
        }

        setRangeForPicker(pickerForDays, pickerForHours, pickerForMinutes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
            && data != null && data.data != null
        ) {
            val imageUri = data.data
            Glide.with(this)
                .load(imageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(petImage)
            buffer.pet.petImageURI = imageUri.toString()
        }
    }

    private fun setRangeForPicker(
        pickerForDays: NumberPicker,
        pickerForHours: NumberPicker,
        pickerForMinutes: NumberPicker
    ) {
        pickerForDays.maxValue = 100
        pickerForDays.minValue = 0
        pickerForHours.maxValue = 23
        pickerForHours.minValue = 0
        pickerForMinutes.maxValue = 59
        pickerForMinutes.minValue = 0
    }


    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE)
    }

    private fun setUpBinding() {
        petName = binding.etChoosePetName
        petImage = binding.petImage
        okButton = binding.buttonOk
        pickerForDays = binding.pickerDays
        pickerForHours = binding.pickerHours
        pickerForMinutes = binding.pickerMinutes
    }

    private fun showToastTimeMustBeAboveZero() {
        Toast.makeText(
            requireContext(),
            getString(R.string.toast_time_must_be_above_zero),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun showToastPetNameEmpty() {
        Toast.makeText(
            requireContext(),
            getString(R.string.toast_name_is_empty),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun injectDependency() {
        component =
            (requireContext().applicationContext as FeedThePetApplication).appComponent.addNewPetComponent()
                .create(
                    ClickHandlersProvideModule(requireActivity() as MainActivity)
                )
        component.provideDependenciesFor(this)
    }

    private fun getTotalTime(
        daysInMs: Long,
        hoursInMs: Long,
        minutesInMs: Long
    ): Long {
        return daysInMs + hoursInMs + minutesInMs
    }
}

interface PetCreationClickHandler {
    fun onOkButtonClicked()
    fun getBuffer(): Buffer
}
