package com.mahyco.assetsverification.asset_verification.asset_detail

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mahyco.assetsverification.MainActivity
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.ThanksFragment
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.AssetStatusFragment
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.*
import com.mahyco.assetsverification.asset_verification.asset_detail.viewmodel.HomeViewModel
import com.mahyco.assetsverification.core.Messageclass
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.assetsverification.databinding.FragmentAssetDetailsBinding
import com.mahyco.assetsverification.login.LoginActivity
import com.mahyco.cmr_app.core.Constant
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AssetDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AssetDetailsFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var filterValue: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAssetDetailsBinding
    var activity: MainActivity? = getActivity() as MainActivity?
    var msclass: Messageclass? = null
    private val viewModel: HomeViewModel by viewModels()
    lateinit var sharedPreference: SharedPreference

    var asseData = ScanQRResult()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterValue = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAssetDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        registerObserver()

        setUI()
        val scanQRParam = ScanQRParam(filterValue)
        msclass = Messageclass(context)
        sharedPreference = SharedPreference(requireContext())
        viewModel.getAssetDetailsApi(scanQRParam)
        return root
    }

    private fun registerObserver() {
        viewModel!!.loadingLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it) {
                binding.llProgressBar.loader.visibility = View.VISIBLE
                binding.buttonMismatchDetails.isEnabled = false
                binding.buttonConfirm.isEnabled = false
            } else {
                binding.llProgressBar.loader.visibility = View.GONE
                binding.buttonMismatchDetails.isEnabled = true
                binding.buttonConfirm.isEnabled = true
            }
        })

        //In Case of error will show error in  toast message
        viewModel!!.errorLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.QRdata.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.assetQRId != null) {
                var result = it
                asseData = result
                binding.textViewAssetQrId.text = result.assetQRId.toString()
                binding.textViewPlantCode.text = result.plantCode.toString()
                binding.textViewPlantName.text = result.plantName.toString()
                binding.textViewClassName.text = result.className.toString()
                binding.textViewClassCode.text = result.classCode.toString()
                binding.textViewQrCode.text = result.qRCode.toString()
                binding.textViewAssetCode.text = result.assetCode.toString()
                binding.textViewCapDate.text = result.capDt.toString()
                binding.textViewAssetDesc.text = result.assetDescription.toString()
                binding.textViewEndDate.text = result.endDt.toString()


                val emp_id = sharedPreference.getValueString(Constant.EMP_ID)
                val checkUserValidParam = CheckUserValidParam(emp_id, 0, 1)

                viewModel.checkUserValidData(checkUserValidParam)
            }else{
                binding.buttonMismatchDetails.isEnabled = false
                binding.buttonConfirm.isEnabled = false
                msclass?.showMessage("Error occurred in scanning asset")
            }

        })
        viewModel.checkUserValidData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            var result = it
            if (result.resultFlag == true) {
                binding.buttonMismatchDetails.isEnabled = true
                binding.buttonConfirm.isEnabled = true
            } else {
                binding.buttonMismatchDetails.isEnabled = false
                binding.buttonConfirm.isEnabled = false
                val alertDialog = AlertDialog.Builder(context).create()

                alertDialog.setTitle("Asset Verification")

                alertDialog.setMessage(result.comment)

                alertDialog.setButton("OK") { dialog, which ->
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    activity?.finish()
                        alertDialog.dismiss()
                }

                // Showing Alert Message

                // Showing Alert Message
                alertDialog.show()
            }
        })
        viewModel.SaveAssetStatusData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            var result = it

            if (result.status?.equals("Missmatch") == true) {

                Constant.addFragmentToActivity(
                    ThanksFragment(),
                    R.id.container, this.parentFragmentManager, ThanksFragment.TAG
                )
            }

        })
    }

    private fun setUI() {
        binding.buttonConfirm.setOnClickListener {
//activity?.addFragmentToActivity(AssetStatusFragment())
            Constant.addFragmentToActivity(
                AssetStatusFragment.newInstance(asseData.assetQRId.toString()),
                R.id.container, this.parentFragmentManager, AssetStatusFragment.TAG
            )
        }

        binding.buttonMismatchDetails.setOnClickListener {
            missMatchAlert()
        }


        val sd = SimpleDateFormat(
            "hh:mm aa" +
                    " - EEE,dd MMM"
        )
        val currentDate = sd.format(Date())
        binding.textViewTIme.text = currentDate
    }

    private fun missMatchAlert() {
        val dialog = Dialog(requireContext())
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog)


        val dialogButtonSubmit: TextView = dialog?.findViewById(R.id.btnSubmit) as TextView
        val dialogButtonCancel: TextView = dialog?.findViewById(R.id.btnCancel) as TextView
        val editTextReason: EditText = dialog?.findViewById(R.id.editTextReason) as EditText
        dialogButtonSubmit.setOnClickListener(View.OnClickListener {
            // dialog.dismiss()
            if (editTextReason.text.isNotEmpty()) {
                val emp_id = sharedPreference.getValueString(Constant.EMP_ID)
                val saveAssetStatusParam = SaveAssetStatusParam(
                    "Missmatch",
                    emp_id,
                    asseData.assetQRId?.toInt(),
                    editTextReason.text.toString()
                )

                viewModel.saveAssetStatus(saveAssetStatusParam)
            } else {
                Toast.makeText(requireContext(), "Please enter reason", Toast.LENGTH_SHORT).show()
            }
        })

        dialogButtonCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        dialog?.show()
    }

    companion object {

        val TAG = AssetDetailsFragment::class.java.name

        @JvmStatic
        fun newInstance(param1: String) =
            AssetDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }
}