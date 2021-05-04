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
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(resources.getString(R.string.dialog_message))
            .setPositiveButton(resources.getString(R.string.positive_button_text)) { _: DialogInterface, _: Int ->
                viewModel.positiveButtonClick(pet!!)
            }
            .setNegativeButton(resources.getString(R.string.negative_button_text), null)

        return builder.create()
    }
}


