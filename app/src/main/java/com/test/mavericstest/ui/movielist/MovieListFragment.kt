package com.test.mavericstest.ui.movielist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.mavericstest.R
import com.test.mavericstest.common.Loading
import com.test.mavericstest.common.NetworkError
import com.test.mavericstest.common.Success
import com.test.mavericstest.common.ViewState
import com.test.mavericstest.data.models.MovieResponse
import com.test.mavericstest.data.models.Search
import com.test.mavericstest.ui.movielist.adapter.MyMovieDataRecyclerViewAdapter
import com.test.mavericstest.ui.movielist.viewmodel.MovieFetcherViewModel
import com.test.mavericstest.util.CustomProgressDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.enter_movie_name.view.*
import kotlinx.android.synthetic.main.fragment_movie_list_item.*
import javax.inject.Inject


/**
 * A fragment representing a list of Items.
 */
class MovieListFragment : Fragment(), MyMovieDataRecyclerViewAdapter.onMovieClicked {

    private var columnCount = 2
    private var page = 1
    private var mSearchTerm = "Marvel"
    private lateinit var viewModel: MovieFetcherViewModel
    private val progressDialog = CustomProgressDialog()
    private var mSavedResponse: ArrayList<Search> = arrayListOf<Search>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mAdapter: MyMovieDataRecyclerViewAdapter

    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
    }

    private fun initListener() {
        mAdapter = MyMovieDataRecyclerViewAdapter(this)
        with(movieList) {
            layoutManager = GridLayoutManager(context, columnCount)
            adapter = mAdapter
        }
        movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mSavedResponse.size - 1) {
                        //bottom of list!
                        ++page
                        viewModel.getMovieList().removeObservers(viewLifecycleOwner)
                        checkMovies()
                        isLoading = true
                    }
                }
            }
        })
        searchMovie.setOnClickListener {
            showSearchMovie()
        }
        checkMovies()
    }

    private fun checkMovies() {
        viewModel.fetchMovies(mSearchTerm, page)
        viewModel.getMovieList().observe(viewLifecycleOwner, Observer { onMovieList(it) })
    }

    private fun onMovieList(viewState: ViewState<MovieResponse>?) {
        when (viewState) {
            is Loading -> {
                setProgress(true)
            }
            is NetworkError -> {
                setProgress(false)
                showError(viewState.message!!)
            }
            is Success -> {
                setProgress(false)
                onMoviesListReceived(viewState.mData)
            }
        }

    }

    private fun onMoviesListReceived(mData: MovieResponse) {
        if (mData != null) {
            if (mData.search.isNotEmpty()) {
                if (mSavedResponse.isEmpty()) {
                    mSavedResponse.addAll(mData.search)
                } else {
                    if (!mSavedResponse.containsAll(mData.search)) {
                        mSavedResponse.addAll(mData.search)
                    }
                }
                mAdapter.setRefreshData(mSavedResponse, requireContext())
                isLoading = false
            } else {
                Toast.makeText(requireContext(), getString(R.string.noMovie), Toast.LENGTH_LONG)
                    .show()
            }
        }
        setProgress(false)
    }

    private fun setProgress(isLoading: Boolean) {
        if (isLoading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showError(message: String) {
        if (isAdded)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        if (isAdded) {
            progressDialog.show(requireContext(), getString(R.string.loading))
        }
    }

    private fun hideProgress() {
        if (isAdded)
            progressDialog.dialog.dismiss()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MovieFetcherViewModel::class.java)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private lateinit var mDialogView: View

    private fun showSearchMovie() {
        mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.enter_movie_name, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()

        //submit button click of custom layout
        mDialogView.submitSearch.setOnClickListener {
            mSearchTerm = mDialogView.movieName.text.toString()
            mAlertDialog.dismiss()
            viewModel.getMovieList().removeObservers(viewLifecycleOwner)
            checkMovies()
        }
        //cancel button click of custom layout
        mDialogView.crossPaymentDialog.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
        // Change the alert dialog background color to transparent
        mAlertDialog.window?.setBackgroundDrawable(null)
    }

    override fun onAvatarClicked(mMovieItem: Search) {
        viewModel.getSelectedMovie().postValue(mMovieItem)
    }

}