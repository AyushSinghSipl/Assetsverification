package com.mahyco.assetsverification.asset_verification.asset_detail.asset_verified

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.SaveStatusResponse
import com.mahyco.assetsverification.databinding.FragmentAssetVerifiedBinding
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AssetVerifiedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AssetVerifiedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var assetDetail: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAssetVerifiedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            assetDetail = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAssetVerifiedBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setUi()
        return root
    }

    private fun setUi() {
        val sd = SimpleDateFormat(
            "hh:mm aa" +
                    " - EEE,dd MMM"
        )
        val currentDate = sd.format(Date())
binding.textViewTime.text = currentDate
        val gson = Gson()


        val assetData: SaveStatusResponse = gson.fromJson(
            assetDetail,
            SaveStatusResponse::class.java
        )

        binding.textViewAssetQrId.text = assetData.assetQRId.toString()
        binding.textViewPlantCode.text = assetData.plantCode.toString()
        binding.textViewPlantName.text = assetData.plantName.toString()
        binding.textViewClassName.text = assetData.className.toString()
        binding.textViewClassCode.text = assetData.classCode.toString()
        binding.textViewQrCode.text = assetData.qRCode.toString()
        binding.textViewAssetCode.text = assetData.assetCode.toString()
        binding.textViewCapDate.text = assetData.capDt.toString()
        binding.textViewAssetDesc.text = assetData.assetDescription.toString()
        binding.textViewStatus.text = assetData.status.toString()

    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                // handle back button's click listener
                val fm: FragmentManager = this.parentFragmentManager
                for (i in 0 until fm.getBackStackEntryCount()) {
                    fm.popBackStack()
                }
                return@OnKeyListener true
            }
            false
        })
    }

    companion object {
        val TAG= AssetVerifiedFragment::class.java.name


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            AssetVerifiedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}