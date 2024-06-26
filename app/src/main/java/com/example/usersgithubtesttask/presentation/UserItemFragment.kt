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
import com.example.usersgithubtesttask.databinding.FragmentUserItemBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class UserItemFragment : Fragment() {

    private var _binding: FragmentUserItemBinding? = null
    private val binding: FragmentUserItemBinding
        get() = _binding ?: throw RuntimeException("FragmentUserItemBinding null")

    private val viewModel: UserViewModel by viewModels()
    private val args by navArgs<UserItemFragmentArgs>()

    private val compositeDisposable = CompositeDisposable()
    private val navController by lazy {
        (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        showUserData(args.name)
        //goFragmentBackPressed()
       // navController.popBackStack(R.id.userItemFragment, true)
    }

    private fun showUserData(name: String) {
        compositeDisposable += viewModel.getUserData(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userData ->
                    Log.d("userData", userData.toString())
                    viewModel._userItem.value = userData
                    setImageUsers(viewModel.userItem.value?.avatar_url.toString())
                    setEmailUsers(viewModel.userItem.value?.email.toString())
                    //initRecyclerView(userDataList)
                },
                //  onError = { e -> onlyShowErrorMsg(e.message!!) }
            )
    }

    private fun setEmailUsers(email: String) {
        binding.email.text = email
    }

    private fun setImageUsers(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .error(R.drawable.baseline_airline_seat_legroom_normal_24)
            .into(binding.imageViewAvatar)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }




    companion object {

    }
}