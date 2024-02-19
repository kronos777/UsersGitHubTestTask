package com.example.usersgithubtesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.usersgithubtesttask.R
import com.example.usersgithubtesttask.databinding.FragmentListUserBinding
import com.example.usersgithubtesttask.databinding.FragmentUserItemBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class UserItemFragment : Fragment() {

    private var _binding: FragmentUserItemBinding? = null
    private val binding: FragmentUserItemBinding
        get() = _binding ?: throw RuntimeException("FragmentUserItemBinding null")

    private val viewModel: MainViewModel by viewModels()
    private val args by navArgs<UserItemFragmentArgs>()

    private val compositeDisposable = CompositeDisposable()



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

    }

    private fun showUserData(name: String) {
        compositeDisposable += viewModel.getUserData(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userData ->
                    Log.d("userData", userData.toString())
                    viewModel._userItem.value = userData
                    //initRecyclerView(userDataList)
                },
                //  onError = { e -> onlyShowErrorMsg(e.message!!) }
            )
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    companion object {

    }
}