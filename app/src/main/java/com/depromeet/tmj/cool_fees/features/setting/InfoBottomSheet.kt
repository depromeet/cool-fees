package com.depromeet.tmj.cool_fees.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.tmj.cool_fees.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_info, container, false)
    }

    companion object {
        const val TAG = "InfoBottomSheet"

        fun newInstance(): InfoBottomSheet {
            return InfoBottomSheet()
        }
    }
}