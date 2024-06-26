package com.example.usersgithubtesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.usersgithubtesttask.R
import com.example.usersgithubtesttask.data.ListItemData
import com.example.usersgithubtesttask.databinding.FragmentListUserBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.List
import javax.inject.Inject


@AndroidEntryPoint
class ListUserFragment : Fragment() {

    private var _binding: FragmentListUserBinding? = null
    private val binding: FragmentListUserBinding
        get() = _binding ?: throw RuntimeException("FragmentListUserBinding null")


    private val navController by lazy {
        (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }
    private val viewModel: UserViewModel by viewModels()

    //private val compositeDisposable = CompositeDisposable()
    private lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var adapterUsers: UsersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        //Toast.makeText(activity, "reload", Toast.LENGTH_SHORT).show()
        if(viewModel.userItemsList.value != null) {
            val userData = viewModel.userItemsList.value!!
            initRecyclerView(userData)
        } else {
            showUserData()
        }

        clickButtonRefresh()
        goFragmentBackPressed()
    }

    private fun clickButtonRefresh() {
        binding.buttonRefresh.setOnClickListener {
            showUserData()
        }
    }

    private fun showUserData() {
        compositeDisposable += viewModel.getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userDataList ->
                    viewModel._userItemsList.value = userDataList.toList()
                    initRecyclerView(userDataList.toList())
                },
                onError = { e -> Toast.makeText(activity, e.message!!, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun initRecyclerView(userDataList: kotlin.collections.List<ListItemData>) {
        with(binding.usersList) {
            adapter = adapterUsers
            recycledViewPool.setMaxRecycledViews(
                UsersListAdapter.VIEW_TYPE_ENABLED,
                UsersListAdapter.MAX_POOL_SIZE
            )
        }

        adapterUsers.submitList(userDataList.toList())
        setupClickListener()
    }

    private fun setupClickListener() {
        adapterUsers.onAutoItemClickListener = {
            //navController.popBackStack(R.id.listUserFragment, true)
            navController.navigate(ListUserFragmentDirections.actionListUserFragmentToUserItemFragment(it.login))
        }
    }
    private fun goFragmentBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAffinity()
        }
    }
    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }


}