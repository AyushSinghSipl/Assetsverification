package com.mahyco.assetsverification.asset_verification.asset_detail.asset_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mahyco.assetsverification.HomeActivity
import com.mahyco.assetsverification.R
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.adapter.NotInUseReasonAdapter
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.adapter.NotInUseReasonModel
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.adapter.onCLick
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_status.model.SaveAssetStatusParam
import com.mahyco.assetsverification.asset_verification.asset_detail.asset_verified.AssetVerifiedFragment
import com.mahyco.assetsverification.asset_verification.asset_detail.viewmodel.HomeViewModel
import com.mahyco.assetsverification.core.SharedPreference
import com.mahyco.assetsverification.databinding.FragmentAssetStatusBinding
import com.mahyco.cmr_app.core.Constant
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AssetStatusFragment : Fragment(), onCLick {
    // TODO: Rename and change types of parameters
    private var assetQRId: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAssetStatusBinding
    var activity = getActivity() as HomeActivity?
    var menuList = ArrayList<NotInUseReasonModel>()
    lateinit var notInUseReasonAdapter : NotInUseReasonAdapter
    private val viewModel: HomeViewModel by viewModels()
    lateinit var status: String
    lateinit var reason: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            assetQRId = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssetStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root
        activity?.binding?.appBarHome?.titleToolbar?.text = "Asset Status"
registerObserver()
        setUi()
        return root
    }
    private fun registerObserver() {
        viewModel!!.loadingLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it) {
                binding.llProgressBar.loader.visibility = View.VISIBLE

            } else {
                binding.llProgressBar.loader.visibility = View.GONE

            }
        })

        //In Case of error will show error in  toast message
        viewModel!!.errorLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null)
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.SaveAssetStatusData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            var result = it

            if (result.status?.equals("Missmatch") != true){
                val gson = Gson()
                val json = gson.toJson(result)
                Constant.addFragmentToActivity(AssetVerifiedFragment.newInstance(json),R.id.container,this.parentFragmentManager,AssetVerifiedFragment.TAG)

            }

        })


    }

    private fun setUi() {

        val sd = SimpleDateFormat(
            "hh:mm aa" +
                    " - EEE,dd MMM"
        )
        val currentDate = sd.format(Date())
        binding.textViewTime.text = currentDate
        binding.chooseType.setOnClickListener {

            binding.typeCard.visibility = View.VISIBLE
        }

        binding.textViewInUse.setOnClickListener {
            binding.typeCard.visibility = View.GONE
            binding.typeSelection.text = "IN USE"
            binding.buttonVerify.visibility = View.VISIBLE
            binding.layoutNotInUse.visibility = View.GONE
            status= "IN USE"
        }


        binding.buttonVerify.setOnClickListener {

            if (status.equals("IN USE")){
                val sharedPreference = SharedPreference(requireContext())
                val emp_id = sharedPreference.getValueString(Constant.EMP_ID)
                val saveAssetStatusParam = SaveAssetStatusParam(
                    "IN USE",
                    emp_id,
                    assetQRId?.toInt(),
                   ""
                )

                viewModel.saveAssetStatus(saveAssetStatusParam)
            }else{
                if (reason != null) {
                    val sharedPreference = SharedPreference(requireContext())
                    val emp_id = sharedPreference.getValueString(Constant.EMP_ID)
                    val saveAssetStatusParam = SaveAssetStatusParam(
                        "NOT IN USE",
                        emp_id,
                        assetQRId?.toInt(),
                        reason
                    )

                    viewModel.saveAssetStatus(saveAssetStatusParam)
                }else{
                    Toast.makeText(requireContext(), "Please select a valid reason", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.textViewNotInUse.setOnClickListener {
            status= "NOT IN USE"
            binding.typeCard.visibility = View.GONE
            binding.typeSelection.text = "NOT IN USE"
            binding.buttonVerify.visibility = View.GONE
            binding.layoutNotInUse.visibility = View.VISIBLE
        }
        binding.chooseReason.setOnClickListener {
            binding.ReasonCard.visibility = View.VISIBLE
        }
        menuList.add(NotInUseReasonModel("HIGH REPAIRING COST",1))
        menuList.add(NotInUseReasonModel("NOT REPAIRABLE",2))
        menuList.add(NotInUseReasonModel("DAMAGED PRODUCTS",3))
        menuList.add(NotInUseReasonModel("NPA",4))

        notInUseReasonAdapter = NotInUseReasonAdapter(menuList,this)
        binding.recyclerViewReason.adapter = notInUseReasonAdapter
        binding.recyclerViewReason.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


    }

    companion object {

        val TAG = AssetStatusFragment::class.java.name

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            AssetStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                   // putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onReasonSelect(position: Int) {
        binding.ReasonCard.visibility = View.GONE
        binding.reasonSelection.text = menuList.get(position).name
        reason = menuList.get(position).name
        binding.buttonVerify.visibility = View.VISIBLE
    }
}