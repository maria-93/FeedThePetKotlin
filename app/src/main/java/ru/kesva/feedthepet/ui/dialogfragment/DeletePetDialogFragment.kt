package ru.kesva.feedthepet.ui.dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.viewmodel.PetViewModel

class DeletePetDialogFragment : AppCompatDialogFragment() {

    lateinit var viewModel: PetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(requireActivity())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val pet = viewModel.alertDialogInitiated.value?.peekContent()
        val builder = AlertDialog.Builder(activity, R.style.AlertDialogCustom)
        builder
            .setTitle("Внимание!")
            .setMessage("Удалить питомца?")
            .setPositiveButton("Да") { _: DialogInterface, _: Int ->
                viewModel.positiveButtonClick(pet!!)
            }
            .setNegativeButton("Нет", null)

        return builder.create()
    }
}


