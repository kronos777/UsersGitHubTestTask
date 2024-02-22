package com.example.usersgithubtesttask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.usersgithubtesttask.R
import com.example.usersgithubtesttask.data.RepositoryData
import com.example.usersgithubtesttask.databinding.FragmentListOrgRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@AndroidEntryPoint
class ListOrgRepositoryFragment : Fragment() {

    private var _binding: FragmentListOrgRepositoryBinding? = null
    private val binding: FragmentListOrgRepositoryBinding
        get() = _binding ?: throw RuntimeException("FragmentListOrgRepositoryBinding null")


    private val navController by lazy {
        (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }
    private val viewModel: RepositoryViewModel by viewModels()

    //private val compositeDisposable = CompositeDisposable()
    private lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var adapterRepo: RepositoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOrgRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        //Toast.makeText(activity, "reload", Toast.LENGTH_SHORT).show()
        //Toast.makeText(activity, viewModel._repositoryItemList.value.toString(), Toast.LENGTH_SHORT).show()
        if (viewModel._repositoryItemList.value != null) {
            val repoData = viewModel._repositoryItemList.value!!
          initRecyclerView(repoData)
        }

        clickButtonSendQuery()
        goFragmentBackPressed()
    }

    private fun clickButtonSendQuery() {
        val orgName = binding.etTitle.text.toString()
        binding.buttonSendQuery.setOnClickListener {

            showOrgRepositoryData("")
        // showUserData()
        }
    }

    private fun showOrgRepositoryData(orgName: String?) {
        var queryString = ""
        if (orgName == "" || orgName == null) {
            queryString = "netguru"
        } else {
            queryString = orgName
        }
        compositeDisposable += viewModel.getRepositoryOrg(queryString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userDataList ->
                    viewModel._repositoryItemList.value = userDataList.toList()
                    //Log.d("netData", userDataList.toString())
                    initRecyclerView(userDataList.toList())
                },
                onError = { e -> Toast.makeText(activity, e.message!!, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun initRecyclerView(userDataList: kotlin.collections.List<RepositoryData>) {
        with(binding.repositoryList) {
            adapter = adapterRepo
            recycledViewPool.setMaxRecycledViews(
                UsersListAdapter.VIEW_TYPE_ENABLED,
                UsersListAdapter.MAX_POOL_SIZE
            )
        }

        adapterRepo.submitList(userDataList.toList())
        setupClickListener()
    }

    private fun setupClickListener() {
        adapterRepo.onAutoItemClickListener = {
               // navController.popBackStack()
                navController.navigate(ListOrgRepositoryFragmentDirections.actionListOrgRepositoryFragmentToRepositoryItemFragment(it.owner.login, it.name))
            }
    }


    private fun goFragmentBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.listOrgRepositoryFragment, true)
            navController.navigate(R.id.listUserFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }


}