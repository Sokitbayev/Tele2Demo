package com.example.tele2demo.presentaion

import android.R.attr.bitmap
import android.app.Dialog
import android.content.Context.WINDOW_SERVICE
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.navArgs
import com.example.tele2demo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.WriterException
import net.glxn.qrgen.android.QRCode


class QrBottomSheetFragment : BottomSheetDialogFragment() {

    private val args: QrBottomSheetFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_bottom_sheet, container)
        view.findViewById<TextView>(R.id.close).setOnClickListener {
            dismiss()
        }
        val bitmap = QRCode.from(args.link).withSize(330, 330).bitmap()
        view.findViewById<ImageView>(R.id.qr_img).setImageBitmap(bitmap)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}