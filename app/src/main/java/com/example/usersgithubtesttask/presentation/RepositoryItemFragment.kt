package com.example.usersgithubtesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.usersgithubtesttask.R
import com.example.usersgithubtesttask.databinding.FragmentRepositoryItemBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class RepositoryItemFragment : Fragment() {

    private var _binding: FragmentRepositoryItemBinding? = null
    private val binding: FragmentRepositoryItemBinding
        get() = _binding ?: throw RuntimeException("FragmentRepositoryItemBinding null")

    private val viewModel: RepositoryViewModel by viewModels()
    private val args by navArgs<RepositoryItemFragmentArgs>()

    private val compositeDisposable = CompositeDisposable()
    private val navController by lazy {
        (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoryItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        showRepositoryData(args.firstName, args.lastName)

    }

    private fun showRepositoryData(firstName: String, lastName: String) {
        compositeDisposable += viewModel.getRepository(firstName, lastName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userData ->
                    Log.d("userData", userData.toString())
                    viewModel._repositoryItem.value = userData
                    setImageUsers(viewModel.repositoryItem.value?.organization?.avatar_url ?: "")
                },
                //  onError = { e -> onlyShowErrorMsg(e.message!!) }
            )
    }

    private fun setImageUsers(avatarUrl: String) {
        Picasso.get()
            .load(avatarUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .error(R.drawable.baseline_airline_seat_legroom_normal_24)
            .into(binding.imageViewAvatar)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    private fun goFragmentBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.repositoryItemFragment, true)
        }
    }

    companion object {

    }
}