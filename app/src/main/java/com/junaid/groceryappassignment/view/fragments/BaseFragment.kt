package com.junaid.groceryappassignment.view.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.junaid.groceryappassignment.utils.application.showSnackBar
import com.junaid.groceryappassignment.view.activity.BaseActivity
import com.junaid.groceryappassignment.viewModel.BaseViewModel


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {


    private val mProgressDialog by lazy { (requireActivity() as BaseActivity).mProgressDialog }
    protected lateinit var parentView: View



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentView = view
        setupViews()
        attachViewModel()
    }


    abstract fun setupViews()
    open fun attachViewModel() {}



    protected fun showProgressDialog(show: Boolean) {
        if (show) {
            if (!mProgressDialog.isShowing)
                mProgressDialog.apply {
                    show()
                }
        } else if (!show) {
            if (mProgressDialog.isShowing)
                mProgressDialog.dismiss()
        }
    }

    protected fun setupGeneralViewModel(generalViewModel: BaseViewModel) {
        with(generalViewModel)
        {
            progressBar.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    showProgressDialog(it)
                }
            }
        }
    }




}