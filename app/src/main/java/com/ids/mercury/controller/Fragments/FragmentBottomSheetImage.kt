package com.ids.mercury.controller.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.R
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.utils.AppConstants
import com.ids.mercury.utils.loadImagesUrlResize
import kotlinx.android.synthetic.main.bottom_sheet_full_image.*


class FragmentBottomSheetNotification : BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(com.ids.mercury.R.layout.bottom_sheet_full_image, container, false)


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,com.ids.mercury.R.style.MyBottomSheetDialog)

    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog

        if (dialog != null) {
            val bottomSheet = dialog.findViewById(com.ids.mercury.R.id.design_bottom_sheet) as FrameLayout
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        val view = view
        view.let {

            it!!.post {
                val parent = requireView().parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
                bottomSheetBehavior!!.setPeekHeight(requireView().measuredHeight)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage()
    }



    private fun loadImage(){
        val options: RequestOptions = RequestOptions()
            .fitCenter()
            .placeholder(com.ids.mercury.R.drawable.rectangular_gray)
            .error(com.ids.mercury.R.drawable.rectangular_gray)
             Glide.with(this).load(MyApplication.selectedImage).apply(options)
            .into(ivMedia)
    }


}